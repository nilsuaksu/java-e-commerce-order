import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    // Veritabanı bağlantı bilgileri
    private static final String URL = "jdbc:mysql://localhost:3306/ecommerce";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Veritabanına bağlantı kuran metod
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Tüm ürünleri veritabanından çeken metod
    public static List<Product> fetchProducts() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            // ResultSet'ten ürün bilgilerini alıp listeye ekleme
            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    // Yeni kullanıcı kaydı yapan metod
    public static boolean registerUser(String email, String password) {
        try (Connection conn = connect()) {
            String query = "INSERT INTO users (email, password) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            // Parametreleri query'ye set etme
            stmt.setString(1, email);
            stmt.setString(2, password);
            stmt.executeUpdate();
            return true; // Kayıt başarılı
        } catch (SQLException e) {
            System.out.println("Kayıt başarısız: " + e.getMessage());
            return false; // Kayıt başarısız
        }
    }

    // Kullanıcı giriş kontrolü yapan metod
    public static boolean loginUser(String email, String password) {
        try (Connection conn = connect()) {
            String query = "SELECT * FROM users WHERE email = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            // Email ve password parametrelerini set etme
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Kayıt bulunursa true, bulunamazsa false döner
        } catch (SQLException e) {
            System.out.println("Giriş başarısız: " + e.getMessage());
            return false;
        }
    }

    // Benzersiz kategorileri çeken metod
    public static List<String> fetchCategories() {
        List<String> categories = new ArrayList<>();
        try (Connection conn = connect()) {
            String query = "SELECT DISTINCT category FROM products";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            // Kategorileri listeye ekleme
            while (rs.next()) {
                categories.add(rs.getString("category"));
            }
        } catch (SQLException e) {
            System.out.println("Kategori çekme hatası: " + e.getMessage());
        }
        return categories;
    }

    // Belirli bir kategoriye ait ürünleri çeken metod
    public static List<Product> fetchProductsByCategory(String category) {
        List<Product> products = new ArrayList<>();
        try (Connection conn = connect()) {
            String query = "SELECT id, name, price FROM products WHERE category = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            // Kategori parametresini set etme
            stmt.setString(1, category);
            ResultSet rs = stmt.executeQuery();
            // Ürün bilgilerini alıp listeye ekleme
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                products.add(new Product(id, name, price));
            }
        } catch (SQLException e) {
            System.out.println("Kategoriye göre ürün çekme hatası: " + e.getMessage());
        }
        return products;
    }

}