package Admin.Controller.Event;

import Admin.Controller.ChuyenViewController;
import Admin.Controller.NewStaff.StaffDAO;
import Admin.Model.Event.EventModel;
import Admin.Model.Event.StudentEventModel;
import Admin.Model.Staff.StaffModel;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.fxml.Initializable;
import javax.management.modelmbean.ModelMBean;
import java.awt.event.ActionEvent;
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
    @FXML
    private TableView<StudentEventModel> StudentTable;

    @FXML
    private Button GoBackBtn;

    @FXML
    private Button CheckingBtn;

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
    private StaffModel user;

    public StaffModel getUser() {
        return user;
    }

    public void setUser(StaffModel user) {
        this.user = user;
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
        StaffDAO staffDAO = new StaffDAO();
        List<StaffModel> staffs = staffDAO.getAllStaffs("",1);
        for (StaffModel staff : staffs){
            if (staff.getId() == object.getCheckBy()){
                txtCheckBy.setText(staff.getName());
            }
            if (staff.getId() == object.getCreateBy()){
                txtCreateBy.setText(staff.getName());
            }
        }
        if (txtCheckBy.getText().isEmpty()){
            txtCheckBy.setText("None");
        }

    }


//    @FXML
//    public void handleGoBack(ActionEvent event) {
//        try {
//            // Tải FXML của EventMainForm
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/Event/EventForm/EventMainForm.fxml"));
//            Parent root = loader.load();
//
//            // Tạo một Scene mới từ FXML và lấy Stage hiện tại
//            Scene scene = new Scene(root);
//            Stage stage = (Stage) GoBackBtn.getScene().getWindow();
//
//            // Đặt Scene mới cho Stage
//            stage.setScene(scene);
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//            // Xử lý ngoại lệ khi tải FXML không thành công
//        }
//    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    @FXML
    void GoCheckAttendance(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/Event/EventForm/attendanceCheck.fxml"));
            Parent page = loader.load();
            AttendaceController attendaceController = loader.getController();
            attendaceController.seteventId(object.getEventId());
            attendaceController.setUser(user);

            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setTitle("Checking Attendance");

            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            dialogStage.setOnHidden(event -> {
                System.out.println("Modal closed, refreshing...");
                refresh();
            });

// Show the modal window and wait
            dialogStage.showAndWait();
            System.out.println("Open Successful");

        }
        catch (IOException e) {
            System.out.println("Open Fail");
            e.printStackTrace();
        }
    }


    public void handleGoBack(MouseEvent event) {
        try {
            // Load FXML của ViewChinh.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewChinh.fxml"));
            StackPane root = loader.load();
            ChuyenViewController chuyenViewController = loader.getController();
            chuyenViewController.setLoginStaff(user);

            // Lấy controller của ViewChinh
            // Gọi phương thức loadView của controller để hiển thị EventMainForm

            FXMLLoader view = chuyenViewController.loadView("/Admin/Event/EventForm/MainForm.fxml");
            Admin.Controller.Event.MainController mainController = view.getController();
            mainController.setSetUser(user);
            // Tạo một Scene mới từ StackPane
            Scene scene = new Scene(root);

            // Lấy Stage từ ActionEvent
            Stage stage = (Stage) GoBackBtn.getScene().getWindow();

            // Đặt Scene mới cho Stage
            stage.setScene(scene);

            // Hiển thị Stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Xử lý nếu có lỗi khi chuyển đổi
        }
    }
}
