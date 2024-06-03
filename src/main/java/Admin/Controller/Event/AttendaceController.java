package Admin.Controller.Event;

import Admin.Model.Event.RegistrationModel;

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
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;


import java.io.FileOutputStream;
import java.io.IOException;
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
    void ExportBtnClick(ActionEvent event) {
        exportToExcel();
    }

    public void exportToExcel() {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Registrations");

        // Create a bold font style for the header
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        headerCellStyle.setBorderBottom(BorderStyle.THIN);
        headerCellStyle.setBorderTop(BorderStyle.THIN);
        headerCellStyle.setBorderLeft(BorderStyle.THIN);
        headerCellStyle.setBorderRight(BorderStyle.THIN);

        // Create a cell style for the phone numbers
        CellStyle phoneCellStyle = workbook.createCellStyle();
        phoneCellStyle.setDataFormat(workbook.createDataFormat().getFormat("@"));
        phoneCellStyle.setBorderBottom(BorderStyle.THIN);
        phoneCellStyle.setBorderTop(BorderStyle.THIN);
        phoneCellStyle.setBorderLeft(BorderStyle.THIN);
        phoneCellStyle.setBorderRight(BorderStyle.THIN);

        // Create a default cell style with borders
        CellStyle defaultCellStyle = workbook.createCellStyle();
        defaultCellStyle.setBorderBottom(BorderStyle.THIN);
        defaultCellStyle.setBorderTop(BorderStyle.THIN);
        defaultCellStyle.setBorderLeft(BorderStyle.THIN);
        defaultCellStyle.setBorderRight(BorderStyle.THIN);

        // Create header row
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < mainTable.getColumns().size(); i++) {
            var cell = headerRow.createCell(i);
            cell.setCellValue(mainTable.getColumns().get(i).getText());
            cell.setCellStyle(headerCellStyle);
        }

        // Create data rows
        ObservableList<RegistrationModel> items = mainTable.getItems();
        for (int i = 0; i < items.size(); i++) {
            Row row = sheet.createRow(i + 1);
            RegistrationModel item = items.get(i);
            for (int j = 0; j < mainTable.getColumns().size(); j++) {
                TableColumn<RegistrationModel, ?> column = mainTable.getColumns().get(j);
                Object cellValue = column.getCellData(item);
                Cell cell = row.createCell(j);
                if (cellValue != null) {
                    if (cellValue instanceof Boolean) {
                        // Assuming the column with CheckBox uses Boolean type
                        cell.setCellValue((Boolean) cellValue ? "Checked" : "Unchecked");
                    } else if (cellValue instanceof CheckBox) {
                        // In case a CheckBox object is directly used (not typical)
                        cell.setCellValue(((CheckBox) cellValue).isSelected() ? "Checked" : "Unchecked");
                    } else if (column.getText().equalsIgnoreCase("Phone Number")) {
                        // Apply phone number style
                        cell.setCellValue(cellValue.toString());
                        cell.setCellStyle(phoneCellStyle);
                    } else {
                        cell.setCellValue(cellValue.toString());
                    }
                }
                if (column.getText().equalsIgnoreCase("Phone Number")) {
                    cell.setCellStyle(phoneCellStyle);
                } else {
                    cell.setCellStyle(defaultCellStyle);
                }
            }
        }

        // Auto-size all columns to fit the content
        for (int i = 0; i < mainTable.getColumns().size(); i++) {
            sheet.autoSizeColumn(i);
        }

        // Save the file
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
        fileChooser.setInitialFileName("registrations.xlsx");
        var file = fileChooser.showSaveDialog(mainTable.getScene().getWindow());

        if (file != null) {
            try (FileOutputStream fileOut = new FileOutputStream(file)) {
                workbook.write(fileOut);
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
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
