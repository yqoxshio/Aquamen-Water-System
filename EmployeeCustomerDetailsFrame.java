package aquamen.ui;

import aquamen.data.DataStore;
import aquamen.model.Client;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class EmployeeCustomerDetailsFrame extends JFrame {

    private JTextField searchField;
    private JLabel nameValue, addressValue, contactValue, gallonsValue, dateValue, remarksValue;
    private DataStore store;

    public EmployeeCustomerDetailsFrame() {
        setTitle("Aquamen - Employee Customer Details");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 550);
        setMinimumSize(new Dimension(450, 400));
        setLocationRelativeTo(null);

        store = DataStore.getInstance();

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(new Color(245, 250, 255));

        // Header with search
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(140, 190, 220));
        header.setBorder(new EmptyBorder(12, 20, 12, 20));

        searchField = new JTextField(25);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.putClientProperty("JTextField.placeholderText", "Search by name or ID...");

        JButton searchBtn = new JButton("Search");
        searchBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        searchBtn.setBackground(new Color(30, 80, 140));
        searchBtn.setForeground(Color.WHITE);
        searchBtn.setFocusPainted(false);
        searchBtn.setBorderPainted(false);
        searchBtn.addActionListener(e -> searchClient());

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        searchPanel.setOpaque(false);
        searchPanel.add(searchField);
        searchPanel.add(searchBtn);

        header.add(searchPanel, BorderLayout.CENTER);

        // Title
        JLabel title = new JLabel("EMPLOYEE CUSTOMER DETAILS", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setBorder(new EmptyBorder(20, 0, 15, 0));

        // Details card
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 220, 240)),
                new EmptyBorder(25, 30, 25, 30)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        nameValue = createValueLabel();
        addressValue = createValueLabel();
        contactValue = createValueLabel();
        gallonsValue = createValueLabel();
        dateValue = createValueLabel();
        remarksValue = createValueLabel();

        addRow(card, gbc, 0, "Name", nameValue);
        addRow(card, gbc, 1, "Address", addressValue);
        addRow(card, gbc, 2, "Contact Number", contactValue);
        addRow(card, gbc, 3, "Gallons Ordered", gallonsValue);
        addRow(card, gbc, 4, "Delivery Date", dateValue);
        addRow(card, gbc, 5, "Remarks", remarksValue);

        // Back button
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.setOpaque(false);
        bottom.setBorder(new EmptyBorder(15, 20, 20, 30));

        JButton backBtn = new JButton("BACK");
        backBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        backBtn.setBackground(new Color(30, 60, 100));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);
        backBtn.setBorderPainted(false);
        backBtn.setPreferredSize(new Dimension(110, 38));
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backBtn.addActionListener(e -> {
            dispose();
            new EmployeeHomeFrame().setVisible(true);
        });
        bottom.add(backBtn);

        JPanel center = new JPanel(new BorderLayout());
        center.setOpaque(false);
        center.setBorder(new EmptyBorder(0, 40, 0, 40));
        center.add(title, BorderLayout.NORTH);
        center.add(card, BorderLayout.CENTER);

        main.add(header, BorderLayout.NORTH);
        main.add(center, BorderLayout.CENTER);
        main.add(bottom, BorderLayout.SOUTH);

        setContentPane(main);

        // Load first client as example if available
        if (!store.getAllClients().isEmpty()) {
            displayClient(store.getAllClients().get(0));
        }
    }

    private void addRow(JPanel panel, GridBagConstraints gbc, int row, String label, JLabel value) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.3;
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        panel.add(lbl, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.1;
        panel.add(new JLabel(":"), gbc);

        gbc.gridx = 2;
        gbc.weightx = 0.6;
        panel.add(value, gbc);
    }

    private JLabel createValueLabel() {
        JLabel lbl = new JLabel("-");
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        return lbl;
    }

    private void searchClient() {
        String query = searchField.getText().trim();
        if (query.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a name or ID to search.");
            return;
        }

        // Try by ID first
        store.findClientById(query).ifPresentOrElse(
                this::displayClient,
                () -> store.findClientByName(query).ifPresentOrElse(
                        this::displayClient,
                        () -> JOptionPane.showMessageDialog(this, "Client not found.")
                )
        );
    }

    private void displayClient(Client c) {
        nameValue.setText(c.getName());
        addressValue.setText(c.getAddress());
        contactValue.setText(c.getContactNumber());
        gallonsValue.setText(c.getGallonsOrdered() + " gallons");
        dateValue.setText(c.getDeliveryDate());
        remarksValue.setText(c.getRemarks() == null || c.getRemarks().isEmpty() ? "-" : c.getRemarks());
    }
}
