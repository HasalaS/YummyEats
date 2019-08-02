package lk.sliit.yummyeats.Model;

public class Deliver {
    private String Mobile;
    private String Name;
    private String Password;
    private String Email;
    private String VehicleNo;

    public Deliver() {
    }

    public Deliver(String name, String password, String email, String vehicleNo) {
        Name = name;
        Password = password;
        Email = email;
        VehicleNo = vehicleNo;
    }

    public Deliver(String mobile, String name, String password, String email, String vehicleNo) {
        Mobile = mobile;
        Name = name;
        Password = password;
        Email = email;
        VehicleNo = vehicleNo;
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

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getVehicleNo() {
        return VehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        VehicleNo = vehicleNo;
    }
}
