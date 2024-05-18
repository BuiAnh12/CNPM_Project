package Admin.Controller.Staff;

import Admin.Model.Staff.Staff;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class UserController implements Initializable {

    @FXML
    private TableView<Staff> tableView;
    @FXML
    private TableColumn<User, Boolean> selectedColumn;
    @FXML
    private TableColumn<User, String> nameColumn;
    @FXML
    private TableColumn<User, LocalDate> birthdayColumn;
    @FXML
    private TableColumn<User, String> phoneNumberColumn;
    @FXML
    private TableColumn<User, String> userNameColumn;
    @FXML
    private Button edit;
    @FXML
    private Button insert;
    @FXML
    private Button delete;
    @FXML
    private CheckBox selectAll;
    @FXML
    private TextField textSearch;
    @FXML
    private Button buttonSearch;
    @FXML
    private Button GoEventButton, GoStaffButton, GoStudentForm;
    private StaffIpml dao = new StaffIpml();
    public void updateUserList() {
        tableView.setItems(FXCollections.observableArrayList(dao.getAllStaff()));
    }

    @FXML
    public void search() {
        ObservableList<Staff> staffList = FXCollections.observableArrayList();
        List<Staff> staffs = dao.getAllStaff();
        if (textSearch.getText() != null && !textSearch.getText().trim().equals("")) {

            String keyword = textSearch.getText().trim();
            Pattern pattern = Pattern.compile(keyword, Pattern.CASE_INSENSITIVE);
            staffList = staffs
                    .stream()
                    .filter(staff ->
                            pattern.matcher(staff.getUsername()).find() ||
                                    pattern.matcher(staff.getPhoneNumber()).find() ||
                                    pattern.matcher(DateUtils
                                            .convertLocalDateToStringPattern(staff.getDateOfBirth().toLocalDate(), "dd/MM/yyyy")).find())
                    .collect(Collectors.toCollection(FXCollections::observableArrayList));
            tableView.setItems(staffList);
        } else {
            tableView.setItems(FXCollections.observableArrayList(staffs));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedColumn.setCellValueFactory(new PropertyValueFactory<User, Boolean>("flag")); //lỗi ở đây nhé đượt trước mng sửa db làm cái này bay màu
        nameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        birthdayColumn.setCellValueFactory(new PropertyValueFactory<User, LocalDate>("dateOfBirth"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<User, String>("phoneNumber"));
        userNameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        selectedColumn.setReorderable(false);
        nameColumn.setReorderable(false);
        birthdayColumn.setReorderable(false);
        phoneNumberColumn.setReorderable(false);
        userNameColumn.setReorderable(false);
        tableView.setItems(FXCollections.observableArrayList(dao.getAllStaff()));
    }

    @FXML
    public void selectAllUser(ActionEvent event) {
        CheckBox selectAllCheckBox = (CheckBox) event.getSource();
        boolean selected = selectAllCheckBox.isSelected();
        dao.updateAllFlag(selected);
        updateUserList();
    }

    @FXML
    public void switchToNewUser(ActionEvent event) throws IOException {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/Staff/StaffForm/new-user.fxml"));
            System.out.println(getClass().getResource(""));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1200, 800);
            stage.setResizable(false);
            scene.getStylesheets().add(getClass().getResource("/Admin/Staff/CSS/new-user.css").toExternalForm());
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void openDialogEdit(ActionEvent event) throws IOException {
        Staff selectedStaff = tableView.getSelectionModel().getSelectedItem();
        if (selectedStaff != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/Staff/StaffForm/edit-user.fxml"));
            Parent editDialogRoot = loader.load();

            // Tạo một dialog mới và đặt DialogPane là editDialogPane
            Dialog<String> dialog = new Dialog<>();
            dialog.getDialogPane().setContent(editDialogRoot);
            dialog.setResizable(false);
            Scene dialogScene = dialog.getDialogPane().getScene();
            dialogScene.getStylesheets().add(getClass().getResource("/Admin/Staff/CSS/edit.css").toExternalForm());
            // Lấy controller của edit dialog
            EditController controller = loader.getController();
            controller.setOldData(selectedStaff);
            controller.setTableView(tableView);
            Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
            stage.setOnCloseRequest(e -> {
                dialog.setResult("close");
                dialog.close();
            });
            // Hiển thị dialog và đợi cho đến khi nó đóng
            dialog.show();
            controller.setDialog(dialog);
        }
    }

    @FXML
    public void deleteSelectedUser() throws IOException {
        ObservableList<Staff> deleteUserList = dao.getAllStaffSelected()
                .stream()
                .collect(Collectors
                        .toCollection(FXCollections::observableArrayList));
        if (!deleteUserList.isEmpty()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/Staff/StaffForm/delete.fxml"));
            Parent editDialogRoot = loader.load();
            // Tạo một dialog mới và đặt DialogPane là editDialogPane
            Dialog<String> dialog = new Dialog<>();
            dialog.getDialogPane().setContent(editDialogRoot);
            dialog.setResizable(false);
            Scene dialogScene = dialog.getDialogPane().getScene();
            dialogScene.getStylesheets().add(getClass().getResource("/Admin/Staff/CSS/delete.css").toExternalForm());
            // Lấy controller của edit dialog
            DeleteController controller = loader.getController();
            controller.setTableView(tableView);
            controller.setData(deleteUserList);
            Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
            stage.setOnCloseRequest(e -> {
                dialog.setResult("close");
                dialog.close();
            });
            // Hiển thị dialog và đợi cho đến khi nó đóng
            dialog.show();
            controller.setDialog(dialog);
        }
    }
    @FXML
    private void handleGoEventButtonAction(ActionEvent event) {
        try {
            // Load file fxml mới
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/Event/EventForm/EventMainForm.fxml"));
            Parent root = loader.load();

            // Tạo scene mới với root là node gốc của file fxml mới
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1200, 800);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleGoStudentButtonAction(ActionEvent event) {
        try {
            // Load file fxml mới
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/Student/StudentForm/MainAdminForm.fxml"));
            Parent root = loader.load();

            // Tạo scene mới với root là node gốc của file fxml mới
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1200, 800);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleGoStaffButtonAction(ActionEvent event) {
        try {
            // Load file fxml mới
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/Staff/StaffForm/staffs-view.fxml"));
            Parent root = loader.load();

            // Kiểm tra nếu phần tử gốc là một BorderPane
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//            Scene scene = new Scene(root, 1200, 800);
//            stage.setResizable(false);
//            stage.setScene(scene);
//            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleGoLoginButtonAction(ActionEvent event) {
        try {


            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Login/Login.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 400, 530);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Quản lí hoạt động sinh viên");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}