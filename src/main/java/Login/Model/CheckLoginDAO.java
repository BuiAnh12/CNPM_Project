package Login.Model;

import Admin.Model.Staff.StaffModel;
import Admin.Model.Student.StudentModel;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CheckLoginDAO {
    private String jdbcUrl;
    private String dbUsername;
    private String dbPassword;

    public CheckLoginDAO() {
        // Mặc định, bạn có thể thiết lập các giá trị kết nối ở đây.
//        this.jdbcUrl =
//                "jdbc:sqlserver://localhost:1433;" +
//                        "databaseName=EventManagement;integratedSecurity=true;" +
//                        "encrypt=true;trustServerCertificate=true";
        this.jdbcUrl = "jdbc:sqlserver://localhost:1433;databaseName=EventManagement;encrypt=true;trustServerCertificate=true";
//       this.jdbcUrl = "Data Source=DESKTOP-IL0B7TN\\MSSQLSERVER02;Initial Catalog=EventManagement;Integrated Security=True" ;
        this.dbUsername = "cnpm";
        this.dbPassword = "123";
    }

    // Có thể thêm phương thức khởi tạo khác hoặc sử dụng setter

    public int checkLogin(String username, String password) {
        int result = 0;
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword)) {
            try (CallableStatement cs = connection.prepareCall("{call ValidateLogin(?, ?, ?)}")) {
                cs.setString(1, username);
                cs.setString(2, password);
                cs.registerOutParameter(3, Types.INTEGER);

                // Thực thi stored procedure
                cs.execute();

                // Đọc kết quả từ OUTPUT parameter
                result = cs.getInt(3);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return result;
    }


    public Integer getPermissionIDForStaff(String username) {
        Integer permissionID = null;
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword)) {
            try (CallableStatement cs = connection.prepareCall("{call GetPermissionIDForStaff(?, ?)}")) {
                cs.setString(1, username);
                cs.registerOutParameter(2, Types.INTEGER);

                // Thực thi stored procedure
                cs.execute();

                // Đọc kết quả từ OUTPUT parameter
                int result = cs.getInt(2);
                if (!cs.wasNull()) {
                    permissionID = result;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return permissionID;
    }

    public StaffModel getLoginStaff(String LoginUsername) {

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);
             CallableStatement statement = connection.prepareCall("{call sp_getLoginStaff(?)}")) {

            // Set the input parameters
            statement.setString(1, LoginUsername);


            // Execute the stored procedure
            ResultSet resultSet = statement.executeQuery();

            StaffModel staff = new StaffModel();

            while (resultSet.next()) {
                int staffId = resultSet.getInt("StaffId");
                String fullname = resultSet.getString("Fullname");
                String phone = resultSet.getString("PhoneNumber");
                int permissionId = resultSet.getInt("PermissionId");
                String permissionName = resultSet.getString("PermissionName");
                LocalDate dob = resultSet.getDate("DOB").toLocalDate();
                boolean enable = resultSet.getBoolean("Enable");
                int accountId = resultSet.getInt("AccountID");
                String username = resultSet.getString("Username");
                String password = resultSet.getString("Password");

                // Create StaffModel object and add it to the list
                staff = new StaffModel(staffId, fullname, username, password, permissionId, permissionName, phone, dob, enable, accountId);

            }
            return staff;
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
            throw new RuntimeException(e);
        }
    }
    public StudentModel getLoginStudent(String LoginUsername) {

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);
             CallableStatement statement = connection.prepareCall("{call sp_getLoginStudent(?)}")) {

            // Set the input parameters
            statement.setString(1, LoginUsername);


            // Execute the stored procedure
            ResultSet resultSet = statement.executeQuery();

            StudentModel student = new StudentModel();

            while (resultSet.next()) {
                String studentId = resultSet.getString("StudentId");
                int classId = resultSet.getInt("ClassId");
                String phoneNumber = resultSet.getString("PhoneNumber");
                LocalDate dob = resultSet.getDate("DOB").toLocalDate();
                boolean enable = resultSet.getBoolean("Enable");
                String fullname = resultSet.getString("Fullname");
                int accountId = resultSet.getInt("AccountID");
                String username = resultSet.getString("Username");
                String password = resultSet.getString("Password");

                // Create StaffModel object and add it to the list
                student = new StudentModel(studentId, username, classId, phoneNumber, dob, enable, fullname, accountId, password);

            }
            return student;
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
            throw new RuntimeException(e);
        }
    }
}