package Admin.Controller.NewStaff;

import Admin.Controller.ChuyenViewController;
import Admin.Model.Staff.RoleModel;
import Admin.Model.Staff.StaffModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import Exception.InputHandle;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class InsertController implements Initializable {
    private StaffModel object = new StaffModel();

    private List<RoleModel> roleModelList = new ArrayList<>();

    private StaffDAO staffDAO = new StaffDAO();

    InputHandle inputHandle = new InputHandle();
    @FXML
    private Button AcceptBtn;

    @FXML
    private AnchorPane insertForm;
    @FXML
    public TextField txtId;


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


    @FXML
    private ComboBox<String> cmbPrivilege;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        roleModelList = staffDAO.getAllRole();
        ObservableList<String> range = FXCollections.observableArrayList(
                roleModelList.stream().map(RoleModel::toString).collect(Collectors.toList())
        );
        cmbPrivilege.setItems(range);
        cmbPrivilege.setValue(roleModelList.getFirst().getName());

    }

    @FXML
    void AcceptClickBtn(ActionEvent event) {
        try {
            int id =  -1;
            String phoneNumber = txtPhoneNumber.getText();
            LocalDate dob = txtDateOfBirth.getValue();
            String fullName = txtFullName.getText();
            String password = txtPassword.getText();
            String username = txtUsername.getText();
            String permission = cmbPrivilege.getValue();
            int permissionId = staffDAO.getIdFromName(roleModelList,permission);
            boolean enable = true;
            StaffDAO staffDAO = new StaffDAO();

            if (phoneNumber.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText(null);
                alert.setContentText("Số điện thoại không được để trống");
                alert.showAndWait();
                txtPhoneNumber.requestFocus();
                return;
            }
            LocalDate selectedDate = txtDateOfBirth.getValue();
            LocalDate currentDate = LocalDate.now();
            if (selectedDate.isAfter(currentDate)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText(null);
                alert.setContentText("Ngày sinh không được lớn hơn ngày hiện tại");
                alert.showAndWait();
                txtDateOfBirth.requestFocus();
                return;
            }
            if (fullName.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText(null);
                alert.setContentText("Tên không được để trống");
                alert.showAndWait();
                txtFullName.requestFocus();
                return;
            }
            if (username.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText(null);
                alert.setContentText("Tên đăng nhập không được để trống");
                alert.showAndWait();
                txtUsername.requestFocus();
                return;
            }
            if (password.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText(null);
                alert.setContentText("Mật khẩu không được để trống");
                alert.showAndWait();
                txtPassword.requestFocus();
                return;
            }
            if (!inputHandle.isValidPhoneNumber(phoneNumber)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText(null);
                alert.setContentText("Số điện thoại không hợp lệ");
                alert.showAndWait();
                txtPhoneNumber.requestFocus();
                return;
            }
            if (staffDAO.isUsernameExists(username)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText(null);
                alert.setContentText("Tên đăng nhập đã bị trùng");
                alert.showAndWait();
                txtUsername.requestFocus();
                return;
            }
            object.setId(id);
            object.setAccountId(-1);
            object.setPhoneNumber(phoneNumber);
            object.setDateOfBirth(dob);
            object.setName(fullName);
            object.setPassword(password);
            object.setUsername(username);
            object.setPermissionId(permissionId);
            object.setEnable(enable);

            staffDAO.insertOrUpdateStaff(object);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Student added successfully!");
            alert.showAndWait();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ViewChinh.fxml"));
            Parent root = fxmlLoader.load();

            // Lấy controller của ViewChinh
            ChuyenViewController controller = fxmlLoader.getController();

            // Gọi phương thức loadView của controller để hiển thị EventMainForm
            controller.loadView("/Admin/NewStaff/StaffForm/MainForm.fxml");
            // Lấy ra Scene của giao diện trước đó
            Scene previousScene = new Scene(root);

            // Lấy ra Stage hiện tại
            Stage stage = (Stage) insertForm.getScene().getWindow();

            // Thiết lập Scene của giao diện trước đó vào Stage
            stage.setScene(previousScene);
            stage.show();

        } catch (Exception e) {
            // Thông báo lỗi
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to add staff!");
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    @FXML
    void CancelClickBtn() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ViewChinh.fxml"));
            Parent root = fxmlLoader.load();

            // Lấy controller của ViewChinh
            ChuyenViewController controller = fxmlLoader.getController();

            // Gọi phương thức loadView của controller để hiển thị EventMainForm
            controller.loadView("/Admin/NewStaff/StaffForm/MainForm.fxml");
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
