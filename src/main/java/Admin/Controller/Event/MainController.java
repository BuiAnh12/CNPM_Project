package Admin.Controller.Event;

import Admin.Controller.Event.InsertCotroller;
import Admin.Model.Event.EventModel;
import Admin.Model.Staff.StaffModel;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private AnchorPane DashbaordForm;

    @FXML
    private ChoiceBox<String> Filter;

    @FXML
    private ChoiceBox<String> FilterSort;

    @FXML
    private ImageView SearchBtn;

    @FXML
    private TextField SearchField;

    @FXML
    private TableColumn<EventModel, LocalDate> colDeadline;

    @FXML
    private TableColumn<EventModel, String> colName;

    @FXML
    private TableColumn<EventModel, Integer> colNum;

    @FXML
    private TableColumn<EventModel, String> colOrg;

    @FXML
    private TableColumn<EventModel, String> colPlace;

    @FXML
    private TableView<EventModel> table;
    

    private StaffModel user = new StaffModel();

    public StaffModel getSetUser() {
        return user;
    }

    public void setSetUser(StaffModel setUser) {
        this.user = setUser;
    }

    public void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void DeleteBtnClickEvent(MouseEvent event) {
        if (user.getPermissionId() != 1 && user.getPermissionId() != 2){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Phân quyền");
            alert.setHeaderText(null);
            alert.setContentText("Bạn không có quyền thực hiện chức năng này");
            System.out.println("Permission: " + user.getPermissionId());
            alert.showAndWait();
            return;
        }
        if (table.getSelectionModel().isEmpty()) {
            // Show an error message or handle the situation accordingly
            showAlert("Lỗi", "Vui lòng chọn một sự kiện để tiến hành xóa.");
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận xóa");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có muốn xóa sự kiện này không?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            EventModel selected = table.getSelectionModel().getSelectedItem();
            EventDAO eventDAO = new EventDAO();
            boolean success = eventDAO.deleteEvent(selected.getEventId());
            if (success){
                System.out.println("Xóa thành công!");
            }
            else{
                System.out.println("Xóa thất bại!");
            }
            updateTableView();
        }


    }

    @FXML
    void EditBtnClickEvent(MouseEvent event) {
        if (user.getPermissionId() != 1 && user.getPermissionId() != 2){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Phân quyền");
            alert.setHeaderText(null);
            alert.setContentText("Bạn không có quyền thực hiện chức năng này");
            System.out.println("Permission: " + user.getPermissionId());
            alert.showAndWait();
            return;
        }
        if (table.getSelectionModel().isEmpty()) {
            // Show an error message or handle the situation accordingly
            showAlert("Lỗi", "Vui lòng chọn một sự kiện để tiến hành cập nhật.");
            return;
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Admin/Event/EventForm/UpdateForm.fxml"));
            Parent page = fxmlLoader.load();

            // Create a new stage for the dialog
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setTitle("Cập nhật sự kiện");

            // Set the scene
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set controller and update fields
            UpdateController updateController = fxmlLoader.getController();
            updateController.setObject(table.getSelectionModel().getSelectedItem());
            updateController.updateFields();
            updateController.setUser(this.getSetUser());
            dialogStage.setOnHiding(eventS -> {
                // Call updateTableView() when the dialog is closed
                updateTableView();
            });
            dialogStage.setOnCloseRequest(events -> {
                // Call the updateTableView() function
                updateTableView();
            });
            // Show the dialog
            dialogStage.showAndWait();
            updateTableView();

        } catch (IOException e) {
            System.out.println("Open Fail");
            e.printStackTrace();
        }

    }

    @FXML
    void GetFilter(MouseEvent event) {

    }

    @FXML
    void GetFilterOrder(MouseEvent event) {

    }

    @FXML
    void InsertBtnClickEvent(MouseEvent event) {
        if (user.getPermissionId() != 1 && user.getPermissionId() != 2){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Phân quyền");
            alert.setHeaderText(null);
            alert.setContentText("Bạn không có quyền thực hiện chức năng này");
            System.out.println("Permission: " + user.getPermissionId());
            alert.showAndWait();
            return;
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Admin/Event/EventForm/InsertForm.fxml"));
            Parent page = fxmlLoader.load();

            // Create a new stage for the dialog
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setTitle("Thêm sự kiện");

            // Set the scene
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Get the controller and set necessary data
            InsertCotroller insertController = fxmlLoader.getController();
            insertController.setUser(this.getSetUser());
            System.out.println("Userset: " + this.getSetUser());

            dialogStage.setOnHiding(eventS -> {
                // Call updateTableView() when the dialog is closed
                updateTableView();
            });
            // Set the close request handler
            dialogStage.setOnCloseRequest(events -> {
                // Call the updateTableView() function
                updateTableView();
            });

            // Show the dialog
            dialogStage.showAndWait();
            updateTableView();

        } catch (IOException e) {
            System.out.println("Open Fail");
            e.printStackTrace();
        }
    }


    @FXML
    void SearchTxtType(KeyEvent event) {
        updateTableView();
    }

    @FXML
    void TblClickEvent(MouseEvent event) {

    }
    private void updateTableView() {
        // Get the selected filter index and sort index
        int filterIndex = Filter.getSelectionModel().getSelectedIndex();
        int sortIndex = FilterSort.getSelectionModel().getSelectedIndex();
        String searchWords = SearchField.getText();

        // Retrieve data from the database using your EventDAO
        EventDAO eventDAO = new EventDAO();
        List<EventModel> tableList = eventDAO.getTable(filterIndex, sortIndex, searchWords);

        // Update the TableView with the new data
        table.getItems().clear(); // Clear existing data
        table.setItems(FXCollections.observableArrayList(tableList));
    }

    private void showDetail(EventModel object) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Admin/Event/EventForm/EventDetailForm.fxml"));
            Parent page = fxmlLoader.load();

            // Get the current stage
            Scene currentScene = table.getScene();
            Stage currentStage = (Stage) table.getScene().getWindow();
            // Create a new scene with the loaded FXML as the root
            Scene newScene = new Scene(page, 1200, 800);

            EventDetailController eventDetailController = fxmlLoader.getController();
            eventDetailController.setUser(user);
            eventDetailController.setPreviousStage(currentScene);
            // Set the id for function to show
            eventDetailController.setObject(object);

            eventDetailController.refresh();
            // Set the scene to the current stage
            currentStage.setScene(newScene);

            // Pass the reference to the previous stage to the controller


            System.out.println("Open Successful");
        } catch (IOException e) {
            System.out.println("Open Fail");
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.Filter.getItems().addAll("Date", "Name","No. Attendance");
        this.FilterSort.getItems().addAll("Asc","Desc");
        this.Filter.setValue("Date");
        this.FilterSort.setValue("Desc");
        Filter.setOnAction(event -> updateTableView());
        FilterSort.setOnAction(event -> updateTableView());
        EventDAO eventDAO = new EventDAO();
        List<EventModel> tableList = new ArrayList<>();
//        tableList = eventDAO.getTable(Filter.getSelectionModel().getSelectedIndex(), FilterSort.getSelectionModel().getSelectedIndex(), SearchField.getText());
        tableList = eventDAO.getTable(0, 0, "");
        colName.setCellValueFactory(new PropertyValueFactory<EventModel,String>("eventName"));
        colPlace.setCellValueFactory(new PropertyValueFactory<EventModel,String>("place"));
        colOrg.setCellValueFactory(new PropertyValueFactory<EventModel,String>("organizationName"));
        colNum.setCellValueFactory(new PropertyValueFactory<EventModel,Integer>("numberOfAttendance"));
        colDeadline.setCellValueFactory(new PropertyValueFactory<EventModel, LocalDate>("deadline"));
        table.setItems(FXCollections.observableArrayList(tableList));
        table.setRowFactory(tv -> {
            TableRow<EventModel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    EventModel rowData = row.getItem();
                    showDetail(rowData);
                    // passing the new window
                }
            });
            return row;
        });
        System.out.println("Main form init");
    }
}
