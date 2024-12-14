import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class PayrollSystem extends JFrame {
    private JTextField txtEmployeeID, txtName, txtHourlyRate, txtHoursWorked;
    private DefaultTableModel tableModel;
    private JTable table;
    private ArrayList<String> payrollRecords = new ArrayList<>();
    private static final String FILE_NAME = "payroll.txt";

    public PayrollSystem() {
        // Set up JFrame
        setTitle("Payroll System");
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Set up colors
        Color primaryColor = new Color(60, 63, 65); // Dark gray
        Color secondaryColor = new Color(220, 220, 220); // Light gray
        Color buttonColor = new Color(46, 204, 113); // Green

        // Input Panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(secondaryColor);
        inputPanel.setBorder(BorderFactory.createTitledBorder("Employee Information"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel lblEmployeeID = new JLabel("Employee ID:");
        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(lblEmployeeID, gbc);

        txtEmployeeID = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 0;
        inputPanel.add(txtEmployeeID, gbc);

        JLabel lblName = new JLabel("Name:");
        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(lblName, gbc);

        txtName = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 1;
        inputPanel.add(txtName, gbc);

        JLabel lblHourlyRate = new JLabel("Hourly Rate:");
        gbc.gridx = 0; gbc.gridy = 2;
        inputPanel.add(lblHourlyRate, gbc);

        txtHourlyRate = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 2;
        inputPanel.add(txtHourlyRate, gbc);

        JLabel lblHoursWorked = new JLabel("Hours Worked:");
        gbc.gridx = 0; gbc.gridy = 3;
        inputPanel.add(lblHoursWorked, gbc);

        txtHoursWorked = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 3;
        inputPanel.add(txtHoursWorked, gbc);

        JButton btnAddEmployee = createButton("Add Employee", buttonColor);
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        inputPanel.add(btnAddEmployee, gbc);

        JButton btnCalculatePay = createButton("Calculate Pay", buttonColor);
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        inputPanel.add(btnCalculatePay, gbc);

        add(inputPanel, BorderLayout.WEST);

        // Table Panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tableModel = new DefaultTableModel(new String[]{"Employee ID", "Name", "Hourly Rate", "Hours Worked", "Gross Pay", "Net Pay"}, 0);
        table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);
        add(tablePanel, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel(new FlowLayout());
        buttonsPanel.setBackground(primaryColor);

        JButton btnSaveRecord = createButton("Save Record", buttonColor);
        JButton btnDisplayRecords = createButton("Display Records", buttonColor);
        buttonsPanel.add(btnSaveRecord);
        buttonsPanel.add(btnDisplayRecords);

        add(buttonsPanel, BorderLayout.SOUTH);

        // Load existing records
        loadRecords();

        // Add Employee Action
        btnAddEmployee.addActionListener(e -> addEmployee());

        // Calculate Pay Action
        btnCalculatePay.addActionListener(e -> calculatePay());

        // Save Record Action
        btnSaveRecord.addActionListener(e -> saveRecords());

        // Display Records Action
        btnDisplayRecords.addActionListener(e -> displayRecords());
    }

    private JButton createButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        return button;
    }

    private void addEmployee() {
        String id = txtEmployeeID.getText();
        String name = txtName.getText();
        String hourlyRateStr = txtHourlyRate.getText();
        String hoursWorkedStr = txtHoursWorked.getText();

        if (id.isEmpty() || name.isEmpty() || hourlyRateStr.isEmpty() || hoursWorkedStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double hourlyRate = Double.parseDouble(hourlyRateStr);
            double hoursWorked = Double.parseDouble(hoursWorkedStr);
            double grossPay = hourlyRate * hoursWorked;
            double netPay = grossPay * 0.8;

            payrollRecords.add(String.format("%s,%s,%.2f,%.2f,%.2f,%.2f", id, name, hourlyRate, hoursWorked, grossPay, netPay));
            tableModel.addRow(new Object[]{id, name, hourlyRate, hoursWorked, grossPay, netPay});
            JOptionPane.showMessageDialog(this, "Employee added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

            // Clear input fields
            txtEmployeeID.setText("");
            txtName.setText("");
            txtHourlyRate.setText("");
            txtHoursWorked.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void calculatePay() {
        String id = txtEmployeeID.getText();
        for (String record : payrollRecords) {
            if (record.startsWith(id + ",")) {
                String[] parts = record.split(",");
                double hourlyRate = Double.parseDouble(parts[2]);
                double hoursWorked = Double.parseDouble(parts[3]);
                double grossPay = hourlyRate * hoursWorked;
                double netPay = grossPay * 0.8;

                JOptionPane.showMessageDialog(this, String.format("Gross Pay: %.2f\nNet Pay: %.2f", grossPay, netPay), "Pay Details", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Employee ID not found.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void saveRecords() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (String record : payrollRecords) {
                writer.write(record);
                writer.newLine();
            }
            JOptionPane.showMessageDialog(this, "Records saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving records.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayRecords() {
        tableModel.setRowCount(0);
        for (String record : payrollRecords) {
            String[] parts = record.split(",");
            tableModel.addRow(new Object[]{parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]});
        }
    }

    private void loadRecords() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                payrollRecords.add(line);
                String[] parts = line.split(",");
                tableModel.addRow(new Object[]{parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]});
            }
        } catch (IOException ex) {
            // No existing records found
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PayrollSystem().setVisible(true));
    }
}
