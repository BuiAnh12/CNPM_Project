package Admin.Controller.UserDetail;

import Admin.Controller.UserDetail.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDetailDAO {
    private String jdbcUrl;
    private String dbUsername;
    private String dbPassword;

    public UserDetailDAO() {
        this.jdbcUrl = "jdbc:sqlserver://localhost:1433;databaseName=EventManagement;encrypt=false;";
        this.dbUsername = "cnpm";
        this.dbPassword = "123";
    }

    public User getUserDetails(String username) {
        User user = new User();

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword)) {
            String sql = "{call sp_GetUserDetailsStaff(?)}";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, username);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        user.setUsername(rs.getString("Username"));
                        user.setPhoneNumber(rs.getString("Phonenumber"));
                        user.setDob(rs.getString("DOB"));
                        user.setFullName(rs.getString("Fullname"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return user;
    }
}