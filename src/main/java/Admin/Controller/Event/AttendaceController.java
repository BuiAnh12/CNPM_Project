package Admin.Controller.Event;

import Admin.Model.Event.RegistrationModel;
import Admin.Model.Event.StudentEventModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AttendaceController implements Initializable {

    @FXML
    private AnchorPane DashbaordForm;

    @FXML
    private TextField SearchField;

    @FXML
    private TableColumn<RegistrationModel, Boolean> attendaceColumn;

    @FXML
    private TableColumn<RegistrationModel, String> classColumn;

    @FXML
    private TableColumn<RegistrationModel, LocalDate> dobColumn;

    @FXML
    private TableColumn<RegistrationModel, Integer> idColumn;

    @FXML
    private TableView<RegistrationModel> mainTable;

    @FXML
    private TableColumn<RegistrationModel, String> nameColumn;

    @FXML
    private TableColumn<RegistrationModel, String> phoneColumn;

    @FXML
    private Button revertBtn;

    @FXML
    private Button saveBtn;

    private RegistrationDAO registrationDAO = new RegistrationDAO();

    private int eventId = 2; //for testing

    public void seteventId(int eventId) {
        this.eventId = eventId;
        loadData(eventId,"");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        attendaceColumn.setCellValueFactory(new PropertyValueFactory<RegistrationModel, Boolean>("statusCheckBox"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<RegistrationModel, String>("studentName"));
        idColumn.setCellValueFactory(new PropertyValueFactory<RegistrationModel, Integer>("studentId"));
        classColumn.setCellValueFactory(new PropertyValueFactory<RegistrationModel, String>("class"));
        dobColumn.setCellValueFactory(new PropertyValueFactory<RegistrationModel, LocalDate>("dob"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<RegistrationModel, String>("phoneNumber"));

        attendaceColumn.setReorderable(false);
        nameColumn.setReorderable(false);
        idColumn.setReorderable(false);
        classColumn.setReorderable(false);
        dobColumn.setReorderable(false);
        phoneColumn.setReorderable(false);
        mainTable.setItems(FXCollections.observableArrayList());

        attendaceColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        attendaceColumn.setCellFactory(new Callback<TableColumn<RegistrationModel, Boolean>, TableCell<RegistrationModel, Boolean>>() {
            @Override
            public TableCell<RegistrationModel, Boolean> call(TableColumn<RegistrationModel, Boolean> param) {
                return new TableCell<RegistrationModel, Boolean>() {

                    private final CheckBox checkBox = new CheckBox();

                    @Override
                    protected void updateItem(Boolean item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setGraphic(null);
                        } else {
                            checkBox.setSelected(item);

                            // Update the status in the model when the checkbox is clicked
                            checkBox.setOnAction(event -> {
                                RegistrationModel model = getTableView().getItems().get(getIndex());
                                model.setStatus(checkBox.isSelected());
                            });

                            setGraphic(checkBox);
                        }
                    }
                };
            }
        });

    }

    void loadData(int eventId, String searchTxt){
        List<RegistrationModel> registrationModelList = registrationDAO.getTable(eventId,searchTxt);
        ObservableList<RegistrationModel> registrationModels = FXCollections.observableArrayList(registrationModelList);
        mainTable.setItems(registrationModels);
    }

    @FXML
    void RevertBtnClick(ActionEvent event) {
        loadData(eventId, SearchField.getText());
    }

    @FXML
    void SaveBtnClick(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có muốn xóa sự kiện này không?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            ObservableList<RegistrationModel> items = mainTable.getItems();
            // Loop through the items
            for (RegistrationModel item : items) {
                if (item.isStatus()){
                    registrationDAO.isAttend(eventId,item.getStudentId());
                }
                else{
                    registrationDAO.isNotAttend(eventId,item.getStudentId());
                }
            }
        }

    }

    @FXML
    void SearchTxtType(KeyEvent event) {
        loadData(eventId, SearchField.getText());
    }

    @FXML
    void TblClickEvent(MouseEvent event) {

    }


    @FXML
    void EditBtnClickEvent(MouseEvent event) {

    }

    @FXML
    void InsertBtnClickEvent(MouseEvent event) {

    }

}
