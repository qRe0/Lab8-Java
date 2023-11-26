import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface FoodOrderService extends Remote {
    List<Dish> getMenu() throws RemoteException;
    String placeOrder(Order order) throws RemoteException;
}