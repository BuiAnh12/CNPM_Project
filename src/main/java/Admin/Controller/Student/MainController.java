package Admin.Controller.Student;

import Admin.Controller.Event.EventDAO;
import Admin.Model.Event.EventModel;
import Admin.Model.Event.StudentEventModel;
import Admin.Model.Student.StudentModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;


import javafx.collections.FXCollections;
import javafx.scene.control.TableView;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import java.util.List;


import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainController implements Initializable {

    @FXML
    public TableView<StudentModel> mainTable;
    public Button insertFile;

    @FXML
    private ComboBox<String> Filter;

    @FXML
    private ImageView SearchBtn;

    @FXML
    private TextField SearchField;
    public TableColumn<StudentEventModel, String> idColumn;
    public TableColumn <StudentEventModel, String> nameColumn;
    public TableColumn<StudentEventModel, String> classColumn;
    public TableColumn <StudentEventModel, String> phoneColumn;
    public AnchorPane DashbaordForm;
    public TableColumn<StudentEventModel, LocalDate> dobColumn;
    private StudentDAO studentDAO;
    private StudentModel StudentModels;

    private int permissionId = 0;

    public void setPermissionId(int permissionId) {
        this.permissionId = permissionId;
    }

    private String jdbcUrl = "jdbc:sqlserver://localhost:1433;databaseName=EventManagement;encrypt=true;trustServerCertificate=true";
    private String dbUsername = "cnpm";
    private String dbPassword = "123";

    public MainController() {
        this.studentDAO = new StudentDAO();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        ObservableList<String> range = FXCollections.observableArrayList(
                "Sinh Viên","Id Sinh Viên",
                "Id Lớp"
        );

        Filter.setItems(range);

        Filter.setValue("Sinh Viên");

        Filter.setOnAction(event -> updateTableView());


        List<StudentModel> students = this.studentDAO.getAllStudents();

        setupTable(students);


    }

//    @FXML
//    void EditBtnClickEvent(MouseEvent event) {
//
//        try{
//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Admin/Student/StudentForm/UpdateForm.fxml"));
//            Parent page = fxmlLoader.load();
//
//            Scene currentScene = mainTable.getScene();
//            Stage currentStage = (Stage) mainTable.getScene().getWindow();
//
//            UpdateController updateController = fxmlLoader.getController();
//            updateController.setObject(mainTable.getSelectionModel().getSelectedItem());
//            updateController.updateFields();
//            AnchorPane Container = (AnchorPane) DashbaordForm.getParent();
//            Container.getChildren().clear();
//            Container.getChildren().add(page);
//
//        } catch (IOException e) {
//            System.out.println("Open Fail");
//            e.printStackTrace();
//        }
//    }

    @FXML
    void SearchTxtType(KeyEvent event) {
        updateTableView();
    }

    @FXML
    void TblClickEvent(MouseEvent event) {

    }

    @FXML
    void GetFilter(MouseEvent event) {

    }

    @FXML
    void GetFilterOrder(MouseEvent event) {

    }

    @FXML
    void InsertFileBtnClickEvent(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx", "*.xls"));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            readExcelFile(file);
        }
    }

    // Sdt, studentID , username
    // none empty

    private void readExcelFile(File file) {
        List<StudentModel> validRowsStudent = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) { // Skip header row
                    continue;
                }

                // Get cell values
                String studentId = getCellValueAsString(row.getCell(0));
                String classId = getCellValueAsString(row.getCell(1));
                String phoneNumber = String.valueOf((long)row.getCell(2).getNumericCellValue());
                String dob = getCellValueAsString(row.getCell(3));
                String fullName = getCellValueAsString(row.getCell(4));
                String username = getCellValueAsString(row.getCell(5));
                String password = getCellValueAsString(row.getCell(6));


                // Check for null or empty fields
                if (studentId == null || classId == null || phoneNumber == null || dob == null ||
                        fullName == null || username == null || password == null ||
                        studentId.isEmpty() || classId.isEmpty() || phoneNumber.isEmpty() ||
                        dob.isEmpty() || fullName.isEmpty() || username.isEmpty() || password.isEmpty()) {
                    showAlert("Insert From Excel Error", "Lỗi thiếu dữ liệu tại dòng " + (row.getRowNum() + 1));
                    return;
                }

                if (phoneNumber.length() != 10) {
                    showAlert("Insert From Excel Error", "Số điện thoại tại dòng " + (row.getRowNum() + 1) + " không đúng định dạng!");
                    return;
                }

                // Check for duplicates in the database
                if (isDuplicateStudent(studentId, phoneNumber) || isDuplicateAccount(username)) {
                    showAlert("Insert From Excel Error", "Dữ liệu tại dòng " + (row.getRowNum() + 1) + " đã tồn tại!");
                    return;
                }

                // Store the row data temporarily
                validRowsStudent.add(new StudentModel(studentId,username, Integer.parseInt(classId), phoneNumber, LocalDate.parse(dob, DateTimeFormatter.ofPattern("yyyy-MM-dd")),true, fullName, 0, password));
            }

            // If no errors, add all valid rows to the database
            for (StudentModel rowData : validRowsStudent) {
                addDataToDatabase(rowData.getStudentId(), String.valueOf(rowData.getClassId()), rowData.getPhoneNumber(),
                        String.valueOf(rowData.getDob()), rowData.getFullName(), rowData.getUsername(), rowData.getPassword());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isDuplicateStudent(String studentId, String phoneNumber) {
        String query = "SELECT COUNT(*) FROM Student WHERE StudentId = ? OR PhoneNumber = ?";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, studentId);
            preparedStatement.setString(2, phoneNumber);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isDuplicateAccount(String username) {
        String query = "SELECT COUNT(*) FROM Account WHERE Username = ?";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }



    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return new SimpleDateFormat("yyyy-MM-dd").format(cell.getDateCellValue());
                } else {
                    return String.valueOf((int) cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }
    private void addDataToDatabase(String studentId, String classId, String phoneNumber, String dob, String fullName, String username, String password) {
        String insertAccountSQL = "INSERT INTO Account (Username, Password) VALUES (?, ?)";
        String insertStudentSQL = "INSERT INTO Student (StudentId, ClassId, PhoneNumber, DOB, Enable, Fullname, AccountID) VALUES (?, ?, ?, ?, 1, ?, ?)";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);
             PreparedStatement accountStmt = connection.prepareStatement(insertAccountSQL, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement studentStmt = connection.prepareStatement(insertStudentSQL)) {

            // Thêm dữ liệu vào bảng Account và lấy AccountID mới được tạo
            accountStmt.setString(1, username);
            accountStmt.setString(2, password);
            int affectedRows = accountStmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating account failed, no rows affected.");
            }

            try (ResultSet generatedKeys = accountStmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int accountId = generatedKeys.getInt(1);

                    // Thêm dữ liệu vào bảng Student
                    studentStmt.setString(1, studentId);
                    studentStmt.setString(2, classId);
                    studentStmt.setString(3, phoneNumber);

                    System.out.println("So dien thoai " + phoneNumber);

                    studentStmt.setDate(4, Date.valueOf(dob)); // Assuming DOB is in yyyy-mm-dd format
                    studentStmt.setString(5, fullName);
                    studentStmt.setInt(6, accountId);

                    studentStmt.executeUpdate();
                } else {
                    throw new SQLException("Creating account failed, no ID obtained.");
                }
            }

            List<StudentModel> students = this.studentDAO.getAllStudents();

            setupTable(students);

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "An error occurred while inserting data into the database.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public String getTypeRange() {
        String typeRange = "";

        if (Filter.getValue().equals("Sinh Viên")) {
            typeRange = "Order By\n" +
                    "LTrim(Reverse(Left(Reverse(FullName), CharIndex(' ', Reverse(FullName))))) Asc,\n" +
                    "FullName Asc";
        } else if (Filter.getValue().equals("Id Lớp")) {
            typeRange = "ORDER BY ClassId DESC";
        } else if (Filter.getValue().equals("Id Sinh Viên")) {
            typeRange = "ORDER BY StudentId ASC";
        } else {
            // Xử lý trường hợp không thỏa mãn
            typeRange = ""; // hoặc giá trị mặc định khác nếu cần
        }

        return typeRange;
    }


    private void updateTableView() {

        String TypeOrder = getTypeRange();

        // Get the selected filter index and sort index
        int filterIndex = Filter.getSelectionModel().getSelectedIndex();
        String searchWords = SearchField.getText();

        // Retrieve data from the database using your EventDAO
        EventDAO eventDAO = new EventDAO();
        List<StudentModel> tableList = studentDAO.getTable(filterIndex, searchWords, TypeOrder);

        // Update the TableView with the new data
        mainTable.getItems().clear(); // Clear existing data
        mainTable.setItems(FXCollections.observableArrayList(tableList));
    }

    @FXML
    void EditBtnClickEvent(MouseEvent event) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Admin/Student/StudentForm/UpdateForm.fxml"));
            Parent page = fxmlLoader.load();

            UpdateController updateController = fxmlLoader.getController();
            updateController.setObject(mainTable.getSelectionModel().getSelectedItem());
            //updateController.updateFields();

            AnchorPane Container = (AnchorPane) DashbaordForm.getParent();
            Container.getChildren().clear();
            Container.getChildren().add(page);

        } catch (IOException e) {
            System.out.println("Open Fail");
            e.printStackTrace();
        }
    }


    @FXML
    void InsertBtnClickEvent(MouseEvent event) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Admin/Student/StudentForm/InsertForm.fxml"));
            Parent page = fxmlLoader.load();

            Scene currentScene = mainTable.getScene();
            Stage currentStage = (Stage) mainTable.getScene().getWindow();

            InsertController insertCotroller = fxmlLoader.getController();
            insertCotroller.setPreviousScene(currentScene);

            AnchorPane Container = (AnchorPane) DashbaordForm.getParent();
            Container.getChildren().clear();
            Container.getChildren().add(page);

        } catch (IOException e) {
            System.out.println("Open Fail");
            e.printStackTrace();
        }

    }

    private void setupTable(List<StudentModel> StudentModels) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        classColumn.setCellValueFactory(new PropertyValueFactory<>("classId"));
        dobColumn.setCellValueFactory(new PropertyValueFactory<>("dob"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));


        ObservableList<StudentModel> data = FXCollections.observableArrayList(StudentModels);
        mainTable.setItems(data);
    }

    public void DeleteBtnClickEvent(MouseEvent mouseEvent) {
        StudentModel selectedStudent = mainTable.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Delete");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete this student?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                studentDAO.deleteStudent(selectedStudent.getStudentId()); // Truyền studentId thay vì selectedStudent
                mainTable.getItems().remove(selectedStudent);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select a student to delete.");
            alert.showAndWait();
        }
    }
}
