import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginWindow extends JFrame {
    // Kullanıcı giriş alanları
    private JTextField emailField;
    private JPasswordField passwordField;

    // Modern renk paleti (Ana GUI ile uyumlu)
    private static final Color PRIMARY_COLOR = new Color(25, 118, 210);      // Material Blue
    private static final Color SECONDARY_COLOR = new Color(66, 165, 245);    // Light Blue
    private static final Color SUCCESS_COLOR = new Color(76, 175, 80);       // Material Green
    private static final Color DANGER_COLOR = new Color(244, 67, 54);        // Material Red
    private static final Color BACKGROUND_COLOR = new Color(250, 250, 250);  // Light Gray
    private static final Color CARD_COLOR = Color.WHITE;
    private static final Color TEXT_PRIMARY = new Color(33, 33, 33);
    private static final Color TEXT_SECONDARY = new Color(117, 117, 117);
    private static final Color BORDER_COLOR = new Color(224, 224, 224);

    // LoginWindow constructor - pencereyi başlatır
    public LoginWindow() {
        setupWindow();
        createComponents();
        setVisible(true);
    }

    // Pencere ayarlarını yapılandırma
    private void setupWindow() {
        // Modern Look and Feel ayarlama
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("shopNT - Kullanıcı Girişi");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Tam ekran
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BACKGROUND_COLOR);
    }

    // Ana bileşenleri oluşturma
    private void createComponents() {
        setLayout(new BorderLayout());

        // Header Panel - Üst kısım
        JPanel headerPanel = createHeader();

        // Main Content Panel - Ana içerik
        JPanel mainPanel = createMainPanel();

        // Footer Panel - Alt kısım
        JPanel footerPanel = createFooter();

        add(headerPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        add(footerPanel, BorderLayout.SOUTH);
    }

    // Üst başlık panelini oluşturma
    private JPanel createHeader() {
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setPreferredSize(new Dimension(0, 200));
        headerPanel.setLayout(new BorderLayout());

        // İçerik paneli
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(PRIMARY_COLOR);
        contentPanel.setBorder(new EmptyBorder(50, 0, 50, 0));

        // Ana başlık label'ı - tek satır
        JLabel mainLabel = new JLabel("🛒 shopNT", JLabel.CENTER);
        mainLabel.setFont(new Font("Segoe UI", Font.BOLD, 56));
        mainLabel.setForeground(Color.WHITE);

        contentPanel.add(mainLabel);
        headerPanel.add(contentPanel, BorderLayout.CENTER);

        return headerPanel;
    }

    // Ana içerik panelini oluşturma
    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 50));

        // Login Card - Sabit boyut
        JPanel loginCard = createLoginCard();
        loginCard.setPreferredSize(new Dimension(600, 500));

        mainPanel.add(loginCard);

        return mainPanel;
    }

    // Giriş formunu içeren kartı oluşturma
    private JPanel createLoginCard() {
        JPanel card = new JPanel();
        card.setBackground(CARD_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                new EmptyBorder(40, 40, 40, 40)
        ));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        // Form başlığı
        JLabel formTitle = new JLabel("Giriş Yapın", JLabel.CENTER);
        formTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        formTitle.setForeground(TEXT_PRIMARY);
        formTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // E-posta bölümü
        JLabel emailLabel = new JLabel(" E-posta Adresi");
        emailLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        emailLabel.setForeground(TEXT_PRIMARY);
        emailLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // E-posta giriş alanı
        emailField = new JTextField();
        emailField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        emailField.setPreferredSize(new Dimension(0, 45));
        emailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        emailField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                new EmptyBorder(10, 15, 10, 15)
        ));

        // Şifre bölümü
        JLabel passwordLabel = new JLabel(" Şifre");
        passwordLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        passwordLabel.setForeground(TEXT_PRIMARY);
        passwordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Şifre giriş alanı
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        passwordField.setPreferredSize(new Dimension(0, 45));
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                new EmptyBorder(10, 15, 10, 15)
        ));

        // Butonlar paneli
        JPanel buttonPanel = createButtonPanel();

        // Bileşenleri karta ekleme
        card.add(formTitle);
        card.add(Box.createVerticalStrut(30));
        card.add(emailLabel);
        card.add(Box.createVerticalStrut(8));
        card.add(emailField);
        card.add(Box.createVerticalStrut(20));
        card.add(passwordLabel);
        card.add(Box.createVerticalStrut(8));
        card.add(passwordField);
        card.add(Box.createVerticalStrut(30));
        card.add(buttonPanel);

        return card;
    }

    // Buton panelini oluşturma
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(CARD_COLOR);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Giriş butonu
        JButton loginButton = createStyledButton(" Giriş Yap", PRIMARY_COLOR);
        loginButton.addActionListener(e -> handleLogin());

        // Kayıt butonu
        JButton registerButton = createStyledButton(" Yeni Hesap Oluştur", SUCCESS_COLOR);
        registerButton.addActionListener(e -> handleRegister());

        // Bilgi metni
        JLabel infoLabel = new JLabel("Hesabınız yok mu? Kayıt olun!", JLabel.CENTER);
        infoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        infoLabel.setForeground(TEXT_SECONDARY);
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(loginButton);
        panel.add(Box.createVerticalStrut(15));
        panel.add(infoLabel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(registerButton);

        return panel;
    }

    // Stilize edilmiş buton oluşturma
    private JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(backgroundColor);
        button.setPreferredSize(new Dimension(0, 50));
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        button.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Hover efekti için renk hesaplaması
        Color originalColor = backgroundColor;
        Color hoverColor = new Color(
                Math.max(0, backgroundColor.getRed() - 30),
                Math.max(0, backgroundColor.getGreen() - 30),
                Math.max(0, backgroundColor.getBlue() - 30)
        );

        // Mouse hover efekti
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(originalColor);
            }
        });

        return button;
    }

    // Alt footer panelini oluşturma
    private JPanel createFooter() {
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(248, 249, 250));
        footerPanel.setPreferredSize(new Dimension(0, 80));
        footerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        // Footer etiketi
        JLabel footerLabel = new JLabel("© 2025 shopNT - Güvenli Alışveriş Platformu");
        footerLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        footerLabel.setForeground(TEXT_SECONDARY);

        footerPanel.add(footerLabel);

        return footerPanel;
    }

    // Giriş işlemini yönetme
    private void handleLogin() {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        // Doğrulama kontrolleri
        if (email.isEmpty() || password.isEmpty()) {
            showStyledMessage(" Lütfen tüm alanları doldurun!", "Eksik Bilgi", WARNING_COLOR);
            return;
        }

        if (!isValidEmail(email)) {
            showStyledMessage(" Geçerli bir e-posta adresi girin!", "Geçersiz E-posta", DANGER_COLOR);
            return;
        }

        // Veritabanında giriş kontrolü
        if (Database.loginUser(email, password)) {
            showStyledMessage(" Giriş başarılı! Hoş geldiniz!", "Başarılı Giriş", SUCCESS_COLOR);

            // Bu pencereyi kapat ve ana GUI'yi aç
            SwingUtilities.invokeLater(() -> {
                dispose();
                new GUI().createAndShowGUI();
            });
        } else {
            showStyledMessage(" E-posta veya şifre hatalı!", "Giriş Başarısız", DANGER_COLOR);
            passwordField.setText(""); // Şifre alanını temizle
        }
    }

    // Kayıt işlemini yönetme
    private void handleRegister() {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        // Doğrulama kontrolleri
        if (email.isEmpty() || password.isEmpty()) {
            showStyledMessage("️ Lütfen tüm alanları doldurun!", "Eksik Bilgi", WARNING_COLOR);
            return;
        }

        if (!isValidEmail(email)) {
            showStyledMessage(" Geçerli bir e-posta adresi girin!", "Geçersiz E-posta", DANGER_COLOR);
            return;
        }

        if (password.length() < 6) {
            showStyledMessage("️ Şifre en az 6 karakter olmalıdır!", "Zayıf Şifre", WARNING_COLOR);
            return;
        }

        // Veritabanına kayıt işlemi
        boolean success = Database.registerUser(email, password);
        if (success) {
            showStyledMessage(" Kayıt başarılı! Artık giriş yapabilirsiniz.", "Kayıt Tamamlandı", SUCCESS_COLOR);
            passwordField.setText(""); // Şifre alanını temizle
        } else {
            showStyledMessage(" Bu e-posta adresi zaten kayıtlı!", "Kayıt Başarısız", DANGER_COLOR);
        }
    }

    // E-posta formatını kontrol etme
    private boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".") && email.length() > 5;
    }

    // Stilize edilmiş mesaj gösterme
    private void showStyledMessage(String message, String title, Color backgroundColor) {
        JLabel messageLabel = new JLabel(message, JLabel.CENTER);
        messageLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        messageLabel.setForeground(Color.WHITE);
        messageLabel.setBackground(backgroundColor);
        messageLabel.setOpaque(true);
        messageLabel.setBorder(new EmptyBorder(20, 30, 20, 30));

        JOptionPane.showMessageDialog(this, messageLabel, title, JOptionPane.PLAIN_MESSAGE);
    }

    // Uyarı rengi tanımı
    private static final Color WARNING_COLOR = new Color(255, 152, 0); // Material Orange
}