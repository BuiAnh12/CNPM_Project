package Admin.Model.Student;

import java.time.LocalDate;

public class StudentModel {
    private String studentId;
    private int classId;
    private String phoneNumber;
    private LocalDate dob; // Đã sửa thành LocalDate
    private boolean enable;
    private String fullName;
    private int accountId;
    private String password;
    private String username; // Thêm trường username

    // Constructors
    public StudentModel() {

    }

    public StudentModel(String studentId, String username, int classId) {
        this.studentId = studentId;
        this.username = username;
        this.classId = classId;
    }

    public StudentModel(String studentId, String username, int classId, String phoneNumber, LocalDate dob, boolean enable, String fullName, int accountId, String password) {
        this.studentId = studentId;
        this.username = username;
        this.classId = classId;
        this.phoneNumber = phoneNumber;
        this.dob = dob;
        this.enable = enable;
        this.fullName = fullName;
        this.accountId = accountId;
        this.password = password;
    }

    public StudentModel(String studentId, int classId, String phoneNumber, LocalDate dob, boolean enable, String fullName, int accountId) {
        this.studentId = studentId;
        this.classId = classId;
        this.phoneNumber = phoneNumber;
        this.dob = dob;
        this.enable = enable;
        this.fullName = fullName;
        this.accountId = accountId;
    }

    public StudentModel(String studentId, int classId, String phoneNumber, LocalDate dob, String fullName) {
        this.studentId = studentId;
        this.classId = classId;
        this.phoneNumber = phoneNumber;
        this.dob = dob;
        this.fullName = fullName;
    }

    // Getters and setters
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) { // Cập nhật setDob để chấp nhận LocalDate
        this.dob = dob;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // toString method
    @Override
    public String toString() {
        return "Student{" +
                "studentId='" + studentId + '\'' +
                ", username='" + username + '\'' +
                ", classId=" + classId +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", dob=" + dob + // Cập nhật hiển thị của dob
                ", enable=" + enable +
                ", fullName='" + fullName + '\'' +
                ", accountId=" + accountId +
                ", password='" + password + '\'' +
                '}';
    }
}
