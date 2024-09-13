package Boundary;

import Controller.UpdateUserAccountController;
import Entity.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class UpdateUserAccountGUI {
    public UpdateUserAccountGUI(UserAccount original, UserAccount selected) {
        displayUpdateUserAccount(original, selected);
    }

    public void displayUpdateUserAccount(UserAccount original, UserAccount selected) {
        JFrame frame = new JFrame("Edit Account");
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel titleLabel = new JLabel("Edit User Account");
        titleLabel.setBounds(240, 30,350, 50);
        titleLabel.setFont(new Font("Helvetica", Font.PLAIN,36));
        panel.add(titleLabel);

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(225, 125, 235, 50);
        panel.add(usernameLabel);

        JTextField usernameField = new JTextField(selected.getUsername());
        usernameField.setBounds(325, 125, 235, 50);
        usernameField.setEnabled(false);
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(225, 175, 235, 50);
        panel.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField(selected.getPassword());
        passwordField.setBounds(325, 175, 235, 50);
        panel.add(passwordField);

        JLabel firstNameLabel = new JLabel("First Name");
        firstNameLabel.setBounds(225, 225, 235, 50);
        panel.add(firstNameLabel);

        JTextField firstNameField = new JTextField(selected.getFirstName());
        firstNameField.setBounds(325, 225, 235, 50);
        panel.add(firstNameField);

        JLabel lastNameLabel = new JLabel("Last Name");
        lastNameLabel.setBounds(225, 275, 235, 50);
        panel.add(lastNameLabel);

        JTextField lastNameField = new JTextField(selected.getLastName());
        lastNameField.setBounds(325, 275, 235, 50);
        panel.add(lastNameField);

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setBounds(225, 325, 235, 50);
        panel.add(emailLabel);

        JTextField emailField = new JTextField(selected.getEmail());
        emailField.setBounds(325, 325, 235, 50);
        panel.add(emailField);

        JLabel profileLabel = new JLabel("Profile");
        profileLabel.setBounds(225, 375, 235, 50);
        panel.add(profileLabel);

        // Profile dropdown
        ArrayList<String> profileList = new UpdateUserAccountController().getProfileList();
        DefaultComboBoxModel<String> profileComboModel = new DefaultComboBoxModel<>(profileList.toArray(new String[0]));
        JComboBox<String> profileComboBox = new JComboBox<>(profileComboModel);
        profileComboBox.setSelectedIndex(selected.getUserProfile().getProfileID()-1);
        profileComboBox.setBounds(325, 375, 235,50);
        panel.add(profileComboBox);

        // Back button
        JButton backButton = new JButton("Back");
        backButton.setBounds(100, 500, 235, 30);
        panel.add(backButton);

        // Save button
        JButton saveButton = new JButton("Save");
        saveButton.setBounds(500, 500, 235, 30);
        panel.add(saveButton);

        frame.add(panel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        backButton.addActionListener(e -> {
            frame.dispose();
            new SystemAdminGUI(original);
        });

        saveButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String email = emailField.getText();
            int profile = profileComboBox.getSelectedIndex() + 1;
            UserAccount updatedUser = new UserAccount(username, password, firstName, lastName, email, new UserProfile(profile));
            if(password.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please don't leave any empty field", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (new UpdateUserAccountController().UpdateUserAccount(updatedUser)) {
                JOptionPane.showMessageDialog(frame, "User account successfully saved", "Success", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
                new SystemAdminGUI(original);
            } else {
                JOptionPane.showMessageDialog(frame, "Failed to save user account", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}