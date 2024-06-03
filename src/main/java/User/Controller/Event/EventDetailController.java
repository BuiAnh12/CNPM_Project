package User.Controller.Event;

import Admin.Controller.Event.EventDAO;
import Admin.Model.Event.EventModel;
import Admin.Model.Event.StudentEventModel;
import Admin.Model.Student.StudentModel;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EventDetailController {

    private EventModel object;

    public EventModel getObject() {
        return object;
    }

    public void setObject(EventModel object) {
        this.object = object;
    }

    @FXML
    private TableView<StudentEventModel> StudentTable;

    @FXML
    private Button btnRegister;

    @FXML
    private Button btnUnregister;

    @FXML
    private TableColumn<StudentEventModel, String> colClass;

    @FXML
    private TableColumn<StudentEventModel, String> colName;

    @FXML
    private TableColumn<StudentEventModel, String> colStudentId;

    @FXML
    private TextField txtAvailableSlot;

    @FXML
    private TextField txtCheckBy;

    @FXML
    private TextField txtDeadline;

    @FXML
    private TextArea txtDetail;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtOganization;

    @FXML
    private TextField txtPlace;

    @FXML
    private TextField txtStatus;

    @FXML
    private TextField txtTime;

    private StudentModel user = new StudentModel();

    public StudentModel getUser() {
        return user;
    }

    public void setUser(StudentModel user) {
        this.user = user;
    }

    @FXML
    void registerBtnClickEvent(MouseEvent event) {
        System.out.println("User: " + this.user);
        LocalDate today = LocalDate.now();

        if (Integer.parseInt(txtAvailableSlot.getText()) <= 0){
            System.out.println("Out of slot");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Hiện tại đã hết suất. Xin vui lòng thử lại sau");
            alert.showAndWait();
        }
        else if (object.getDeadline().compareTo(today) < 0){
            System.out.println("Out of ReDate");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Hết hạn đăng ký.");
            alert.showAndWait();
        }
        else{
            EventDAO eventDAO = new EventDAO();
            boolean registrationSuccessful = eventDAO.insertRegistration(today, object.getEventId(), user.getStudentId());

            if (registrationSuccessful) {
                System.out.println("Registration successful.");
                object.setNumberOfAttendance(object.getNumberOfAttendance() + 1);
                refresh();

            } else {
                System.out.println("Failed to register for the event.");
            }

        }


    }

    @FXML
    void unregisterBtnClickEvent(MouseEvent event) {
        System.out.println("User: " + this.user);
        // Assuming you have access to the eventId and studentId variables
        LocalDate today = LocalDate.now();
        int eventId = object.getEventId();
        String studentId = user.getStudentId(); // Example student ID
        if (object.getDeadline().compareTo(today) < 0){
            System.out.println("Out of ReDate");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Hết hạn hủy đăng ký.");
            alert.showAndWait();
        }
        EventDAO eventDAO = new EventDAO();
        boolean unregistered = eventDAO.unregisterEvent(eventId, studentId);

        if (unregistered) {
            System.out.println("Event unregistered successfully.");
            object.setNumberOfAttendance(object.getNumberOfAttendance() - 1);
            refresh();
            // Optionally, you can update the UI or perform other actions here
        } else {
            System.out.println("Failed to unregister event.");
            // Optionally, you can handle the failure case here
        }
    }


    private Scene previousScene;
    @FXML
    void goBack(MouseEvent event) {

        if (previousScene != null) {
            Stage currentStage = (Stage) StudentTable.getScene().getWindow();
            currentStage.setScene(previousScene);
        }
    }


    public void setPreviousStage(Scene scene) {
        this.previousScene = scene;
    }


    public void refresh(){
        this.txtName.setText(object.getEventName());
        this.txtOganization.setText(object.getOrganizationName());
        this.txtPlace.setText(object.getPlace());
        this.txtTime.setText(object.getOccurDate().toString());
        this.txtAvailableSlot.setText(String.valueOf(object.getMaxSlot() - object.getNumberOfAttendance()));
        this.txtDetail.setText(object.getDetail());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Specify the desired format
        String deadlineString = object.getDeadline().format(formatter);
        this.txtDeadline.setText(deadlineString);

        txtStatus.setText(object.isStatus() ? "Mở" : "Đã hủy");


        EventDAO eventDAO = new EventDAO();
        List<StudentEventModel> tableList = new ArrayList<>();
//        tableList = eventDAO.getTable(Filter.getSelectionModel().getSelectedIndex(), FilterSort.getSelectionModel().getSelectedIndex(), SearchField.getText());
        tableList = eventDAO.getTable(object.getEventId());
        colName.setCellValueFactory(new PropertyValueFactory<StudentEventModel, String>("studentName"));
        colStudentId.setCellValueFactory(new PropertyValueFactory<StudentEventModel, String>("studentId"));
        colClass.setCellValueFactory(new PropertyValueFactory<StudentEventModel, String>("studentClass"));
        boolean isRegister = false;
        for (StudentEventModel element : tableList) {
            System.out.println("Element StudentId: " + element.getStudentId() + ", User: " + user);
            if (element.getStudentId().trim().equals(user.getStudentId().trim())) { // Trim whitespace before comparison
                isRegister = true;
                break;
            }
        }
        System.out.println("Is Registered: " + isRegister);

        if (isRegister) {
            this.btnRegister.setDisable(true);
            this.btnUnregister.setDisable(false);
        } else {
            this.btnUnregister.setDisable(true);
            this.btnRegister.setDisable(false);
        }
        StudentTable.setItems(FXCollections.observableArrayList(tableList));
    }

}
