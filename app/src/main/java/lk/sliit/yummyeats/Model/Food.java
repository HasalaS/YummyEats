package lk.sliit.yummyeats.Model;

public class Food {
    private String id;
    private String name;
    private String description;
    private String price;
    private String category;
    private String image;
    private String restaurantMobile;
    private String restaurant;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRestaurantMobile() {
        return restaurantMobile;
    }

    public void setRestaurantMobile(String restaurantMobile) {
        this.restaurantMobile = restaurantMobile;
    }

    public Food() {
    }

    public Food(String name, String description, String price, String category, String image, String resturantMobile, String restaurant) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.image = image;
        this.restaurantMobile = resturantMobile;
        this.restaurant = restaurant;
    }

    public String getResturantMobile() {
        return restaurantMobile;
    }

    public void setResturantMobile(String resturantMobile) {
        this.restaurantMobile = resturantMobile;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
