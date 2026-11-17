import java.awt.FlowLayout;
import java.awt.event.*;
import javax.swing.*;

public class ConverterFrameListener extends JFrame implements ActionListener {
    private final JTextField amount=new JTextField(10);
    private final JComboBox<String> target=new JComboBox<>(new String[]{"USD","EUR"});
    private final JLabel result=new JLabel(" ");
    public ConverterFrameListener(){
        super("Converter – Frame listener");setLayout(new FlowLayout());
        JButton b=new JButton("Convert");add(new JLabel("INR"));add(amount);add(target);add(b);add(result);
        b.addActionListener(this);amount.addActionListener(this);
        addWindowListener(new WindowAdapter(){@Override public void windowClosing(WindowEvent e){dispose();}});
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);pack();
    }
    @Override public void actionPerformed(ActionEvent e){convert();}
    private void convert(){try{double v=Double.parseDouble(amount.getText().trim());if(!Double.isFinite(v)||v<0)throw new NumberFormatException();showResult(v);}catch(NumberFormatException ex){JOptionPane.showMessageDialog(this,"Invalid amount");}}
    private void showResult(double v){String c=(String)target.getSelectedItem();double rate=c.equals("USD")?.012:.011;String s=String.format("%.2f %s",v*rate,c);result.setText(s);JOptionPane.showMessageDialog(this,s,"Converted amount",JOptionPane.INFORMATION_MESSAGE);}
    public static void main(String[]a){SwingUtilities.invokeLater(()->new ConverterFrameListener().setVisible(true));}
}
