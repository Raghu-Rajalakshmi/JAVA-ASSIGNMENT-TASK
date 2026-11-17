import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class LibrarySearchScreen extends JFrame {
    private final JTextField query = new JTextField(24);
    private final JComboBox<String> category = new JComboBox<>(new String[]{"All","Book","Journal","Thesis"});
    private final DefaultListModel<String> model = new DefaultListModel<>();
    private final JList<String> results = new JList<>(model);
    private final JLabel status = new JLabel("Ready");
    public LibrarySearchScreen() {
        super("Campus Library Search");
        JPanel title = new JPanel(new FlowLayout(FlowLayout.LEFT));
        title.add(new JLabel("Library Catalogue"));
        JPanel search = new JPanel(new FlowLayout(FlowLayout.LEFT));
        search.add(new JLabel("Search")); search.add(query); search.add(category);
        JButton searchButton = new JButton("Search"); search.add(searchButton);
        JPanel north = new JPanel(new BorderLayout()); north.add(title,BorderLayout.NORTH); north.add(search,BorderLayout.SOUTH);
        JScrollPane centre = new JScrollPane(results);
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.CENTER));
        actions.add(new JButton("Prev")); actions.add(new JButton("Next")); actions.add(new JButton("Reserve"));
        JPanel south = new JPanel(new BorderLayout()); south.add(actions,BorderLayout.NORTH); south.add(status,BorderLayout.SOUTH);
        add(north,BorderLayout.NORTH); add(centre,BorderLayout.CENTER); add(south,BorderLayout.SOUTH);
        searchButton.addActionListener(e -> {
            model.clear();
            String term=query.getText().trim();
            if(term.isEmpty()){status.setText("Enter a search term.");return;}
            model.addElement("Example result for \""+term+"\" ("+category.getSelectedItem()+")");
            status.setText("1 result");
        });
        setDefaultCloseOperation(EXIT_ON_CLOSE); setSize(700,450); setLocationRelativeTo(null);
    }
    public static void main(String[] a){SwingUtilities.invokeLater(()->new LibrarySearchScreen().setVisible(true));}
}
