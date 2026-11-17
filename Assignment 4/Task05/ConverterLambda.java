import java.awt.FlowLayout;
import java.awt.event.*;
import javax.swing.*;

public class ConverterLambda extends JFrame {
    private final JTextField amount = new JTextField(10);
    private final JComboBox<String> target = new JComboBox<>(new String[]{"USD", "EUR"});
    private final JLabel result = new JLabel(" ");
    public ConverterLambda() {
        super("Currency Converter"); setLayout(new FlowLayout());
        JButton convertButton = new JButton("Convert");
        add(new JLabel("INR")); add(amount); add(target); add(convertButton); add(result);
        // TODO 1 completed: one lambda listener registered for both triggers.
        ActionListener convertAction = e -> convert();
        convertButton.addActionListener(convertAction);
        amount.addActionListener(convertAction);
        addWindowListener(new WindowAdapter() { @Override public void windowClosing(WindowEvent e) { dispose(); }});
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); pack();
    }
    private void convert() {
        // TODO 2 completed: validate and display formatted result or an error dialog.
        try {
            double value = Double.parseDouble(amount.getText().trim());
            if (!Double.isFinite(value) || value < 0) throw new NumberFormatException();
            String currency = (String) target.getSelectedItem();
            double rate = currency.equals("USD") ? 0.012 : 0.011;
            String formatted = String.format("%.2f %s", value * rate, currency);
            result.setText(formatted);
            JOptionPane.showMessageDialog(this, formatted, "Converted amount", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            result.setText("Invalid amount");
            JOptionPane.showMessageDialog(this, "Invalid amount", "Input error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public static void main(String[] args) { SwingUtilities.invokeLater(() -> new ConverterLambda().setVisible(true)); }
}
