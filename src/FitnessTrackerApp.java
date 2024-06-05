import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class FitnessTrackerApp extends JFrame {
    private JTextField stepsField;
    private JTextField distanceField;
    private JTextField heightField;
    private JTextField weightField;
    private JComboBox<String> unitBox;
    private JComboBox<String> activityBox;
    private JButton trackButton;
    private JButton clearButton;
    private JButton bmiButton;
    private JButton exportButton;
    private JButton loadButton;
    private JLabel resultLabel;
    private JLabel bmiLabel;
    private JTextArea historyArea;
    private ArrayList<String> activityHistory;

    public FitnessTrackerApp() {
        setTitle("FITNESS TRACKER");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 600);

        stepsField = new JTextField(10);
        distanceField = new JTextField(10);
        heightField = new JTextField(10);
        weightField = new JTextField(10);
        unitBox = new JComboBox<>(new String[]{"Kilometers", "Miles"});
        activityBox = new JComboBox<>(new String[]{"Walking", "Running", "Cycling"});
        trackButton = new JButton("Track Activity");
        clearButton = new JButton("Clear");
        bmiButton = new JButton("Calculate BMI");
        exportButton = new JButton("Export History");
        loadButton = new JButton("Load History");
        resultLabel = new JLabel();
        bmiLabel = new JLabel();
        historyArea = new JTextArea(10, 40);
        activityHistory = new ArrayList<>();

        JScrollPane historyScrollPane = new JScrollPane(historyArea);

        JPanel inputPanel = new JPanel(new GridLayout(9, 2, 5, 5));
        inputPanel.add(new JLabel("Steps taken:"));
        inputPanel.add(stepsField);
        inputPanel.add(new JLabel("Distance:"));
        inputPanel.add(distanceField);
        inputPanel.add(new JLabel("Unit:"));
        inputPanel.add(unitBox);
        inputPanel.add(new JLabel("Activity:"));
        inputPanel.add(activityBox);
        inputPanel.add(new JLabel("Height (m):"));
        inputPanel.add(heightField);
        inputPanel.add(new JLabel("Weight (kg):"));
        inputPanel.add(weightField);
        inputPanel.add(trackButton);
        inputPanel.add(clearButton);
        inputPanel.add(bmiButton);
        inputPanel.add(exportButton);
        inputPanel.add(loadButton);

        JPanel resultPanel = new JPanel(new GridLayout(3, 1));
        resultPanel.add(resultLabel);
        resultPanel.add(bmiLabel);

        JPanel historyPanel = new JPanel(new BorderLayout());
        historyPanel.add(new JLabel("Activity History:"), BorderLayout.NORTH);
        historyPanel.add(historyScrollPane, BorderLayout.CENTER);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPanel.add(inputPanel, BorderLayout.NORTH);
        contentPanel.add(resultPanel, BorderLayout.CENTER);
        contentPanel.add(historyPanel, BorderLayout.SOUTH);

        add(contentPanel);

        trackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int steps = Integer.parseInt(stepsField.getText());
                    double distance = Double.parseDouble(distanceField.getText());
                    String unit = (String) unitBox.getSelectedItem();
                    String activity = (String) activityBox.getSelectedItem();
                    double caloriesBurned = ActivityTracker.calculateCaloriesBurned(steps, distance, unit, activity);
                    resultLabel.setText("Calories Burned: " + caloriesBurned);

                    String historyEntry = String.format("Activity: %s, Steps: %d, Distance: %.2f %s, Calories: %.2f",
                            activity, steps, distance, unit, caloriesBurned);
                    activityHistory.add(historyEntry);
                    updateHistory();
                } catch (NumberFormatException ex) {
                    resultLabel.setText("Invalid input. Please enter numeric values.");
                }
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stepsField.setText("");
                distanceField.setText("");
                heightField.setText("");
                weightField.setText("");
                resultLabel.setText("");
                bmiLabel.setText("");
                historyArea.setText("");
                activityHistory.clear();
            }
        });

        bmiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double height = Double.parseDouble(heightField.getText());
                    double weight = Double.parseDouble(weightField.getText());
                    double bmi = BMICalculator.calculateBMI(height, weight);
                    bmiLabel.setText("BMI: " + bmi);
                } catch (NumberFormatException ex) {
                    bmiLabel.setText("Invalid input. Please enter numeric values.");
                }
            }
        });

        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (PrintWriter writer = new PrintWriter(new FileWriter("activity_history.txt"))) {
                    for (String entry : activityHistory) {
                        writer.println(entry);
                    }
                    JOptionPane.showMessageDialog(FitnessTrackerApp.this, "History exported successfully.");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(FitnessTrackerApp.this, "Error exporting history.");
                }
            }
        });

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (BufferedReader reader = new BufferedReader(new FileReader("activity_history.txt"))) {
                    activityHistory.clear();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        activityHistory.add(line);
                    }
                    updateHistory();
                    JOptionPane.showMessageDialog(FitnessTrackerApp.this, "History loaded successfully.");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(FitnessTrackerApp.this, "Error loading history.");
                }
            }
        });
    }

    private void updateHistory() {
        StringBuilder historyText = new StringBuilder();
        for (String entry : activityHistory) {
            historyText.append(entry).append("\n");
        }
        historyArea.setText(historyText.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FitnessTrackerApp().setVisible(true));
    }
}
