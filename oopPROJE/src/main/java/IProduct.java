// Ürün nesneleri için temel arayüz (interface)
public interface IProduct {
    // Ürünün benzersiz ID'sini döndüren metod
    int getId();

    // Ürünün adını döndüren metod
    String getName();

    // Ürünün fiyatını döndüren metod
    double getPrice();
}