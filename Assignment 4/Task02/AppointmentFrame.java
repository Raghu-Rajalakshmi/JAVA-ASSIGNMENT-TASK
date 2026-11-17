import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class AppointmentFrame extends JFrame {
    private final JTextField patient = new JTextField(12);
    private final JComboBox<String> department = new JComboBox<>(new String[]{"Cardiology","Dental","Neurology"});
    private final JComboBox<String> doctor = new JComboBox<>();
    private final JComboBox<String> slot = new JComboBox<>(new String[]{"09:00","10:00","11:00","14:00"});
    private final JLabel status = new JLabel("Enter appointment details.");
    private final Map<String, java.util.List<String>> doctors = Map.of(
        "Cardiology", java.util.List.of("Dr. Rao","Dr. Iyer"),
        "Dental", java.util.List.of("Dr. Kumar","Dr. Shah"),
        "Neurology", java.util.List.of("Dr. Das","Dr. Nair"));
    private boolean bookingInProgress;
    public AppointmentFrame() {
        super("Outpatient Appointment Desk");
        JPanel form = new JPanel(new GridLayout(4,2,8,8));
        form.add(new JLabel("Patient ID")); form.add(patient);
        form.add(new JLabel("Department")); form.add(department);
        form.add(new JLabel("Doctor")); form.add(doctor);
        form.add(new JLabel("Time slot")); form.add(slot);
        JButton book = new JButton("Book appointment");
        add(form, BorderLayout.CENTER); add(book, BorderLayout.NORTH); add(status, BorderLayout.SOUTH);
        department.addItemListener(e -> { if (e.getStateChange()==ItemEvent.SELECTED) refreshDoctors(); });
        book.addActionListener(e -> book());
        refreshDoctors();
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override public void windowClosing(WindowEvent e) {
                if (bookingInProgress && JOptionPane.showConfirmDialog(AppointmentFrame.this,
                        "A booking is in progress. Close anyway?", "Confirm", JOptionPane.YES_NO_OPTION)!=JOptionPane.YES_OPTION) return;
                if (JOptionPane.showConfirmDialog(AppointmentFrame.this,"Close the desk?","Confirm",
                        JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) dispose();
            }
        });
        pack(); setLocationRelativeTo(null);
    }
    private void refreshDoctors() {
        doctor.removeAllItems();
        for (String name : doctors.getOrDefault((String)department.getSelectedItem(), java.util.List.of()))
            doctor.addItem(name);
    }
    private void book() {
        if (patient.getText().isBlank() || doctor.getSelectedItem()==null || slot.getSelectedItem()==null) {
            status.setText("Error: complete all fields."); return;
        }
        bookingInProgress=true;
        try { status.setText("Appointment confirmed for " + patient.getText().trim() + " with " +
                doctor.getSelectedItem() + " at " + slot.getSelectedItem()); }
        finally { bookingInProgress=false; }
    }
    public static void main(String[] a) { SwingUtilities.invokeLater(() -> new AppointmentFrame().setVisible(true)); }
}
