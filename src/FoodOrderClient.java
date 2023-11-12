import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FoodOrderClient {
    public static void main(String[] args) {
        try (
                Socket socket = new Socket("192.168.0.107", 12345);
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream())
        ) {
            // Получение меню от сервера
            List<Dish> menu = (List<Dish>) in.readObject();
            System.out.println("Меню блюд:");

            for (Dish dish : menu) {
                System.out.println(dish.getName() + " - $" + dish.getPrice());
            }

            // Создание заказа
            List<Dish> orderItems = getOrderFromUser(menu);
            String deliveryAddress = getDeliveryAddressFromUser();

            Order order = new Order(orderItems, deliveryAddress);

            // Отправка заказа серверу
            out.writeObject(order);

            // Получение подтверждения от сервера
            String confirmation = (String) in.readObject();
            System.out.println("Подтверждение от сервера: " + confirmation);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static List<Dish> getOrderFromUser(List<Dish> menu) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите номера блюд из меню (через пробел):");
        String input = scanner.nextLine();

        String[] dishNumbers = input.split(" ");
        List<Dish> orderItems = new ArrayList<>();

        for (String number : dishNumbers) {
            int index = Integer.parseInt(number) - 1;
            if (index >= 0 && index < menu.size()) {
                orderItems.add(menu.get(index));
            }
        }

        return orderItems;
    }

    private static String getDeliveryAddressFromUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите адрес доставки:");
        return scanner.nextLine();
    }
}
