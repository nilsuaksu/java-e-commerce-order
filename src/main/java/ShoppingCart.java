import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    // Sepetteki ürünlerin listesi
    private List<Product> cartItems = new ArrayList<>();

    // Sepete ürün ekleme metodu
    public void addProduct(Product product) {
        cartItems.add(product);
    }

    // Ürünü ismine göre sepetten çıkarma metodu
    public boolean removeByName(String name) {
        for (Product p : cartItems) {
            // Büyük/küçük harf duyarsız karşılaştırma
            if (p.getName().equalsIgnoreCase(name)) {
                cartItems.remove(p);
                return true; // Ürün bulunup çıkarıldı
            }
        }
        return false; // Ürün bulunamadı
    }

    // Sepetteki tüm ürünleri döndüren metod
    public List<Product> getItems() {
        return cartItems;
    }

    // Sepetteki ürünlerin toplam fiyatını hesaplayan metod
    public double getTotal() {
        return cartItems.stream().mapToDouble(Product::getPrice).sum();
    }

    // Sepeti temizleme metodu
    public void clear() {
        cartItems.clear();
    }
}