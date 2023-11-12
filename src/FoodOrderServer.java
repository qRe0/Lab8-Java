import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class FoodOrderServer {
    private static List<Dish> menu = new ArrayList<>();

    static {
        // Заполните меню блюдами и ценами
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
        // Добавьте другие блюда
    }

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Сервер запущен. Ожидание подключений...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Подключен клиент: " + clientSocket.getInetAddress());

                // Создание отдельного потока для каждого клиента
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket clientSocket;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try (
                    ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                    ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())
            ) {
                // Отправка меню клиенту
                out.writeObject(menu);

                // Получение заказа от клиента
                Order order = (Order) in.readObject();
                System.out.println("Получен заказ от клиента:");
                System.out.println("Адрес доставки: " + order.getDeliveryAddress());
                System.out.println("Список блюд:");

                for (Dish dish : order.getDishes()) {
                    System.out.println(dish.getName() + " - $" + dish.getPrice());
                }

                // Обработка заказа (может потребоваться вызов сервиса доставки и т.д.)

                // Отправка подтверждения клиенту
                out.writeObject("Ваш заказ принят и будет доставлен по указанному адресу.");

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
