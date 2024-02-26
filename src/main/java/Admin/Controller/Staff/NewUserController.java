package Admin.Controller.Staff;

import Dao.StaffIpml;
import Entity.Staff;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class NewUserController implements Initializable {

    @FXML
    private TextField fullName;
    @FXML
    private DatePicker birthday;
    @FXML
    private TextField phone;
    @FXML
    private TextField userName;
    @FXML
    private PasswordField password;
    @FXML
    private Button submitButton;
    @FXML
    private Button back;
    private StaffIpml dao = new StaffIpml();


    private void checkFields() {
        submitButton.setDisable(!validateFields());
    }
    private boolean validateFields() {
        return fullName.getText() != null && !fullName.getText().trim().isEmpty() &&
                birthday.getValue() != null &&
                phone.getText() != null && !phone.getText().trim().isEmpty() &&
                userName.getText() != null && !userName.getText().trim().isEmpty() &&
                password.getText() != null && !password.getText().trim().isEmpty();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //call api de lay du lieu tu mysql
        //gan du lieu vao tung TextField
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String text = change.getText();
            if (text.matches("[0-9]*")) {
                return change;
            }
            return null;
        };
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        phone.setTextFormatter(textFormatter);
        birthday.setConverter(new StringConverter<LocalDate>() {
            private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            @Override
            public String toString(LocalDate object) {
                if(object != null) {
                    return dateTimeFormatter.format(object);
                }
                return "";
            }

            @Override
            public LocalDate fromString(String string) {
                if (string !=null && !string.isEmpty()) {
                    try {
                        return LocalDate.parse(string, dateTimeFormatter);
                    } catch (DateTimeException e) {
                        birthday.getEditor().setText("");
                        return null;
                    }
                } else return null;
            }
        });

        fullName.textProperty().addListener((observable, oldValue, newValue) -> checkFields());
        birthday.valueProperty().addListener((observable, oldValue, newValue) -> checkFields());
        phone.textProperty().addListener((observable, oldValue, newValue) -> checkFields());
        userName.textProperty().addListener((observable, oldValue, newValue) -> checkFields());
        password.textProperty().addListener((observable, oldValue, newValue) -> checkFields());
        checkFields();
    }
    @FXML
    public void submit(ActionEvent event) throws IOException {
        if (validateFields()) {
            Staff newStaff = new Staff();
            newStaff.setUsername(userName.getText().trim());
            newStaff.setName(fullName.getText().trim());
            newStaff.setPhoneNumber(phone.getText().trim());
            newStaff.setDateOfBirth(Date.valueOf(birthday.getValue()));
            newStaff.setPassword(password.getText());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/Staff/StaffForm/staffs-view.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 900, 600);
            scene.getStylesheets().add(getClass().getResource("/Admin/Staff/CSS/staffs.css").toExternalForm());
            stage.setResizable(false);
            UserController controller = loader.getController();
            dao.addStaff(newStaff);
            controller.updateUserList();
            stage.setScene(scene);
            stage.show();
        }
    }
    @FXML
    public void switchToUserList(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/Staff/StaffForm/staffs-view.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 900, 600);
        scene.getStylesheets().add(getClass().getResource("/Admin/Staff/CSS/staffs.css").toExternalForm());
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
