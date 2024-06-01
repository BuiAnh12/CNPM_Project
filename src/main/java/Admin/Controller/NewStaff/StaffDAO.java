package Admin.Controller.NewStaff;

import Admin.Model.Staff.StaffModel;
import Admin.Model.Staff.RoleModel;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StaffDAO {
    private String jdbcUrl;
    private String dbUsername;
    private String dbPassword;

    public StaffDAO() {
        // Mặc định, bạn có thể thiết lập các giá trị kết nối ở đây.
        this.jdbcUrl = "jdbc:sqlserver://localhost:1433;databaseName=EventManagement;encrypt=true;trustServerCertificate=true";
        this.dbUsername = "cnpm";
        this.dbPassword = "123";
    }

    public List<StaffModel> getAllStaffs(String searchWords, int sortIndex) {
        List<StaffModel> staffs = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);
             CallableStatement statement = connection.prepareCall("{call sp_GetEnabledStaffByStatus(?, ?)}")) {

            // Set the input parameters
            statement.setString(1, searchWords);
            statement.setInt(2, sortIndex);

            // Execute the stored procedure
            ResultSet resultSet = statement.executeQuery();

            // Process the result set
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
                StaffModel staffModel = new StaffModel(staffId, fullname, username, password, permissionId, permissionName, phone, dob, enable, accountId);
                staffs.add(staffModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
            throw new RuntimeException(e);
        }

        return staffs;
    }

    public boolean insertOrUpdateStaff(StaffModel staff) {
        // insert staffId = -1
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword)) {
            try (CallableStatement cs = connection.prepareCall("{call sp_upsertStaff(?, ?, ?, ?, ?, ?, ?, ?, ?)}")) {
                cs.setInt(1, staff.getId());
                cs.setString(2, staff.getName());
                cs.setString(3, staff.getUsername());
                cs.setString(4, staff.getPassword());
                cs.setInt(5, staff.getPermissionId());
                cs.setString(6, staff.getPhoneNumber());
                LocalDate dob = staff.getDateOfBirth();
                if (dob != null) {
                    cs.setDate(7, java.sql.Date.valueOf(dob));
                } else {
                    cs.setNull(7, Types.DATE); // Handle null date of birth
                }
                cs.setInt(8, staff.getAccountId());
                cs.setBoolean(9, staff.isEnable());
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

    public boolean deleteStaff(int staffId) {
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);
             CallableStatement statement = connection.prepareCall("{call sp_deleteStaff(?)}")) {

            // Set the input parameter
            statement.setInt(1, staffId);

            // Execute the stored procedure
            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
            throw new RuntimeException(e);
        }
    }

    public List<RoleModel> getAllRole(){
        List<RoleModel> roles = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);
             CallableStatement statement = connection.prepareCall("select * from Permission")) {
            // Execute the stored procedure
            ResultSet resultSet = statement.executeQuery();
            // Process the result set
            while (resultSet.next()) {

                int roleId = resultSet.getInt("PermissionId");
                String name = resultSet.getString("Name");


                // Create StudentModel object and add it to the list
                RoleModel roleModel = new RoleModel(roleId,name);
                roles.add(roleModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
            throw new RuntimeException(e);
        }

        return roles;
    }

    public int getIdFromName(List<RoleModel> list, String  name){
        for (RoleModel role : list){
            if (name.equals(role.getName())){
                return role.getId();
            }
        }
        return -1;
    }


    public boolean isUsernameExists(String username) {
        boolean exists = false;

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);
             CallableStatement statement = connection.prepareCall("{call sp_checkUsername(?)}")) {

            // Set the input parameter
            statement.setString(1, username);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    exists = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
            throw new RuntimeException(e);
        }

        return exists;
    }
}


