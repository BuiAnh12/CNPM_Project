package Admin.Controller;

import Admin.Model.Event.EventModelDashboard;
import Admin.Model.Event.ReportModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.util.Callback;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private TableView<EventModelDashboard> nextEventTable;

    @FXML
    private TableColumn<EventModelDashboard, String> nameColumn;

    @FXML
    private TableColumn<EventModelDashboard, String> timeColumn;

    @FXML
    private TableView<String> recentRegisterTable;

    @FXML
    private TableColumn<String, String> recentRegisterColumn;

    @FXML
    private TableView<ReportModel> reportTable;

    @FXML
    private TableColumn<ReportModel, Integer> totalEventColumn;

    @FXML
    private TableColumn<ReportModel, Integer> totalStudentColumn;

    private DashboardDAO dashboardDAO;

    public DashboardController() {
        this.dashboardDAO = new DashboardDAO();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize Event Table columns
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        timeColumn.setCellValueFactory(cellData -> cellData.getValue().occurDateProperty());

        // Call method to load event data from database
        loadEventDataFromDatabase();

        dashboardDAO = new DashboardDAO();

        // Khởi tạo cột cho bảng báo cáo
        totalEventColumn.setCellValueFactory(cellData -> cellData.getValue().totalEventProperty().asObject());
        totalStudentColumn.setCellValueFactory(cellData -> cellData.getValue().totalStudentProperty().asObject());

        // Initialize Recent Register Table column
        //if (recentRegisterColumn != null) {
        recentRegisterColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()));

        // }




        // Load dữ liệu báo cáo
        loadReportData();

        // Set up double-click event handler for nextEventTable
        nextEventTable.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                EventModelDashboard selectedEvent = nextEventTable.getSelectionModel().getSelectedItem();
                if (selectedEvent != null) {
                    loadRecentRegisterData(selectedEvent.getName());
                }
            }
        });
    }

    private void loadEventDataFromDatabase() {
        ObservableList<EventModelDashboard> eventList = FXCollections.observableArrayList();
        try {
            // Get next events from database
            eventList.addAll(dashboardDAO.getNextEvents());
        } catch (Exception e) {
            System.out.println("Lỗi ở đây");
            e.printStackTrace();
        }
        // Set the items of the Event TableView
        nextEventTable.setItems(eventList);
    }

    private void loadReportData() {
        ObservableList<ReportModel> reportList = FXCollections.observableArrayList();

        // Lấy dữ liệu từ DAO
        ReportModel reportModel = dashboardDAO.getTotals();
        if (reportModel != null) {
            reportList.add(reportModel);
        }

        // Đặt dữ liệu vào bảng
        reportTable.setItems(reportList);
    }

    private void loadRecentRegisterData(String eventName) {
        ObservableList<String> recentRegisterList = FXCollections.observableArrayList();
        try {
            // Get student full names registered for the event from database
            List<String> studentNames = dashboardDAO.getStudentNamesByEvent(eventName);
            recentRegisterList.addAll(studentNames);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Set the items of the Recent Register TableView
        recentRegisterTable.setItems(recentRegisterList);
    }
}
