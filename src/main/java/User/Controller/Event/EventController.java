package User.Controller.Event;

import Admin.Model.Student.StudentModel;
import User.Controller.Event.MainController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EventController implements Initializable {

    private StudentModel user;

    public StudentModel getUser() {
        return user;
    }

    public void setUser(StudentModel user) {
        this.user = user;
    }

    @FXML
    private AnchorPane Containner;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/User/Event/EventForm/MainForm.fxml"));
            Parent page = fxmlLoader.load();

            // Get the controller after loading
            MainController controller = fxmlLoader.getController();
            controller.setSetUser(user);
            // Add the loaded page to the container
            Containner.getChildren().add(page);

        } catch (IOException e) {
            System.out.println("Open Fail");
            e.printStackTrace();
        }

    }
}
