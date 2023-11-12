import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class FoodOrderClient {
    public static void main(String[] args) {
        try (
                Socket socket = new Socket("localhost", 12345);
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream())
        ) {
            // Получение меню от сервера
            List<Dish> menu = (List<Dish>) in.readObject();
            System.out.println("Меню блюд:");

            for (Dish dish : menu) {
                System.out.println(dish.getName() + " - $" + dish.getPrice());
            }

            // Создание заказа (здесь нужно реализовать ввод с клавиатуры или другой пользовательский интерфейс)
            List<Dish> orderItems = List.of(menu.get(0), menu.get(1)); // Пример заказа с первыми двумя блюдами
            Order order = new Order(orderItems, "Улица, дом");

            // Отправка заказа серверу
            out.writeObject(order);

            // Получение подтверждения от сервера
            String confirmation = (String) in.readObject();
            System.out.println("Подтверждение от сервера: " + confirmation);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
