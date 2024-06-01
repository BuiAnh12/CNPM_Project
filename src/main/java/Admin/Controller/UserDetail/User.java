package Admin.Controller.UserDetail;

public class User {
    private String username;
    private String password;
    private String phoneNumber;
    private String dob;
    private String fullName;

    // Thêm constructor và các phương thức getter và setter ở đây

    // Ví dụ:
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getDob()
    {
        return dob;
    }

    public void setDob(String dob)
    {
        this.dob = dob;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }
    public String getFullName()
    {
        return  fullName;
    }
    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }
    // Thêm các phương thức getter và setter cho các trường khác tương tự
}

