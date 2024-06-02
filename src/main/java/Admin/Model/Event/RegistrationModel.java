package Admin.Model.Event;

import javafx.beans.property.BooleanProperty;
import javafx.scene.control.CheckBox;

import java.time.LocalDate;

public class RegistrationModel {
    private String studentId;
    private String studentName;
    private String classes;
    private String phoneNumber;
    private LocalDate dob;
    private boolean status;

    public RegistrationModel(String studentId, String studentName, String classes, String phoneNumber, LocalDate dob, boolean status) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.classes = classes;
        this.phoneNumber = phoneNumber;
        this.dob = dob;
        this.status = status;
    }


    public String getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getClasses() {
        return classes;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public LocalDate getDob() {
        return dob;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status){
        this.status = status;
    }
}
