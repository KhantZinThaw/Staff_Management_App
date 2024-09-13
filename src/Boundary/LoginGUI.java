package Boundary;

import Controller.LoginController;
import Entity.*;

import javax.swing.*;
import java.awt.*;

public class LoginGUI {
    // Variables declaration
    private JFrame frame;
    private boolean errorMessageDisplayed;

    // Constructor
    public LoginGUI() {
        displayLogin(); // Call the login GUI method
    }

    // Method to display the login GUI
    public void displayLogin() {
        // Initializing frame and panel
        frame = new JFrame("Login");
        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Title label for the login window
        JLabel titleLabel = new JLabel("Cafe Staff Management System");
        titleLabel.setBounds(100, 20, 500, 25);
        panel.add(titleLabel);

        // Username label
        JLabel usernameLabel = new JLabel("Username : ");
        usernameLabel.setBounds(75, 75, 100, 25);
        panel.add(usernameLabel);

        // Username field
        JTextField usernameField = new JTextField(20);
        usernameField.setBounds(150, 75, 175, 25);
        panel.add(usernameField);

        // Password label
        JLabel passwordLabel = new JLabel("Password : ");
        passwordLabel.setBounds(75, 115, 100, 25);
        panel.add(passwordLabel);

        // Password field
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setBounds(150, 115, 175, 25);
        panel.add(passwordField);

        // Login button
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(150, 175, 100, 25);
        panel.add(loginButton);

        // Set up frame properties
        frame.setContentPane(panel);
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Action listener for the login button
        loginButton.addActionListener(e -> login(usernameField.getText(), passwordField.getText()));

        // Action listener for the password field
        passwordField.addActionListener(e -> login(usernameField.getText(), passwordField.getText()));
    }

    // Method to handle the login process
    public void login(String username, String password) {
        // Call the loginController to validate login credentials
        UserAccount userAccount = new LoginController().login(username, password);

        // Check the account username and password / account status / profile status
        if (userAccount == null) {
            JOptionPane.showMessageDialog(frame, "Invalid username or password", "Login Error", JOptionPane.ERROR_MESSAGE);
            errorMessageDisplayed = true;
        } else if (!userAccount.getUserProfile().isProfileStatus()) {
            JOptionPane.showMessageDialog(frame, "This profile is suspended", "Login Error", JOptionPane.ERROR_MESSAGE);
            errorMessageDisplayed = true;
        } else if (!userAccount.isStatus()) {
            JOptionPane.showMessageDialog(frame, "This account is suspended", "Login Error", JOptionPane.ERROR_MESSAGE);
            errorMessageDisplayed = true;
        } else {
            frame.dispose(); // Close the login window
            // Open the appropriate GUI based on the user's profile
            if (userAccount.getUserProfile().getProfileName().equals("System Admin")) {
                new SystemAdminGUI(userAccount);
            } else if (userAccount.getUserProfile().getProfileName().equals("Cafe Owner")) {
                new CafeOwnerGUI(userAccount);
            } else if (userAccount.getUserProfile().getProfileName().equals("Cafe Manager")) {
                new CafeManagerGUI(userAccount);
            } else if (userAccount.getUserProfile().getProfileName().equals("Cafe Staff")) {
                new CafeStaffGUI(userAccount);
            }else {
                new OtherProfileGUI(userAccount);
            }
        }
    }

    // For Testing
    public boolean isErrorMessageDisplayed() {
        return errorMessageDisplayed;
    }

    // Main method to start
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginGUI();
            }
        });
    }
}