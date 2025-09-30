import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ATM_Simulator extends JFrame implements ActionListener {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    private JTextField pinField;
    private JLabel statusLabel;
    private JLabel balanceLabel;
    private JTextField amountField;

    private double balance = 10000.0;  // initial balance
    private final String correctPin = "1234";

    public ATM_Simulator() {
        setTitle("ATM Simulator");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // center the window

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(createLoginPanel(), "Login");
        mainPanel.add(createMenuPanel(), "Menu");

        add(mainPanel);
        cardLayout.show(mainPanel, "Login");
    }

    // Panel: Login Screen
    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        panel.add(new JLabel("Enter PIN:"));
        pinField = new JPasswordField();
        panel.add(pinField);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        panel.add(loginButton);

        statusLabel = new JLabel("", SwingConstants.CENTER);
        statusLabel.setForeground(Color.RED);
        panel.add(statusLabel);

        return panel;
    }

    // Panel: ATM Menu
    private JPanel createMenuPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Balance display
        balanceLabel = new JLabel("Balance: ₹" + balance, SwingConstants.CENTER);
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(balanceLabel, BorderLayout.NORTH);

        // Buttons
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        String[] actions = {"Withdraw", "Deposit", "Check Balance", "Exit"};

        for (String action : actions) {
            JButton btn = new JButton(action);
            btn.addActionListener(this);
            buttonPanel.add(btn);
        }

        panel.add(buttonPanel, BorderLayout.CENTER);

        // Amount input
        amountField = new JTextField();
        panel.add(amountField, BorderLayout.SOUTH);

        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        // Handle login
        if (command.equals("Login")) {
            String enteredPin = pinField.getText();
            if (enteredPin.equals(correctPin)) {
                statusLabel.setText("");
                cardLayout.show(mainPanel, "Menu");
                updateBalanceLabel();
            } else {
                statusLabel.setText("Incorrect PIN. Try again.");
            }
            return;
        }

        // ATM operations
        switch (command) {
            case "Check Balance":
                updateBalanceLabel();
                break;

            case "Deposit":
                handleDeposit();
                break;

            case "Withdraw":
                handleWithdraw();
                break;

            case "Exit":
                JOptionPane.showMessageDialog(this, "Thank you for using the ATM!");
                System.exit(0);
                break;
        }
    }

    private void handleDeposit() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (amount <= 0) throw new NumberFormatException();
            balance += amount;
            updateBalanceLabel();
            amountField.setText("");
            JOptionPane.showMessageDialog(this, "₹" + amount + " deposited successfully.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid amount.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleWithdraw() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (amount <= 0 || amount > balance) {
                JOptionPane.showMessageDialog(this, "Invalid or insufficient amount.", "Transaction Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            balance -= amount;
            updateBalanceLabel();
            amountField.setText("");
            JOptionPane.showMessageDialog(this, "₹" + amount + " withdrawn successfully.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid amount.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateBalanceLabel() {
        balanceLabel.setText("Balance: ₹" + String.format("%.2f", balance));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ATM_Simulator().setVisible(true);
        });
    }
}
