import java.awt.FlowLayout;import java.awt.event.*;import javax.swing.*;
public class ConverterNamedInner extends JFrame {
 private final JTextField amount=new JTextField(10);private final JComboBox<String> target=new JComboBox<>(new String[]{"USD","EUR"});private final JLabel result=new JLabel(" ");
 public ConverterNamedInner(){super("Converter – named inner");setLayout(new FlowLayout());JButton b=new JButton("Convert");add(new JLabel("INR"));add(amount);add(target);add(b);add(result);ActionListener handler=new ConvertHandler();b.addActionListener(handler);amount.addActionListener(handler);addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent e){dispose();}});setDefaultCloseOperation(DISPOSE_ON_CLOSE);pack();}
 private class ConvertHandler implements ActionListener {public void actionPerformed(ActionEvent e){try{double v=Double.parseDouble(amount.getText().trim());if(!Double.isFinite(v)||v<0)throw new NumberFormatException();String c=(String)target.getSelectedItem();String s=String.format("%.2f %s",v*(c.equals("USD")?.012:.011),c);result.setText(s);JOptionPane.showMessageDialog(ConverterNamedInner.this,s);}catch(NumberFormatException ex){JOptionPane.showMessageDialog(ConverterNamedInner.this,"Invalid amount");}}}
 public static void main(String[]a){SwingUtilities.invokeLater(()->new ConverterNamedInner().setVisible(true));}
}
