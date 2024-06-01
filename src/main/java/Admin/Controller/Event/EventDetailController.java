package Admin.Controller.Event;

import Admin.Controller.AttendentController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import Admin.Model.Event.EventModel;
import Admin.Model.Event.StudentEventModel;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.fxml.Initializable;
import javax.management.modelmbean.ModelMBean;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EventDetailController implements Initializable{
    private Scene previousScene;

    private EventModel object;
    private int eventId;
    @FXML
    private TableView<StudentEventModel> StudentTable;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtOganization;

    @FXML
    private TextField txtPlace;

    @FXML
    private TextField txtTime;

    @FXML
    private TextField txtTotal;

    @FXML
    private TextArea txtDetail;

    @FXML
    private TextField txtCheckBy;

    @FXML
    private TextField txtCreateBy;

    @FXML
    private TextField txtDeadline;

    @FXML
    private TextField txtStatus;

    @FXML
    private TableColumn<StudentEventModel, String> colClass;

    @FXML
    private TableColumn<StudentEventModel, String> colName;

    @FXML
    private TableColumn<StudentEventModel, String> colStudentId;
    @FXML
    void goBack(MouseEvent event) {
        // Show the previous stage
        if (previousScene != null) {
            Stage currentStage = (Stage) StudentTable.getScene().getWindow();
            currentStage.setScene(previousScene);
        }

    }

    public void setPreviousStage(Scene scene) {
        this.previousScene = scene;
    }

    public void setObject(EventModel object){
        this.object = object;
    }

    public void refresh(){
        this.txtName.setText(object.getEventName());
        this.txtOganization.setText(object.getOrganizationName());
        this.txtPlace.setText(object.getPlace());
        this.txtTime.setText(object.getOccurDate().toString());
        this.txtTotal.setText(String.valueOf(object.getNumberOfAttendance()));
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
        StudentTable.setItems(FXCollections.observableArrayList(tableList));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void attendanceButton(MouseEvent mouseEvent) {
        try {
            // Tải attendance.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/Staff/StaffForm/attendance.fxml"));
            Parent root = loader.load();

            // Tạo một Scene mới với root là attendance.fxml
            Scene scene = new Scene(root);

            // Lấy controller của AttendanceController
            AttendentController controller = loader.getController();

            // Thiết lập eventId cho AttendanceController
            controller.setEventId(object.getEventId());

            // Lấy Stage hiện tại từ nút được nhấp
            Stage stage = (Stage) StudentTable.getScene().getWindow();

            // Đặt Scene mới cho Stage và hiển thị
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
