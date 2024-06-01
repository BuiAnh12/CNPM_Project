package Admin.Controller.Event;

import Admin.Model.Event.EventModel;
import Admin.Model.Event.Organization;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import jdk.jfr.Event;
import Exception.InputHandle;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UpdateController {
    private EventModel object;

    private InputHandle inputHandle = new InputHandle();
    public EventModel getObject() {
        return object;
    }

    public void setObject(EventModel object) {
        this.object = object;
    }

    @FXML
    private AnchorPane insertForm;

    @FXML
    private DatePicker dateDeadline;

    @FXML
    private DatePicker dateOccur;

    @FXML
    private TextArea txtDetail;

    @FXML
    private TextField txtMaxSlot;

    @FXML
    private TextField txtName;

    @FXML
    private ComboBox<Organization> cmbOrganization;

    @FXML
    private TextField txtPlace;

    private int user;

    @FXML
    private TextField txtStatus;

    @FXML
    private TextField txtEnable;

    List<Organization> organizationList = new ArrayList<Organization>();

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void updateFields() {
        if (object != null) {
            dateOccur.setValue(object.getOccurDate());
            dateDeadline.setValue(object.getDeadline());
            txtDetail.setText(object.getDetail());
            txtMaxSlot.setText(String.valueOf(object.getMaxSlot()));
            txtName.setText(object.getEventName());
            cmbOrganization.setConverter(new StringConverter<Organization>() {
                @Override
                public String toString(Organization organization) {
                    return organization.getName(); // Display organization name
                }

                @Override
                public Organization fromString(String string) {
                    // Not needed for ComboBox
                    return null;
                }
            });
            EventDAO dao = new EventDAO();
            try {
                organizationList = dao.getAllOrganizations();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            for (Organization organization : organizationList) {
                cmbOrganization.getItems().add(organization);
            }

            // Optionally, set a default value for the ComboBox
            if (!organizationList.isEmpty()) {
                String organizationName = object.getOrganizationName();

                // Find the Organization object with the specified name
                Organization selectedOrganization = organizationList.stream()
                        .filter(org -> org.getName().equals(organizationName))
                        .findFirst()
                        .orElse(null);

                // Set the selected Organization as the value of the ComboBox
                cmbOrganization.setValue(selectedOrganization);

            }
            txtPlace.setText(object.getPlace());
            txtStatus.setText(Boolean.toString(object.isStatus()));
            txtEnable.setText("Enable");
        }
    }
    @FXML
    void AcceptClickBtn(MouseEvent event) {
        System.out.println("Update btn accpt click");
        // Check for empty field
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
        if (inputHandle.isNumber(txtMaxSlot.getText())){
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
        int organizationId = cmbOrganization.getSelectionModel().getSelectedItem().getId();
        int maxSlot = Integer.parseInt(txtMaxSlot.getText());
        String detail = txtDetail.getText();
        boolean status = Boolean.parseBoolean(txtStatus.getText());
        String text = txtEnable.getText();
        boolean enable;

        if (text.equalsIgnoreCase("Enable")) {
            enable = true;
        } else if (text.equalsIgnoreCase("Disable")) {
            enable = false;
        } else {
            enable = true;
        }

        // Call the insertEvent method
        EventDAO eventDAO = new EventDAO();
        boolean success = eventDAO.insertOrUpdateEvent(object.getEventId(), name, occurDate, place, organizationId, maxSlot, deadline, detail,status, enable,user, null);
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
