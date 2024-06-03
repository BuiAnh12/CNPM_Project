package Admin.Controller.Event;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Admin.Model.Event.EventModel;
import Admin.Model.Event.Organization;
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
                    if (rs == null){
                        return tableList;
                    }
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
                        boolean status = rs.getBoolean("Status");
                        int createBy = rs.getInt("CreateBy");
                        int checkBy = rs.getInt("CheckBy");


                        // Create EventModel object and add it to the ArrayList
                        EventModel event = new EventModel(eventId, eventName, place, organizationName,numberOfAttendance, deadline, occurDate, maxSlot,status, createBy, checkBy,detail);
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

                        String fullname = rs.getString("Fullname");
                        String className = rs.getString("Name");
                        String studentId = rs.getString("StudentId");


                        // Create EventModel object and add it to the ArrayList
                        StudentEventModel student = new StudentEventModel(studentId,fullname,className);
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

    public boolean insertOrUpdateEvent(Integer eventId, String name, LocalDate occurDate, String place, String organizationName, int maxSlot, LocalDate deadline, String detail, boolean status, boolean enable, Integer createBy, Integer checkBy) {
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword)) {
            try (CallableStatement cs = connection.prepareCall("{call sp_upsertEvent(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}")) {
                cs.setObject(1, eventId, java.sql.Types.INTEGER);; // Pass eventId as the first parameter
                cs.setString(2, name);
                cs.setDate(3, java.sql.Date.valueOf(occurDate));
                cs.setString(4, place);
                cs.setString(5, organizationName);
                cs.setInt(6, maxSlot);
                cs.setDate(7, java.sql.Date.valueOf(deadline));
                cs.setString(8, detail);
                cs.setBoolean(9, status); // Convert boolean to BIT for status
                cs.setBoolean(10, enable); // Convert boolean to BIT for enable
                cs.setInt(11, createBy); // Added createBy parameter
                cs.setObject(12, checkBy, java.sql.Types.INTEGER); // Added checkBy parameter

                // Execute the stored procedure
                int rowsAffected = cs.executeUpdate();

                // Check if any rows were affected
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


//    public List<Organization> getAllOrganizations() {
//        List<Organization> organizations = new ArrayList<>();
//
//        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);
//             CallableStatement statement = connection.prepareCall("{call sp_getAllOrganization()}")) {
//
//            // Execute the stored procedure
//            ResultSet resultSet = statement.executeQuery();
//
//            // Process the result set
//            while (resultSet.next()) {
//                int id = resultSet.getInt("OrganizationId");
//                String name = resultSet.getString("Name");
//                String detail = resultSet.getString("Detail");
//                Organization organization = new Organization(id, name,detail);
//                organizations.add(organization);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            // Handle exceptions appropriately
//            throw new RuntimeException(e);
//        }
//
//        return organizations;
//    }

    public boolean updateEvent(int id, String name, LocalDate occurDate, String place, String organizationName, int maxSlot, LocalDate deadline, String detail) {
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword)) {
            try (CallableStatement cs = connection.prepareCall("{call sp_updateEvent(?,?, ?, ?, ?, ?, ?, ?)}")) {
                cs.setInt(1,id);
                cs.setString(2, name);
                cs.setDate(3, java.sql.Date.valueOf(occurDate));
                cs.setString(4, place);
                cs.setString(5, organizationName);
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

    public boolean insertRegistration(LocalDate occurDate, int eventId, String studentId) {
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword)) {
            try (CallableStatement cs = connection.prepareCall("{call sp_registerEvent(?, ?, ?)}")) {
                cs.setObject(1, eventId, java.sql.Types.INTEGER);; // Pass eventId as the first parameter
                cs.setString(2, studentId);
                cs.setDate(3, java.sql.Date.valueOf(occurDate));

                // Execute the stored procedure
                int rowsAffected = cs.executeUpdate();

                // Check if any rows were affected
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public boolean unregisterEvent(int eventId, String studentId) {
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword)) {
            try (CallableStatement cs = connection.prepareCall("{call sp_unregisterEvent(?, ?)}")) {
                cs.setString(1, studentId);
                cs.setInt(2, eventId);



                // Execute the stored procedure
                int rowsAffected = cs.executeUpdate();

                // Check if any rows were affected
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }



}




