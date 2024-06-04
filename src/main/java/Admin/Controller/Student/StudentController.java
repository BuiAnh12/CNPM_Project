package Admin.Controller.Student;

import Admin.Controller.Event.EventDAO;
import Admin.Model.Event.EventModel;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.*;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;


import javafx.collections.FXCollections;
import javafx.scene.control.TableView;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import java.util.List;




public class StudentController implements Initializable {

    @FXML
    public TableView<StudentModel> mainTable;

    @FXML
    private ComboBox<String> Filter;

    @FXML
    private ImageView SearchBtn;

    @FXML
    private TextField SearchField;
    public TableColumn<StudentEventModel, String> idColumn;
    public TableColumn <StudentEventModel, String> nameColumn;
    public TableColumn<StudentEventModel, String> classColumn;
    public TableColumn <StudentEventModel, String> phoneColumn;
    public AnchorPane DashbaordForm;
    public TableColumn<StudentEventModel, LocalDate> dobColumn;
    private StudentDAO studentDAO;
    private StudentModel StudentModels;

//    @FXML
//    private TableView<StudentModel> table;

    public StudentController() {
        this.studentDAO = new StudentDAO();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<String> range = FXCollections.observableArrayList(
                "Sinh Viên","Id Sinh Viên",
                "Id Lớp"
        );

        Filter.setItems(range);

        Filter.setValue("Sinh Viên");

        Filter.setOnAction(event -> updateTableView());


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
    void SearchTxtType(KeyEvent event) {
        updateTableView();
    }

    @FXML
    void TblClickEvent(MouseEvent event) {

    }

    @FXML
    void GetFilter(MouseEvent event) {

    }

    @FXML
    void GetFilterOrder(MouseEvent event) {

    }

    public String getTypeRange() {
        String typeRange = "";

        if (Filter.getValue().equals("Sinh Viên")) {
            typeRange = "Order By\n" +
                    "LTrim(Reverse(Left(Reverse(FullName), CharIndex(' ', Reverse(FullName))))) Asc,\n" +
                    "FullName Asc";
        } else if (Filter.getValue().equals("Id Lớp")) {
            typeRange = "ORDER BY ClassId DESC";
        } else if (Filter.getValue().equals("Id Sinh Viên")) {
            typeRange = "ORDER BY StudentId ASC";
        } else {
            // Xử lý trường hợp không thỏa mãn
            typeRange = ""; // hoặc giá trị mặc định khác nếu cần
        }

        return typeRange;
    }


    private void updateTableView() {

        String TypeOrder = getTypeRange();

        // Get the selected filter index and sort index
        int filterIndex = Filter.getSelectionModel().getSelectedIndex();
        String searchWords = SearchField.getText();

        // Retrieve data from the database using your EventDAO
        EventDAO eventDAO = new EventDAO();
        List<StudentModel> tableList = studentDAO.getTable(filterIndex, searchWords, TypeOrder);

        // Update the TableView with the new data
        mainTable.getItems().clear(); // Clear existing data
        mainTable.setItems(FXCollections.observableArrayList(tableList));
    }

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
            alert.setTitle("Xác nhận xóa");
            alert.setHeaderText(null);
            alert.setContentText("Bạn có chắc rằng muốn xóa sinh viên này không?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                studentDAO.deleteStudent(selectedStudent.getStudentId()); // Truyền studentId thay vì selectedStudent
                mainTable.getItems().remove(selectedStudent);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng chọn sinh viên bạn muốn xóa.");
            alert.showAndWait();
        }
}
}
