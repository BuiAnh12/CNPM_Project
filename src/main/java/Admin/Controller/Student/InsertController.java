package Admin.Controller.Student;

import Admin.Controller.Staff.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;


public class InsertController {

    private MainController mainController;

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
            String studentClass =txtClass.getText();

            if (username.isEmpty() || password.isEmpty() || fullName.isEmpty() || dateOfBirth == null ||
                    phoneNumber.isEmpty() || studentId.isEmpty() || txtClass.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText(null);
                alert.setContentText("Không điền đủ thông tin!");
                alert.showAndWait();
                return;
            }

            /*boolean studentEnable = true;*/
            // Insert student information into database
            studentDAO.insertStudentWithAccount(username, password,fullName, dateOfBirth, phoneNumber, studentId, studentClass);

            // Thông báo thành công
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thành công");
            alert.setHeaderText(null);
            alert.setContentText("Thêm sinh viên mới thành công!");
            alert.showAndWait();

            // Đóng cửa sổ hiện tại
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();


        } catch (SQLException e) {
            // Thông báo lỗi
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Thêm sinh viên thất bại!");
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    @FXML
    void CancelClickBtn(MouseEvent mouseEvent) {
        // Đóng cửa sổ hiện tại
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void setPreviousScene(Scene currentScene) {
    }
}
