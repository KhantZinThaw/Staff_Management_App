package Boundary;

import Controller.*;
import Entity.UserAccount;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.ArrayList;

public class SystemAdminGUI {
    // Variables declaration
    private JFrame frame;
    private DefaultTableModel model;
    private JTextField searchTextField;
    private final String[] columnNames = {"Username", "First Name", "Last Name", "Profile", "Status"};
    private ArrayList<UserAccount> userAccounts;

    // Constructor
    public SystemAdminGUI(UserAccount u) {
        displaySystemAdminGUI(u); // Call the system admin GUI method
    }

    // Method to display system admin GUI
    public void displaySystemAdminGUI(UserAccount u) {
        // Initialize frame and panel
        frame = new JFrame("System Admin");
        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Display welcome message for the logged-in user
        JLabel titleLabel = new JLabel("Welcome, " + u.getFullName());
        titleLabel.setBounds(50,75, 500, 36);
        titleLabel.setFont(new Font("Helvetica", Font.PLAIN,28));
        panel.add(titleLabel);

        // Search Field
        searchTextField = new JTextField();
        searchTextField.setBounds(50, 135, 180, 36);
        searchTextField.setFont(new Font("Helvetica", Font.PLAIN,18));
        panel.add(searchTextField);

        // Search Button
        JButton searchButton = new JButton("Search");
        searchButton.setBounds(230, 135, 90, 36);
        searchButton.setFont(new Font("Helvetica", Font.PLAIN,18));
        panel.add(searchButton);

        // Profile list dropdown
        DefaultComboBoxModel<String> profileComboModel = new DefaultComboBoxModel<>(getProfileList().toArray(new String[0]));
        JComboBox profileFilter = new JComboBox(profileComboModel);
        profileFilter.setBounds(380, 135, 180, 36);
        profileFilter.setFont(new Font("Helvetica", Font.PLAIN,18));
        panel.add(profileFilter);

        // View profile button
        JButton viewProfileButton = new JButton("View Profile");
        viewProfileButton.setBounds(580, 135, 135, 36);
        viewProfileButton.setFont(new Font("Helvetica", Font.PLAIN,18));
        panel.add(viewProfileButton);

        // Create account button
        JButton createAccountButton = new JButton("+");
        createAccountButton.setBounds(716, 135, 36, 36);
        createAccountButton.setFont(new Font("Helvetica", Font.PLAIN,18));
        panel.add(createAccountButton);

        // Logout Button
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(650, 50, 100, 36);
        logoutButton.setFont(new Font("Helvetica", Font.PLAIN,18));
        panel.add(logoutButton);

        // Edit profile button
        JButton editButton = new JButton("Edit");
        editButton.setBounds(600, 200, 110, 36);
        editButton.setFont(new Font("Helvetica", Font.PLAIN,18));
        panel.add(editButton);

        // Suspend profile button
        JButton suspendButton = new JButton("Suspend");
        suspendButton.setBounds(600, 250, 110, 36);
        suspendButton.setFont(new Font("Helvetica", Font.PLAIN,18));
        suspendButton.setEnabled(false);
        panel.add(suspendButton);

        // Clear Button
        JButton clearButton = new JButton("Clear");
        clearButton.setBounds(320, 135, 60, 36);
        clearButton.setFont(new Font("Helvetica", Font.PLAIN,18));
        panel.add(clearButton);

        // User account table
        model = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };

        // Fetch data into table
        getAccountList();

        //
        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50,175, 510, 350);
        frame.add(scrollPane);

        // Logout Action Button
        logoutButton.addActionListener(e -> logout());

        // Search action button
        searchButton.addActionListener(e -> searchUserAccount());

        // View profile action
        viewProfileButton.addActionListener(e -> {
            frame.dispose();
            new ViewUserProfileGUI(u);
        });

        clearButton.addActionListener(e -> {
            searchTextField.setText("");
            searchUserAccount();
        });

        // Press "ENTER" to search
        searchTextField.addActionListener(e -> searchUserAccount());

        // Create account action button
        createAccountButton.addActionListener(e -> {
            frame.dispose();
            new CreateUserAccountGUI(u);
        });

        // Action for profile filter
        profileFilter.addItemListener(e -> {
            if(e.getStateChange() == ItemEvent.SELECTED) {
                profileFilter((String) profileFilter.getSelectedItem());
            }
        });

        // Edit account button
        editButton.addActionListener(e -> {
            if (table.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(frame, "Please select account to edit", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                String username = model.getValueAt(table.getSelectedRow(),0).toString();
                UserAccount userAccount = new UpdateUserAccountController().getSelectedAccount(username);
                if (userAccount.getUsername().equals(u.getUsername())){
                    JOptionPane.showMessageDialog(frame, "Please select other users than yourself", "Failed", JOptionPane.ERROR_MESSAGE);
                }else {
                    frame.dispose();
                    new UpdateUserAccountGUI(u, userAccount);
                }

            }
        });

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                if (table.getSelectedRow() != -1 ) {
                    suspendButton.setEnabled(true);
                    String status = model.getValueAt(table.getSelectedRow(), 4).toString();
                    if (status.equals("Active")) {
                        suspendButton.setText("Suspend");
                    } else {
                        suspendButton.setText("Unsuspend");
                    }
                }
            }
        });

        suspendButton.addActionListener(e -> {
            String status = model.getValueAt(table.getSelectedRow(), 4).toString();
            String username = model.getValueAt(table.getSelectedRow(),0).toString();
            String profile = model.getValueAt(table.getSelectedRow(),3).toString();
            if(!profile.equals("System Admin")) {
                if(status.equals("Active")) {
                    if(new SuspendUserAccountController().suspendUserAccount(username)) {
                        getAccountList();
                        JOptionPane.showMessageDialog(frame, "Successfully suspend account", "Success", JOptionPane.PLAIN_MESSAGE);
                        suspendButton.setEnabled(false);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Failed to suspend account", "Failed", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    if(new UnsuspendUserAccountController().unsuspendUserAccount(username)) {
                        getAccountList();
                        JOptionPane.showMessageDialog(frame, "Successfully unsuspend account", "Success", JOptionPane.PLAIN_MESSAGE);
                        suspendButton.setEnabled(false);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Failed to unsuspend account", "Failed", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Can't suspend System Admin", "Failed", JOptionPane.ERROR_MESSAGE);
            }

        });

        frame.add(panel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void logout() {
        frame.dispose();
        new LoginGUI();
    }

    public JFrame getFrame() {
        return frame;
    }

    public void searchUserAccount() {
        String search = searchTextField.getText();

        if(search.isEmpty()) {
            getAccountList();
        } else {
            model.setRowCount(0);
            userAccounts = new SearchUserAccountController().searchUserAccount(search);
            for (UserAccount userAccount : userAccounts) {
                String status;
                if(userAccount.isStatus()) {
                    status = "Active";
                } else {
                    status = "Suspended";
                }
                model.addRow(new Object[]{
                        userAccount.getUsername(),
                        userAccount.getFirstName(),
                        userAccount.getLastName(),
                        userAccount.getUserProfile().getProfileName(),
                        status
                });
            }
        }
    }

    // Method for populating list of profile for dropdown
    public ArrayList<String> getProfileList() {
        return new FilterUserAccountController().getProfileList();
    }

    public void getAccountList() {
        model.setRowCount(0);
        model.setColumnIdentifiers(columnNames);
        userAccounts = new ViewUserAccountController().getAccountList();
        for (UserAccount userAccount : userAccounts) {
            String status;
            if(userAccount.isStatus()) {
                status = "Active";
            } else {
                status = "Suspended";
            }
            model.addRow(new Object[]{
                    userAccount.getUsername(),
                    userAccount.getFirstName(),
                    userAccount.getLastName(),
                    userAccount.getUserProfile().getProfileName(),
                    status
            });
        }
    }

    public void profileFilter(String profileName) {
        model.setRowCount(0);
        userAccounts = new FilterUserAccountController().FilterUserAccount(profileName);
        for (UserAccount userAccount : userAccounts) {
            String status;
            if(userAccount.isStatus()) {
                status = "Active";
            } else {
                status = "Suspended";
            }
            model.addRow(new Object[]{
                    userAccount.getUsername(),
                    userAccount.getFirstName(),
                    userAccount.getLastName(),
                    userAccount.getUserProfile().getProfileName(),
                    status
            });
        }
    }
}
