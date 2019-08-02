package lk.sliit.yummyeats.Model;

public class Customer {
    private String Mobile;
    private String Name;
    private String Password;
    private String Email;

    public String getName() {
        return Name;
    }

    public Customer(String name, String password, String email) {
        Name = name;
        Password = password;
        Email = email;
    }

    public Customer(String mobile, String name, String password, String email) {
        Mobile = mobile;
        Name = name;
        Password = password;
        Email = email;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public Customer() {
    }
}
