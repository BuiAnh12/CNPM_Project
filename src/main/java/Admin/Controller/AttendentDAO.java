package Admin.Controller;

import Admin.Model.Event.StudentEventModel;
import Admin.Model.Student.StudentModel;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttendentDAO {
    private String jdbcUrl;
    private String dbUsername;
    private String dbPassword;
    private Connection connection;

    public AttendentDAO(){
        this.jdbcUrl = "jdbc:sqlserver://localhost:1433;databaseName=EventManagement;encrypt=true;trustServerCertificate=true";
        this.dbUsername = "cnpm";
        this.dbPassword = "123";

        try {
            this.connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<StudentEventModel> getStudentsByEventId(int eventId) {
        List<StudentEventModel> students = new ArrayList<>();
        String query = "EXEC sp_getStudentEvent ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, eventId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                CheckBox checkBox = null;
                String fullname = rs.getString("Fullname");
                String studentId = rs.getString("StudentId");
                String className = rs.getString("ClassName");
                int status = rs.getInt("status");


                StudentEventModel studentEventModel = new StudentEventModel(checkBox,fullname, studentId, className, status);
                students.add(studentEventModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }
    public void updateStudentStatus(int eventId, ObservableList<StudentEventModel> items) {
        String query = "UPDATE Registration SET Status = 0 WHERE EventId = ? AND Status = 1";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, eventId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
