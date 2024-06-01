package Admin.Controller.Event;

import Admin.Controller.Student.StudentController;
import Admin.Model.Event.EventModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

public class EventController implements Initializable {

    @FXML
    private Button GoEventDetails;
    @FXML
    private Button GoStudentForm;
    @FXML
    private Button GoStaffButton;

    @FXML
    private Button GoLoginButton;

    private int user;

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    @FXML
    private AnchorPane Containner;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Admin/Event/EventForm/MainForm.fxml"));
            Parent page = fxmlLoader.load();

            // Get the controller after loading
            MainController controller = fxmlLoader.getController();

            // Now set the user
            controller.setSetUser(1);

            // Add the loaded page to the container
            Containner.getChildren().add(page);

        } catch (IOException e) {
            System.out.println("Open Fail");
            e.printStackTrace();
        }

    }






    public void handleGoStudentButtonAction(javafx.event.ActionEvent actionEvent) {

        try {
            // Load file fxml mới
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/Student/StudentForm/MainAdminForm.fxml"));
            AnchorPane root = loader.load();

            // Tạo scene mới với root là node gốc của file fxml mới
            Scene scene = new Scene(root);

            // Lấy stage từ event
            Stage stage = (Stage) GoStudentForm.getScene().getWindow();

            // Đặt scene mới cho stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleGoLoginButtonAction(javafx.event.ActionEvent actionEvent) {
        try {


            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Login/Login.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 400, 530);
            Stage stage = new Stage();
            stage.setTitle("Quản lí hoạt động sinh viên");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleGoStaffButtonAction(javafx.event.ActionEvent actionEvent) {

        try {
            // Load file fxml mới
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/Staff/StaffForm/staffs-view.fxml"));
            BorderPane root = loader.load();

            // Kiểm tra nếu phần tử gốc là một BorderPane
            if (root instanceof BorderPane) {
                // Lấy scene hiện tại
                Scene currentScene = GoStaffButton.getScene();

                // Tạo một AnchorPane mới để chứa nội dung
                AnchorPane anchorPane = new AnchorPane();
                anchorPane.getChildren().add(root);

                // Thay đổi nội dung của phần tử gốc
                currentScene.setRoot(anchorPane);
            } else {
                System.err.println("Phần tử gốc của tập tin FXML không phải là một BorderPane.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleGoEventDetailsButtonAction(javafx.event.ActionEvent actionEvent) {
        try {
            // Load file fxml mới
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/Event/EventForm/EventDetailForm.fxml"));
            AnchorPane root = loader.load();

            // Tạo scene mới với root là node gốc của file fxml mới
            Scene scene = new Scene(root);

            // Lấy stage từ event
            Stage stage = (Stage) GoEventDetails.getScene().getWindow();

            // Đặt scene mới cho stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
