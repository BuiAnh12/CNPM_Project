package Admin.Controller.NewStaff;

import Admin.Controller.Event.EventDAO;
import Admin.Controller.NewStaff.InsertController;
import Admin.Controller.NewStaff.UpdateController;
import Admin.Model.Staff.StaffModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private AnchorPane DashboardForm;

    @FXML
    private ComboBox<String> Filter;

    private String user = "";
    @FXML
    private ImageView SearchBtn;

    @FXML
    private TextField SearchField;

    @FXML
    private TableColumn<StaffModel, Integer> idColumn;

    @FXML
    private TableColumn<StaffModel, String> nameColumn;

    @FXML
    private TableColumn<StaffModel, String> phoneColumn;

    @FXML
    private Button insertFile;

    @FXML
    private TableView<StaffModel> mainTable;

    @FXML
    private TableColumn<StaffModel, String> usernameColumn;

    @FXML
    private TableColumn<StaffModel, String > roleColumn;

    StaffDAO staffDao = new StaffDAO();

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> range = FXCollections.observableArrayList(
                "Name","Id",
                "Role"
        );

        Filter.setItems(range);

        Filter.setValue("Id");

        Filter.setOnAction(event -> updateTableView());


        List<StaffModel> staffs = this.staffDao.getAllStaffs("",0);

        setupTable(staffs);


    }

    private void setupTable(List<StaffModel> StaffList) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("permissionName"));
        ObservableList<StaffModel> data = FXCollections.observableArrayList(StaffList);
        mainTable.setItems(data);
    }

    private void updateTableView() {
        // Get the selected filter index and sort index
        int filterIndex = Filter.getSelectionModel().getSelectedIndex();
        String searchWords = SearchField.getText();

        // Retrieve data from the database using your EventDAO
        EventDAO eventDAO = new EventDAO();
        List<StaffModel> tableList = staffDao.getAllStaffs(searchWords, filterIndex);
        // Update the TableView with the new data
        mainTable.getItems().clear(); // Clear existing data
        mainTable.setItems(FXCollections.observableArrayList(tableList));
    }
    @FXML
    void DeleteBtnClickEvent(MouseEvent event) {
        if (mainTable.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng chọn nhân viên để xóa");
            alert.showAndWait();
            return; // Exit the method
        }
        StaffModel selectedStaff = mainTable.getSelectionModel().getSelectedItem();
        if (selectedStaff.getUsername().equals(this.user)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng không xóa chính mình");
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có muốn xóa nhân viên này không?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            staffDao.deleteStaff(selectedStaff.getId()); // Truyền studentId thay vì selectedStudent
            mainTable.getItems().remove(selectedStaff);
        }

    }

    @FXML
    void EditBtnClickEvent(MouseEvent event) {
        // Check if any item is selected in the table
        if (mainTable.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng chọn nhân viên để chỉnh sửa");
            alert.showAndWait();
            return; // Exit the method
        }

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Admin/NewStaff/StaffForm/UpdateForm.fxml"));
            Parent page = fxmlLoader.load();

            UpdateController updateController = fxmlLoader.getController();
            updateController.setObject(mainTable.getSelectionModel().getSelectedItem());
            //updateController.updateFields();
            AnchorPane Container = (AnchorPane) DashboardForm.getParent();
            Container.getChildren().clear();
            Container.getChildren().add(page);

        } catch (IOException e) {
            System.out.println("Failed to open update form");
            e.printStackTrace();
        }
    }


    @FXML
    void GetFilter(MouseEvent event) {

    }

    @FXML
    void InsertBtnClickEvent(MouseEvent event) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Admin/NewStaff/StaffForm/InsertForm.fxml"));
            Parent page = fxmlLoader.load();

            Scene currentScene = mainTable.getScene();
            Stage currentStage = (Stage) mainTable.getScene().getWindow();

            InsertController insertController = fxmlLoader.getController();
            insertController.setPreviousScene(currentScene);

            AnchorPane Container = (AnchorPane) DashboardForm.getParent();
            Container.getChildren().clear();
            Container.getChildren().add(page);

        } catch (IOException e) {
            System.out.println("Open Fail");
            e.printStackTrace();
        }
    }

    @FXML
    void InsertFileBtnClickEvent(MouseEvent event) {

    }

    @FXML
    void SearchTxtType(KeyEvent event) {
        updateTableView();
    }

    @FXML
    void TblClickEvent(MouseEvent event) {

    }




}
