package Admin.Controller.Event;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class AttendaceController {

    @FXML
    private AnchorPane DashbaordForm;

    @FXML
    private TextField SearchField;

    @FXML
    private TableColumn<?, ?> attendaceColumn;

    @FXML
    private TableColumn<?, ?> classColumn;

    @FXML
    private TableColumn<?, ?> dobColumn;

    @FXML
    private TableColumn<?, ?> idColumn;

    @FXML
    private TableView<?> mainTable;

    @FXML
    private TableColumn<?, ?> nameColumn;

    @FXML
    private TableColumn<?, ?> phoneColumn;

    @FXML
    private Button revertBtn;

    @FXML
    private Button saveBtn;

    @FXML
    void EditBtnClickEvent(MouseEvent event) {

    }

    @FXML
    void InsertBtnClickEvent(MouseEvent event) {

    }

    @FXML
    void RevertBtnClick(ActionEvent event) {

    }

    @FXML
    void SaveBtnClick(ActionEvent event) {

    }

    @FXML
    void SearchTxtType(KeyEvent event) {

    }

    @FXML
    void TblClickEvent(MouseEvent event) {

    }

}
