package Boundary;

import Entity.UserAccount;

import javax.swing.*;
import java.awt.*;

public class OtherProfileGUI {
    private JFrame frame;
    public OtherProfileGUI(UserAccount u){
        displayView(u);
    }

    public void displayView(UserAccount u){
        frame = new JFrame(u.getUserProfile().getProfileName());
        frame.setLayout(null);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(650, 50, 100, 36);
        logoutButton.setFont(new Font("Helvetica", Font.PLAIN,18));
        logoutButton.addActionListener(e -> logout());
        frame.add(logoutButton);

    }

    public void logout() {
        frame.dispose();
        new LoginGUI();
    }
}
