package Boundary;

import Controller.*;
import Entity.Bid;
import Entity.UserAccount;
import Entity.WorkSlot;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;

public class AssignStaffGUI {
    // ~~ For Available Staff Table data ~~
    private Object[][] as_data;
    private DefaultTableModel as_model;
    private JTable as_table;
    private JScrollPane as_scrollPane;

    // ~~ For Bids Table data ~~
    private Object[][] bids_data;
    private DefaultTableModel bids_model;
    private JTable bids_table;
    private JScrollPane bids_scrollPane;

    // Store selected data
    private Bid bid;

    // Dynamic labels
    private JLabel chefAmt;
    private JLabel cashierAmt;
    private JLabel waiterAmt;

    public AssignStaffGUI(UserAccount u, WorkSlot workSlot){
        displayAssignStaff(u, workSlot);
    }

    public void displayAssignStaff(UserAccount u, WorkSlot workSlot){
        // Set frame
        JFrame frame = new JFrame("Assign Staff");
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set title
        JLabel title = new JLabel("Assign Staff");
        title.setFont(new Font("Jost", Font.BOLD, 20));
        title.setBounds(50, 46, 214, 80);
        frame.add(title);

        // Set Date
        JLabel slot_date = new JLabel("Date: " + workSlot.dateToString());
        slot_date.setFont(new Font("Jost", Font.BOLD, 20));
        slot_date.setBounds(550, 46, 214, 80);
        frame.add(slot_date);

        // Back button
        JButton back = new JButton("Back");
        back.setBounds(660, 23, 110, 36);
        back.addActionListener(e->{
            frame.dispose();
            new CafeManagerGUI(u);
        });
        frame.add(back);

        // Panel for position of each role left
        JPanel amountPanel = new JPanel();
        amountPanel.setBackground(Color.LIGHT_GRAY);
        amountPanel.setBounds(40, 110, 700, 50);
        amountPanel.setLayout(null);

        // Label for chef amount
        chefAmt = new JLabel("Chef: " + workSlot.getChefAmount());
        chefAmt.setFont(new Font("Jost", Font.PLAIN, 20));
        chefAmt.setBounds(10, 5, 100, 36);
        amountPanel.add(chefAmt);

        // Label for cashier amount
        cashierAmt = new JLabel("Cashier: " + workSlot.getCashierAmount());
        cashierAmt.setFont(new Font("Jost", Font.PLAIN, 20));
        cashierAmt.setBounds(275, 5, 120, 36);
        amountPanel.add(cashierAmt);

        // Label for cashier amount
        waiterAmt = new JLabel("Waiter: " + workSlot.getWaiterAmount());
        waiterAmt.setFont(new Font("Jost", Font.PLAIN, 20));
        waiterAmt.setBounds(575, 5, 100, 36);
        amountPanel.add(waiterAmt);
        // Add amount panel to frame
        frame.add(amountPanel);


        // Set panel for available staff
        JPanel as_panel = new JPanel();
        as_panel.setBackground(Color.LIGHT_GRAY);
        as_panel.setBounds(20, 170, 360,350);
        as_panel.setLayout(null);

        // Set title of available staff panel
        JLabel as_title = new JLabel("Available Staff");
        as_title.setFont(new Font("Jost", Font.BOLD, 20));
        as_title.setBounds(110, 20, 200, 30);
        as_panel.add(as_title);

        // Initialize column name
        String[] as_col = {"Name", "Username", "Role"};

        // Fetch available staffs from database
        as_data = new ViewAvailStaffController().getAvailableStaffs(workSlot.getDate());
        as_model = new DefaultTableModel(as_data, as_col){
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make all cells in the button column editable
                return column == 3;
            }
        };

        // Put into table
        as_table = new JTable(as_model);
        as_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Add table to scroll pane
        as_scrollPane = new JScrollPane(as_table);
        as_scrollPane.setBounds(10, 60, 210, 265);
        as_panel.add(as_scrollPane);

        // Set Button for assign staff
        JButton as_button = new JButton("Assign");
        as_button.setBounds(245, 100, 100, 30);
        as_panel.add(as_button);
        // Add available staff panel to frame
        frame.add(as_panel);

        // Assign Staff Button is implemented below
        // Set panel for upcoming bids
        JPanel bidsPanel = new JPanel();
        bidsPanel.setBackground(Color.LIGHT_GRAY);
        bidsPanel.setBounds(400, 170, 360, 350);
        bidsPanel.setLayout(null);

        // Set upcoming bid panel title
        JLabel bid_title = new JLabel("Upcoming Bids");
        bid_title.setFont(new Font("Jost", Font.BOLD, 20));
        bid_title.setBounds(120, 20, 200, 30);
        bidsPanel.add(bid_title);

        // Set the table and it's data
        String[] bid_col = {"Bid ID","Staff Name", "Role Name"};
        // Fetch data from database
        bids_data = new ViewBidDataController().viewBidData(workSlot.getDate());
        bids_model = new DefaultTableModel(bids_data, bid_col){
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make all cells in the button column editable
                return column == 3;
            }
        };

        // Make a table
        bids_table = new JTable(bids_model);
        bids_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // Add table to scroll pane
        bids_scrollPane = new JScrollPane(bids_table);
        bids_scrollPane.setBounds(10, 60, 210, 265);
        bidsPanel.add(bids_scrollPane);

        // Get data from table after selecting
        bids_table.getSelectionModel().addListSelectionListener(e-> {

        if(!e.getValueIsAdjusting()) {
            int selected = bids_table.getSelectedRow();
            if (selected != -1) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd MMM, yyyy");

                try {
                    // Create Workslot object
                    int bid_id = (int) bids_table.getValueAt(selected, 0);
                    String name = (String) bids_table.getValueAt(selected, 1);
                    String role = (String) bids_table.getValueAt(selected, 2);

                    bid = new Bid(bid_id, name, role, workSlot.getDate());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        });

        // Button for approving bid
        JButton approve = new JButton("Approve");
        approve.setBounds(245, 80, 100, 30);
        approve.setHorizontalAlignment(SwingConstants.CENTER);
        bidsPanel.add(approve);

        // Button for rejecting bid
        JButton reject = new JButton("Reject");
        reject.setBounds(245, 120, 100, 30);
        reject.setHorizontalAlignment(SwingConstants.CENTER);

        // Add Reject button to bids panel
        bidsPanel.add(reject);
        // Add bids panel to frame
        frame.add(bidsPanel);

        // Available staff button implementation
        as_button.addActionListener(e->{
            int selectedRow = as_table.getSelectedRow();
            if (selectedRow != -1){
                String name = (String) as_table.getValueAt(selectedRow, 0);
                String username = (String) as_table.getValueAt(selectedRow, 1);
                String role = (String) as_table.getValueAt(selectedRow, 2);
                int confirm = JOptionPane.showConfirmDialog(frame, String.format("Are you sure you want to assign %s (Role: %s) to %s?", name, role, workSlot.dateToString()), "Assign Staff", JOptionPane.YES_NO_OPTION);

                if(confirm == JOptionPane.YES_OPTION) {
                    Object[] result = new AssignAvailStaffController().assignAvailStaff(name, username, role, workSlot);
                    boolean isSuccess = (boolean)result[0];

                    if(isSuccess){
                        // Delete bid from JTable row
                        as_model.removeRow(selectedRow);

                        // Remove Existing amount panel
                        amountPanel.remove(chefAmt);
                        amountPanel.remove(cashierAmt);
                        amountPanel.remove(waiterAmt);

                        amountPanel.repaint();
                        amountPanel.revalidate();

                        frame.remove(amountPanel);

                        // Update labels
                        chefAmt.setText("Chef: " + workSlot.getChefAmount());

                        cashierAmt.setText("Cashier: " + workSlot.getCashierAmount());
                        waiterAmt.setText("Waiter: " + workSlot.getWaiterAmount());

                        // Add updated amount panel
                        amountPanel.add(chefAmt);
                        amountPanel.add(cashierAmt);
                        amountPanel.add(waiterAmt);

                        amountPanel.repaint();
                        amountPanel.revalidate();

                        frame.add(amountPanel);

                        frame.repaint();
                        frame.revalidate();
                        // Update bids table
                        bidsPanel.remove(bids_scrollPane);

                        frame.repaint();
                        frame.revalidate();

                        bids_data = new ViewBidDataController().viewBidData(workSlot.getDate());
                        bids_model.setDataVector(bids_data, bid_col);

                        bids_table.setModel(bids_model);
                        bids_scrollPane = new JScrollPane(bids_table);
                        bids_scrollPane.setBounds(10, 60, 210, 265);
                        bidsPanel.add(bids_scrollPane);

                        frame.repaint();
                        frame.revalidate();

                        // Show success message
                        JOptionPane.showMessageDialog(frame, "Assigning process completed", "Assign Success", JOptionPane.INFORMATION_MESSAGE);
                    }else {
                        JOptionPane.showMessageDialog(frame, result[1], "Warning!", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }else {
                JOptionPane.showMessageDialog(frame, "Please select a Staff", "Select Staff", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Implement approve button
        approve.addActionListener(e->{
            if(bid == null){
                // Error message when no bid is selected
                JOptionPane.showMessageDialog(frame, "Please select a bid", "Select Bid", JOptionPane.WARNING_MESSAGE);
            }else{
                int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to approve this bid?", "Approve Bid", JOptionPane.YES_NO_OPTION);

                if(confirm == JOptionPane.YES_OPTION){

                    Object[] result = new ApproveBidController().processApprove(bid, workSlot);
                    boolean isSuccess = (boolean)result[0];

                    if(isSuccess){
                        int selectedRow = bids_table.getSelectedRow();
                        if ((selectedRow != -1)){
                            // Delete bid from JTable row
                            bids_model.removeRow(selectedRow);

                            // Remove Existing amount panel
                            amountPanel.remove(chefAmt);
                            amountPanel.remove(cashierAmt);
                            amountPanel.remove(waiterAmt);

                            amountPanel.repaint();
                            amountPanel.revalidate();

                            frame.remove(amountPanel);


                            // Update labels
                            chefAmt.setText("Chef: " + workSlot.getChefAmount());

                            cashierAmt.setText("Cashier: " + workSlot.getCashierAmount());
                            waiterAmt.setText("Waiter: " + workSlot.getWaiterAmount());

                            // Add updated amount panel
                            amountPanel.add(chefAmt);
                            amountPanel.add(cashierAmt);
                            amountPanel.add(waiterAmt);

                            amountPanel.repaint();
                            amountPanel.revalidate();

                            frame.add(amountPanel);

                            frame.repaint();
                            frame.revalidate();

                            // Update available staff table
                            as_panel.remove(as_scrollPane);

                            frame.repaint();
                            frame.revalidate();

                            as_data = new ViewAvailStaffController().getAvailableStaffs(workSlot.getDate());
                            as_model.setDataVector(as_data, as_col);

                            as_table.setModel(as_model);
                            as_scrollPane = new JScrollPane(as_table);
                            as_scrollPane.setBounds(10, 60, 210, 265);
                            as_panel.add(as_scrollPane);


                            frame.repaint();
                            frame.revalidate();


                            // Set selected Bid data to default
                            bid = null;

                            // Show success message
                            JOptionPane.showMessageDialog(frame, "Bid approved", "Approve Success", JOptionPane.INFORMATION_MESSAGE);
                        }


                    }else{
                        JOptionPane.showMessageDialog(frame, result[1], "Warning!", JOptionPane.WARNING_MESSAGE);
                    }

                }
            }
        });

        // Action listener to reject bid
        reject.addActionListener(e->{
            if(bid == null){
                // Error message when no bid is selected
                JOptionPane.showMessageDialog(frame, "Please select a bid", "Select Bid", JOptionPane.WARNING_MESSAGE);
            }else{
                // Show confirmation message
                int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to reject this bid?", "Reject Bid", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION){
                    // Process Reject
                    boolean isSuccess = new RejectBidController().processReject(bid);

                    if (isSuccess){
                        int selectedRow = bids_table.getSelectedRow();
                        if ((selectedRow != -1)){
                            // Delete bid from JTable row
                            bids_model.removeRow(selectedRow);
                            // Show success message
                            JOptionPane.showMessageDialog(frame, "Bid rejected", "Reject Success", JOptionPane.INFORMATION_MESSAGE);

                            // Set selected bid data to default
                            bid = null;
                        }
                    }else{
                        // Show error message when issue occur
                        JOptionPane.showMessageDialog(frame, "Issue when rejecting bid", "Error", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });
    }
}
