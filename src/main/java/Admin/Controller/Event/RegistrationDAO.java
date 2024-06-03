package Admin.Controller.Event;

import Admin.Model.Event.EventModel;
import Admin.Model.Event.RegistrationModel;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RegistrationDAO {
    private String jdbcUrl;
    private String dbUsername;
    private String dbPassword;
    private List<EventModel> tableList;
    public RegistrationDAO() {
        // Mặc định, bạn có thể thiết lập các giá trị kết nối ở đây.
        this.jdbcUrl = "jdbc:sqlserver://localhost:1433;databaseName=EventManagement;encrypt=true;trustServerCertificate=true";
        this.dbUsername = "cnpm";
        this.dbPassword = "123";
        tableList = new ArrayList<>();
    }

    public ArrayList<RegistrationModel> getTable(int id, String searchTxt) {
        ArrayList<RegistrationModel> tableList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword)) {
            try (CallableStatement cs = connection.prepareCall("{call sp_getAttendance(?,?)}")) {
                cs.setInt(1, id);
                cs.setString(2,"%"+searchTxt+"%");
                // Execute the stored procedure
                cs.execute();

                // Read the result from the OUTPUT parameter
                try (ResultSet rs = cs.getResultSet()) {
                    if (rs == null){
                        return tableList;
                    }
                    while (rs.next()) {
                        // Retrieve data from the result set and create Event objects
                        String studentId = rs.getString("StudentId");
                        String classId = rs.getString("ClassId");
                        String phoneNumber = rs.getString("PhoneNumber");
                        LocalDate dob = rs.getDate("DOB").toLocalDate();
                        String fullname = rs.getString("Fullname");
                        boolean status = rs.getBoolean("Status");


                        // Create EventModel object and add it to the ArrayList
                        RegistrationModel registrationModel = new RegistrationModel(studentId, fullname, classId, phoneNumber,dob,status);
                        tableList.add(registrationModel);
                    }
                }
                return tableList;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public boolean isAttend(int eventId, String studentId, int staffId){
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword)) {
            try (CallableStatement cs = connection.prepareCall("{call sp_isAttend(?,?,?)}")) {
                cs.setInt(1, eventId);
                cs.setString(2,studentId);
                cs.setInt(3, staffId);
                // Execute the stored procedure
                cs.execute();

                // Read the result from the OUTPUT parameter
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isNotAttend(int eventId, String studentId, int staffId){
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword)) {
            try (CallableStatement cs = connection.prepareCall("{call sp_isNotAttend(?,?, ?)}")) {
                cs.setInt(1, eventId);
                cs.setString(2,studentId);
                cs.setInt(3, staffId);

                // Execute the stored procedure
                cs.execute();

                // Read the result from the OUTPUT parameter
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
