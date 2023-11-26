import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class FoodOrderServer extends UnicastRemoteObject implements FoodOrderService {
    private static final long serialVersionUID = 1L;
    private List<Dish> menu;

    public FoodOrderServer(List<Dish> menu) throws RemoteException {
        super();
        this.menu = menu;
    }

    public List<Dish> getMenu() throws RemoteException {
        return menu;
    }

    public String placeOrder(Order order) throws RemoteException {
        // Логика обработки заказа
        System.out.println("Клиент отправил заказ:");
        System.out.println("Адрес доставки: " + order.getDeliveryAddress());
        System.out.println("Список блюд в заказе:");
        for (Dish dish : order.getDishes()) {
            System.out.println(dish.getName() + " - $" + dish.getPrice());
        }
        System.out.println("Общая сумма заказа: $" + order.getTotalPrice());

        return "Ваш заказ на сумму $" + order.getTotalPrice() + " принят и будет доставлен по указанному адресу.";
    }

    public static void main(String[] args) {
        try {
            // Создание списка блюд
            List<Dish> menu = new ArrayList<>();
            menu.add(new Dish("1. Пицца", 10.99));
            menu.add(new Dish("2. Паста", 8.99));
            menu.add(new Dish("3. Салат", 5.99));
            menu.add(new Dish("4. Суп", 3.99));
            menu.add(new Dish("5. Сэндвич", 4.99));
            menu.add(new Dish("6. Картофель фри", 2.99));
            menu.add(new Dish("7. Картофельное пюре", 2.99));
            menu.add(new Dish("8. Картофель по-деревенски", 2.99));
            menu.add(new Dish("9. Стейк", 17.99));
            menu.add(new Dish("10. Курица на гриле", 12.99));
            menu.add(new Dish("11. Куриные крылышки", 8.99));
            menu.add(new Dish("12. Куриные наггетсы", 7.99));
            menu.add(new Dish("13. Паста Болоньезе", 7.99));

            // Создание удаленного объекта
            FoodOrderServer foodOrderServer = new FoodOrderServer(menu);

            // Регистрация удаленного объекта в RMI Registry
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("FoodOrderService", foodOrderServer);

            System.out.println("Сервер запущен...");

            while (true) {
                Thread.sleep(1000); // Пауза для предотвращения закрытия сервера
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
