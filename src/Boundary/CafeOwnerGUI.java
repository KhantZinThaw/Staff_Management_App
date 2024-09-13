package Boundary;

import Controller.*;
import Entity.UserAccount;
import Entity.WorkSlot;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;

public class CafeOwnerGUI {
    private JFrame frame;
    private JPanel panel;
    private JTable table;
    private DefaultTableModel model;
    private final String[] columnNames = {"Date", "Chef's", "Cashier's", "Waiter's"};

    // Constructor
    public CafeOwnerGUI(UserAccount u) {
        displayCafeOwnerGUI(u);
    }

    // Display cafe owner GUI
    public void displayCafeOwnerGUI(UserAccount u) {
        frame = new JFrame("Cafe Owner");
        panel = new JPanel();
        panel.setLayout(null);

        // Title Label
        JLabel titleLabel = new JLabel("Welcome, " + u.getFullName());
        titleLabel.setBounds(50,75, 500, 36);
        titleLabel.setFont(new Font("Helvetica", Font.PLAIN,28));
        panel.add(titleLabel);

        // Logout Button
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(650, 50, 100, 36);
        logoutButton.setFont(new Font("Helvetica", Font.PLAIN,18));
        panel.add(logoutButton);

        // Search Button
        JButton searchButton = new JButton("Search");
        searchButton.setBounds(375, 135, 90, 36);
        searchButton.setFont(new Font("Helvetica", Font.PLAIN,18));
        panel.add(searchButton);

        // Search Bar
        JDateChooser searchDate = new JDateChooser();
        searchDate.setDateFormatString("dd/MM/yyyy");
        Calendar current = Calendar.getInstance();
        searchDate.setMinSelectableDate(current.getTime());
        searchDate.setBounds(50, 135, 325, 36);
        frame.add(searchDate);

        // Clear Search
        JButton clearSearchButton = new JButton("Clear");
        clearSearchButton.setBounds(465, 135, 60, 36);
        clearSearchButton.setFont(new Font("Helvetica", Font.PLAIN,18));
        panel.add(clearSearchButton);

        // Create workSlot button
        JButton createWorkSlotButton = new JButton("+");
        createWorkSlotButton.setBounds(525, 135, 36, 36);
        createWorkSlotButton.setFont(new Font("Helvetica", Font.PLAIN,18));
        panel.add(createWorkSlotButton);

        model = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };

        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Display Table
        WorkSlotTable();

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50,175, 510, 350);
        panel.add(scrollPane);

        // Delete Button
        JButton deleteButton = new JButton("Delete");
        deleteButton.setBounds(600, 250, 110, 36);
        deleteButton.setFont(new Font("Helvetica", Font.PLAIN,18));
        panel.add(deleteButton);
        deleteButton.addActionListener(e -> deleteSelectedRow());

        frame.add(panel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Edit Button
        JButton editButton = new JButton("Edit");
        editButton.setBounds(600, 200, 110, 36);
        editButton.setFont(new Font("Helvetica", Font.PLAIN,18));
        panel.add(editButton);


        editButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if(selectedRow != -1) {
                String dateString = model.getValueAt(table.getSelectedRow(), 0).toString();
                int chefAmount = (int) model.getValueAt(table.getSelectedRow(), 1);
                int cashierAmount = (int) model.getValueAt(table.getSelectedRow(), 2);
                int waiterAmount = (int) model.getValueAt(table.getSelectedRow(), 3);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy");
                try {
                    java.util.Date utilDate = dateFormat.parse(dateString); // Parse to java.util.Date
                    java.sql.Date date = new java.sql.Date(utilDate.getTime()); // Convert to java.sql.Date
                    WorkSlot workSlot = new WorkSlot(date, chefAmount, cashierAmount, waiterAmount);
                    frame.dispose();
                    new UpdateWorkSlotGUI(u, workSlot);
                } catch (ParseException d) {
                    d.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please select a row to edit");
            }

        });

        // Action for searchButton
        searchButton.addActionListener(e -> {
            if (searchDate.getDate() == null) {
                WorkSlotTable();
            } else {
                Date selectedDate = new Date(searchDate.getDate().getTime());
                searchWorkSlot(selectedDate);
            }
        });

        // Action for clear search
        clearSearchButton.addActionListener(e -> {
            searchDate.setDate(null);
            WorkSlotTable();
        });

        // Action for createWorkSlotButton
        createWorkSlotButton.addActionListener(e -> {
            frame.dispose();
            new OwnerCreateWorkSlotGUI(u);
        });

        // Action for logout button
        logoutButton.addActionListener(e -> {
            logout();
        });
    }

    public JFrame getFrame() {
        return frame;
    }
    public void logout() {
        frame.dispose();
        new LoginGUI();
    }

    private void searchWorkSlot(Date selectedDate) {
        model.setRowCount(0);
        WorkSlot workSlot = new SearchWorkSlotController().searchDate(selectedDate);
        if (workSlot != null) {
            model.addRow(new Object[]{workSlot.dateToString(), workSlot.getChefAmount(), workSlot.getCashierAmount(), workSlot.getWaiterAmount()});
        }
    }

    private void deleteSelectedRow() {
        int selectedRow = table.getSelectedRow();
        if(selectedRow != -1) {
            String dateToDelete = model.getValueAt(selectedRow, 0).toString();
            JFrame deleteFrame = new JFrame();

            int confirmDelete = JOptionPane.showConfirmDialog(
                    deleteFrame,
                    "Confirm Delete?",
              "Delete Confirmation",
              JOptionPane.YES_NO_OPTION
            );

            if(confirmDelete == JOptionPane.YES_OPTION) {
                try {
                    model.removeRow(selectedRow);
                    DeleteWorkSlotController deleteWorkSlotController = new DeleteWorkSlotController();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy");
                    java.util.Date utilDate = dateFormat.parse(dateToDelete);
                    Date sqlDate = new Date(utilDate.getTime());
                    boolean deleteSuccess = deleteWorkSlotController.deleteWorkSlot(sqlDate);
                    if(deleteSuccess) {
                        System.out.println("Row Deleted");
                    }
                    else {
                        System.out.println("Error In Deleting");
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a row to delete");
        }
    }

    private void WorkSlotTable() {

        model.setRowCount(0);
        model.setColumnIdentifiers(columnNames);

        WorkSlotController workSlotController = new WorkSlotController();
        ArrayList<WorkSlot> workSlotData = workSlotController.getAllWorkSlots();

        SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM, yyyy");

        for (WorkSlot workSlot : workSlotData) {
            try {
                String formattedDate = outputFormat.format(workSlot.getDate());

                Object[] rowData = {
                        formattedDate,
                        workSlot.getChefAmount(),
                        workSlot.getCashierAmount(),
                        workSlot.getWaiterAmount()
                };
                model.addRow(rowData);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }
}

