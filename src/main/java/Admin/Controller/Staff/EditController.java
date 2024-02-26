package Admin.Controller.Staff;

import Dao.StaffIpml;
import Entity.Staff;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.net.URL;
import java.sql.Date;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class EditController implements Initializable {
    private Dialog<String> dialog;
    private TableView<Staff> tableView;
    private Staff oldData;
    @FXML
    private TextField fullNameEdit;
    @FXML
    private DatePicker birthdayEdit;
    @FXML
    private TextField phoneEdit;
    @FXML
    private TextField userNameEdit;
    @FXML
    private PasswordField passwordEdit;
    @FXML
    private Button submitButton;

    public Dialog<String> getDialog() {
        return dialog;
    }
    public void setTableView(TableView<Staff> tableView) {
        this.tableView = tableView;
    }
    private StaffIpml dao = new StaffIpml();

    public void setDialog(Dialog<String> dialog) {
        this.dialog = dialog;
    }

    public void setOldData(Staff staff) {
        this.oldData = staff;
        fullNameEdit.setText(staff.getName());
        birthdayEdit.setValue(staff.getDateOfBirth().toLocalDate());
        phoneEdit.setText(staff.getPhoneNumber());
        userNameEdit.setText(staff.getUsername());
        passwordEdit.setText(staff.getPassword());
    }

    private void checkFields() {
        submitButton.setDisable(!validateFields());
    }

    private boolean validateFields() {
        return fullNameEdit.getText() != null && !fullNameEdit.getText().trim().isEmpty() &&
                birthdayEdit.getValue() != null &&
                phoneEdit.getText() != null && !phoneEdit.getText().trim().isEmpty() &&
                userNameEdit.getText() != null && !userNameEdit.getText().trim().isEmpty() &&
                passwordEdit.getText() != null && !passwordEdit.getText().trim().isEmpty();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String text = change.getText();
            if (text.matches("[0-9]*")) {
                return change;
            }
            return null;
        };
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        phoneEdit.setTextFormatter(textFormatter);
        birthdayEdit.setConverter(new StringConverter<LocalDate>() {
            private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            @Override
            public String toString(LocalDate object) {
                if (object != null) {
                    return dateTimeFormatter.format(object);
                }
                return "";
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    try {
                        return LocalDate.parse(string, dateTimeFormatter);
                    } catch (DateTimeException e) {
                        birthdayEdit.getEditor().setText("");
                        return null;
                    }
                } else return null;
            }
        });
        fullNameEdit.textProperty().addListener((observable, oldValue, newValue) -> checkFields());
        birthdayEdit.valueProperty().addListener((observable, oldValue, newValue) -> checkFields());
        phoneEdit.textProperty().addListener((observable, oldValue, newValue) -> checkFields());
        userNameEdit.textProperty().addListener((observable, oldValue, newValue) -> checkFields());
        passwordEdit.textProperty().addListener((observable, oldValue, newValue) -> checkFields());
        checkFields();
    }

    @FXML
    public void submitEdit(ActionEvent event) {
        Staff staff = new Staff();
        staff.setName(fullNameEdit.getText().trim());
        staff.setDateOfBirth(Date.valueOf(birthdayEdit.getValue()));
        staff.setPhoneNumber(phoneEdit.getText().trim());
        staff.setUsername(userNameEdit.getText().trim());
        staff.setPassword(passwordEdit.getText().trim());
        staff.setId(oldData.getId());
        boolean editResult = dao.updateStaff(staff);
        if (editResult) {
            tableView.setItems(FXCollections.observableArrayList(dao.getAllStaff()));
            this.dialog.setResult("submit");
            this.dialog.close();
        }
    }
}
