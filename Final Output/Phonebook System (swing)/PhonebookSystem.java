import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.text.*;

public class PhonebookSystem extends JFrame implements ActionListener {
    private JTextField nameField, phoneField;
    private JTextArea contactArea;
    private JButton addButton, deleteButton, searchButton, updateButton, displayButton;
    private final File contactFile = new File("phonebook.txt");

    public PhonebookSystem() {
        // Initialize UI components
        setTitle("Phonebook System");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(255, 182, 193)); // Light pink

        // Input Panel
        JPanel inputPanel = createInputPanel();
        JPanel buttonPanel = createButtonPanel();
        JPanel textAreaPanel = createTextAreaPanel();

        // Add panels to the main panel
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(textAreaPanel, BorderLayout.SOUTH);

        add(mainPanel);
        loadContacts(); // Load contacts when the system is initialized
    }

    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        inputPanel.setBackground(new Color(255, 182, 193)); // Light pink
        inputPanel.setBorder(BorderFactory.createTitledBorder("PHONEBOOK SYSTEM"));

        nameField = new JTextField(15);
        phoneField = new JTextField(15);
        JLabel nameLabel = new JLabel("NAME:");
        JLabel phoneLabel = new JLabel("PHONE:");

        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        phoneLabel.setFont(new Font("Arial", Font.BOLD, 14));

        phoneField.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                String text = ((JTextField) input).getText();
                if (!text.matches("\\d*")) {
                    JOptionPane.showMessageDialog(PhonebookSystem.this,
                            "Phone number must contain only digits!",
                            "Invalid Input",
                            JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                return true;
            }
        });

        nameField.setDocument(new UpperCaseDocument());
        phoneField.setDocument(new UpperCaseDocument());

        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(phoneLabel);
        inputPanel.add(phoneField);
        return inputPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        buttonPanel.setBackground(new Color(255, 182, 193)); // Light pink
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        addButton = createStyledButton("ADD");
        deleteButton = createStyledButton("DELETE");
        searchButton = createStyledButton("SEARCH");
        updateButton = createStyledButton("UPDATE");
        displayButton = createStyledButton("DISPLAY"); // New display button

        displayButton.addActionListener(e -> displayContacts()); // Action for display button

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(displayButton); // Add display button to the panel

        return buttonPanel;
    }

    private JPanel createTextAreaPanel() {
        JPanel textAreaPanel = new JPanel(new BorderLayout());
        textAreaPanel.setBackground(new Color(230, 230, 250)); // Lavender
        textAreaPanel.setBorder(BorderFactory.createTitledBorder("CONTACT LISTS:"));

        contactArea = new JTextArea(12, 20);
        contactArea.setEditable(false);
        contactArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        contactArea.setBackground(new Color(240, 240, 255)); // Light lavender
        contactArea.setBorder(BorderFactory.createLineBorder(new Color(199, 21, 133))); // Dark pink border

        JScrollPane scrollPane = new JScrollPane(contactArea);
        textAreaPanel.add(scrollPane, BorderLayout.CENTER);

        return textAreaPanel;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(90, 30));
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBackground(new Color(199, 21, 133)); // Dark pink
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(255, 105, 180))); // Hot pink border
        button.addActionListener(this);
        return button;
    }

    private void loadContacts() {
        contactArea.setText(""); // Clear current contacts in the display
        try (BufferedReader br = new BufferedReader(new FileReader(contactFile))) {
            String line;
            contactArea.append("NAME:                         NUMBER:\n");
            contactArea.append("-------------------------------------------\n");
            while ((line = br.readLine()) != null) {
                String[] contact = line.split(",");
                if (contact.length == 2) {
                    String formattedName = String.format("%-30s", contact[0].trim());
                    String formattedPhone = String.format("%-15s", contact[1].trim());
                    contactArea.append(formattedName + formattedPhone + "\n");
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading contacts: " + e.getMessage());
        }
    }

    private void saveContacts(List<String> contacts) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(contactFile))) {
            for (String contact : contacts) {
                bw.write(contact);
                bw.newLine();
            }
            loadContacts(); // Reload contacts after saving
        } catch (IOException e) {
            System.err.println("Error saving contacts: " + e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            addContact();
        } else if (e.getSource() == deleteButton) {
            deleteContact();
        } else if (e.getSource() == searchButton) {
            searchContact();
        } else if (e.getSource() == updateButton) {
            updateContact();
        }
    }

    // Method to display contacts manually
    private void displayContacts() {
        loadContacts(); // Simply reload contacts to refresh display
    }

    // Method to add a contact
    private void addContact() {
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();

        if (name.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Both fields must be filled!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<String> contacts = loadContactsFromFile();
        contacts.add(name + "," + phone);
        saveContacts(contacts);

        nameField.setText("");
        phoneField.setText("");
    }

    // Method to delete a contact
    private void deleteContact() {
        String name = nameField.getText().trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the name of the contact to delete.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<String> contacts = loadContactsFromFile();
        boolean found = false;
        for (int i = 0; i < contacts.size(); i++) {
            String[] contact = contacts.get(i).split(",");
            if (contact[0].trim().equalsIgnoreCase(name)) {
                contacts.remove(i);
                found = true;
                break;
            }
        }

        if (found) {
            saveContacts(contacts);
            nameField.setText("");
            phoneField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Contact not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to search for a contact
    private void searchContact() {
        String name = nameField.getText().trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the name of the contact to search.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<String> contacts = loadContactsFromFile();
        boolean found = false;
        for (String contact : contacts) {
            String[] contactInfo = contact.split(",");
            if (contactInfo[0].trim().equalsIgnoreCase(name)) {
                JOptionPane.showMessageDialog(this, "Contact found: " + contact, "Search Result", JOptionPane.INFORMATION_MESSAGE);
                found = true;
                break;
            }
        }

        if (!found) {
            JOptionPane.showMessageDialog(this, "Contact not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to update a contact
    private void updateContact() {
        String name = nameField.getText().trim();
        String newPhone = phoneField.getText().trim();

        if (name.isEmpty() || newPhone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Both fields must be filled!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<String> contacts = loadContactsFromFile();
        boolean found = false;
        for (int i = 0; i < contacts.size(); i++) {
            String[] contact = contacts.get(i).split(",");
            if (contact[0].trim().equalsIgnoreCase(name)) {
                contacts.set(i, name + "," + newPhone);
                found = true;
                break;
            }
        }

        if (found) {
            saveContacts(contacts);
            nameField.setText("");
            phoneField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Contact not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to load contacts from the file
    private List<String> loadContactsFromFile() {
        List<String> contacts = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(contactFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                contacts.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading contacts: " + e.getMessage());
        }
        return contacts;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PhonebookSystem().setVisible(true));
    }
}

// UpperCaseDocument remains the same
class UpperCaseDocument extends PlainDocument {
    @Override
    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
        if (str == null) return;
        super.insertString(offset, str.toUpperCase(), attr);
    }
}
