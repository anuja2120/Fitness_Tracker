import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class LoginPage extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel imageLabel;

    public LoginPage() {
        setTitle("Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 600); // Keeping the size consistent with FitnessTrackerApp

        usernameField = new JTextField(10); // Reduce the size of the text fields
        passwordField = new JPasswordField(10); // Reduce the size of the text fields
        loginButton = new JButton("Login");
        imageLabel = new JLabel();

        // Load and scale the image
        try {
            BufferedImage img = ImageIO.read(new File("C:\\Users\\anuja\\Downloads\\fitness.jpeg"));
            Image scaledImg = img.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(scaledImg);
            imageLabel.setIcon(icon);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding between components

        panel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        // Add an empty label for spacing
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(new JLabel(), gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(loginButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(imageLabel, gbc);

        add(panel);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Check login credentials (e.g., hard-coded for demonstration)
                if (username.equals("anuja") && password.equals("anuja")) {
                    openFitnessTrackerApp();
                    dispose(); // Close the login window
                } else {
                    JOptionPane.showMessageDialog(LoginPage.this, "Invalid username or password");
                }
            }
        });
    }

    private void openFitnessTrackerApp() {
        SwingUtilities.invokeLater(() -> new FitnessTrackerApp().setVisible(true));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginPage().setVisible(true));
    }
}
