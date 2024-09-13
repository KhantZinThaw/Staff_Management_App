package Boundary;

import Controller.UpdateWorkSlotController;
import Entity.UserAccount;
import Entity.WorkSlot;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class UpdateWorkSlotGUI {

    public UpdateWorkSlotGUI(UserAccount userAccount, WorkSlot workSlot) {
        displayUpdateWorkSlot(userAccount, workSlot);
    }

    public void displayUpdateWorkSlot(UserAccount userAccount, WorkSlot workSlot) {
        // Frame
        JFrame frame = new JFrame("Edit Work Slot");
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel titleLabel = new JLabel("Edit Work Slot");
        titleLabel.setBounds(275, 30,350, 50);
        titleLabel.setFont(new Font("Helvetica", Font.PLAIN,36));
        panel.add(titleLabel);

        JLabel chefLabel = new JLabel("Chef");
        chefLabel.setBounds(225, 125, 235, 50);
        panel.add(chefLabel);

        JLabel cashierLabel = new JLabel("Cashier");
        cashierLabel.setBounds(225, 175, 235, 50);
        panel.add(cashierLabel);

        JLabel waiterLabel = new JLabel("Waiter");
        waiterLabel.setBounds(225, 225, 235, 50);
        panel.add(waiterLabel);

        JTextField chefField = new JTextField(String.valueOf(workSlot.getChefAmount()));
        chefField.setBounds(325, 125, 235, 50);
        panel.add(chefField);

        JTextField cashierField = new JTextField(String.valueOf(workSlot.getCashierAmount()));
        cashierField.setBounds(325, 175, 235, 50);
        panel.add(cashierField);

        JTextField waiterField = new JTextField(String.valueOf(workSlot.getWaiterAmount()));
        waiterField.setBounds(325, 225, 235, 50);
        panel.add(waiterField);

        JButton saveButton = new JButton("Save");
        saveButton.setBounds(500, 500, 235, 30);
        panel.add(saveButton);

        JButton backButton = new JButton("Back");
        backButton.setBounds(100, 500, 235, 30);
        panel.add(backButton);

        frame.add(panel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);

        // Action for back button
        backButton.addActionListener(e -> {
            frame.dispose();
            new CafeOwnerGUI(userAccount);
        });

        // Action for the save button
        saveButton.addActionListener(e -> {
            try {
                if(chefField.getText().isEmpty() || cashierField.getText().isEmpty() || waiterField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please don't leave any empty field", "Failed", JOptionPane.WARNING_MESSAGE);
                } else if (Integer.parseInt(chefField.getText()) < 1 || Integer.parseInt(cashierField.getText()) < 1 || Integer.parseInt(waiterField.getText()) < 1) {
                    JOptionPane.showMessageDialog(frame, "Must be greater than 0", "Failed", JOptionPane.WARNING_MESSAGE);
                } else {
                    UpdateWorkSlotController updateWorkSlotController = new UpdateWorkSlotController(workSlot.getDate());

                    // Update chef
                    if (!updateWorkSlotController.updateRoleAmount(3, Integer.parseInt(chefField.getText()))){
                        JOptionPane.showMessageDialog(frame, "New number must not less than the number of assigned/approved Chef on this date", "Failed", JOptionPane.WARNING_MESSAGE);
                    }else{
                        // Update Cashier
                        if (!updateWorkSlotController.updateRoleAmount(2, Integer.parseInt(cashierField.getText()))){
                            JOptionPane.showMessageDialog(frame, "New number must not less than the number of assigned/approved Cashier on this date", "Failed", JOptionPane.WARNING_MESSAGE);
                        }else{
                            // Update Waiter
                            if (!updateWorkSlotController.updateRoleAmount( 1, Integer.parseInt(waiterField.getText()))){
                                JOptionPane.showMessageDialog(frame, "New number must not less than the number of assigned/approved Waiter on this date", "Failed", JOptionPane.WARNING_MESSAGE);
                            }else{
                                // Done, return to cafe owner GUI
                                frame.dispose();
                                new CafeOwnerGUI(userAccount);
                            }
                        }
                    }
                }
            }catch (NumberFormatException nfe){
                JOptionPane.showMessageDialog(frame, "Please enter valid value (numbers)");
            }

        });


    }
}
