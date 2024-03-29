package Admin.Controller.Student;

import Admin.Model.Event.StudentEventModel;
import Admin.Model.Student.StudentModel;
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
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;


public class StudentController implements Initializable {

    @FXML
    public TableView<StudentModel> mainTable;
    public TableColumn<StudentEventModel, String> idColumn;
    public TableColumn <StudentEventModel, String> nameColumn;
    public TableColumn<StudentEventModel, String> classColumn;
    public TableColumn <StudentEventModel, String> phoneColumn;
    public AnchorPane DashbaordForm;
    public TableColumn<StudentEventModel, LocalDate> dobColumn;
    private StudentDAO studentDAO;
    private StudentModel StudentModels;

    public StudentController() {
        this.studentDAO = new StudentDAO();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<StudentModel> students = this.studentDAO.getAllStudents();

        setupTable(students);
    }

//    @FXML
//    void EditBtnClickEvent(MouseEvent event) {
//
//        try{
//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Admin/Student/StudentForm/UpdateForm.fxml"));
//            Parent page = fxmlLoader.load();
//
//            Scene currentScene = mainTable.getScene();
//            Stage currentStage = (Stage) mainTable.getScene().getWindow();
//
//            UpdateController updateController = fxmlLoader.getController();
//            updateController.setObject(mainTable.getSelectionModel().getSelectedItem());
//            updateController.updateFields();
//            AnchorPane Container = (AnchorPane) DashbaordForm.getParent();
//            Container.getChildren().clear();
//            Container.getChildren().add(page);
//
//        } catch (IOException e) {
//            System.out.println("Open Fail");
//            e.printStackTrace();
//        }
//    }

    @FXML
    void EditBtnClickEvent(MouseEvent event) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Admin/Student/StudentForm/UpdateForm.fxml"));
            Parent page = fxmlLoader.load();

            UpdateController updateController = fxmlLoader.getController();
            updateController.setObject(mainTable.getSelectionModel().getSelectedItem());
            //updateController.updateFields();

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

    private void setupTable(List<StudentModel> StudentModels) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        classColumn.setCellValueFactory(new PropertyValueFactory<>("classId"));
        dobColumn.setCellValueFactory(new PropertyValueFactory<>("dob"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));


        ObservableList<StudentModel> data = FXCollections.observableArrayList(StudentModels);
        mainTable.setItems(data);
    }

    public void DeleteBtnClickEvent(MouseEvent mouseEvent) {
        StudentModel selectedStudent = mainTable.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Delete");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete this student?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                studentDAO.deleteStudent(selectedStudent.getStudentId()); // Truyền studentId thay vì selectedStudent
                mainTable.getItems().remove(selectedStudent);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select a student to delete.");
            alert.showAndWait();
        }
}
}
