package Admin.Controller.NewStaff;

import Admin.Controller.ChuyenViewController;
import Admin.Model.Staff.RoleModel;
import Admin.Model.Staff.StaffModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import Exception.InputHandle;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;


public class UpdateController implements Initializable {

    private StaffModel object;

    private List<RoleModel> roleModelList = new ArrayList<>();

    private StaffDAO staffDAO = new StaffDAO();

    private InputHandle inputHandle = new InputHandle();

    @FXML
    private AnchorPane updateForm;

    @FXML
    private TextField txtId;

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
        updateFields(object);

    }

    public UpdateController() {
    }

    public UpdateController(AnchorPane updateForm, ComboBox<String> cmbPrivilege, DatePicker txtDateOfBirth, TextField txtFullName, TextField txtPassword, TextField txtPhoneNumber, TextField txtUsername) {
        this.updateForm = updateForm;
        this.cmbPrivilege = cmbPrivilege;
        this.txtDateOfBirth = txtDateOfBirth;
        this.txtFullName = txtFullName;
        this.txtPassword = txtPassword;
        this.txtPhoneNumber = txtPhoneNumber;
        this.txtUsername = txtUsername;
    }

    public StaffModel getObject() {
        return object;
    }

    public void setObject(StaffModel object) {
        this.object = object;
        updateFields(object);
    }

    public void updateFields(StaffModel object) {
        if (object != null) {
            ObservableList<String> range = FXCollections.observableArrayList(
                    roleModelList.stream().map(RoleModel::toString).collect(Collectors.toList())
            );
            cmbPrivilege.setItems(range);
            cmbPrivilege.setValue(object.getPermissionName());
            txtDateOfBirth.setValue(object.getDateOfBirth());
            txtFullName.setText(object.getName());
            txtPassword.setText(object.getPassword());
            txtUsername.setText(object.getUsername());
            txtPhoneNumber.setText(object.getPhoneNumber());
            txtId.setText(String.valueOf(object.getId()));
        }
    }

@FXML
void AcceptClickBtn() {
    try {
        // Lấy dữ liệu mới từ các trường nhập liệu
        int id = Integer.parseInt(txtId.getText());
        String phoneNumber = txtPhoneNumber.getText();
        LocalDate dob = txtDateOfBirth.getValue();
        String fullName = txtFullName.getText();
        String password = txtPassword.getText();
        String username = txtUsername.getText();
        String permision = cmbPrivilege.getValue().toString();
        int permisionId = staffDAO.getIdFromName(roleModelList,permision);
        boolean enalbe = true;
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
        if (txtDateOfBirth.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Ngày sinh không được để trống");
            alert.showAndWait();
            txtDateOfBirth.requestFocus();
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
        if (!object.getUsername().equals(username)  && staffDAO.isUsernameExists(username)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Tên đăng nhập đã bị trùng");
            alert.showAndWait();
            txtUsername.setText(object.getUsername());
            txtUsername.requestFocus();
            return;
        }
        System.out.println("Updated Object: " + object.getAccountId());

        object.setId(id);
        object.setPhoneNumber(phoneNumber);
        object.setDateOfBirth(dob);
        object.setName(fullName);
        object.setPassword(password);
        object.setUsername(username);
        object.setPermissionId(permisionId);
        object.setEnable(enalbe);
        staffDAO.insertOrUpdateStaff(object);



        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thành công");
        alert.setHeaderText(null);
        alert.setContentText("Chỉnh sửa nhân viên thành công!");
        alert.showAndWait();


    } catch (Exception e) {
        e.printStackTrace();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thất bại");
        alert.setHeaderText(null);
        alert.setContentText("Chỉnh sửa nhân viên thất bại!");
        alert.showAndWait();
    }
    // Get the stage of the current AnchorPane
    Stage stage = (Stage) updateForm.getScene().getWindow();

    // Close the stage to effectively close the modal dialog
    stage.close();
}





    @FXML
    void CancelClickBtn(MouseEvent event) {
        // Xử lý khi người dùng nhấn nút Cancel
        try {
            // Tạo một FXMLLoader cho giao diện trước đó
            Stage stage = (Stage) updateForm.getScene().getWindow();
            // Close the stage to effectively close the modal dialog
            stage.close();
        } catch (Exception e) {
            System.out.println("Close Fail");
            e.printStackTrace();
        }
    }
}
