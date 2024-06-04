package Admin.Controller.Student;

import Admin.Model.Event.EventModel;
import Admin.Model.Event.StudentEventModel;
import Admin.Model.Student.StudentModel;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    private String jdbcUrl;
    private String dbUsername;
    private String dbPassword;

    public StudentDAO() {
        // Mặc định, bạn có thể thiết lập các giá trị kết nối ở đây.
        this.jdbcUrl = "jdbc:sqlserver://localhost:1433;databaseName=EventManagement;encrypt=true;trustServerCertificate=true";
        this.dbUsername = "cnpm";
        this.dbPassword = "123";
    }

    public List<StudentModel> getAllStudents() {
        List<StudentModel> students = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);
             CallableStatement statement = connection.prepareCall("select * from student s left join Account a on a.AccountID = s.AccountID where s.Enable <> 0")) {

            // Execute the stored procedure
            ResultSet resultSet = statement.executeQuery();

            // Process the result set
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

                // Create StudentModel object and add it to the list
                StudentModel student = new StudentModel(studentId, username, classId, phoneNumber, dob, enable, fullname, accountId, password);
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
            throw new RuntimeException(e);
        }

        return students;
    }

//    public boolean insertOrUpdateStudent(String studentId, String username, int classId, String phoneNumber, LocalDate dob, boolean enable, String fullname, int accountId, String password) {
//        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword)) {
//            try (CallableStatement cs = connection.prepareCall("{call sp_upsertStudent(?, ?, ?, ?, ?, ?, ?, ?, ?)}")) {
//                cs.setString(1, studentId);
//                cs.setString(2, username);
//                cs.setInt(3, classId);
//                cs.setString(4, phoneNumber);
//                cs.setDate(5, java.sql.Date.valueOf(dob));
//                cs.setBoolean(6, enable);
//                cs.setString(7, fullname);
//                cs.setInt(8, accountId);
//                cs.setString(9, password);
//
//                // Execute the stored procedure
//                int rowsAffected = cs.executeUpdate();
//
//                // Check if any rows were affected
//                if (rowsAffected > 0) {
//                    return true;
//                } else {
//                    return false;
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//    }

//    public boolean deleteStudent(String studentId) {
//        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword)) {
//            try (CallableStatement cs = connection.prepareCall("{call sp_deleteStudent(?)}")) {
//                cs.setString(1, studentId);
//
//                // Execute the stored procedure
//                int rowsAffected = cs.executeUpdate();
//
//                // Check if any rows were affected
//                if (rowsAffected > 0) {
//                    return true;
//                } else {
//                    return false;
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//    }


//    public int insertAccount(String username, String password) throws SQLException {
//        Connection conn = null;
//        PreparedStatement statement = null;
//        ResultSet generatedKeys = null;
//        int accountId = 0;
//
//        try {
//            conn = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);
//            String insertAccountQuery = "INSERT INTO Account (Username, Password) VALUES (?, ?)";
//            statement = conn.prepareStatement(insertAccountQuery, Statement.RETURN_GENERATED_KEYS);
//            statement.setString(1, username);
//            statement.setString(2, password);
//            statement.executeUpdate();
//
//            // Lấy AccountId mới tạo
//            generatedKeys = statement.getGeneratedKeys();
//            if (generatedKeys.next()) {
//                accountId = generatedKeys.getInt(1);
//            }
//        } finally {
//            // Đóng kết nối và tài nguyên
//            if (generatedKeys != null) {
//                generatedKeys.close();
//            }
//            if (statement != null) {
//                statement.close();
//            }
//            if (conn != null) {
//                conn.close();
//            }
//        }
//
//        return accountId;
//    }
//
//    public void insertStudent(int accountId, String fullName, LocalDate dateOfBirth, String phoneNumber, String studentId, Integer studentClass, Boolean studentEnable) throws SQLException {
//        Connection conn = null;
//        PreparedStatement statement = null;
//
//        try {
//            conn = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);
//            String insertStudentQuery = "INSERT INTO Student (AccountID, FullName, DOB, PhoneNumber, StudentId, ClassId, Enable) VALUES (?, ?, ?, ?, ?, ?, ?)";
//            statement = conn.prepareStatement(insertStudentQuery);
//            statement.setInt(1, accountId);
//            statement.setString(2, fullName);
//            statement.setDate(3, Date.valueOf(dateOfBirth));
//            statement.setString(4, phoneNumber);
//            statement.setString(5, studentId);
//            statement.setInt(6, studentClass);
//            statement.setBoolean(7, true);
//            statement.executeUpdate();
//        } finally {
//            // Đóng kết nối và tài nguyên
//            if (statement != null) {
//                statement.close();
//            }
//            if (conn != null) {
//                conn.close();
//            }
//        }
//    }

    public void insertStudentWithAccount(String username, String password, String fullName, LocalDate dateOfBirth, String phoneNumber, String studentId, Integer studentClass) throws SQLException {
        Connection conn = null;
        CallableStatement statement = null;

        try {
            conn = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);
            String procedureCall = "{CALL InsertStudentWithAccount(?, ?, ?, ?, ?, ?, ?)}";
            statement = conn.prepareCall(procedureCall);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, fullName);
            statement.setDate(4, Date.valueOf(dateOfBirth));
            statement.setString(5, phoneNumber);
            statement.setString(6, studentId);
            statement.setInt(7, studentClass);
            statement.execute();
        } finally {
            // Close all resources
            if (statement != null) {
                statement.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }


    public void updateStudent(String studentId, int classId, String phoneNumber, LocalDate dob, boolean enable, String fullName, String username, String password) {
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword)) {
            try (CallableStatement cs = connection.prepareCall("{call UpdateStudentAndAccount(?, ?, ?, ?, ?, ?, ?)}")) {
                cs.setString(1, studentId);
                cs.setString(2, fullName);
                cs.setDate(3, java.sql.Date.valueOf(dob));
                cs.setString(4, phoneNumber);
                cs.setString(5, username);
                cs.setString(6, password);
                cs.setInt(7, classId);



                // Execute the stored procedure
                int rowsAffected = cs.executeUpdate();

                // Check if any rows were affected
                if (rowsAffected > 0) {
                    // Cập nhật thành công
                    System.out.println("Update successful");
                } else {
                    // Không có bản ghi nào được cập nhật
                    System.out.println("No records updated");
                }
            }
        } catch (SQLException e) {
            // Xử lý lỗi
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public void deleteStudent(String studentId) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // Kết nối tới cơ sở dữ liệu
            connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);

            // Chuẩn bị câu lệnh SQL để cập nhật trạng thái sinh viên thành ẩn
            String sql = "UPDATE Student SET Enable = 0 WHERE StudentId = ?";

            // Tạo một PreparedStatement để thực hiện câu lệnh SQL
            statement = connection.prepareStatement(sql);

            // Thiết lập tham số cho câu lệnh SQL
            statement.setString(1, studentId);

            // Thực thi câu lệnh SQL để cập nhật trạng thái sinh viên
            statement.executeUpdate();
        } catch (SQLException e) {
            // Xử lý exception SQLException
            e.printStackTrace(); // Hoặc xử lý theo cách phù hợp với ứng dụng của bạn
            throw new RuntimeException("Error deleting student: " + e.getMessage());
        } finally {
            // Đảm bảo đóng tài nguyên (statement và connection)
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace(); // Hoặc xử lý theo cách phù hợp với ứng dụng của bạn
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace(); // Hoặc xử lý theo cách phù hợp với ứng dụng của bạn
                }
            }
        }
    }



    public ArrayList<StudentModel> getTable(int filterCategory, String searchWords, String typeRange) {
        ArrayList<StudentModel> tableList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword)) { String sql = "SELECT * FROM Student WHERE Fullname LIKE ? " + typeRange;

            try (CallableStatement cs = connection.prepareCall(sql)){

                cs.setString(1, "%" + searchWords + "%");


                // Execute the stored procedure
                cs.execute();

                // Read the result from the OUTPUT parameter
                try (ResultSet resultSet = cs.getResultSet()) {
                    while (resultSet.next()) {
                        String studentId = resultSet.getString("StudentId");
                        int classId = resultSet.getInt("ClassId");
                        String phoneNumber = resultSet.getString("PhoneNumber");
                        LocalDate dob = resultSet.getDate("DOB").toLocalDate();

                        String fullname = resultSet.getString("Fullname");


                        // Create StudentModel object and add it to the list
                        StudentModel student = new StudentModel(studentId, classId, phoneNumber, dob, fullname);
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
    public ArrayList<StudentModel> getTable(int id) {
        ArrayList<StudentModel> tableList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword)) {
            try (CallableStatement cs = connection.prepareCall("select * from student s left join Account a on a.AccountID = s.AccountID where s.Enable <> 0")) {
                cs.setInt(1, id);
                // Execute the stored procedure
                cs.execute();

                // Read the result from the OUTPUT parameter
                try (ResultSet resultSet = cs.getResultSet()) {
                    while (resultSet.next()) {
                        // Retrieve data from the result set and create Event objects

                        String studentId = resultSet.getString("StudentId");
                        int classId = resultSet.getInt("ClassId");
                        String phoneNumber = resultSet.getString("PhoneNumber");
                        LocalDate dob = resultSet.getDate("DOB").toLocalDate();
                        boolean enable = resultSet.getBoolean("Enable");
                        String fullname = resultSet.getString("Fullname");
                        int accountId = resultSet.getInt("AccountID");
                        String username = resultSet.getString("Username");
                        String password = resultSet.getString("Password");

                        // Create StudentModel object and add it to the list
                        StudentModel student = new StudentModel(studentId, username, classId, phoneNumber, dob, enable, fullname, accountId, password);
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



}


