import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/Login/Login.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 400, 530);
//        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/Admin/Event/EventForm/EventMainForm.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 1200, 800);
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/Admin/Student/StudentForm/MainAdminForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 800);
        stage.setTitle("Quản lí hoạt động sinh viên");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}