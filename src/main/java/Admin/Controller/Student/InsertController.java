package Admin.Controller.Student;

import Admin.Controller.Staff.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class InsertController {

    @FXML
    private Button AcceptBtn;

    @FXML
    public TextField txtStudentId;

    @FXML
    private AnchorPane insertForm;

    @FXML
    private TextField txtClass;

    @FXML
    private DatePicker txtDateOfBirth;

    @FXML
    private TextField txtFullName;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtPhoneNumber;

    @FXML
    private TextField txtUsername;

    private StudentDAO studentDAO;

    public InsertController() {
        this.studentDAO = new StudentDAO();
    }

    @FXML
    void AcceptClickBtn(ActionEvent event) {
        try {
            String username = txtUsername.getText();
            String password = txtPassword.getText();

            // Insert account into database and get accountId


            String fullName = txtFullName.getText();
            LocalDate dateOfBirth = txtDateOfBirth.getValue();
            String phoneNumber = txtPhoneNumber.getText();
            String studentId = txtStudentId.getText();
            Integer studentClass = Integer.
                    parseInt(txtClass.getText());
            /*boolean studentEnable = true;*/
            // Insert student information into database
            studentDAO.insertStudentWithAccount(username, password,fullName, dateOfBirth, phoneNumber, studentId, studentClass);

            // Thông báo thành công
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Student added successfully!");
            alert.showAndWait();
        } catch (SQLException e) {
            // Thông báo lỗi
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to add student!");
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    @FXML
    void CancelClickBtn() {
        try {
            // Tạo một FXMLLoader cho giao diện trước đó
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Admin/Student/StudentForm/MainForm.fxml"));
            Parent root = fxmlLoader.load();

            // Lấy ra Scene của giao diện trước đó
            Scene previousScene = new Scene(root);

            // Lấy ra Stage hiện tại
            Stage stage = (Stage) insertForm.getScene().getWindow();

            // Thiết lập Scene của giao diện trước đó vào Stage
            stage.setScene(previousScene);
            stage.show();
        } catch (IOException e) {
            System.out.println("Open Fail");
            e.printStackTrace();
        }
    }

    public void setPreviousScene(Scene currentScene) {
    }
}
