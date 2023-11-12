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

    public double getTotalPrice() {
        double totalPrice = 0.0;
        for (Dish dish : dishes) {
            totalPrice += dish.getPrice();
        }
        return totalPrice;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }
}

//
