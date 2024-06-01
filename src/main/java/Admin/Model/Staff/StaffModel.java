package Admin.Model.Staff;

import javafx.scene.control.CheckBox;

import java.sql.Date;
import java.time.LocalDate;

public class StaffModel {
    private int id;
    private String name;
    private String username;
    private String password;
    private int permissionId;

    private String permissionName;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private boolean enable;

    private int accountId;

    public StaffModel() {
        this.id = -1;
        this.name = "";
        this.username = "";
        this.password = "";
        this.permissionId = -1;
        this.permissionName = "";
        this.phoneNumber = "";
        this.dateOfBirth = LocalDate.of(1970, 1, 1);
        this.enable = false;
        this.accountId = 0;
    }

    public StaffModel(int id, String name, String username, String password, int permissionId, String permissionName, String phoneNumber, LocalDate dateOfBirth, boolean enable, int accountId) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.permissionId = permissionId;
        this.permissionName = permissionName;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.enable = enable;
        this.accountId = accountId;
    }


    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(int permissionId) {
        this.permissionId = permissionId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }


}
