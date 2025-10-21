import java.util.ArrayList;
import java.util.List;

public class OrderManager {
    // Singleton pattern için tek instance
    private static OrderManager instance;
    // Tüm siparişlerin listesi
    private List<Order> orders;

    // Private constructor - dışarıdan nesne oluşturulmasını engeller
    private OrderManager() {
        orders = new ArrayList<>();
    }

    // Singleton instance'ını döndüren metod
    public static OrderManager getInstance() {
        if (instance == null) {
            instance = new OrderManager();
        }
        return instance;
    }

    // Yeni sipariş ekleme metodu
    public void addOrder(Order order) {
        orders.add(order);
    }

    // Tüm siparişleri döndüren metod
    public List<Order> getAllOrders() {
        return orders;
    }
}