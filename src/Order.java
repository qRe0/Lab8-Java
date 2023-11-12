import java.util.List;

public class Order {
    private List<Dish> dishes;
    private String deliveryAddress;

    public Order(List<Dish> dishes, String deliveryAddress) {
        this.dishes = dishes;
        this.deliveryAddress = deliveryAddress;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }
}
