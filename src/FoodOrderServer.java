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
        menu.add(new Dish("Пицца", 10.99));
        menu.add(new Dish("Паста", 8.99));
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
                System.out.println("Получен заказ от клиента: " + order.getDeliveryAddress());

                // Обработка заказа (может потребоваться вызов сервиса доставки и т.д.)

                // Отправка подтверждения клиенту
                out.writeObject("Ваш заказ принят и будет доставлен по указанному адресу.");

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
