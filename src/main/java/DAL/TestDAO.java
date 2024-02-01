package DAL;

import java.sql.*;

public class TestDAO {
    private String jdbcUrl;
    private String dbUsername;
    private String dbPassword;

    public TestDAO() {
        // Mặc định, bạn có thể thiết lập các giá trị kết nối ở đây.
        this.jdbcUrl = "jdbc:sqlserver://localhost:1433;databaseName=EventManagement;encrypt=true;trustServerCertificate=true";
        this.dbUsername = "cnpm";
        this.dbPassword = "123";
    }

    // Có thể thêm phương thức khởi tạo khác hoặc sử dụng setter

    public int checkLogin(String username, String password) {
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword)) {
            try (CallableStatement cs = connection.prepareCall("{call sp_CheckLogin(?, ?, ?)}")) {
                cs.setString(1, username);
                cs.setString(2, password);
                cs.registerOutParameter(3, Types.INTEGER);

                // Thực thi stored procedure
                cs.execute();

                // Đọc kết quả từ OUTPUT parameter
                return cs.getInt(3);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}