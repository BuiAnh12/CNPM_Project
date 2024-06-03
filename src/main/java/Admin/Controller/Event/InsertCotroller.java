package Admin.Controller.Event;

import Admin.Model.Event.Organization;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import Exception.InputHandle;

public class InsertCotroller {
    private Scene previousScene;

    @FXML
    private AnchorPane insertForm;


    @FXML
    private TextArea txtDetail;

    @FXML
    private TextField txtMaxSlot;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtOgranization;

    @FXML
    private DatePicker dateDeadline;

    @FXML
    private DatePicker dateOccur;

    @FXML
    private TextField txtPlace;
    private InputHandle inputHandle = new InputHandle();

    private int user;

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public Scene getPreviousScene() {
        return previousScene;
    }

    public void setPreviousScene(Scene previousScene) {
        this.previousScene = previousScene;
        System.out.println(previousScene);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    void AcceptClickBtn(MouseEvent event) {
        if (txtName.getText().isEmpty()){
            showAlert("Lỗi", "Trường tên sự kiện đang trống");
            return;
        }
        if (txtPlace.getText().isEmpty()){
            showAlert("Lỗi", "Trường địa điểm sự kiện đang trống");
            return;
        }
        if (dateOccur.getValue().toString().isEmpty()){
            showAlert("Lỗi", "Ngày diễn ra sự kiện đang trống");
            return;
        }
        if (dateDeadline.getValue().toString().isEmpty()){
            showAlert("Lỗi", "Ngày hết hạn đăng ký sự kiện đang trống");
            return;
        }
        if (txtMaxSlot.getText().isEmpty()){
            showAlert("Lỗi", "Trường số lượng sinh viên tối đa đang trống");
            return;
        }
        if (!inputHandle.isNumber(txtMaxSlot.getText())){
            showAlert("Lỗi", "Trường số lượng slot tối đa không phải là số");
            return;
        }
        LocalDate deadline = LocalDate.parse(dateDeadline.getValue().toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate occurDate = LocalDate.parse(dateOccur.getValue().toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        if (deadline.isAfter(occurDate)) {
            showAlert("Lỗi", "Ngày hết hạn đăng ký phải trước ngày diễn ra sự kiện");
            return;
        }
        String name = txtName.getText();
        String place = txtPlace.getText();
        String organizationId = txtOgranization.getText();
        int maxSlot = Integer.parseInt(txtMaxSlot.getText());
        String detail = txtDetail.getText();

        EventDAO eventDAO = new EventDAO();
        boolean success = eventDAO.insertOrUpdateEvent(null, name, occurDate, place, organizationId, maxSlot, deadline, detail, true, true, this.user, null);

        if (success) {
            try {
                // Get the stage of the current AnchorPane
                Stage stage = (Stage) insertForm.getScene().getWindow();

                // Close the stage to effectively close the modal dialog
                stage.close();
            } catch (Exception e) {
                System.out.println("Close Fail");
                e.printStackTrace();
            }
        }
        else{
            System.out.println("Call Fail");
        }
    }

    @FXML
    void CancelClickBtn(MouseEvent event) {
        try {
            // Get the stage of the current AnchorPane
            Stage stage = (Stage) insertForm.getScene().getWindow();

            // Close the stage to effectively close the modal dialog
            stage.close();
        } catch (Exception e) {
            System.out.println("Close Fail");
            e.printStackTrace();
        }

    }

}
