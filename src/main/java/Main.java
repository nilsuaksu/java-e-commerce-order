import javax.swing.SwingUtilities;

public class Main {
    // Ana metod - programın başlangıç noktası
    public static void main(String[] args) {
        // GUI'yi Event Dispatch Thread'de güvenli bir şekilde başlatma
        javax.swing.SwingUtilities.invokeLater(() -> {
            new LoginWindow(); // Önce giriş/kayıt ekranı açılır
        });
    }
}