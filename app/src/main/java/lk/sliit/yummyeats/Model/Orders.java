package lk.sliit.yummyeats.Model;

public class Orders {

    private String foodId;
    private String customerId;
    private String address;

    public Orders() {
    }

    public Orders(String foodId, String customerId, String address) {
        this.foodId = foodId;
        this.customerId = customerId;
        this.address = address;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
