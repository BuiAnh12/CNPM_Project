package Admin.Controller.Student;

import Admin.Controller.Event.EventDAO;
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

    public class InsertController {
        private Scene previousScene;

        @FXML
        private AnchorPane insertForm;

        @FXML
        private TextField txtClass;

        @FXML
        private TextField txtDateOfBirth;

        @FXML
        private TextField txtFullName;

        @FXML
        private TextField txtPassword;

        @FXML
        private TextField txtPhoneNumber;

        @FXML
        private TextField txtUsername;
        public Scene getPreviousScene() {
            return previousScene;
        }

        public void setPreviousScene(Scene previousScene) {
            this.previousScene = previousScene;
            System.out.println(previousScene);
        }

        private int user;

        public int getUser() {
            return user;
        }

        public void setUser(int user) {
            this.user = user;
        }

        @FXML
        void AcceptClickBtn(MouseEvent event) {

        }

    @FXML
    void CancelClickBtn(MouseEvent event) {
        try {
            AnchorPane currentContainer = (AnchorPane) this.insertForm.getParent();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Admin/Student/StudentForm/MainAdminForm.fxml"));
            Parent page = fxmlLoader.load();
            currentContainer.getChildren().add(page);
        } catch (IOException e) {
            System.out.println("Open Fail");
            e.printStackTrace();
        }

    }

        public TextField getTxtClass() {
            return txtClass;
        }

        public void setTxtClass(TextField txtClass) {
            this.txtClass = txtClass;
        }
    }
