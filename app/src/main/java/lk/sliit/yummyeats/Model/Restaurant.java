package lk.sliit.yummyeats.Model;

public class Restaurant {
    private String Mobile;
    private String Name;
    private String Password;
    private String Address;
    private String Email;

    public Restaurant() {
    }

    public Restaurant(String name, String password, String address, String email) {
        Name = name;
        Password = password;
        Address = address;
        Email = email;
    }

    public Restaurant(String mobile, String name, String password, String address, String email) {
        Mobile = mobile;
        Name = name;
        Password = password;
        Address = address;
        Email = email;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getName() {
        return Name;
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

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
