package Login.Controller;
import Login.Model.CheckLoginDAO;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;


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
        if (result == 1) {
            // Nếu đăng nhập thành công
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText(null);
            alert.setContentText(" Student đăng nhập thành công!");
            alert.showAndWait();

        } else if (result == 2) {
            // Nếu đăng nhập thành công
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText(null);
            alert.setContentText("Staff đăng nhập thành công!");
            alert.showAndWait();

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
