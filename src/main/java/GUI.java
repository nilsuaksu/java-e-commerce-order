import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class GUI {
    // Ana pencere ve bileşenler
    private JFrame frame;
    private JTable productTable;
    private JTextArea cartArea;
    private DefaultTableModel tableModel;
    private ShoppingCart cart = new ShoppingCart();
    private JLabel totalLabel;
    private JList<String> categoryList;
    private JLabel cartCountLabel;

    // Modern renk paleti tanımları
    private static final Color PRIMARY_COLOR = new Color(25, 118, 210);      // Material Blue
    private static final Color SECONDARY_COLOR = new Color(66, 165, 245);    // Light Blue
    private static final Color SUCCESS_COLOR = new Color(76, 175, 80);       // Material Green
    private static final Color DANGER_COLOR = new Color(244, 67, 54);        // Material Red
    private static final Color WARNING_COLOR = new Color(255, 152, 0);       // Material Orange
    private static final Color BACKGROUND_COLOR = new Color(250, 250, 250);  // Light Gray
    private static final Color CARD_COLOR = Color.WHITE;
    private static final Color TEXT_PRIMARY = new Color(33, 33, 33);
    private static final Color TEXT_SECONDARY = new Color(117, 117, 117);
    private static final Color BORDER_COLOR = new Color(224, 224, 224);

    // Ana GUI oluşturma ve gösterme metodu
    public void createAndShowGUI() {
        // Modern Look and Feel ayarlama
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setupMainFrame();
        createComponents();
        setupLayout();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Varsayılan kategoriyi seç
        initializeDefaultCategory();
    }

    // Ana pencere kurulumu
    private void setupMainFrame() {
        frame = new JFrame("shopNT");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1500, 900);
        frame.setMinimumSize(new Dimension(1200, 700));
        frame.getContentPane().setBackground(BACKGROUND_COLOR);
    }

    // Tüm bileşenleri oluşturan ana metod
    private void createComponents() {
        createHeader();
        createCategoryPanel();
        createProductPanel();
        createCartPanel();
        createActionButtons();
    }

    // Üst başlık panelini oluşturma
    private void createHeader() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setPreferredSize(new Dimension(0, 90));
        headerPanel.setBorder(new EmptyBorder(20, 30, 20, 30));

        // Sol taraf - Logo ve başlık
        JPanel leftSection = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        leftSection.setBackground(PRIMARY_COLOR);

        // Alışveriş sepeti ikonu
        JLabel logoIcon = new JLabel("🛒");
        logoIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));

        // Başlık bölümü
        JPanel titleSection = new JPanel();
        titleSection.setLayout(new BoxLayout(titleSection, BoxLayout.Y_AXIS));
        titleSection.setBackground(PRIMARY_COLOR);

        // Ana başlık
        JLabel mainTitle = new JLabel("shopNT");
        mainTitle.setFont(new Font("Segoe UI", Font.BOLD, 32));
        mainTitle.setForeground(Color.WHITE);

        // Alt başlık
        JLabel subtitle = new JLabel("Modern E-Ticaret Deneyimi");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitle.setForeground(new Color(255, 255, 255, 200));

        titleSection.add(mainTitle);
        titleSection.add(Box.createVerticalStrut(5));
        titleSection.add(subtitle);

        leftSection.add(logoIcon);
        leftSection.add(Box.createHorizontalStrut(15));
        leftSection.add(titleSection);

        // Sağ taraf - Sepet bilgisi
        JPanel rightSection = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightSection.setBackground(PRIMARY_COLOR);

        // Sepet sayacı etiketi
        cartCountLabel = new JLabel(" Sepet: 0 ürün");
        cartCountLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        cartCountLabel.setForeground(Color.WHITE);
        cartCountLabel.setOpaque(true);
        cartCountLabel.setBackground(new Color(255, 255, 255, 20));
        cartCountLabel.setBorder(new EmptyBorder(10, 20, 10, 20));

        rightSection.add(cartCountLabel);

        headerPanel.add(leftSection, BorderLayout.WEST);
        headerPanel.add(rightSection, BorderLayout.EAST);

        frame.add(headerPanel, BorderLayout.NORTH);
    }

    private JPanel categoryPanel;
    // Kategori listesi paneli oluşturma
    private void createCategoryPanel() {
        categoryPanel = createStyledCard(" Kategoriler");
        categoryPanel.setPreferredSize(new Dimension(280, 0));

        // Veritabanından kategorileri çekme
        List<String> categories = Database.fetchCategories();
        categoryList = new JList<>(categories.toArray(new String[0]));

        // Kategori listesi stil ayarları
        categoryList.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        categoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        categoryList.setBackground(CARD_COLOR);
        categoryList.setSelectionBackground(SECONDARY_COLOR);
        categoryList.setSelectionForeground(Color.WHITE);
        categoryList.setBorder(new EmptyBorder(10, 0, 10, 0));

        // Kategoriler için özel renderer
        categoryList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                setBorder(new EmptyBorder(12, 20, 12, 20));
                setFont(new Font("Segoe UI", Font.PLAIN, 16));

                if (isSelected) {
                    setBackground(SECONDARY_COLOR);
                    setForeground(Color.WHITE);
                } else {
                    setBackground(CARD_COLOR);
                    setForeground(TEXT_PRIMARY);
                }

                return this;
            }
        });

        // Kategori seçim dinleyicisi
        categoryList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedCategory = categoryList.getSelectedValue();
                if (selectedCategory != null) {
                    loadProducts(selectedCategory);
                }
            }
        });

        JScrollPane categoryScroll = new JScrollPane(categoryList);
        categoryScroll.setBorder(null);
        categoryScroll.setBackground(CARD_COLOR);
        categoryScroll.getViewport().setBackground(CARD_COLOR);

        categoryPanel.add(categoryScroll, BorderLayout.CENTER);
    }

    private JPanel productPanel;
    // Ürün tablosu paneli oluşturma
    private void createProductPanel() {
        productPanel = createStyledCard(" Ürün Kataloğu");

        // Tablo kolon başlıkları
        String[] columnNames = {"ID", "ÜRÜN ADI", "FİYAT"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tablo hücrelerini düzenlenemez yap
            }
        };

        // Ürün tablosunu oluşturma ve stil ayarları
        productTable = new JTable(tableModel);
        productTable.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        productTable.setRowHeight(45);
        productTable.setGridColor(BORDER_COLOR);
        productTable.setShowVerticalLines(true);
        productTable.setShowHorizontalLines(true);
        productTable.setSelectionBackground(new Color(SECONDARY_COLOR.getRed(),
                SECONDARY_COLOR.getGreen(),
                SECONDARY_COLOR.getBlue(), 60));
        productTable.setSelectionForeground(TEXT_PRIMARY);

        // Tablo başlığı styling
        productTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        productTable.getTableHeader().setBackground(PRIMARY_COLOR);
        productTable.getTableHeader().setForeground(Color.WHITE);
        productTable.getTableHeader().setPreferredSize(new Dimension(0, 50));
        productTable.getTableHeader().setBorder(BorderFactory.createEmptyBorder());

        // Kolon genişlikleri ayarlama
        productTable.getColumnModel().getColumn(0).setPreferredWidth(80);
        productTable.getColumnModel().getColumn(0).setMaxWidth(100);
        productTable.getColumnModel().getColumn(1).setPreferredWidth(400);
        productTable.getColumnModel().getColumn(2).setPreferredWidth(150);

        // ID kolonu için ortalama renderer
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        productTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);

        // Fiyat kolonu için özel renderer
        DefaultTableCellRenderer priceRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Fiyat formatını ayarlama
                if (value instanceof Double) {
                    setText("₺" + String.format("%.2f", (Double) value));
                }
                setHorizontalAlignment(JLabel.RIGHT);
                setFont(new Font("Segoe UI", Font.BOLD, 16));

                if (isSelected) {
                    setBackground(table.getSelectionBackground());
                    setForeground(table.getSelectionForeground());
                } else {
                    setBackground(Color.WHITE);
                    setForeground(SUCCESS_COLOR);
                }

                return this;
            }
        };
        productTable.getColumnModel().getColumn(2).setCellRenderer(priceRenderer);

        JScrollPane productScroll = new JScrollPane(productTable);
        productScroll.setBorder(null);
        productScroll.getViewport().setBackground(Color.WHITE);

        productPanel.add(productScroll, BorderLayout.CENTER);
    }

    private JPanel cartPanel;
    // Alışveriş sepeti paneli oluşturma
    private void createCartPanel() {
        cartPanel = createStyledCard(" Alışveriş Sepetim");
        cartPanel.setPreferredSize(new Dimension(400, 0));

        // Sepet içeriği metin alanı
        cartArea = new JTextArea();
        cartArea.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        cartArea.setEditable(false);
        cartArea.setBackground(CARD_COLOR);
        cartArea.setForeground(TEXT_PRIMARY);
        cartArea.setBorder(new EmptyBorder(20, 20, 20, 20));
        cartArea.setLineWrap(true);
        cartArea.setWrapStyleWord(true);
        // Başlangıç mesajı
        cartArea.setText(" Sepetiniz henüz boş\n\nAlışverişe başlamak için\nsoldan kategori seçin ve\nürün ekleyin!");

        JScrollPane cartScroll = new JScrollPane(cartArea);
        cartScroll.setBorder(null);
        cartScroll.setBackground(CARD_COLOR);
        cartScroll.getViewport().setBackground(CARD_COLOR);

        // Toplam tutar etiketi
        totalLabel = new JLabel(" Toplam: ₺0.00", JLabel.CENTER);
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        totalLabel.setForeground(Color.WHITE);
        totalLabel.setBackground(SUCCESS_COLOR);
        totalLabel.setOpaque(true);
        totalLabel.setBorder(new EmptyBorder(15, 10, 15, 10));

        cartPanel.add(cartScroll, BorderLayout.CENTER);
        cartPanel.add(totalLabel, BorderLayout.SOUTH);
    }

    private JPanel actionPanel;
    // Aksiyon butonları paneli oluşturma
    private void createActionButtons() {
        actionPanel = new JPanel();
        actionPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 25));
        actionPanel.setBackground(BACKGROUND_COLOR);
        actionPanel.setBorder(new EmptyBorder(20, 0, 20, 0));

        // Ana aksiyon butonları
        JButton addButton = createStyledButton(" Sepete Ekle", SUCCESS_COLOR);
        JButton removeButton = createStyledButton(" Sepetten Çıkar", DANGER_COLOR);
        JButton orderButton = createStyledButton(" Siparişi Tamamla", PRIMARY_COLOR);

        // Buton olay dinleyicileri
        addButton.addActionListener(e -> addProductToCart());
        removeButton.addActionListener(e -> removeProductFromCart());
        orderButton.addActionListener(e -> completeOrder());

        actionPanel.add(addButton);
        actionPanel.add(removeButton);
        actionPanel.add(orderButton);
    }

    // Stilize edilmiş buton oluşturma metodu
    private JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(backgroundColor);
        button.setPreferredSize(new Dimension(200, 50));
        button.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
        button.setBorderPainted(false);

        // Hover efekti için renk hesaplaması
        Color originalColor = backgroundColor;
        Color hoverColor = new Color(
                Math.max(0, backgroundColor.getRed() - 40),
                Math.max(0, backgroundColor.getGreen() - 40),
                Math.max(0, backgroundColor.getBlue() - 40)
        );

        // Mouse hover efekti dinleyicisi
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

    // Stilize edilmiş kart paneli oluşturma metodu
    private JPanel createStyledCard(String title) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                new EmptyBorder(0, 0, 0, 0)
        ));

        // Kart başlık etiketi
        JLabel titleLabel = new JLabel(title, JLabel.LEFT);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(TEXT_PRIMARY);
        titleLabel.setBorder(new EmptyBorder(20, 25, 20, 25));
        titleLabel.setBackground(new Color(248, 249, 250));
        titleLabel.setOpaque(true);

        card.add(titleLabel, BorderLayout.NORTH);

        return card;
    }

    // Ana layout düzenlemesi
    private void setupLayout() {
        JPanel mainContainer = new JPanel(new BorderLayout(25, 25));
        mainContainer.setBackground(BACKGROUND_COLOR);
        mainContainer.setBorder(new EmptyBorder(25, 25, 25, 25));

        JPanel centerContainer = new JPanel(new BorderLayout(20, 0));
        centerContainer.setBackground(BACKGROUND_COLOR);
        centerContainer.add(categoryPanel, BorderLayout.WEST);
        centerContainer.add(productPanel, BorderLayout.CENTER);
        centerContainer.add(cartPanel, BorderLayout.EAST);

        mainContainer.add(centerContainer, BorderLayout.CENTER);

        frame.add(mainContainer, BorderLayout.CENTER);
        frame.add(actionPanel, BorderLayout.SOUTH);
    }

    // Varsayılan kategoriyi başlatma
    private void initializeDefaultCategory() {
        SwingUtilities.invokeLater(() -> {
            List<String> categories = Database.fetchCategories();
            if (!categories.isEmpty()) {
                categoryList.setSelectedIndex(0);
                loadProducts(categories.get(0));
            }
        });
    }

    // Seçili ürünü sepete ekleme metodu
    private void addProductToCart() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow >= 0) {
            // Seçili ürün bilgilerini alma
            int productId = (int) tableModel.getValueAt(selectedRow, 0);
            String productName = (String) tableModel.getValueAt(selectedRow, 1);
            double productPrice = (double) tableModel.getValueAt(selectedRow, 2);

            Product product = new Product(productId, productName, productPrice);
            cart.addProduct(product);

            updateCartDisplay();
            showNotification("" + productName + " sepete eklendi!", SUCCESS_COLOR);
        } else {
            showNotification("️ Lütfen sepete eklemek için bir ürün seçin!", WARNING_COLOR);
        }
    }

    // Seçili ürünü sepetten çıkarma metodu
    private void removeProductFromCart() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow >= 0) {
            String productName = (String) tableModel.getValueAt(selectedRow, 1);
            boolean removed = cart.removeByName(productName);

            if (removed) {
                updateCartDisplay();
                showNotification("" + productName + " sepetten çıkarıldı!", SUCCESS_COLOR);
            } else {
                showNotification("" + productName + " sepetinizde bulunamadı!", DANGER_COLOR);
            }
        } else {
            showNotification("️ Lütfen sepetten çıkarmak için bir ürün seçin!", WARNING_COLOR);
        }
    }

    // Sipariş tamamlama metodu
    private void completeOrder() {
        if (cart.getItems().isEmpty()) {
            showNotification("⚠️ Sepetiniz boş! Önce ürün ekleyin.", WARNING_COLOR);
            return;
        }

        // Ödeme seçenekleri
        String[] paymentOptions = {" Kredi Kartı", " Nakit Ödeme", " Banka Havalesi"};
        String selectedPayment = (String) JOptionPane.showInputDialog(
                frame,
                "Ödeme yöntemini seçiniz:",
                "Ödeme Seçimi",
                JOptionPane.QUESTION_MESSAGE,
                null,
                paymentOptions,
                paymentOptions[0]
        );

        if (selectedPayment != null) {
            // Yeni sipariş oluşturma
            Order newOrder = new Order(cart.getItems());
            OrderManager.getInstance().addOrder(newOrder);

            // Sipariş onay mesajı
            String confirmationMessage = String.format(
                    " Siparişiniz başarıyla tamamlandı!\n\n" +
                            " Toplam Tutar: ₺%.2f\n" +
                            " Ödeme Yöntemi: %s\n\n" +
                            "Teşekkür ederiz! shopNT'yi tercih ettiğiniz için memnunuz! ",
                    newOrder.getTotal(),
                    selectedPayment.substring(2)
            );

            JOptionPane.showMessageDialog(frame, confirmationMessage,
                    "Sipariş Onayı", JOptionPane.INFORMATION_MESSAGE);

            // Sepeti temizleme
            cart.clear();
            updateCartDisplay();
        }
    }

    // Bildirim gösterme metodu
    private void showNotification(String message, Color backgroundColor) {
        JLabel notificationLabel = new JLabel(message, JLabel.CENTER);
        notificationLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        notificationLabel.setForeground(Color.WHITE);
        notificationLabel.setBackground(backgroundColor);
        notificationLabel.setOpaque(true);
        notificationLabel.setBorder(new EmptyBorder(15, 25, 15, 25));

        JOptionPane.showMessageDialog(frame, notificationLabel, "Bildirim",
                JOptionPane.PLAIN_MESSAGE);
    }

    // Seçili kategoriye göre ürünleri yükleme metodu
    private void loadProducts(String category) {
        if (category == null || category.trim().isEmpty()) {
            return;
        }

        // Tablo verilerini temizleme
        tableModel.setRowCount(0);
        List<Product> products = Database.fetchProductsByCategory(category);

        // Ürünleri tabloya ekleme
        for (Product product : products) {
            tableModel.addRow(new Object[]{
                    product.getId(),
                    product.getName(),
                    product.getPrice()
            });
        }
    }

    // Sepet görünümünü güncelleme metodu
    private void updateCartDisplay() {
        StringBuilder cartContent = new StringBuilder();
        List<Product> cartItems = cart.getItems();
        int totalItems = cartItems.size();

        if (cartItems.isEmpty()) {
            // Boş sepet mesajı
            cartContent.append(" Sepetiniz henüz boş\n\n");
            cartContent.append("Alışverişe başlamak için:\n");
            cartContent.append("1️⃣ Sol taraftan kategori seçin\n");
            cartContent.append("2️⃣ Ürün listesinden seçim yapın\n");
            cartContent.append("3️⃣ 'Sepete Ekle' butonuna tıklayın\n\n");
            cartContent.append("İyi alışverişler! ");
        } else {
            // Sepet içeriğini gösterme
            cartContent.append("️ Sepetinizdeki Ürünler\n");
            cartContent.append("═".repeat(35)).append("\n\n");

            // Her ürünü listeleme
            for (int i = 0; i < cartItems.size(); i++) {
                Product item = cartItems.get(i);
                cartContent.append(String.format("%d. %s\n", (i + 1), item.getName()));
                cartContent.append(String.format("    ₺%.2f\n\n", item.getPrice()));
            }

            cartContent.append("═".repeat(35)).append("\n");
            cartContent.append(String.format(" Toplam %d ürün\n", totalItems));
            cartContent.append(String.format(" Genel Toplam: ₺%.2f", cart.getTotal()));
        }

        // Sepet görünümünü güncelleme
        cartArea.setText(cartContent.toString());
        totalLabel.setText(String.format(" Toplam: ₺%.2f", cart.getTotal()));
        cartCountLabel.setText(String.format(" Sepet: %d ürün", totalItems));
    }
}