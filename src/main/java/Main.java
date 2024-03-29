import User.Controller.Event.EventController;
import Admin.Controller.Event.MainController;
import Admin.Controller.Staff.IStaff;
import Admin.Controller.Staff.StaffIpml;
import Admin.Model.Staff.Staff;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class Main extends Application {
    //maven no mac dinh coppy code và fxml vao file target ma cac ong cu di cònig cho no coppy lung tung
    @Override
    public void start(Stage stage) throws IOException {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/User/Event/EventForm/EventMainForm.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1200, 800);
            EventController controller = fxmlLoader.getController();
            controller.setUser(1);
        //        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/Admin/Event/EventForm/EventMainForm.fxml"));
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Admin/Event/EventForm/EventMainForm.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 1200, 800);
//        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/Admin/Student/StudentForm/MainAdminForm.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 1200, 800);
//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Admin/Staff/StaffForm/staffs-view.fxml"));
//            Parent content = fxmlLoader.load();

//            scene.getStylesheets().add(getClass().getResource("/Admin/Staff/CSS/staffs.css").toExternalForm());
            stage.setTitle("Quản lí hoạt động sinh viên");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}