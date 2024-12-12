import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Calculator extends JFrame implements ActionListener {
    private JTextField display;
    private String num1 = "", num2 = "", operator = "";
    private boolean isResultDisplayed = false;

    private static final String HISTORY_FILE = "calculator_history.txt";

    public Calculator() {
        setTitle("Modern Calculator");
        setSize(400, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Outer panel for the case
        JPanel casePanel = new JPanel();
        casePanel.setBackground(new Color(255, 182, 193)); // Light pink background
        casePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        casePanel.setLayout(new BorderLayout());

        // Inner calculator body
        JPanel calculatorBody = new JPanel();
        calculatorBody.setBackground(new Color(230, 230, 250)); // Lavender background for calculator
        calculatorBody.setLayout(new BorderLayout());
        calculatorBody.setBorder(BorderFactory.createLineBorder(new Color(199, 21, 133), 3, true)); // Dark pink border

        // Display panel
        display = new JTextField();
        display.setEditable(false);
        display.setFont(new Font("Segoe UI", Font.BOLD, 36));
        display.setHorizontalAlignment(SwingConstants.RIGHT);
        display.setPreferredSize(new Dimension(360, 100));
        display.setBackground(Color.WHITE);
        display.setForeground(Color.BLACK);
        display.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        calculatorBody.add(display, BorderLayout.NORTH);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 5, 5));
        buttonPanel.setBackground(new Color(255, 182, 193)); // Light pink for button panel

        String[] buttons = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "C", "0", "=", "+",
            "%"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Segoe UI", Font.BOLD, 20));
            button.setBackground(new Color(199, 21, 133)); // Dark pink buttons
            button.setForeground(Color.WHITE); // White text on buttons
            button.setFocusPainted(false);
            button.setBorder(BorderFactory.createLineBorder(new Color(255, 105, 180), 2, true)); // Hot pink border for buttons
            button.addActionListener(this);
            buttonPanel.add(button);
        }

        calculatorBody.add(buttonPanel, BorderLayout.CENTER);
        casePanel.add(calculatorBody, BorderLayout.CENTER);
        add(casePanel);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if ("0123456789".contains(command)) {
            if (isResultDisplayed) {
                display.setText(command);
                isResultDisplayed = false;
            } else {
                display.setText(display.getText() + command);
            }
        } else if ("/*-+".contains(command)) {
            if (!num1.isEmpty() && !operator.isEmpty()) {
                // Perform calculation if operator already exists
                num2 = display.getText();
                try {
                    double result = calculate(Double.parseDouble(num1), Double.parseDouble(num2), operator);
                    num1 = String.valueOf(result);
                    operator = command;
                    display.setText(num1 + " " + operator + " ");
                    num2 = "";
                } catch (Exception ex) {
                    display.setText("Error");
                    num1 = operator = num2 = "";
                }
            } else {
                // First operator pressed
                num1 = display.getText();
                operator = command;
                display.setText(num1 + " " + operator + " ");
            }
        } else if ("=".equals(command)) {
            if (!num1.isEmpty() && !operator.isEmpty()) {
                num2 = display.getText().replace(num1 + " " + operator + " ", "");
                try {
                    double result = calculate(Double.parseDouble(num1), Double.parseDouble(num2), operator);
                    display.setText(String.valueOf(result));
                    saveToHistory(num1, operator, num2, result);
                    num1 = String.valueOf(result); // Store the result for further calculations
                    operator = "";
                    num2 = "";
                    isResultDisplayed = true;
                } catch (Exception ex) {
                    display.setText("Error");
                    num1 = operator = num2 = "";
                }
            }
        } else if ("C".equals(command)) {
            display.setText("");
            num1 = num2 = operator = "";
            isResultDisplayed = false;
        } else if ("%".equals(command)) {
            try {
                double value = Double.parseDouble(display.getText());
                display.setText(String.valueOf(value / 100));
            } catch (Exception ex) {
                display.setText("Error");
            }
        }
    }

    private double calculate(double num1, double num2, String operator) {
        switch (operator) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "*":
                return num1 * num2;
            case "/":
                if (num2 == 0) throw new ArithmeticException("Cannot divide by zero");
                return num1 / num2;
            default:
                throw new IllegalArgumentException("Invalid operator");
        }
    }

    private void saveToHistory(String num1, String operator, String num2, double result) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(HISTORY_FILE, true))) {
            writer.write(num1 + " " + operator + " " + num2 + " = " + result + "\n");
        } catch (IOException e) {
            System.out.println("Error saving history: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Calculator();
    }
}
