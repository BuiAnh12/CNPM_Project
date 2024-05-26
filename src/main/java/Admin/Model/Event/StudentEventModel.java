package Admin.Model.Event;

import java.time.LocalDate;

public class StudentEventModel {
    private String studentId;
    private String studentName;
    private String studentClass;
    private String dateOfBirth;
    private String username;
    private String password;
    private String phoneNumber;

    public StudentEventModel(String studentId, String studentName, String studentClass) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentClass = studentClass;
    }

    public StudentEventModel(String studentId, String studentName, String studentClass, String dateOfBirth, String username, String password, String phoneNumber) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentClass = studentClass;
        this.dateOfBirth = dateOfBirth;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFullName() {
        return studentName; // Assuming the full name is just the student's name
    }
}
