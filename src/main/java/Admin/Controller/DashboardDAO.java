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
            // Prepare statement
            PreparedStatement preparedStatement = connection.prepareStatement("EXECUTE dbo.GetNextEvents");
            // Execute query
            ResultSet resultSet = preparedStatement.executeQuery();
            // Populate eventList with results
            while (resultSet.next()) {
                String name = resultSet.getString("Name");
                LocalDate occurDate = resultSet.getDate("OccurDate").toLocalDate();
                eventList.add(new EventModelDashboard(name, occurDate));
            }
            // Close connections
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return eventList;
    }


    public ReportModel getTotals() {
        ReportModel reportModel = null;

        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);
            PreparedStatement preparedStatement = connection.prepareStatement("EXECUTE dbo.GetReportData");
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int totalEvent = resultSet.getInt("TotalEvent");
                int totalStudent = resultSet.getInt("TotalStudent");
                reportModel = new ReportModel(totalEvent, totalStudent);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reportModel;
    }

}
