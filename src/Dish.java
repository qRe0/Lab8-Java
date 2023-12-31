import java.io.Serializable;

public class Dish implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private double price;

    public Dish(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
