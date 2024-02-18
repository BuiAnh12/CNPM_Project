package Admin.Controller.Student;

import Admin.Controller.Student.InsertController;
import Admin.Controller.Student.UpdateController;
import Admin.Model.Event.EventModel;
import Admin.Model.Event.StudentEventModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class StudentController implements Initializable {

    @FXML
    public TableView<StudentEventModel> mainTable;
    public TableColumn<StudentEventModel, String> idColumn;
    public TableColumn <StudentEventModel, String>nameColumn;
    public TableColumn<StudentEventModel, String> classColumn;
    public TableColumn <StudentEventModel, String>phoneColumn;
    public TableColumn <StudentEventModel, String>usernameColumn;
    public AnchorPane DashbaordForm;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<StudentEventModel> students = List.of(
                new StudentEventModel("N21DCCN033", "Trung Nguyen", "CNTT"),
                new StudentEventModel("N21DCCN032", "Trung Nguyen 1", "CNTT"),
                new StudentEventModel("N21DCCN031", "Trung Nguyen 2", "CNTT"),
                new StudentEventModel("N21DCCN030", "Trung Nguyen 3", "CNTT"),
                new StudentEventModel("N21DCCN020", "Trung Nguyen 4", "CNTT"),
                new StudentEventModel("N21DCCN021", "Trung Nguyen 5", "CNTT"),
                new StudentEventModel("N21DCCN010", "Trung Nguyen 6", "CNTT")
        );

        setupTable(students);

        System.out.println(mainTable);

    }

    @FXML
    void EditBtnClickEvent(MouseEvent event) {

        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Admin/Student/StudentForm/UpdateForm.fxml"));
            Parent page = fxmlLoader.load();

            Scene currentScene = mainTable.getScene();
            Stage currentStage = (Stage) mainTable.getScene().getWindow();

            UpdateController updateController = fxmlLoader.getController();
            updateController.setObject(mainTable.getSelectionModel().getSelectedItem());
            updateController.updateFields();
            AnchorPane Container = (AnchorPane) DashbaordForm.getParent();
            Container.getChildren().clear();
            Container.getChildren().add(page);

        } catch (IOException e) {
            System.out.println("Open Fail");
            e.printStackTrace();
        }
    }

    @FXML
    void InsertBtnClickEvent(MouseEvent event) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Admin/Student/StudentForm/InsertForm.fxml"));
            Parent page = fxmlLoader.load();

            Scene currentScene = mainTable.getScene();
            Stage currentStage = (Stage) mainTable.getScene().getWindow();

            InsertController insertCotroller = fxmlLoader.getController();
            insertCotroller.setPreviousScene(currentScene);

            AnchorPane Container = (AnchorPane) DashbaordForm.getParent();
            Container.getChildren().clear();
            Container.getChildren().add(page);

        } catch (IOException e) {
            System.out.println("Open Fail");
            e.printStackTrace();
        }

    }

    private void setupTable(List<StudentEventModel> studentEventModels) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        classColumn.setCellValueFactory(new PropertyValueFactory<>("studentClass"));

        mainTable.getColumns().add(idColumn);
        mainTable.getColumns().add(nameColumn);
        mainTable.getColumns().add(classColumn);

        ObservableList<StudentEventModel> data = FXCollections.observableArrayList(studentEventModels);
        mainTable.setItems(data);
    }

    public void DeleteBtnClickEvent(MouseEvent mouseEvent) {
    }

}
