# Q2 – Hospital outpatient appointment desk

## Event model

| Source | Event object | Listener | Action |
|---|---|---|---|
| Department JComboBox | ItemEvent | ItemListener | Refresh doctor model from department map |
| Doctor JComboBox | ItemEvent | ItemListener | Clear/revalidate selected slot |
| Slot JComboBox | ItemEvent | ItemListener | Store selected slot |
| Book JButton | ActionEvent | ActionListener | Validate fields and book |
| JFrame close | WindowEvent | WindowAdapter | Confirm if booking is in progress |

## Containment hierarchy

```text
JFrame AppointmentFrame (BorderLayout)
├── JPanel form (GridLayout 4x2)
│   ├── JLabel + JTextField patient ID
│   ├── JLabel + JComboBox department
│   ├── JLabel + JComboBox doctor
│   └── JLabel + JComboBox time slot
├── JPanel actions (FlowLayout): Book JButton
└── JLabel status (SOUTH)
```

## Handler pseudocode (SIMATS-style)

```text
ALGORITHM RefreshDoctors
INPUT: selectedDepartment
BEGIN
    CLEAR doctorComboBox
    FOR EACH doctor IN doctorMap[selectedDepartment]
        ADD doctor TO doctorComboBox
    END FOR
    CLEAR selectedSlot
END

ALGORITHM BookAppointment
INPUT: patientId, department, doctor, slot
BEGIN
    IF patientId IS EMPTY OR department/doctor/slot IS NOT SELECTED
        DISPLAY VALIDATION ERROR
        RETURN
    END IF
    SET bookingInProgress TO TRUE
    TRY
        SAVE appointment through AppointmentService
        DISPLAY confirmation
    CATCH booking error
        DISPLAY error message
    FINALLY
        SET bookingInProgress TO FALSE
    END TRY
END
```

## Event trace and applet comparison

`main -> SwingUtilities.invokeLater -> constructor -> pack -> setVisible -> event queue -> listener -> service -> repaint/update -> windowClosing -> confirmation -> dispose -> windowClosed`. Applet lifecycle methods are `init`, `start`, `paint`, `stop`, `destroy`; they are container-managed lifecycle callbacks, not the same as a standalone JFrame's window events.

## Alternative and complexity

Loading once into `Map<String,List<Doctor>>` costs O(d+m) time and O(m+d) memory for d departments and m doctors. A department change then selects the list in expected O(1) map lookup plus O(k) to populate k doctors. Fetching on every change costs database round-trip latency plus O(k) transfer/render work per change and uses less persistent client memory, but adds repeated network/database load. Cache invalidation/staleness must be considered.
