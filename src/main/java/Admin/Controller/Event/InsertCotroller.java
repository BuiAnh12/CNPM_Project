package Admin.Controller.Event;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class InsertCotroller {
    private Scene previousScene;

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
    public Scene getPreviousScene() {
        return previousScene;
    }

    public void setPreviousScene(Scene previousScene) {
        this.previousScene = previousScene;
        System.out.println(previousScene);
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
        boolean success = eventDAO.insertEvent(name, occurDate, place, organizationId, maxSlot, deadline, detail);
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
