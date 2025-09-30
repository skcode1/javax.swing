import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class calculator extends JFrame implements ActionListener {
    private JTextField display;
    private String operator = "";
    private double num1 = 0;

    public calculator() {
        setTitle("Calculator");
        setSize(300, 400);
        setLocationRelativeTo(null); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        display = new JTextField();
        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setHorizontalAlignment(SwingConstants.RIGHT);
        display.setEditable(false);

        add(display, BorderLayout.NORTH);

        String[] buttonLabels = {
            "7", "8", "9", "/", 
            "4", "5", "6", "*", 
            "1", "2", "3", "-", 
            "0", ".", "=", "+", 
            "C"  
        };

      
        JPanel panel = new JPanel(new GridLayout(5, 4, 5, 5));

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("Arial", Font.PLAIN, 20));
            button.addActionListener(this);
            panel.add(button);
        }

      
        panel.add(new JLabel("")); 

        add(panel, BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.matches("[0-9\\.]")) {
            display.setText(display.getText() + command);
        } else if (command.matches("[\\+\\-\\*/]")) {
            if (!display.getText().isEmpty()) {
                num1 = Double.parseDouble(display.getText());
                operator = command;
                display.setText("");
            }
        } else if (command.equals("=")) {
            if (!display.getText().isEmpty() && !operator.isEmpty()) {
                double num2 = Double.parseDouble(display.getText());
                double result = 0;

                switch (operator) {
                    case "+": result = num1 + num2; break;
                    case "-": result = num1 - num2; break;
                    case "*": result = num1 * num2; break;
                    case "/": 
                        if (num2 == 0) {
                            JOptionPane.showMessageDialog(this, "Cannot divide by zero", "Error", JOptionPane.ERROR_MESSAGE);
                            display.setText("");
                            return;
                        }
                        result = num1 / num2; 
                        break;
                }

                display.setText(String.valueOf(result));
                operator = "";
            }
        } else if (command.equals("C")) {
            display.setText("");
            operator = "";
            num1 = 0;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new calculator().setVisible(true);
        });
    }
}
