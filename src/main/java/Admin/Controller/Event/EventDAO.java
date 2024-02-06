package Admin.Controller.Event;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Admin.Model.Event.EventModel;
import Admin.Model.Event.StudentEventModel;

public class EventDAO {
    private String jdbcUrl;
    private String dbUsername;
    private String dbPassword;
    private List<EventModel> tableList;
    public EventDAO() {
        // Mặc định, bạn có thể thiết lập các giá trị kết nối ở đây.
        this.jdbcUrl = "jdbc:sqlserver://localhost:1433;databaseName=EventManagement;encrypt=true;trustServerCertificate=true";
        this.dbUsername = "cnpm";
        this.dbPassword = "123";
        tableList = new ArrayList<>();
    }

    public ArrayList<EventModel> getTable(int filterCategory, int filterSort, String searchWords) {
        ArrayList<EventModel> tableList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword)) {
            try (CallableStatement cs = connection.prepareCall("{call sp_GetEvent(?, ?, ?, ?)}")) {
                cs.setInt(1, filterCategory);
                cs.setInt(2, filterSort);
                cs.setString(3, searchWords);
                cs.registerOutParameter(4, Types.INTEGER);

                // Execute the stored procedure
                cs.execute();

                // Read the result from the OUTPUT parameter
                try (ResultSet rs = cs.getResultSet()) {
                    while (rs.next()) {
                        // Retrieve data from the result set and create Event objects
                        int eventId = rs.getInt("EventId");
                        String eventName = rs.getString("event_name");
                        String place = rs.getString("place");
                        String organizationName = rs.getString("organization_name");
                        int numberOfAttendance = rs.getInt("number_of_attendance");
                        LocalDate deadline = rs.getDate("deadline").toLocalDate();
                        LocalDate occurDate = rs.getDate("OccurDate").toLocalDate();
                        int maxSlot = rs.getInt("MaxSlot");
                        String detail = rs.getString("Detail");

                        // Create EventModel object and add it to the ArrayList
                        EventModel event = new EventModel(eventId, eventName, place, organizationName,numberOfAttendance, deadline, occurDate, maxSlot,detail);
                        System.out.println(event);
                        tableList.add(event);
                    }
                }
                return tableList;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }


    }

    public ArrayList<StudentEventModel> getTable(int id) {
        ArrayList<StudentEventModel> tableList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword)) {
            try (CallableStatement cs = connection.prepareCall("{call sp_getStudentEvent(?)}")) {
                cs.setInt(1, id);
                // Execute the stored procedure
                cs.execute();

                // Read the result from the OUTPUT parameter
                try (ResultSet rs = cs.getResultSet()) {
                    while (rs.next()) {
                        // Retrieve data from the result set and create Event objects

                        String username = rs.getString("Username");
                        String className = rs.getString("Name");
                        String studentId = rs.getString("StudentId");


                        // Create EventModel object and add it to the ArrayList
                        StudentEventModel student = new StudentEventModel(studentId,username,className);
                        System.out.println(student);
                        tableList.add(student);
                    }
                }
                return tableList;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public boolean insertEvent(String name, LocalDate occurDate, String place, int organizationId, int maxSlot, LocalDate deadline, String detail) {
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword)) {
            try (CallableStatement cs = connection.prepareCall("{call sp_insertEvent(?, ?, ?, ?, ?, ?, ?)}")) {
                cs.setString(1, name);
                cs.setDate(2, java.sql.Date.valueOf(occurDate));
                cs.setString(3, place);
                cs.setInt(4, organizationId);
                cs.setInt(5, maxSlot);
                cs.setDate(6, java.sql.Date.valueOf(deadline));
                cs.setString(7, detail);

                // Execute the stored procedure
                int rowsAffected = cs.executeUpdate();

                // Check if any rows were affected
                if (rowsAffected > 0) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public boolean updateEvent(int id, String name, LocalDate occurDate, String place, int organizationId, int maxSlot, LocalDate deadline, String detail) {
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword)) {
            try (CallableStatement cs = connection.prepareCall("{call sp_updateEvent(?,?, ?, ?, ?, ?, ?, ?)}")) {
                cs.setInt(1,id);
                cs.setString(2, name);
                cs.setDate(3, java.sql.Date.valueOf(occurDate));
                cs.setString(4, place);
                cs.setInt(5, organizationId);
                cs.setInt(6, maxSlot);
                cs.setDate(7, java.sql.Date.valueOf(deadline));
                cs.setString(8, detail);

                // Execute the stored procedure
                int rowsAffected = cs.executeUpdate();

                // Check if any rows were affected
                if (rowsAffected > 0) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public boolean deleteEvent(int id) {
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword)) {
            try (CallableStatement cs = connection.prepareCall("{call sp_deleteEvent(?)}")) {
                cs.setInt(1, id);

                // Execute the stored procedure
                int rowsAffected = cs.executeUpdate();

                // Check if any rows were affected
                if (rowsAffected > 0) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}




