package Boundary;

import Controller.*;
import Entity.UserAccount;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CafeStaffGUI {
    private JDateChooser searchDate;
    private Object[][] data;
    private JFrame frame;
    private JPanel panel = new JPanel();
    private Calendar current;
    private JTable workSlotTable;
    private DefaultTableModel tableComponents;
    private JScrollPane scrollPane;
    private String[] tableTitle = {"Date", "Chef's", "Cashier's", "Waiter's"};

    // Constructor
    public CafeStaffGUI(UserAccount u) {

        if (u.getMax_slot() > 0 && u.getRole_id() != 0){
            displayCafeStaffGUI(u);
        }else {
            boolean hasInput = askMaxSlotAndRole(u);

            if (hasInput){
                displayCafeStaffGUI(u);
            }
        }
    }

    public boolean askMaxSlotAndRole(UserAccount u) {
        boolean hasInput = false;
        while(!hasInput) {
            // Create a panel for the input components
            JPanel inputPanel = new JPanel();
            inputPanel.setLayout(new GridLayout(3, 2));

            // Max Slot components
            JLabel maxSlotLabel = new JLabel("Max Slot (must be greater than 0):");
            JTextField maxSlotField = new JTextField();
            inputPanel.add(maxSlotLabel);
            inputPanel.add(maxSlotField);

            // Role components
            JLabel roleLabel = new JLabel("Select role (Permanent):");
            String[] roles = {"Waiter", "Cashier", "Chef"};
            JComboBox<String> roleDropdown = new JComboBox<>(roles);
            inputPanel.add(roleLabel);
            inputPanel.add(roleDropdown);

            // OK button
            int result = JOptionPane.showConfirmDialog(
                    null, inputPanel, "Role and Max Slot Input", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                try {
                    int newMaxSlot = Integer.parseInt(maxSlotField.getText());
                    int roleId = roleDropdown.getSelectedIndex() + 1;
                    if (newMaxSlot > 0 && roleId != -1) {
                        u.setMax_slot(newMaxSlot);
                        u.setRole_id(roleId);

                        if(new SetRoleController().setRole(u) && new SetMaxSlotController().setMaxSlot(u)) {
                            JOptionPane.showMessageDialog(null, "Successfully set the role and max slot");
                        }

                        hasInput = true;
                    } else {
                        JOptionPane.showMessageDialog(null, "Please enter a value greater than 0.");
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid number.");
                }
            }else {
                new LoginGUI();
                break;
            }
        }

        return hasInput;
    }


    // Display cafe staff GUI
    public void displayCafeStaffGUI(UserAccount u) {
        frame = new JFrame("Cafe Staff");
        panel.setLayout(null);

        // Title label
        JLabel titleLabel = new JLabel("Welcome, " + u.getFullName());
        titleLabel.setBounds(50,75, 550, 36);
        titleLabel.setFont(new Font("Helvetica", Font.PLAIN,28));
        panel.add(titleLabel);

        // Schedule button
        JButton scheduleButton = new JButton("Schedule");
        scheduleButton.setBounds(600, 200, 110, 36);
        scheduleButton.setFont(new Font("Helvetica", Font.PLAIN,18));
        panel.add(scheduleButton);

        // Bid Status button
        JButton bidStatusButton = new JButton("Bid Status");
        bidStatusButton.setBounds(600, 250, 110, 36);
        bidStatusButton.setFont(new Font("Helvetica", Font.PLAIN,18));
        panel.add(bidStatusButton);

        // Bid Button
        JButton bid = new JButton("Bid");
        bid.setBounds(600, 300, 110, 36);
        bid.setFont(new Font("Helvetica", Font.PLAIN,18));
        panel.add(bid);

        // Search Button
        JButton searchButton = new JButton("Search");
        searchButton.setBounds(410, 135, 90, 36);
        searchButton.setFont(new Font("Helvetica", Font.PLAIN,18));
        panel.add(searchButton);

        // Search Bar
        searchDate = new JDateChooser();
        searchDate.setDateFormatString("dd MMM, yyyy");
        current = Calendar.getInstance();
        searchDate.setMinSelectableDate(current.getTime());
        searchDate.setBounds(50, 135, 360, 36);
        panel.add(searchDate);

        // Clear search
        JButton clearSearchButton = new JButton("Clear");
        clearSearchButton.setBounds(500, 135, 60, 36);
        clearSearchButton.setFont(new Font("Helvetica", Font.PLAIN,18));
        panel.add(clearSearchButton);

        // Logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(650, 50, 100, 36);
        logoutButton.setFont(new Font("Helvetica", Font.PLAIN,18));
        panel.add(logoutButton);

        // Display All Work Slots Table
        DisplayWorkSlotTable();

        frame.add(panel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Action for Bid Button
        bid.addActionListener(e -> {
            bidSelectedRow(u);
        });

        // Action for Search Button
        searchButton.addActionListener(e -> {
            try {
                if (searchDate.getDate() == null){
                    searchByDate();
                }else{
                    Date selectedDate = new Date(searchDate.getDate().getTime());
                    searchByDate();
                }

            } catch(Exception ex) {
                ex.printStackTrace();
            }
        });

        // Action for Clear Search
        clearSearchButton.addActionListener(e -> {
            searchDate.setDate(null);
            searchByDate();
        });

        // Action for schedule button
        scheduleButton.addActionListener(e -> {
            frame.dispose();
            new MyScheduleGUI(u);
        });

        // Action for bid status button
        bidStatusButton.addActionListener(e -> {
            frame.dispose();
            new BidStatusGUI(u);
        });

        // Action for logout button
        logoutButton.addActionListener(e -> {
            logout();
        });
    }

    public void logout() {
        frame.dispose();
        new LoginGUI();
    }

    public JFrame getFrame() {
        return frame;
    }

    public void DisplayWorkSlotTable() {
        tableComponents = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make all cells in the button column editable
                return column == 4;
            }
        };
        workSlotTable = new JTable(tableComponents);
        workSlotTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tableComponents.setRowCount(0);


        CSViewAvailWSController workSlotController = new CSViewAvailWSController();
        Object[][] workSlotData = workSlotController.getAllWorkSlots();

        tableComponents.setDataVector(workSlotData, tableTitle);

        scrollPane = new JScrollPane(workSlotTable);
        scrollPane.setBounds(50,175, 510, 350);
        panel.add(scrollPane);
    }

    private void searchByDate() {
        // Get selected date
        java.util.Date d = searchDate.getDate();

        // Get data from database
        if (d == null){
            // Search nothing
            // Display all workslots
            data = new CSViewAvailWSController().getAllWorkSlots();
        }else {
            // Search for a workslot
            java.sql.Date selectedDate = new java.sql.Date(d.getTime());
            data = new CSSearchAvailWSController().getWorkSlot(selectedDate);
        }

        // Set data into model
        tableComponents.setDataVector(data, tableTitle);
        workSlotTable.setModel(tableComponents);
    }

    private void bidSelectedRow(UserAccount u) {
        int selectedRow = workSlotTable.getSelectedRow();
        if (selectedRow != -1) {
            String dateString = (String) tableComponents.getValueAt(selectedRow, 0);
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy");
                java.util.Date parsedDate = dateFormat.parse(dateString);
                Date selectedDate = new Date(parsedDate.getTime());

                int slotLeft = new ViewMonthlySlotLeftController().viewMonthlySlotLeft(u,selectedDate);
                if (slotLeft > 0){
                    int confirm = JOptionPane.showConfirmDialog(frame, String.format("Slot left this Month: %d%nAre you sure want to make bid on this date?", slotLeft) , "Make Bid", JOptionPane.YES_NO_OPTION);

                    if (confirm == JOptionPane.YES_OPTION){
                        MakeBidController bidController = new MakeBidController();
                        if (bidController.makeBid(u.getUsername(), selectedDate)) {
                            panel.remove(scrollPane);
                            panel.repaint();
                            panel.revalidate();
                            DisplayWorkSlotTable();
                        }
                    }
                } else if (slotLeft == 0) {
                    JOptionPane.showMessageDialog(frame, String.format("Slot limit reached!%nSlot left this month: %d", slotLeft), "Limit Reached", JOptionPane.WARNING_MESSAGE);
                }else {
                    JOptionPane.showMessageDialog(frame, "Issue while approving bid, please try again!", "Exception Occurs", JOptionPane.WARNING_MESSAGE);
                }
            } catch (IllegalArgumentException | ParseException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a row to place a bid.");
        }
    }
}
