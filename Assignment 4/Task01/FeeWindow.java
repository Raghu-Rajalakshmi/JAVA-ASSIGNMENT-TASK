import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.concurrent.ExecutionException;

public class FeeWindow extends JFrame {
    private final JTextField regNo = new JTextField(14);
    private final JTextField amount = new JTextField(10);
    private final JLabel status = new JLabel("Enter fee details.");
    private final FeeService service = new FeeService(
        System.getenv().getOrDefault("DB_URL", "jdbc:mysql://localhost:3306/campus"),
        System.getenv().getOrDefault("DB_USER", "root"),
        System.getenv().getOrDefault("DB_PASSWORD", ""));
    public FeeWindow() {
        super("Student Fee Payment");
        JPanel form = new JPanel(new GridLayout(0, 2, 8, 8));
        form.add(new JLabel("Register number")); form.add(regNo);
        form.add(new JLabel("Amount")); form.add(amount);
        JButton pay = new JButton("Pay"), clear = new JButton("Clear"), receipt = new JButton("Receipt");
        JPanel buttons = new JPanel(); buttons.add(pay); buttons.add(clear); buttons.add(receipt);
        add(form, BorderLayout.CENTER); add(buttons, BorderLayout.NORTH); add(status, BorderLayout.SOUTH);
        pay.addActionListener(e -> payFee());
        clear.addActionListener(e -> { regNo.setText(""); amount.setText(""); status.setText("Cleared."); });
        receipt.addActionListener(e -> JOptionPane.showMessageDialog(this,
                status.getText(), "Payment receipt", JOptionPane.INFORMATION_MESSAGE));
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override public void windowClosing(WindowEvent e) {
                if (JOptionPane.showConfirmDialog(FeeWindow.this, "Close the fee window?",
                        "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) dispose();
            }
        });
        pack(); setLocationRelativeTo(null);
    }
    private void payFee() {
        String reg = regNo.getText(), value = amount.getText();
        setButtonsEnabled(false);
        new SwingWorker<String, Void>() {
            @Override protected String doInBackground() throws Exception { return service.pay(reg, value); }
            @Override protected void done() {
                setButtonsEnabled(true);
                try { status.setText(get()); }
                catch (InterruptedException ex) { Thread.currentThread().interrupt(); status.setText("Payment interrupted."); }
                catch (ExecutionException ex) { status.setText("Payment failed: " + ex.getCause().getMessage()); }
            }
        }.execute();
    }
    private void setButtonsEnabled(boolean enabled) {
        for (Component c : ((JPanel)getContentPane().getComponent(1)).getComponents())
            if (c instanceof JButton) c.setEnabled(enabled);
    }
    public static void main(String[] args) { SwingUtilities.invokeLater(() -> new FeeWindow().setVisible(true)); }
}
