public class Product implements IProduct {
    // Ürün özellikleri
    private int id;
    private String name;
    private double price;

    // Product constructor - ürün bilgilerini alır ve atar
    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    // IProduct arayüzünden gelen getId metodunu implement etme
    @Override
    public int getId() {
        return id;
    }

    // IProduct arayüzünden gelen getName metodunu implement etme
    @Override
    public String getName() {
        return name;
    }

    // IProduct arayüzünden gelen getPrice metodunu implement etme
    @Override
    public double getPrice() {
        return price;
    }

    // Ürünün string temsilini döndüren metod
    @Override
    public String toString() {
        return name + " - ₺" + price;
    }
}