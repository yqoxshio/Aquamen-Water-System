package aquamen.ui;

import aquamen.data.DataStore;
import aquamen.model.Client;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminClientListFrame extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private DataStore store;

    public AdminClientListFrame() {
        setTitle("Aquamen - Admin Client List");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 650);
        setMinimumSize(new Dimension(600, 450));
        setLocationRelativeTo(null);

        store = DataStore.getInstance();

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(new Color(240, 248, 255));

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(30, 60, 100));
        header.setPreferredSize(new Dimension(0, 55));
        header.setBorder(new EmptyBorder(8, 15, 8, 15));

        JButton logoutBtn = createBtn("LOG OUT", new Color(220, 60, 60));
        logoutBtn.addActionListener(e -> UIHelper.logout(this));

        JLabel title = new JLabel("ADMIN CLIENT LIST", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(Color.WHITE);

        header.add(logoutBtn, BorderLayout.WEST);
        header.add(title, BorderLayout.CENTER);

        // Toolbar
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        toolbar.setBackground(new Color(240, 248, 255));
        toolbar.setBorder(new EmptyBorder(5, 15, 5, 15));

        searchField = new JTextField(20);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        searchField.putClientProperty("JTextField.placeholderText", "Search Bar");

        JButton searchBtn = createBtn("Search", new Color(70, 130, 180));
        searchBtn.addActionListener(e -> filterTable());

        JButton addBtn = createBtn("ADD", new Color(100, 200, 100));
        addBtn.addActionListener(e -> showAddDialog());

        JButton archiveBtn = createBtn("DATA ARCHIVE", new Color(230, 180, 50));
        archiveBtn.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Archive feature: All current data is kept in memory.\nFuture version can export to file.",
                "Data Archive", JOptionPane.INFORMATION_MESSAGE));

        toolbar.add(searchField);
        toolbar.add(searchBtn);
        toolbar.add(Box.createHorizontalStrut(20));
        toolbar.add(addBtn);
        toolbar.add(archiveBtn);

        // Table
        String[] columns = {"ID", "NAME", "ADDRESS", "No.", "STATUS"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(28);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(140, 180, 210));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(new EmptyBorder(0, 15, 0, 15));

        // Action buttons under table
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        actionPanel.setBackground(new Color(240, 248, 255));

        JButton editBtn = createBtn("EDIT", new Color(60, 160, 80));
        editBtn.addActionListener(e -> editSelected());

        JButton deleteBtn = createBtn("DELETE", new Color(200, 60, 60));
        deleteBtn.addActionListener(e -> deleteSelected());

        actionPanel.add(editBtn);
        actionPanel.add(deleteBtn);

        // Bottom
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottom.setBackground(new Color(240, 248, 255));
        bottom.setBorder(new EmptyBorder(10, 0, 15, 0));

        JButton backBtn = createBtn("BACK", new Color(30, 60, 100));
        backBtn.setPreferredSize(new Dimension(120, 38));
        backBtn.addActionListener(e -> {
            dispose();
            new AdminHomeFrame().setVisible(true);
        });
        bottom.add(backBtn);

        JPanel center = new JPanel(new BorderLayout());
        center.setOpaque(false);
        center.add(toolbar, BorderLayout.NORTH);
        center.add(scroll, BorderLayout.CENTER);
        center.add(actionPanel, BorderLayout.SOUTH);

        main.add(header, BorderLayout.NORTH);
        main.add(center, BorderLayout.CENTER);
        main.add(bottom, BorderLayout.SOUTH);

        setContentPane(main);
        refreshTable();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Client> clients = store.getAllClients();
        for (Client c : clients) {
            tableModel.addRow(new Object[]{
                    c.getId(),
                    c.getName(),
                    c.getAddress(),
                    c.getContactNumber(),
                    c.getStatus()
            });
        }
    }

    private void filterTable() {
        String query = searchField.getText().trim().toLowerCase();
        tableModel.setRowCount(0);
        for (Client c : store.getAllClients()) {
            if (query.isEmpty() ||
                    c.getName().toLowerCase().contains(query) ||
                    c.getId().toLowerCase().contains(query) ||
                    c.getAddress().toLowerCase().contains(query)) {
                tableModel.addRow(new Object[]{
                        c.getId(), c.getName(), c.getAddress(),
                        c.getContactNumber(), c.getStatus()
                });
            }
        }
    }

    private void showAddDialog() {
        JTextField nameF = new JTextField(20);
        JTextField addrF = new JTextField(20);
        JTextField contactF = new JTextField(15);
        JTextField gallonsF = new JTextField(5);
        JTextField dateF = new JTextField(12);
        JTextField remarksF = new JTextField(20);

        JPanel panel = new JPanel(new GridLayout(0, 2, 8, 8));
        panel.add(new JLabel("Name:"));
        panel.add(nameF);
        panel.add(new JLabel("Address:"));
        panel.add(addrF);
        panel.add(new JLabel("Contact No.:"));
        panel.add(contactF);
        panel.add(new JLabel("Gallons Ordered:"));
        panel.add(gallonsF);
        panel.add(new JLabel("Delivery Date (YYYY-MM-DD):"));
        panel.add(dateF);
        panel.add(new JLabel("Remarks:"));
        panel.add(remarksF);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add New Client",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                int gallons = Integer.parseInt(gallonsF.getText().trim());
                Client client = new Client(null, nameF.getText().trim(), addrF.getText().trim(),
                        contactF.getText().trim(), gallons, dateF.getText().trim(),
                        remarksF.getText().trim(), "Pending");
                store.addClient(client);
                refreshTable();
                JOptionPane.showMessageDialog(this, "Client added successfully!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Gallons must be a whole number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editSelected() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select a client first.");
            return;
        }
        String id = (String) tableModel.getValueAt(row, 0);
        store.findClientById(id).ifPresent(client -> {
            JTextField nameF = new JTextField(client.getName(), 20);
            JTextField addrF = new JTextField(client.getAddress(), 20);
            JTextField contactF = new JTextField(client.getContactNumber(), 15);
            JTextField gallonsF = new JTextField(String.valueOf(client.getGallonsOrdered()), 5);
            JTextField dateF = new JTextField(client.getDeliveryDate(), 12);
            JTextField remarksF = new JTextField(client.getRemarks(), 20);
            JComboBox<String> statusBox = new JComboBox<>(new String[]{"Pending", "Delivered", "Cancelled"});
            statusBox.setSelectedItem(client.getStatus());

            JPanel panel = new JPanel(new GridLayout(0, 2, 8, 8));
            panel.add(new JLabel("Name:"));
            panel.add(nameF);
            panel.add(new JLabel("Address:"));
            panel.add(addrF);
            panel.add(new JLabel("Contact No.:"));
            panel.add(contactF);
            panel.add(new JLabel("Gallons Ordered:"));
            panel.add(gallonsF);
            panel.add(new JLabel("Delivery Date:"));
            panel.add(dateF);
            panel.add(new JLabel("Remarks:"));
            panel.add(remarksF);
            panel.add(new JLabel("Status:"));
            panel.add(statusBox);

            int result = JOptionPane.showConfirmDialog(this, panel, "Edit Client",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                try {
                    client.setName(nameF.getText().trim());
                    client.setAddress(addrF.getText().trim());
                    client.setContactNumber(contactF.getText().trim());
                    client.setGallonsOrdered(Integer.parseInt(gallonsF.getText().trim()));
                    client.setDeliveryDate(dateF.getText().trim());
                    client.setRemarks(remarksF.getText().trim());
                    client.setStatus((String) statusBox.getSelectedItem());
                    store.updateClient(client);
                    refreshTable();
                    JOptionPane.showMessageDialog(this, "Client updated!");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Gallons must be a whole number.");
                }
            }
        });
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select a client first.");
            return;
        }
        String id = (String) tableModel.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete client " + id + "?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            store.deleteClient(id);
            refreshTable();
        }
    }

    private JButton createBtn(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}
