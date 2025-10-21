public abstract class User {
    // Kullanıcı adı ve şifre alanları (protected - alt sınıflardan erişilebilir)
    protected String username;
    protected String password;

    // User sınıfının constructor'ı - kullanıcı bilgilerini alır
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Abstract login metodu - alt sınıflar tarafından implement edilmesi gerekir
    public abstract boolean login(String username, String password);
}