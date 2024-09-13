package Boundary;

import Controller.*;
import Entity.UserAccount;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.util.Calendar;

public class MyScheduleGUI {
    private Calendar current;
    private JDateChooser date_search;
    private Object[][] data;
    private DefaultTableModel model;
    private JTable table;
    private JScrollPane scrollPane;
    private DefaultTableCellRenderer centerRenderer;

    public MyScheduleGUI(UserAccount userAccount){
        displayUpcomingSchedule(userAccount);
    }

    public void displayUpcomingSchedule(UserAccount userAccount){
        JFrame frame = new JFrame("Schedule");
        frame.setLayout(null);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Title
        JLabel title = new JLabel("My Schedule");
        title.setBounds(50,75, 550, 36);
        title.setFont(new Font("Helvetica", Font.PLAIN,28));
        frame.add(title);

        // Return button
        JButton back = new JButton("Back");
        back.setBounds(650, 50, 100, 36);
        back.setFont(new Font("Helvetica", Font.PLAIN,18));

        frame.add(back);

        // Date search bar
        date_search = new JDateChooser();
        current = Calendar.getInstance();
        // Prevent selecting past dates
        date_search.setMinSelectableDate(current.getTime());
        date_search.setBounds(50, 135, 180, 36);
        frame.add(date_search);

        // Search button
        JButton searchButton = new JButton("Search");
        searchButton.setBounds(230, 135, 90, 36);
        searchButton.setFont(new Font("Helvetica", Font.PLAIN,18));
        frame.add(searchButton);

        // clear filter button
        JButton clearFilter = new JButton("Clear");
        clearFilter.setBounds(320, 135, 60, 36);
        clearFilter.setFont(new Font("Helvetica", Font.PLAIN,18));
        frame.add(clearFilter);

        // Dropdown filter
        String[] options = {"Upcoming", "Past"};
        JComboBox<String > dropdown = new JComboBox<>(options);
        dropdown.setBounds(380, 135, 180, 36);
        dropdown.setFont(new Font("Helvetica", Font.PLAIN,18));
        frame.add(dropdown);

        // Table
        data = new ViewAllUpcomingController().getAllUpcoming("Upcoming", userAccount);
        String[] columnNames = {"ID", "Date", "Status"};
        model = new DefaultTableModel(data, columnNames){
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make all cells in the button column editable
                return column == 3;
            }
        };

        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // To align the data to the center
        centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        // Align center to all columns
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Add table to scroll pane
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50,175, 510, 350);
        frame.add(scrollPane);

        // Dropdown button implementation
        // Set to default (View all) of each option when change
        dropdown.addActionListener(e->{
            // Get option
            String select = dropdown.getSelectedItem().toString();

            // Remove old table
            frame.remove(scrollPane);

            // Refresh frame
            frame.repaint();
            frame.revalidate();

            // Clear date chooser
            date_search.setDate(null);
            current = Calendar.getInstance();

            if (select.equals("Upcoming")){
                date_search.setMinSelectableDate(current.getTime());
                date_search.setMaxSelectableDate(null);
            }else if (select.equals("Past")){
                date_search.setMinSelectableDate(null);
                current.add(Calendar.DAY_OF_MONTH, -1);
                date_search.setMaxSelectableDate(current.getTime());
            }

            data = new FilterWorkScheduleController().getFilterWorkSchedule(select, userAccount);
            // Set data into model
            model.setDataVector(data, columnNames);
            table.setModel(model);

            // To align the data to the center
            centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

            // Align center to all columns
            for (int i = 0; i < table.getColumnCount(); i++) {
                table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }

            // Add table to scroll pane
            scrollPane = new JScrollPane(table);
            scrollPane.setBounds(50,175, 510, 350);
            // Add to frame
            frame.add(scrollPane);

            // Refresh frame
            frame.repaint();
            frame.revalidate();
        });

        searchButton.addActionListener(e->{
            // Remove old table
            frame.remove(scrollPane);

            // Refresh frame
            frame.repaint();
            frame.revalidate();

            // Get selected date
            java.util.Date d = date_search.getDate();

            if (d == null){
                data = new SearchScheduleByDate().getScheduleByDate(dropdown.getSelectedItem().toString(), userAccount, null);
            }else{
                java.sql.Date selectedDate = new java.sql.Date(d.getTime());
                data = new SearchScheduleByDate().getScheduleByDate(dropdown.getSelectedItem().toString(), userAccount, selectedDate);
            }




            // Set data into model
            model.setDataVector(data, columnNames);
            table.setModel(model);

            // To align the data to the center
            centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

            // Align center to all columns
            for (int i = 0; i < table.getColumnCount(); i++) {
                table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }

            // Add table to scroll pane
            scrollPane = new JScrollPane(table);
            scrollPane.setBounds(50,175, 510, 350);
            // Add to frame
            frame.add(scrollPane);

            // Refresh frame
            frame.repaint();
            frame.revalidate();

        });

        clearFilter.addActionListener(e->{
            // Clear date chooser
            date_search.setDate(null);
            current = Calendar.getInstance();

            // Set back to default (Upcoming)
            dropdown.setSelectedIndex(0);
            date_search.setMinSelectableDate(current.getTime());

            // Get data from database
            data = new FilterWorkScheduleController().getFilterWorkSchedule("Upcoming", userAccount);


            // Set data into model
            model.setDataVector(data, columnNames);

            // To align the data to the center
            centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

            // Align center to all columns
            for (int i = 0; i < table.getColumnCount(); i++) {
                table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }

            // Refresh table
            table.repaint();
            table.revalidate();
        });

        back.addActionListener(e -> {
            frame.dispose();
            new CafeStaffGUI(userAccount);
        });
    }
}
