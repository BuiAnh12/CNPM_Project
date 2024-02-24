package Admin.Controller.Student;

import Admin.Model.Event.StudentEventModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class UpdateController implements Initializable {
    private StudentEventModel object;


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
    List<StudentEventModel> students = new ArrayList<>();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
         students = List.of(
                new StudentEventModel("N21DCCN033", "Trung Nguyen", "CNTT", "16-09-2003", "Nguyen", "123123", "0987654321"),
                new StudentEventModel("N21DCCN032", "Trung Nguyen 1", "CNTT"),
                new StudentEventModel("N21DCCN031", "Trung Nguyen 2", "CNTT"),
                new StudentEventModel("N21DCCN030", "Trung Nguyen 3", "CNTT"),
                new StudentEventModel("N21DCCN020", "Trung Nguyen 4", "CNTT"),
                new StudentEventModel("N21DCCN021", "Trung Nguyen 5", "CNTT"),
                new StudentEventModel("N21DCCN010", "Trung Nguyen 6", "CNTT")
        );

        updateFields(students.get(0));
    }

    public UpdateController() {
    }
    public UpdateController(AnchorPane insertForm, TextField txtClass) {
        this.insertForm = insertForm;
        this.txtClass = txtClass;
    }


    public StudentEventModel getObject() {
        return object;
    }

    public void setObject(StudentEventModel object) {
        this.object = object;
    }

    public void updateFields(StudentEventModel object) {
        txtClass.setText(object.getStudentClass());
        txtDateOfBirth.setText(object.getDateOfBirth().toString()); // Assuming date of birth is a LocalDate
        txtFullName.setText(object.getFullName());
        txtPassword.setText(object.getPassword());
        txtPhoneNumber.setText(object.getPhoneNumber());
        txtUsername.setText(object.getUsername());
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
