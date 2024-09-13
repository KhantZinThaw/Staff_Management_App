package Boundary;

import Controller.*;
import Entity.Bid;
import Entity.UserAccount;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicReference;

public class BidStatusGUI {
    private JFrame frame;
    private DefaultTableModel model;
    private String[] columnNames = {"ID", "Date", "Status"};
    private ArrayList<Bid> bids;

    public BidStatusGUI(UserAccount userAccount) {
        displayBidStatus(userAccount);
    }

    public void displayBidStatus(UserAccount userAccount) {
        frame = new JFrame("Bid Status");
        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Title
        JLabel titleLabel = new JLabel("Bid Status");
        titleLabel.setBounds(50,75, 550, 36);
        titleLabel.setFont(new Font("Helvetica", Font.PLAIN,28));
        frame.add(titleLabel);

        // Back button
        JButton backButton = new JButton("Back");
        backButton.setBounds(650, 50, 100, 36);
        backButton.setFont(new Font("Helvetica", Font.PLAIN,18));
        panel.add(backButton);

        // DateChooser
        JDateChooser dateChooser = new JDateChooser();
        AtomicReference<Calendar> currentDate = new AtomicReference<>(Calendar.getInstance());
        dateChooser.setMinSelectableDate(currentDate.get().getTime());
        dateChooser.setBounds(50, 135, 180, 36);
        frame.add(dateChooser);

        // Search Button
        JButton searchButton = new JButton("Search");
        searchButton.setBounds(230, 135, 90, 36);
        searchButton.setFont(new Font("Helvetica", Font.PLAIN,18));
        panel.add(searchButton);

        // Clear Button
        JButton clearButton = new JButton("Clear");
        clearButton.setBounds(320, 135, 60, 36);
        clearButton.setFont(new Font("Helvetica", Font.PLAIN,18));
        panel.add(clearButton);

        // Update Button
        JButton updateButton = new JButton("Update");
        updateButton.setBounds(600, 200, 120, 36);
        updateButton.setFont(new Font("Helvetica", Font.PLAIN,18));
        updateButton.setEnabled(false);
        panel.add(updateButton);

        // Cancel Button
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(600, 250, 120, 36);
        cancelButton.setFont(new Font("Helvetica", Font.PLAIN,18));
        cancelButton.setEnabled(false);
        panel.add(cancelButton);

        // Dropdown filter
        String[] options = {"Pending", "Approved", "Rejected"};
        JComboBox<String> dropdown = new JComboBox<>(options);
        dropdown.setBounds(380, 135, 180, 36);
        dropdown.setFont(new Font("Helvetica", Font.PLAIN,18));
        frame.add(dropdown);

        // Table
        model = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };

        model.setColumnIdentifiers(columnNames);
        getBidStatus(userAccount);
        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50,175, 510, 350);
        frame.add(scrollPane);

        frame.add(panel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        backButton.addActionListener(e -> {
            frame.dispose();
            new CafeStaffGUI(userAccount);
        });

        clearButton.addActionListener(e -> {
            dateChooser.setDate(null);
            currentDate.set(Calendar.getInstance());
            getBidStatus(userAccount);
        });

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                if (table.getSelectedRow() != -1 ) {
                    String bidStatus = model.getValueAt(table.getSelectedRow(), 2).toString();
                    if (bidStatus.equals("Pending")) {
                        cancelButton.setEnabled(true);
                        updateButton.setEnabled(true);
                    } else {
                        cancelButton.setEnabled(false);
                        updateButton.setEnabled(false);
                    }
                }
            }
        });


        updateButton.addActionListener(e -> {
            int bidId = (int) model.getValueAt(table.getSelectedRow(), 0);
            String dateString = model.getValueAt(table.getSelectedRow(), 1).toString();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            try {
                java.util.Date utilDate = format.parse(dateString); // Parse to java.util.Date
                java.sql.Date date = new java.sql.Date(utilDate.getTime()); // Convert to java.sql.Date
                frame.dispose();
                Bid selectedBid = new Bid(bidId, date);
                new UpdateBidGUI(userAccount, selectedBid);
            } catch (ParseException d) {
                d.printStackTrace();
            }
        });

        cancelButton.addActionListener(e -> {
            String dateString = model.getValueAt(table.getSelectedRow(), 1).toString();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                java.util.Date utilDate = format.parse(dateString); // Parse to java.util.Date
                Date date = new Date(utilDate.getTime()); // Convert to java.sql.Date
                CancelBidController cancelBidController = new CancelBidController();
                if(cancelBidController.cancelBid(date)) {
                    JOptionPane.showMessageDialog(frame, "Successfully cancel bid", "Success", JOptionPane.PLAIN_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "Failed to cancel bid", "Failed", JOptionPane.PLAIN_MESSAGE);
                }
                getBidStatus(userAccount);
            } catch (ParseException d) {
                d.printStackTrace();
            }
        });

        dropdown.addItemListener(e -> {
            if(e.getStateChange() == ItemEvent.SELECTED) {
                bidStatusFilter(userAccount, (String) dropdown.getSelectedItem());
            }
        });

        searchButton.addActionListener(e -> {
            if(dateChooser.getDate() == null) {
                getBidStatus(userAccount);
            } else {
                Date selectedDate = new Date(dateChooser.getDate().getTime());
                searchBid(userAccount, selectedDate);
            }
        });
    }

    public void getBidStatus(UserAccount userAccount) {
        model.setRowCount(0);
        ArrayList<Bid> bids = new ViewBidStatusController().viewBidStatus(userAccount.getUsername());
        for (Bid bid : bids) {
            model.addRow(new Object[]{
                    bid.getBidId(),
                    bid.getDate(),
                    bid.getBidStatus()
            });
        }
    }

    public void bidStatusFilter(UserAccount userAccount, String selectedBidStatus) {
        model.setRowCount(0);
        ArrayList<Bid> bids = new FilterBidStatusController().filterBidStatus(userAccount.getUsername(), selectedBidStatus);
        for (Bid bid : bids) {
            model.addRow(new Object[]{
                    bid.getBidId(),
                    bid.getDate(),
                    bid.getBidStatus()
            });
        }
    }

    public void searchBid(UserAccount userAccount, Date selectedDate) {
        model.setRowCount(0);
        ArrayList<Bid> bids = new SearchBidController().searchBid(userAccount.getUsername(), selectedDate);
        for (Bid bid : bids) {
            model.addRow(new Object[]{
                    bid.getBidId(),
                    bid.getDate(),
                    bid.getBidStatus()
            });
        }
    }
}
