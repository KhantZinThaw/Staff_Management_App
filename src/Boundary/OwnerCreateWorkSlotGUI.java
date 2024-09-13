package Boundary;

import Controller.WorkSlotController;
import Controller.CreateWorkSlotController;
import Entity.UserAccount;
import Entity.WorkSlot;
import com.toedter.calendar.JDateChooser;

import java.awt.*;
import java.util.Calendar;
import java.util.List;
import javax.swing.*;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class OwnerCreateWorkSlotGUI {
    private Calendar current;
    private JButton backButton;
    private JButton createButton;
    private JTextField searchField;
    private JTextField cashierField;
    private JTextField chefField;
    private JTextField waiterField;

    public OwnerCreateWorkSlotGUI(UserAccount u){
        displayCreateWorkSlotGUI(u);
    }

    public void displayCreateWorkSlotGUI(UserAccount u){
        JFrame frame = new JFrame("Create Work Slot");
        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Title Label
        JLabel titleLabel = new JLabel("Create Work Slot");
        titleLabel.setBounds(250, 30,350, 50);
        titleLabel.setFont(new Font("Helvetica", Font.PLAIN,36));
        panel.add(titleLabel);

        // Date Label
        JLabel dateLabel = new JLabel("Date");
        dateLabel.setBounds(225, 125, 235, 50);
        panel.add(dateLabel);

        // Date Field
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd/MM/yyyy");
        current = Calendar.getInstance();
        dateChooser.setMinSelectableDate(current.getTime());
        dateChooser.setBounds(325, 125, 235, 50);
        panel.add(dateChooser);

        // Chef Label
        JLabel chefLabel = new JLabel("Chef");
        chefLabel.setBounds(225, 175, 235, 50);
        panel.add(chefLabel);

        // Chef Field
        chefField = new JTextField(20);
        chefField.setBounds(325, 175, 235, 50);
        panel.add(chefField);

        // Cashier Label
        JLabel cashierLabel = new JLabel("Cashier");
        cashierLabel.setBounds(225, 225, 235, 50);
        panel.add(cashierLabel);

        // Cashier Field
        cashierField = new JTextField(20);
        cashierField.setBounds(325, 225, 235, 50);
        panel.add(cashierField);

        // Waiter Label
        JLabel waiterLabel = new JLabel("Waiter");
        waiterLabel.setBounds(225, 275, 235, 50);
        panel.add(waiterLabel);

        // Staff Field
        waiterField = new JTextField(20);
        waiterField.setBounds(325, 275, 235, 50);
        panel.add(waiterField);

        // Create Button
        createButton = new JButton("Create");
        createButton.setBounds(500, 500, 235, 30);
        panel.add(createButton);

        // Back Button
        backButton = new JButton("Back");
        backButton.setBounds(100, 500, 235, 30);
        panel.add(backButton);

        // Action for create button
        createButton.addActionListener(e -> {
            // If date field is not selected, prompt user to select
            if(dateChooser.getDate() != null) {
                java.util.Date selectedDateUtil = dateChooser.getDate();
                java.sql.Date selectedDate = new java.sql.Date(selectedDateUtil.getTime());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String date = sdf.format(selectedDate);

                // Error message if there is already an existing workslot
                if (workSlotAlreadyExists(selectedDate)) {
                    JOptionPane.showMessageDialog(frame, "Work slot already exists for the selected date", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // If any of the fields is empty, prompt user to enter valid amount
                if(!chefField.getText().isEmpty() && !cashierField.getText().isEmpty() && !waiterField.getText().isEmpty()) {
                    try{

                        int numOfChef = Integer.parseInt(chefField.getText());
                        int numOfCashier = Integer.parseInt(cashierField.getText());
                        int numOfWaiter = Integer.parseInt(waiterField.getText());

                        if(numOfCashier < 1 || numOfChef < 1 || numOfWaiter < 1) {
                            JOptionPane.showMessageDialog(frame, "Chef, Cashier and Waiter must be more than 0");
                        } else {
                            JOptionPane.showMessageDialog(frame, "Successfully created!");
                            new CreateWorkSlotController().createWorkSlot(date, numOfChef, numOfCashier, numOfWaiter);
                            frame.dispose();
                            new CafeOwnerGUI(u);
                        }

                    }catch (NumberFormatException nfe){
                        JOptionPane.showMessageDialog(frame, "Please enter valid value (numbers)");

                    }



                } else {
                    JOptionPane.showMessageDialog(frame, "Fields must be filled");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a date");
            }
        });

        // Action for back button
        backButton.addActionListener(e -> {
            frame.dispose();
            new CafeOwnerGUI(u);
        });

        frame.add(panel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private boolean workSlotAlreadyExists(Date selectedDate){
        WorkSlotController workSlotController = new WorkSlotController();
        List<WorkSlot> workSlots = workSlotController.getWorkSlotsByDate(selectedDate);
        return !workSlots.isEmpty();
    }
}