import java.util.List;

public class Order {
    // Siparişteki ürünlerin listesi
    private List<Product> items;
    // Siparişin toplam tutarı
    private double total;

    // Sipariş constructor'ı - ürün listesi alır ve toplam tutarı hesaplar
    public Order(List<Product> items) {
        this.items = items;
        // Stream API kullanarak tüm ürünlerin fiyatlarını toplayıp toplam tutarı hesaplama
        this.total = items.stream().mapToDouble(Product::getPrice).sum();
    }

    // Siparişin toplam tutarını döndüren metod
    public double getTotal() {
        return total;
    }

    // Siparişteki ürünlerin listesini döndüren metod
    public List<Product> getItems() {
        return items;
    }
}