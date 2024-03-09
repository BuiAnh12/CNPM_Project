package Admin.Controller.Staff;

import Admin.Model.Staff.Staff;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class DeleteController implements Initializable {
    private Dialog<String> dialog;
    private TableView<Staff> tableView;

    private ObservableList<Staff> staffDelete = FXCollections.observableArrayList();
    @FXML
    private ComboBox<String> list;
    @FXML
    private Button delete;
    public void setTableView(TableView<Staff> tableView) {
        this.tableView = tableView;
    }
    public Dialog<String> getDialog() {
        return dialog;
    }

    private StaffIpml dao = new StaffIpml();

    public void setDialog(Dialog<String> dialog) {
        this.dialog = dialog;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        list.setItems(FXCollections.observableArrayList(dao.getAllStaffSelected().stream().map(staff1 -> staff1.getUsername()).collect(Collectors.toList())));
    }

    public void setData(ObservableList<Staff> staff) {
        this.staffDelete = staff;
        list.setItems(FXCollections.observableArrayList(dao.getAllStaffSelected().stream().map(staff1 -> staff1.getUsername()).collect(Collectors.toList())));
        list.getItems().add("List of staff");
        list.getSelectionModel().selectLast();
    }

    @FXML
    public void deleteUserSelected() {
        dao.deleteStaffSelected(staffDelete);
        tableView.setItems(FXCollections.observableArrayList(dao.getAllStaff()));
        dialog.setResult("delete");
        dialog.close();
    }
}
