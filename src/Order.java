import java.util.List;
import java.io.Serializable;

public class Order implements Serializable {
    private static final long serialVersionUID = 1L;
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
