import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

// Task class represents a single to-do item
class Task implements Serializable {
    private String description;
    private boolean done;

    public Task(String description) {
        this.description = description;
        this.done = false;
    }

    public String getDescription() { return description; }
    public boolean isDone() { return done; }

    public void toggleDone() { done = !done; }

    @Override
    public String toString() {
        return (done ? "[✓] " : "[ ] ") + description;
    }
}

public class to_do_list extends JFrame {
    private DefaultListModel<Task> taskListModel;
    private JList<Task> taskList;
    private JTextField taskInput;
    private JButton addButton, removeButton, toggleDoneButton;
    private final String DATA_FILE = "tasks.dat";

    public to_do_list() {
        super("to_do_list");

        // Load tasks
        taskListModel = new DefaultListModel<>();
        loadTasks();

        taskList = new JList<>(taskListModel);
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        taskList.setFont(new Font("Arial", Font.PLAIN, 16));

        JScrollPane scrollPane = new JScrollPane(taskList);

        taskInput = new JTextField();
        addButton = new JButton("Add Task");
        removeButton = new JButton("Remove Task");
        toggleDoneButton = new JButton("Toggle Done");

        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        inputPanel.add(taskInput, BorderLayout.CENTER);
        inputPanel.add(addButton, BorderLayout.EAST);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(removeButton);
        buttonsPanel.add(toggleDoneButton);

        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.NORTH);
        add(buttonsPanel, BorderLayout.SOUTH);

        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Button actions
        addButton.addActionListener(e -> addTask());
        removeButton.addActionListener(e -> removeTask());
        toggleDoneButton.addActionListener(e -> toggleDone());

        // Save tasks on close
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveTasks();
            }
        });
    }

    private void addTask() {
        String text = taskInput.getText().trim();
        if (text.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Task description cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        taskListModel.addElement(new Task(text));
        taskInput.setText("");
    }

    private void removeTask() {
        int idx = taskList.getSelectedIndex();
        if (idx != -1) {
            taskListModel.remove(idx);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a task to remove.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void toggleDone() {
        int idx = taskList.getSelectedIndex();
        if (idx != -1) {
            Task task = taskListModel.getElementAt(idx);
            task.toggleDone();
            // Refresh the list to update display
            taskList.repaint();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a task to toggle.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void saveTasks() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            ArrayList<Task> tasks = new ArrayList<>();
            for (int i = 0; i < taskListModel.size(); i++) {
                tasks.add(taskListModel.get(i));
            }
            oos.writeObject(tasks);
        } catch (IOException e) {
            // Removed e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving tasks.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadTasks() {
        File file = new File(DATA_FILE);
        if (!file.exists()) return;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            ArrayList<Task> tasks = (ArrayList<Task>) ois.readObject();
            for (Task t : tasks) {
                taskListModel.addElement(t);
            }
        } catch (Exception e) {
            // Removed e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading saved tasks.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new to_do_list().setVisible(true);
        });
    }
}

    

