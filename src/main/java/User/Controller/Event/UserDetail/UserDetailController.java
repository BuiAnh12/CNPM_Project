package User.Controller.Event.UserDetail;

import Admin.Controller.UserDetail.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

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

    public void initialize(String username) {
        // Sử dụng UserDetailDAO để lấy thông tin từ database
        UserDetailDAO userDetailDAO = new UserDetailDAO();
        User user = userDetailDAO.getUserDetails(username);

        // Hiển thị thông tin lên giao diện
        txtUserName.setText( user.getUsername() );
        txtPhoneNumber.setText(user.getPhoneNumber());
        txtDateOfBirth.setText( user.getDob());
        txtName.setText(user.getFullName());
    }




    @FXML
    void handleButtonClick(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/Event/EventForm/EventMainForm.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            // Lấy Stage từ ActionEvent
            Stage stage = (Stage) backBtn1234.getScene().getWindow();

            // Đặt Scene mới cho Stage
            stage.setScene(scene);

            // Hiển thị Stage
            stage.show();



        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
