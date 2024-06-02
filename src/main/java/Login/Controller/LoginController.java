package Login.Controller;
import Admin.Controller.ChuyenViewController;
import Login.Model.CheckLoginDAO;
import User.Controller.Event.ChuyenViewControllerUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;


public class LoginController {
    @FXML
    private Button si_loginBtn;

    @FXML
    private AnchorPane si_loginForm;

    @FXML
    private PasswordField si_password;

    @FXML
    private TextField si_username;

    public void handleLoginButtonAction(ActionEvent event) {
        String username = si_username.getText();
        String password = si_password.getText();

        // Sử dụng UserDao để kiểm tra đăng nhập
        CheckLoginDAO checkLoginDAO = new CheckLoginDAO();
        int result = checkLoginDAO.checkLogin(username, password);

        // Kiểm tra kết quả
        if (result == 2) {
            // Nếu đăng nhập thành công vào Student
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText(null);
            alert.setContentText(" Student đăng nhập thành công!");
            alert.showAndWait();

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/ViewChinh.fxml"));
                Parent root = loader.load();

                // Lấy controller từ loader
                ChuyenViewControllerUser chuyenViewControllerUser = loader.getController();
                chuyenViewControllerUser.setUsername(username);

                // Hiển thị ViewChinh
                Scene scene = new Scene(root);
                Stage stage = (Stage) si_loginForm.getScene().getWindow();
                stage.setScene(scene);
                stage.setResizable(true);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                // Xử lý nếu có lỗi khi chuyển đổi
            }

        } else if (result == 1) {
            // Nếu đăng nhập thành công vào Staff
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText(null);
            alert.setContentText("Staff đăng nhập thành công!");
            alert.showAndWait();
            try {
                Integer permissionID = checkLoginDAO.getPermissionIDForStaff(username);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewChinh.fxml"));
                Parent root = loader.load();

                // Lấy controller từ loader
                ChuyenViewController chuyenViewController = loader.getController();
                chuyenViewController.setUsername(username);
                chuyenViewController.setPermission(permissionID);
                chuyenViewController.loadInitialView();
                System.out.println("User: " + username);
                System.out.println("Permission: " + permissionID);
                // Hiển thị ViewChinh
                Scene scene = new Scene(root);
                Stage stage = (Stage) si_loginForm.getScene().getWindow();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();


            } catch (IOException e) {
                e.printStackTrace();
                // Xử lý nếu có lỗi khi chuyển đổi
            }



        } else {
            // Nếu đăng nhập thất bại
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Đăng nhập không thành công. Vui lòng kiểm tra lại thông tin đăng nhập.");
            alert.showAndWait();
        }
    }

}
