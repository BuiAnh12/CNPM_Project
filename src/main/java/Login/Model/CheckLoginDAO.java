package Login.Model;

import java.sql.*;

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
}