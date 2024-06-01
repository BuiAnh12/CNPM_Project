package Admin.Controller.Student;

import Admin.Controller.ChuyenViewController;
import Admin.Model.Student.StudentModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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
            txtClass.setText(String.valueOf(object.getClassId()));
            txtDateOfBirth.setValue(object.getDob());
            txtFullName.setText(object.getFullName());
            txtPassword.setText(object.getPassword());
            txtUsername.setText(object.getUsername());
            txtPhoneNumber.setText(object.getPhoneNumber());
            txtStudentId.setText(object.getStudentId());
        }
    }

//    @FXML
//    void AcceptClickBtn(MouseEvent event) {
//
//    }
@FXML
void AcceptClickBtn() {
    try {
        // Lấy dữ liệu mới từ các trường nhập liệu
        String studentId = txtStudentId.getText();
        Integer studentClass = Integer.parseInt(txtClass.getText());
        String phoneNumber = txtPhoneNumber.getText();
        LocalDate dob = txtDateOfBirth.getValue();
        String fullName = txtFullName.getText();
        String password = txtPassword.getText();
        String username = txtUsername.getText();
        boolean studentEnable = true;

//        System.out.println(txtStudentId.getText());
//        System.out.println(Integer.parseInt(txtClass.getText()));
//        System.out.println(txtPhoneNumber.getText());
//        System.out.println(txtDateOfBirth.getValue());
//        System.out.println(txtFullName.getText());
//        System.out.println(txtPassword.getText());
//        System.out.println(txtUsername.getText());
        // Cập nhật dữ liệu vào cơ sở dữ liệu
        StudentDAO studentDAO = new StudentDAO();
//        // Cập nhật trước khi gọi phương thức updateStudent
        updateFields(object);
//        // Thực hiện cập nhật dữ liệu sinh viên
        studentDAO.updateStudent(studentId, studentClass, phoneNumber, dob, studentEnable, fullName, username, password);

        // Hiển thị lại giao diện MainAdminForm
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ViewChinh.fxml"));
        Parent root = fxmlLoader.load();

        // Lấy controller của ViewChinh
        ChuyenViewController controller = fxmlLoader.getController();

        // Gọi phương thức loadView của controller để hiển thị EventMainForm
        controller.loadView("/Admin/Student/StudentForm/MainForm.fxml");
        // Lấy ra Scene của giao diện trước đó
        Scene previousScene = new Scene(root);

        // Lấy ra Stage hiện tại
        Stage stage = (Stage) updateForm.getScene().getWindow();

        // Thiết lập Scene của giao diện trước đó vào Stage
        stage.setScene(previousScene);
        stage.show();
    } catch (Exception e) {
        e.printStackTrace();
    }
}





    @FXML
    void CancelClickBtn(MouseEvent event) {
        // Xử lý khi người dùng nhấn nút Cancel
        try {
            // Tạo một FXMLLoader cho giao diện trước đó
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ViewChinh.fxml"));
            Parent root = fxmlLoader.load();

            // Lấy controller của ViewChinh
            ChuyenViewController controller = fxmlLoader.getController();

            // Gọi phương thức loadView của controller để hiển thị EventMainForm
            controller.loadView("/Admin/Student/StudentForm/MainForm.fxml");
            // Lấy ra Scene của giao diện trước đó
            Scene previousScene = new Scene(root);

            // Lấy ra Stage hiện tại
            Stage stage = (Stage) updateForm.getScene().getWindow();

            // Thiết lập Scene của giao diện trước đó vào Stage
            stage.setScene(previousScene);
            stage.show();
        } catch (IOException e) {
            System.out.println("Open Fail");
            e.printStackTrace();
        }
    }
}
