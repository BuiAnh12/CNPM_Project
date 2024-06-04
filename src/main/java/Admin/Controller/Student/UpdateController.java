package Admin.Controller.Student;

import Admin.Model.Student.StudentModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;


public class UpdateController implements Initializable {
    @FXML
    private TextField txtStudentId;
    private StudentModel object;

    @FXML
    private AnchorPane updateForm;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateFields(object);
    }

    public UpdateController() {
    }

    public UpdateController(AnchorPane updateForm, TextField txtClass, DatePicker txtDateOfBirth, TextField txtFullName, TextField txtPassword, TextField txtPhoneNumber, TextField txtUsername) {
        this.updateForm = updateForm;
        this.txtClass = txtClass;
        this.txtDateOfBirth = txtDateOfBirth;
        this.txtFullName = txtFullName;
        this.txtPassword = txtPassword;
        this.txtPhoneNumber = txtPhoneNumber;
        this.txtUsername = txtUsername;
    }

    public StudentModel getObject() {
        return object;
    }

    public void setObject(StudentModel object) {
        this.object = object;
        updateFields(object);
    }

    public void updateFields(StudentModel object) {
        if (object != null) {
            txtClass.setText(object.getClassId());
            txtDateOfBirth.setValue(object.getDob());
            txtFullName.setText(object.getFullName());
            txtPassword.setText(object.getPassword());
            txtUsername.setText(object.getUsername());
            txtPhoneNumber.setText(object.getPhoneNumber());
            txtStudentId.setText(object.getStudentId());
        }
    }

    @FXML
    void AcceptClickBtn(ActionEvent event) {

    }
    @FXML
    void CancelClickBtn(ActionEvent event) {
        // Đóng cửa sổ hiện tại

    }

    public void AcceptClickBtn(javafx.event.ActionEvent event) {
        try {
            // Lấy dữ liệu mới từ các trường nhập liệu
            String studentId = txtStudentId.getText();
            String studentClass = txtClass.getText();
            String phoneNumber = txtPhoneNumber.getText();
            LocalDate dob = txtDateOfBirth.getValue();
            String fullName = txtFullName.getText();
            String password = txtPassword.getText();
            String username = txtUsername.getText();
            boolean studentEnable = true;

            // Cập nhật dữ liệu vào cơ sở dữ liệu
            StudentDAO studentDAO = new StudentDAO();

            // Cập nhật trước khi gọi phương thức updateStudent
            updateFields(object);

            // Thực hiện cập nhật dữ liệu sinh viên
            studentDAO.updateStudent(studentId, studentClass, phoneNumber, dob, studentEnable, fullName, username, password);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thành công");
            alert.setHeaderText(null);
            alert.setContentText("Cập nhật sinh viên thành công!");
            alert.showAndWait();

            // Đóng cửa sổ hiện tại
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void CancelClickBtn(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
