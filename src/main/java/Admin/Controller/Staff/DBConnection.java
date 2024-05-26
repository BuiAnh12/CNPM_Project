package Admin.Controller.Staff;

import java.sql.*;

public class DBConnection {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private int ok;
    public Connection getConnection() {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=EventManagement;encrypt=true;trustServerCertificate=true";
        String user = "cnpm";
        String password = "123";
        try {
//            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
    public void initPrepar(String sql) {
        try{
            getConnection();
            preparedStatement = connection.prepareStatement(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ResultSet executeSelect() {
        resultSet = null;
        try {
            resultSet  = preparedStatement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }
    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }
}
