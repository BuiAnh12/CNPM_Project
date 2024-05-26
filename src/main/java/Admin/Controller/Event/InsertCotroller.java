package Admin.Controller.Event;

import Admin.Model.Event.Organization;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    private ComboBox<Organization> cmbOrganization;

    @FXML
    private DatePicker dateDeadline;

    @FXML
    private DatePicker dateOccur;

    @FXML
    private TextField txtPlace;

    private int user;

    List<Organization> organizationList = new ArrayList<Organization>();
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

    public void updateFields() {
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
            cmbOrganization.setValue(organizationList.get(0)); // Set the first organization as default
        }
    }

    @FXML
    void AcceptClickBtn(MouseEvent event) {
        String name = txtName.getText();
        LocalDate occurDate = LocalDate.parse(dateOccur.getValue().toString()); // Assuming date format is correct
        String place = txtPlace.getText();
        int organizationId = cmbOrganization.getSelectionModel().getSelectedItem().getId();
        int maxSlot = Integer.parseInt(txtMaxSlot.getText());
        LocalDate deadline = LocalDate.parse(dateDeadline.getValue().toString()); // Assuming date format is correct
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
