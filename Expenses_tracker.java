import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class Expense {
    String name;
    double amount;

    public Expense(String name, double amount) {
        this.name = name;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return name + " - ₹" + String.format("%.2f", amount);
    }
}

public class Expenses_tracker extends JFrame {
    private JTextField nameField;
    private JTextField amountField;
    private DefaultListModel<Expense> expenseListModel;
    private JLabel totalLabel;
    private JList<Expense> expenseList;

    public Expenses_tracker() {
        setTitle("Expense Tracker");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 

       
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add Expense"));

        nameField = new JTextField();
        amountField = new JTextField();

        inputPanel.add(new JLabel("Expense Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Amount (₹):"));
        inputPanel.add(amountField);

        JButton addButton = new JButton("Add");
        JButton deleteButton = new JButton("Delete");

        inputPanel.add(addButton);
        inputPanel.add(deleteButton);

        expenseListModel = new DefaultListModel<>();
        expenseList = new JList<>(expenseListModel);
        JScrollPane scrollPane = new JScrollPane(expenseList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Expenses"));

        
        JPanel totalPanel = new JPanel();
        totalLabel = new JLabel("Total: ₹0.00");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalPanel.add(totalLabel);

   
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(totalPanel, BorderLayout.SOUTH);


        addButton.addActionListener(e -> addExpense());
        deleteButton.addActionListener(e -> deleteSelectedExpense());
    }

    private void addExpense() {
        String name = nameField.getText().trim();
        String amountText = amountField.getText().trim();

        if (name.isEmpty() || amountText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both name and amount.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double amount = Double.parseDouble(amountText);
            if (amount < 0) {
                JOptionPane.showMessageDialog(this, "Amount cannot be negative.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Expense expense = new Expense(name, amount);
            expenseListModel.addElement(expense);
            updateTotal();
            nameField.setText("");
            amountField.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number for amount.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteSelectedExpense() {
        int selectedIndex = expenseList.getSelectedIndex();
        if (selectedIndex != -1) {
            expenseListModel.remove(selectedIndex);
            updateTotal();
        } else {
            JOptionPane.showMessageDialog(this, "Please select an expense to delete.", "Delete Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void updateTotal() {
        double total = 0;
        for (int i = 0; i < expenseListModel.size(); i++) {
            total += expenseListModel.get(i).amount;
        }
        totalLabel.setText("Total: ₹" + String.format("%.2f", total));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Expenses_tracker().setVisible(true);
        });
    }
}

    

