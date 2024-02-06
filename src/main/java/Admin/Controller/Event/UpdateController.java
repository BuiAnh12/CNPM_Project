package Admin.Controller.Event;

import Admin.Model.Event.EventModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class UpdateController {
    private EventModel object;

    public EventModel getObject() {
        return object;
    }

    public void setObject(EventModel object) {
        this.object = object;
    }

    @FXML
    private AnchorPane insertForm;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtDeadline;

    @FXML
    private TextArea txtDetail;

    @FXML
    private TextField txtMaxSlot;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtOrganization;

    @FXML
    private TextField txtPlace;

    public void updateFields() {
        if (object != null) {
            txtDate.setText(object.getOccurDate().toString());
            txtDeadline.setText(object.getDeadline().toString());
            txtDetail.setText(object.getDetail());
            txtMaxSlot.setText(String.valueOf(object.getMaxSlot()));
            txtName.setText(object.getEventName());
            txtOrganization.setText(object.getOrganizationName());
            txtPlace.setText(object.getPlace());
        }
    }
    @FXML
    void AcceptClickBtn(MouseEvent event) {
        String name = txtName.getText();
        LocalDate occurDate = LocalDate.parse(txtDate.getText()); // Assuming date format is correct
        String place = txtPlace.getText();
        int organizationId = Integer.parseInt(txtOrganization.getText());
        int maxSlot = Integer.parseInt(txtMaxSlot.getText());
        LocalDate deadline = LocalDate.parse(txtDeadline.getText()); // Assuming date format is correct
        String detail = txtDetail.getText();

        // Call the insertEvent method
        EventDAO eventDAO = new EventDAO();
        boolean success = eventDAO.updateEvent(object.getEventId(), name, occurDate, place, organizationId, maxSlot, deadline, detail);
        if (success) {
            try {
                AnchorPane currentContainer = (AnchorPane) this.insertForm.getParent();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Admin/Event/EventForm/MainForm.fxml"));
                Parent page = fxmlLoader.load();
                currentContainer.getChildren().add(page);
            } catch (IOException e) {
                System.out.println("Open Fail");
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
            AnchorPane currentContainer = (AnchorPane) this.insertForm.getParent();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Admin/Event/EventForm/MainForm.fxml"));
            Parent page = fxmlLoader.load();
            currentContainer.getChildren().add(page);
        } catch (IOException e) {
            System.out.println("Open Fail");
            e.printStackTrace();
        }
    }


}
