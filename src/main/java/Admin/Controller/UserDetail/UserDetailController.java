package Admin.Controller.UserDetail;
import Admin.Controller.ChuyenViewController;
import Admin.Controller.UserDetail.UserDetailDAO;
import Admin.Controller.UserDetail.User;
import Login.Controller.LoginController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import java.awt.*;
import java.io.IOException;

public class UserDetailController {
    @FXML
    private Button backBtn1234;
    @FXML
    private TextField txtDateOfBirth;

    @FXML
    private TextArea txtDetail;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtPhoneNumber;

    @FXML
    private TextField txtTotal;

    @FXML
    private  TextField txtUserName;

    private String usernameCheck;
    private String passwordCheck;


    private String username;

    public void setUsername(String username) {
        this.username = username;
        initializeUserDetails(); // Gọi hàm khởi tạo thông tin người dùng với username
    }


    public void initialize() {
//        // Sử dụng UserDetailDAO để lấy thông tin từ database
//        UserDetailDAO userDetailDAO = new UserDetailDAO();
//        User user = userDetailDAO.getUserDetails(username);
//
//        // Hiển thị thông tin lên giao diện
//        txtUserName.setText( user.getUsername() );
//        txtPassword.setText( user.getPassword());
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
            txtUserName.setText( user.getUsername() );
            txtPhoneNumber.setText(user.getPhoneNumber());
            txtDateOfBirth.setText( user.getDob());
            txtName.setText(user.getFullName());


            System.out.println("Username: " + username);
        }
    }

    public void handleButtonClick(ActionEvent event) {
        try {
            // Load FXML của ViewChinh.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewChinh.fxml"));
            StackPane root = loader.load();

            // Lấy controller của ViewChinh
            ChuyenViewController controller = loader.getController();

            // Gọi phương thức loadView của controller để hiển thị EventMainForm
            controller.loadView("/Admin/Event/EventForm/EventMainForm.fxml");

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

}
