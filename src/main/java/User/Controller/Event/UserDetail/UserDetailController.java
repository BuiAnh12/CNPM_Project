package User.Controller.Event.UserDetail;

import Admin.Controller.ChuyenViewController;
import User.Controller.Event.ChuyenViewControllerUser;
import User.Controller.Event.UserDetail.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

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
    private TextField txtPhoneNumber;

    @FXML
    private TextField txtUserName;


    private String username;


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

            // Gọi phương thức loadView của controller để hiển thị EventMainForm
            controllerUser.loadViewUser("/User/Event/EventForm/MainForm.fxml");

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



