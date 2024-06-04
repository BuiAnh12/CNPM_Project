package Admin.Controller;

import Admin.Model.Event.EventModelDashboard;
import Admin.Model.Event.ReportModel;

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

    public List<String> getStudentNamesByEvent(String eventName) {
        List<String> studentNames = new ArrayList<>();
        try {
            // Load JDBC driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            // Connect to database
            Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);

            // Get EventId by Event Name
            CallableStatement getEventIdStmt = connection.prepareCall("{call GetEventIdByName(?)}");
            getEventIdStmt.setString(1, eventName);
            ResultSet eventIdResultSet = getEventIdStmt.executeQuery();

            if (eventIdResultSet.next()) {
                int eventId = eventIdResultSet.getInt("EventId");

                // Get StudentIds by EventId
                CallableStatement getStudentIdsStmt = connection.prepareCall("{call GetStudentIdsByEventId(?)}");
                getStudentIdsStmt.setInt(1, eventId);
                ResultSet studentIdsResultSet = getStudentIdsStmt.executeQuery();

                // Get Student full names by StudentIds
                while (studentIdsResultSet.next()) {
                    String studentId = studentIdsResultSet.getString("StudentId");

                    CallableStatement getStudentNameStmt = connection.prepareCall("{call GetStudentNameById(?)}");
                    getStudentNameStmt.setString(1, studentId);

                    ResultSet studentNameResultSet = getStudentNameStmt.executeQuery();

                    if (studentNameResultSet.next()) {
                        String fullName = studentNameResultSet.getString("FullName");
                        studentNames.add(fullName);
                    }
                    getStudentNameStmt.close();
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
        return studentNames;
    }
}
