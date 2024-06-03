package Admin.Controller;

import Admin.Model.Event.EventModelDashboard;
import Admin.Model.Event.ReportModel ;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private TableView<EventModelDashboard> nextEventTable;

    @FXML
    private TableColumn<EventModelDashboard, String> nameColumn;

    @FXML
    private TableColumn<EventModelDashboard, String> timeColumn;

    @FXML
    private TableView<ReportModel> reportTable;

    @FXML
    private TableColumn<ReportModel, Integer> totalEventColumn;

    @FXML
    private TableColumn<ReportModel, Integer> totalStudentColumn;

    @FXML
    private AnchorPane eventMainAnchorPane;

    @FXML
    private Button BackMain;

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

        // Load dữ liệu báo cáo
        loadReportData();
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


}
