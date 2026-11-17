import java.sql.*;
import java.util.Objects;

/** Business rules and persistence are kept out of the event handler. */
public class FeeService {
    private final String url, user, password;
    public FeeService(String url, String user, String password) {
        this.url = Objects.requireNonNull(url);
        this.user = Objects.requireNonNull(user);
        this.password = Objects.requireNonNull(password);
    }
    public String pay(String registerNumber, String amountText) throws SQLException {
        if (registerNumber == null || registerNumber.isBlank())
            throw new IllegalArgumentException("Register number is required.");
        final double amount;
        try { amount = Double.parseDouble(amountText); }
        catch (NumberFormatException ex) { throw new IllegalArgumentException("Enter a valid amount."); }
        if (!Double.isFinite(amount) || amount <= 0)
            throw new IllegalArgumentException("Amount must be greater than zero.");
        String sql = "INSERT INTO fees (reg_no, amount) VALUES (?, ?)";
        try (Connection c = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, registerNumber.trim());
            ps.setDouble(2, amount);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                return rs.next() ? "Payment successful. Receipt #" + rs.getLong(1)
                                 : "Payment successful.";
            }
        }
    }
}
