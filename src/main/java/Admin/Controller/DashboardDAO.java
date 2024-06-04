package Admin.Controller;

import Admin.Model.Event.EventModelDashboard;
import Admin.Model.Event.ReportModel;
import Admin.Model.Student.StudentModel;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DashboardDAO {
    private String jdbcUrl;
    private String dbUsername;
    private String dbPassword;

    public DashboardDAO() {
        this.jdbcUrl = "jdbc:sqlserver://localhost:1433;databaseName=EventManagement;encrypt=true;trustServerCertificate=true";
        this.dbUsername = "cnpm";
        this.dbPassword = "123";
    }

    public List<EventModelDashboard> getNextEvents() {
        List<EventModelDashboard> eventList = new ArrayList<>();
        try {
            // Load JDBC driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            // Connect to database
            Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);
            // Prepare statement to call stored procedure
            CallableStatement callableStatement = connection.prepareCall("{call GetNextEvents}");
            // Execute query
            ResultSet resultSet = callableStatement.executeQuery();
            // Populate eventList with results
            while (resultSet.next()) {
                String name = resultSet.getString("Name");
                LocalDate occurDate = resultSet.getDate("OccurDate").toLocalDate();
                eventList.add(new EventModelDashboard(name, occurDate));
            }
            // Close connections
            resultSet.close();
            callableStatement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return eventList;
    }

    public ReportModel getTotals() {
        ReportModel reportModel = null;
        try {
            // Connect to database
            Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);
            // Prepare statement to call stored procedure
            CallableStatement callableStatement = connection.prepareCall("{call GetReportData}");
            // Execute query
            ResultSet resultSet = callableStatement.executeQuery();
            if (resultSet.next()) {
                int totalEvent = resultSet.getInt("TotalEvent");
                int totalStudent = resultSet.getInt("TotalStudent");
                reportModel = new ReportModel(totalEvent, totalStudent);
            }
            // Close connections
            resultSet.close();
            callableStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reportModel;
    }

    public List<StudentModel> getStudentInfoByEvent(String eventName) {
        List<StudentModel> studentModels= new ArrayList<>();
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);

            CallableStatement getEventIdStmt = connection.prepareCall("{call GetEventIdByName(?)}");
            getEventIdStmt.setString(1, eventName);
            ResultSet eventIdResultSet = getEventIdStmt.executeQuery();

            if (eventIdResultSet.next()) {
                int eventId = eventIdResultSet.getInt("EventId");

                CallableStatement getStudentIdsStmt = connection.prepareCall("{call GetStudentIdsByEventId(?)}");
                getStudentIdsStmt.setInt(1, eventId);
                ResultSet studentIdsResultSet = getStudentIdsStmt.executeQuery();

                while (studentIdsResultSet.next()) {
                    String studentId = studentIdsResultSet.getString("StudentId");

                    CallableStatement getStudentInfoStmt = connection.prepareCall("{call GetStudentInfoById(?)}");
                    getStudentInfoStmt.setString(1, studentId);

                    ResultSet studentInfoResultSet = getStudentInfoStmt.executeQuery();

                    if (studentInfoResultSet.next()) {
                        String fullName = studentInfoResultSet.getString("FullName");
                        String classId = studentInfoResultSet.getString("ClassId");
                        studentModels.add(new StudentModel(fullName, classId));
                    }
                    getStudentInfoStmt.close();
                }
                studentIdsResultSet.close();
                getStudentIdsStmt.close();
            }
            eventIdResultSet.close();
            getEventIdStmt.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return studentModels;
    }
}
