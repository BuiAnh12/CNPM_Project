package User.Controller.Event;

import Admin.Model.Student.StudentModel;
import User.Controller.Event.UserDetail.UserDetailController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


import java.io.IOException;

public class ChuyenViewControllerUser {

    private String username;

//    public void setUsername(String username) {
//        this.username = username;
//    }

    private StudentModel user = new StudentModel();

    public StudentModel getUser() {
        return user;
    }

    public void setUser(StudentModel user) {
        this.user = user;
    }

    @FXML
    private Button GoLoginButton;

    @FXML
    private Button GoStudentForm;

    @FXML
    private StackPane contentPane;

    public void setUsernameUser(String username) {
        this.username = username;
        loadInitialView();
        // loadUserDetail();
    }

    public void loadInitialView() {
        FXMLLoader view = loadViewUser("/Admin/Event/EventForm/Dashboard.fxml");
//        Admin.Controller.DashboardController controller = view.getController();
//        controller.setSetUser(loginStaff);
    }
    @FXML
    void handleGoEventButtonAction(ActionEvent event) {
        System.out.println("Main user: " + user);
        FXMLLoader loader = loadViewUser("/User/Event/EventForm/MainForm.fxml");
        MainController controller =  loader.getController();
        controller.setSetUser(user);

    }

//    public FXMLLoader loadView(String fxmlFile) {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
//            Parent view = loader.load();
//            contentPane.getChildren().setAll(view);
//            return loader;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    public void loadUserDetail() {
        if (username != null) {
            try {
                System.out.println("Main user: "+ user);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/Event/UserDetail.fxml"));
                Parent userDetailRoot = loader.load();

                // Lấy controller của UserDetail.fxml sau khi nó được tải
                UserDetailController userDetailController = loader.getController();
                userDetailController.setUsernameUser(username); // Truyền username vào UserDetailController
                userDetailController.setUser(user);
                // Thêm UserDetail vào contentPane hoặc pane cần thiết
                contentPane.getChildren().add(userDetailRoot);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public FXMLLoader loadViewUser(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent view = loader.load();
            contentPane.getChildren().setAll(view);
            return loader;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @FXML
    void handleGoLoginButtonAction(ActionEvent event) {


// Lấy Stage hiện tại từ một Node bất kỳ trong scene
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();

        // Đóng cửa sổ hiện tại
        stage.close();

        // Mở cửa sổ mới cho màn hình đăng nhập
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login/Login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage loginStage = new Stage();
            loginStage.setScene(scene);
            loginStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Xử lý nếu có lỗi khi mở cửa sổ mới
        }
    }

    @FXML
    void GoDashboardForm (ActionEvent event){

        FXMLLoader view = loadViewUser("/Admin/Event/EventForm/Dashboard.fxml");
    }

    @FXML
    void GoUserDetail(ActionEvent event){
        FXMLLoader view = loadViewUser("/User/Event/UserDetail.fxml");
        loadUserDetail();
    }


}
