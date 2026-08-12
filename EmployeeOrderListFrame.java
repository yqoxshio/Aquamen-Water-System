package aquamen.ui;

import aquamen.data.DataStore;
import aquamen.model.Order;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EmployeeOrderListFrame extends JFrame {

    private DefaultTableModel tableModel;
    private DataStore store;

    public EmployeeOrderListFrame() {
        setTitle("Aquamen - Employee Order List");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setMinimumSize(new Dimension(550, 400));
        setLocationRelativeTo(null);

        store = DataStore.getInstance();

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(new Color(245, 250, 255));

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(30, 60, 100));
        header.setPreferredSize(new Dimension(0, 55));
        header.setBorder(new EmptyBorder(8, 15, 8, 15));

        JButton logoutBtn = new JButton("LOG OUT");
        logoutBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        logoutBtn.setBackground(new Color(220, 60, 60));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setBorderPainted(false);
        logoutBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutBtn.addActionListener(e -> UIHelper.logout(this));

        JLabel title = new JLabel("EMPLOYEE CLIENT LIST", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(Color.WHITE);

        header.add(logoutBtn, BorderLayout.WEST);
        header.add(title, BorderLayout.CENTER);

        // Subtitle
        JLabel subtitle = new JLabel("ORDER LIST (PENDING DELIVERY)", SwingConstants.CENTER);
        subtitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        subtitle.setBorder(new EmptyBorder(15, 0, 10, 0));

        // Table
        String[] columns = {"ID", "NAME", "ADDRESS", "GALLONS ORDERED", "STATUS"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(28);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(140, 180, 210));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(new EmptyBorder(0, 20, 0, 20));

        // Mark as Delivered button
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actionPanel.setOpaque(false);
        actionPanel.setBorder(new EmptyBorder(5, 20, 5, 20));

        JButton deliverBtn = new JButton("MARK AS DELIVERED");
        deliverBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        deliverBtn.setBackground(new Color(60, 160, 80));
        deliverBtn.setForeground(Color.WHITE);
        deliverBtn.setFocusPainted(false);
        deliverBtn.setBorderPainted(false);
        deliverBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        deliverBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Please select an order first.");
                return;
            }
            String id = (String) tableModel.getValueAt(row, 0);
            store.updateOrderStatus(id, "Delivered");
            refreshTable();
            JOptionPane.showMessageDialog(this, "Order marked as Delivered.");
        });
        actionPanel.add(deliverBtn);

        // Back
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottom.setOpaque(false);
        bottom.setBorder(new EmptyBorder(10, 0, 15, 0));

        JButton backBtn = new JButton("BACK");
        backBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        backBtn.setBackground(new Color(30, 60, 100));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);
        backBtn.setBorderPainted(false);
        backBtn.setPreferredSize(new Dimension(120, 38));
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backBtn.addActionListener(e -> {
            dispose();
            new EmployeeHomeFrame().setVisible(true);
        });
        bottom.add(backBtn);

        JPanel center = new JPanel(new BorderLayout());
        center.setOpaque(false);
        center.add(subtitle, BorderLayout.NORTH);
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
        List<Order> orders = store.getPendingOrders();
        for (Order o : orders) {
            tableModel.addRow(new Object[]{
                    o.getId(),
                    o.getClientName(),
                    o.getAddress(),
                    o.getGallonsOrdered(),
                    o.getStatus()
            });
        }
    }
}
