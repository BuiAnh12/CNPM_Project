package User.Controller.Event;

import Admin.Model.Student.StudentModel;
import User.Controller.Event.EventDAO;
import User.Controller.Event.EventDetailController;
import Admin.Controller.Event.InsertCotroller;
import Admin.Controller.Event.UpdateController;
import Admin.Model.Event.EventModel;
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

    private StudentModel setUser = new StudentModel();

    public StudentModel getSetUser() {
        return setUser;
    }

    public void setSetUser(StudentModel setUser) {
        this.setUser = setUser;
    }

    @FXML
    void DeleteBtnClickEvent(MouseEvent event) {
        EventModel selected = table.getSelectionModel().getSelectedItem();
        EventDAO eventDAO = new EventDAO();
        boolean success = eventDAO.deleteEvent(selected.getEventId());
        if (success){
            System.out.println("Delete Success!");
            try{
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Admin/Event/EventForm/MainForm.fxml"));
                Parent page = fxmlLoader.load();

                Scene currentScene = table.getScene();
                Stage currentStage = (Stage) table.getScene().getWindow();

                AnchorPane Container = (AnchorPane) DashbaordForm.getParent();
                Container.getChildren().clear();
                Container.getChildren().add(page);
            } catch (IOException e) {
                System.out.println("Open Fail");
                e.printStackTrace();
            }
        }
        else{
            System.out.println("Delete Fail");
        }
    }

    @FXML
    void EditBtnClickEvent(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Admin/Event/EventForm/UpdateForm.fxml"));
            Parent page = fxmlLoader.load();

            // Create a new stage for the dialog
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setTitle("Update Event");

            // Set the scene
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set controller and update fields
            UpdateController updateController = fxmlLoader.getController();
            updateController.setObject(table.getSelectionModel().getSelectedItem());
            updateController.updateFields();
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
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Admin/Event/EventForm/InsertForm.fxml"));
            Parent page = fxmlLoader.load();

            // Create a new stage for the dialog
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setTitle("Insert Event");

            // Set the scene
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Get the controller and set necessary data
            InsertCotroller insertController = fxmlLoader.getController();

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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/User/Event/EventForm/EventDetailForm.fxml"));
            Parent page = fxmlLoader.load();

            // Get the current stage
            Scene currentScene = table.getScene();
            Stage currentStage = (Stage) table.getScene().getWindow();
            // Create a new scene with the loaded FXML as the root
            Scene newScene = new Scene(page, 1200, 800);

            EventDetailController eventDetailController = fxmlLoader.getController();
            eventDetailController.setPreviousStage(currentScene);
            eventDetailController.setUser(setUser);
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
