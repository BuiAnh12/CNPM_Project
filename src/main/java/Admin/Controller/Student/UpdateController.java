package Admin.Controller.Student;

import Admin.Model.Event.EventModel;
import Admin.Model.Event.StudentEventModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class UpdateController {
    private StudentEventModel object;

    public StudentEventModel getObject() {
        return object;
    }

    public void setObject(StudentEventModel object) {
        this.object = object;
    }

    @FXML
    private AnchorPane insertForm;

    @FXML
    private TextField txtClass;

    @FXML
    private TextField txtDateOfBirth;

    @FXML
    private TextField txtFullName;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtPhoneNumber;

    @FXML
    private TextField txtUsername;

    public void updateFields() {
//        txtClass.setText(object.getClass().toString());
//        txtDeadline.setText(object.getDeadline().toString());
//        txtMaxSlot.setText(String.valueOf(object.getMaxSlot()));
//        txtName.setText(object.getEventName());
//        txtOrganization.setText(object.getOrganizationName());
//        txtPlace.setText(object.getPlace());
    }
    @FXML
    void AcceptClickBtn(MouseEvent event) {

    }

    @FXML
    void CancelClickBtn(MouseEvent event) {
        try {
            AnchorPane currentContainer = (AnchorPane) this.insertForm.getParent();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Admin/Student/StudentForm/MainAdminForm.fxml"));
            Parent page = fxmlLoader.load();
            currentContainer.getChildren().add(page);
        } catch (IOException e) {
            System.out.println("Open Fail");
            e.printStackTrace();
        }
    }


}
