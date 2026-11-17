import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/** Swing UI -> RegistrationService -> RegistrationDao (JDBC). */
public class RegistrationApp extends JFrame {
    private final JTextField studentId = new JTextField(12);
    private final JTextField studentName = new JTextField(14);
    private final JComboBox<String> event = new JComboBox<>(new String[]{"Cultural Fest","Music Night","Tech Expo"});
    private final JLabel status = new JLabel("Ready");
    private final DefaultTableModel model = new DefaultTableModel(new Object[]{"ID","Student ID","Name","Event","Registered At"},0);
    private final JTable table = new JTable(model);
    private final RegistrationService service;
    private int offset = 0;
    private static final int PAGE_SIZE = 50;

    public RegistrationApp() {
        super("Campus Event Registration");
        String url=System.getenv().getOrDefault("DB_URL","jdbc:mysql://localhost:3306/campus_events");
        String user=System.getenv().getOrDefault("DB_USER","root");
        String password=System.getenv().getOrDefault("DB_PASSWORD","");
        service=new RegistrationService(new RegistrationDao(url,user,password));
        JPanel form=new JPanel(new FlowLayout(FlowLayout.LEFT));
        form.add(new JLabel("Student ID"));form.add(studentId);form.add(new JLabel("Name"));form.add(studentName);
        form.add(new JLabel("Event"));form.add(event);
        JButton register=new JButton("Register"),filter=new JButton("Filter"),export=new JButton("Export CSV");
        form.add(register);form.add(filter);form.add(export);
        JMenuBar bar=new JMenuBar();JMenu file=new JMenu("File");JMenuItem refresh=new JMenuItem("Refresh"),quit=new JMenuItem("Exit");file.add(refresh);file.add(quit);bar.add(file);setJMenuBar(bar);
        add(form,BorderLayout.NORTH);add(new JScrollPane(table),BorderLayout.CENTER);add(status,BorderLayout.SOUTH);
        register.addActionListener(e->register());filter.addActionListener(e->loadPage(0));refresh.addActionListener(e->loadPage(offset));export.addActionListener(e->exportCsv());quit.addActionListener(e->dispose());
        setDefaultCloseOperation(EXIT_ON_CLOSE);setSize(950,550);setLocationRelativeTo(null);
        loadPage(0);
    }
    private void register(){
        String sid=studentId.getText().trim(),name=studentName.getText().trim(),ev=(String)event.getSelectedItem();
        if(sid.isEmpty()||name.isEmpty()||ev==null){status.setText("Enter student ID, name and event.");return;}
        status.setText("Registering...");
        new SwingWorker<Void,Void>(){
            protected Void doInBackground() throws Exception {service.register(sid,name,ev);return null;}
            protected void done(){try{get();status.setText("Registration successful.");loadPage(0);}catch(Exception ex){status.setText("Registration failed: "+rootMessage(ex));}}
        }.execute();
    }
    private void loadPage(int page){
        offset=Math.max(0,page);String ev=(String)event.getSelectedItem();status.setText("Loading...");
        new SwingWorker<List<Registration>,Void>(){
            protected List<Registration> doInBackground() throws Exception{return service.page(ev,PAGE_SIZE,offset);}
            protected void done(){try{model.setRowCount(0);for(Registration r:get())model.addRow(new Object[]{r.id,r.studentId,r.studentName,r.eventName,r.registeredAt});status.setText("Loaded page starting at "+offset);}catch(Exception ex){status.setText("Load failed: "+rootMessage(ex));}}
        }.execute();
    }
    private void exportCsv(){
        JFileChooser chooser=new JFileChooser();if(chooser.showSaveDialog(this)!=JFileChooser.APPROVE_OPTION)return;
        try(BufferedWriter w=new BufferedWriter(new FileWriter(chooser.getSelectedFile()))){
            w.write("ID,Student ID,Name,Event,Registered At\n");
            for(int i=0;i<model.getRowCount();i++){for(int j=0;j<5;j++){if(j>0)w.write(",");w.write("\""+String.valueOf(model.getValueAt(i,j)).replace("\"","\"\"")+"\"");}w.newLine();}
            status.setText("CSV exported.");
        }catch(IOException ex){status.setText("Export failed: "+ex.getMessage());}
    }
    private static String rootMessage(Throwable t){while(t.getCause()!=null)t=t.getCause();return t.getMessage();}
    public static void main(String[]a){SwingUtilities.invokeLater(()->new RegistrationApp().setVisible(true));}
}

class Registration {
    final long id;final String studentId,studentName,eventName,registeredAt;
    Registration(long i,String s,String n,String e,String at){id=i;studentId=s;studentName=n;eventName=e;registeredAt=at;}
}
class RegistrationService {
    private final RegistrationDao dao;
    RegistrationService(RegistrationDao d){dao=d;}
    void register(String id,String name,String event)throws SQLException{
        if(id==null||id.isBlank()||name==null||name.isBlank()||event==null||event.isBlank())
            throw new IllegalArgumentException("All fields are required.");
        dao.insert(id.trim(),name.trim(),event);
    }
    List<Registration> page(String event,int limit,int offset)throws SQLException{return dao.findPage(event,limit,offset);}
}
class RegistrationDao {
    private final String url,user,password;
    RegistrationDao(String u,String n,String p){url=u;user=n;password=p;}
    private Connection connect()throws SQLException{return DriverManager.getConnection(url,user,password);}
    void insert(String id,String name,String event)throws SQLException{
        String sql="INSERT INTO registrations(student_id,student_name,event_name) VALUES(?,?,?)";
        try(Connection c=connect();PreparedStatement p=c.prepareStatement(sql)){
            p.setString(1,id);p.setString(2,name);p.setString(3,event);p.executeUpdate();
        }
    }
    List<Registration> findPage(String event,int limit,int offset)throws SQLException{
        String sql="SELECT id,student_id,student_name,event_name,registered_at FROM registrations WHERE event_name=? ORDER BY id DESC LIMIT ? OFFSET ?";
        List<Registration> out=new ArrayList<>();
        try(Connection c=connect();PreparedStatement p=c.prepareStatement(sql)){
            p.setString(1,event);p.setInt(2,limit);p.setInt(3,offset);
            try(ResultSet r=p.executeQuery()){while(r.next())out.add(new Registration(r.getLong(1),r.getString(2),r.getString(3),r.getString(4),String.valueOf(r.getTimestamp(5))));}
        }
        return out;
    }
}
