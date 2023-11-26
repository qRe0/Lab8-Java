import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class FoodOrderClient {
    public static void main(String[] args) {
        try {
            // Получение удаленного объекта из RMI Registry
            Registry registry = LocateRegistry.getRegistry("192.168.0.107", 1099);
            FoodOrderService foodOrderService = (FoodOrderService) registry.lookup("FoodOrderService");

            // Получение меню от сервера
            List<Dish> menu = foodOrderService.getMenu();
            System.out.println("Меню блюд:");

            for (Dish dish : menu) {
                System.out.println(dish.getName() + " - $" + dish.getPrice());
            }

            // Создание заказа
            List<Dish> orderItems = getOrderFromUser(menu);
            String deliveryAddress = getDeliveryAddressFromUser();

            Order order = new Order(orderItems, deliveryAddress);

            // Отправка заказа серверу
            String confirmation = foodOrderService.placeOrder(order);
            System.out.println("Подтверждение от сервера: " + confirmation);

        } catch (Exception e) {
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

//    // Создание списка блюд
//    List<Dish> menu = new ArrayList<>();
//            menu.add(new Dish("1. Пицца", 10.99));
//                    menu.add(new Dish("2. Паста", 8.99));
//                    menu.add(new Dish("3. Салат", 5.99));
//                    menu.add(new Dish("4. Суп", 3.99));
//                    menu.add(new Dish("5. Сэндвич", 4.99));
//                    menu.add(new Dish("6. Картофель фри", 2.99));
//                    menu.add(new Dish("7. Картофельное пюре", 2.99));
//                    menu.add(new Dish("8. Картофель по-деревенски", 2.99));
//                    menu.add(new Dish("9. Стейк", 17.99));
//                    menu.add(new Dish("10. Курица на гриле", 12.99));
//                    menu.add(new Dish("11. Куриные крылышки", 8.99));
//                    menu.add(new Dish("12. Куриные наггетсы", 7.99));
//                    menu.add(new Dish("13. Паста Болоньезе", 7.99));
