package User.Controller.Event.UserDetail;



import java.sql.*;



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
            String sql = "{call sp_GetUserDetailsStudent(?)}";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, username);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        user.setUsername(rs.getString("Username"));
                        user.setPhoneNumber(rs.getString("Phonenumber"));
                        user.setDob(rs.getString("DOB"));
                        user.setFullName(rs.getString("Fullname"));
                        user.setPassword(rs.getString("Password"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return user;
    }

    public void updatePassword(String newPassword, String userName) {
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword)) {
            String sql = "UPDATE Account SET Password = ? WHERE Username = ?";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, newPassword);
                ps.setString(2, userName);

                int rowsUpdated = ps.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Password updated successfully!");
                } else {
                    System.out.println("Failed to update password for username: " + userName);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
