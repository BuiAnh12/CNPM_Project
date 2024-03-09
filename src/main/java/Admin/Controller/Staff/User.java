package Admin.Controller.Staff;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;

import java.time.LocalDate;

public class User {
    private CheckBox select;
    private SimpleIntegerProperty id;
    private SimpleStringProperty name, phoneNumber, userName, password;
    private LocalDate dateOfBirth;


//    public User(int id,CheckBox select, String name, String phoneNumber, String userName, String password, LocalDate dateOfBirth) {
//        this.id = new SimpleIntegerProperty(id);
//        this.select = select;
//        this.select.setSelected(false);
//        this.name = new SimpleStringProperty(name);
//        this.phoneNumber = new SimpleStringProperty(phoneNumber);
//        this.userName = new SimpleStringProperty(userName);
//        this.password = new SimpleStringProperty(password);
//        this.dateOfBirth = dateOfBirth;
//    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public CheckBox getSelect() {
        return select;
    }

    public void setSelect(CheckBox select) {
        this.select = select;
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public SimpleStringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public String getUserName() {
        return userName.get();
    }

    public SimpleStringProperty userNameProperty() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    public String getPassword() {
        return password.get();
    }

    public SimpleStringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
