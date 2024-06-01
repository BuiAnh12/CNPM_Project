package Admin.Controller;

import Admin.Controller.UserDetail.UserDetailController;
import Admin.Controller.NewStaff.MainController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class ChuyenViewController {

    private String username;

    public void setUsername(String username) {
        this.username = username;
        loadUserDetail();
    }

    @FXML
    private StackPane contentPane;

    @FXML
    private Button GoEventDetails;

    @FXML
    private Button GoLoginButton;

    @FXML
    private Button GoStaffButton;

    @FXML
    private Button GoStudentForm;


    @FXML
    private void initialize() {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/UserDetail/UserDetail.fxml"));
//            Parent userDetailRoot = loader.load();
//
//            // Lấy controller của UserDetail.fxml sau khi nó được tải
//            UserDetailController userDetailController = loader.getController();
//            userDetailController.setUsername(username); // Truyền username vào UserDetailController
//
//            // Thêm UserDetail vào contentPane hoặc pane cần thiết
//            contentPane.getChildren().add(userDetailRoot);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private void loadUserDetail() {
        if (username != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/UserDetail/UserDetail.fxml"));
                Parent userDetailRoot = loader.load();

                // Lấy controller của UserDetail.fxml sau khi nó được tải
                UserDetailController userDetailController = loader.getController();
                userDetailController.setUsername(username); // Truyền username vào UserDetailController

                // Thêm UserDetail vào contentPane hoặc pane cần thiết
                contentPane.getChildren().add(userDetailRoot);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void handleGoEventDetailsButtonAction(ActionEvent event) {
        loadView("/Admin/Event/EventForm/EventMainForm.fxml");
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
    void handleGoStaffButtonAction(ActionEvent event) {
        try{
            FXMLLoader loader = loadView("/Admin/NewStaff/StaffForm/MainForm.fxml");
            MainController controller = loader.getController();
            controller.setUser(this.username);
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    @FXML
    void handleGoStudentButtonAction(ActionEvent event) {
        loadView("/Admin/Student/StudentForm/MainForm.fxml");
    }

    public FXMLLoader loadView(String fxmlFile) {
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


    private UserDetailController getLastLoadedController() {
        // Lấy danh sách các node trong StackPane
        ObservableList<Node> children = contentPane.getChildren();

        // Lấy controller của node cuối cùng trong danh sách (đó chính là UserDetail.fxml)
        return ((FXMLLoader) children.get(children.size() - 1).getUserData()).getController();
    }
}
