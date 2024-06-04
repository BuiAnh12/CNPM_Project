package User.Controller.Event.UserDetail;

import Admin.Controller.ChuyenViewController;
import Admin.Model.Student.StudentModel;
import User.Controller.Event.ChuyenViewControllerUser;
import User.Controller.Event.MainController;
import User.Controller.Event.UserDetail.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class UserDetailController {

    public PasswordField oldPasswordCofirm;
    public PasswordField newPassword;
    public Button btn_DoiMatKhau;
    @FXML
    private Button backBtn1234;

    @FXML
    private TextField txtDateOfBirth;

    @FXML
    private TextArea txtDetail;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPhoneNumber;

    @FXML
    private TextField txtUserName;


    private String oldPassword;

    private String username;

    private StudentModel user = new StudentModel();

    public StudentModel getUser() {
        return user;
    }

    public void setUser(StudentModel user) {
        this.user = user;
    }

    public void setUsernameUser(String username) {
        this.username = username;
        initializeUserDetails(); // Gọi hàm khởi tạo thông tin người dùng với username
    }

    public void initialize(String username) {
//        // Sử dụng UserDetailDAO để lấy thông tin từ database
//        UserDetailDAO userDetailDAO = new UserDetailDAO();
//        User user = userDetailDAO.getUserDetails(username);
//
//        // Hiển thị thông tin lên giao diện
//        txtUserName.setText( user.getUsername() );
//        txtPhoneNumber.setText(user.getPhoneNumber());
//        txtDateOfBirth.setText( user.getDob());
//        txtName.setText(user.getFullName());
    }


    private void initializeUserDetails() {
        if (username != null && txtName != null) {
            // Sử dụng username để khởi tạo thông tin người dùng
            UserDetailDAO userDetailDAO = new UserDetailDAO();
            User user = userDetailDAO.getUserDetails(username);
            // Hiển thị thông tin lên giao diện
            txtUserName.setText(user.getUsername());
            txtPhoneNumber.setText(user.getPhoneNumber());
            txtDateOfBirth.setText(user.getDob());
            txtName.setText(user.getFullName());
            oldPassword = user.getPassword();

            btn_DoiMatKhau.setOnAction(event -> changePassword());

            System.out.println("Username: " + username);
        }
    }


    @FXML
    void handleButtonClick(ActionEvent event) {
        try {
            // Load FXML của ViewChinh.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/ViewChinh.fxml"));
            StackPane root = loader.load();

            // Lấy controller của ViewChinh
            ChuyenViewControllerUser controllerUser = loader.getController();
            controllerUser.setUser(user);
            System.out.println("New user = "+ user);

            // Gọi phương thức loadView của controller để hiển thị EventMainForm
            FXMLLoader MainLoader = controllerUser.loadViewUser("/User/Event/EventForm/MainForm.fxml");
            MainController mainController = MainLoader.getController();
            mainController.setSetUser(user);
            // Tạo một Scene mới từ StackPane
            Scene scene = new Scene(root);

            // Lấy Stage từ ActionEvent
            Stage stage = (Stage) backBtn1234.getScene().getWindow();

            // Đặt Scene mới cho Stage
            stage.setScene(scene);

            // Hiển thị Stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Xử lý nếu có lỗi khi chuyển đổi
        }


    }

    public void changePassword () {
        if(checkPassword(oldPassword)) {
            Admin.Controller.UserDetail.UserDetailDAO userDetailDAO = new Admin.Controller.UserDetail.UserDetailDAO();
            userDetailDAO.updatePassword(newPassword.getText(), username);
            showAlert("Thành công", "Cập nhật mật khẩu thành công", Alert.AlertType.INFORMATION);
            newPassword.setText("");
            oldPasswordCofirm.setText("");
        }
    }

    public boolean checkPassword(String oldPassword) {
        String newPass = newPassword.getText();
        String oldPassConfirm = oldPasswordCofirm.getText();



        // Kiểm tra xem mật khẩu cũ có trùng với mật khẩu xác nhận không
        if (!oldPassword.equals(oldPassConfirm)) {
            showAlert("Lỗi", "Mật khẩu cũ không chính xác", Alert.AlertType.ERROR);
            return false;
        }

        // Kiểm tra xem mật khẩu cũ có trùng với mật khẩu mới không
        if (oldPassword.equals(newPass)) {
            showAlert("Lỗi", "Mật khẩu mới không được giống mật khẩu cũ", Alert.AlertType.ERROR);
            return false;
        }

        return true;
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}



