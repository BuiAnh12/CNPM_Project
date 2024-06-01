package Admin.Controller;

import Admin.Model.Event.StudentEventModel;
import Admin.Controller.AttendentDAO;
import Admin.Model.Student.StudentModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AttendentController implements Initializable {

    @FXML
    private TableView<StudentEventModel> tableView;
    @FXML
    private TableColumn<StudentEventModel, Boolean> selectedColumn;
    @FXML
    private TableColumn<StudentEventModel, String> nameColumn;
    @FXML
    private TableColumn<StudentEventModel, String> studentIdColumn;
    @FXML
    private TableColumn<StudentEventModel, String> classIdColumn;
    @FXML
    private TableColumn<StudentEventModel, Integer> statusColumn;
    @FXML
    private  Button save;
    @FXML
    private CheckBox selectAllStudent;
    private int eventId;
    public void setEventId(int eventId) {
            this.eventId = eventId;
        }
    private AttendentDAO attendentDAO = new AttendentDAO();
    public AttendentController(){this.attendentDAO = new AttendentDAO();}

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        selectedColumn.setCellValueFactory(new PropertyValueFactory<StudentEventModel, Boolean>("checkbox"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<StudentEventModel, String>("fullname"));
        studentIdColumn.setCellValueFactory(new PropertyValueFactory<StudentEventModel, String>("studentid"));
        classIdColumn.setCellValueFactory(new PropertyValueFactory<StudentEventModel, String>("classid"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<StudentEventModel, Integer>("status"));

        selectedColumn.setReorderable(false);
        nameColumn.setReorderable(false);
        studentIdColumn.setReorderable(false);
        classIdColumn.setReorderable(false);
        statusColumn.setReorderable(false);
        tableView.setItems(FXCollections.observableArrayList());

        selectedColumn.setCellFactory(column -> new TableCell<StudentEventModel, Boolean>() {

            protected void updateItem(Boolean item, Boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    CheckBox checkBox = new CheckBox();
                    checkBox.setSelected(item); // item là giá trị Boolean
                    setGraphic(checkBox);
                }
            }
        });
        eventId = 1; // This should be dynamic
        loadStudentData();
    }
    private void loadStudentData() {
        List<StudentEventModel> studentList = attendentDAO.getStudentsByEventId(eventId);
        ObservableList<StudentEventModel> students = FXCollections.observableArrayList(studentList);
        tableView.setItems(students);
    }
    @FXML
    private void saveAttendance() {
        attendentDAO.updateStudentStatus(eventId, tableView.getItems());
        loadStudentData();
    }

    @FXML
    private void selectAllStudent() {
        boolean isSelected = selectAllStudent.isSelected();
        for (StudentEventModel student : tableView.getItems()) {
            student.getCheckBox().setSelected(isSelected);
        }
    }


}




