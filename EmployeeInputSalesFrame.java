package aquamen.ui;

import aquamen.data.DataStore;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class EmployeeInputSalesFrame extends JFrame {

    private JTextField nameField;
    private JTextField gallonsField;
    private JLabel amountLabel;
    private DataStore store;

    private static final int PRICE_PER_GALLON = 40; // pesos

    public EmployeeInputSalesFrame() {
        setTitle("Aquamen - Employee Input Sales");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(750, 500);
        setMinimumSize(new Dimension(420, 380));
        setLocationRelativeTo(null);

        store = DataStore.getInstance();

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(new Color(245, 250, 255));

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(30, 60, 100));
        header.setPreferredSize(new Dimension(0, 55));

        JLabel title = new JLabel("EMPLOYEE INPUT SALES", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        header.add(title, BorderLayout.CENTER);

        // Form card
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 220, 240)),
                new EmptyBorder(30, 35, 30, 35)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 10, 12, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Name
        gbc.gridx = 0; gbc.gridy = 0;
        card.add(createLabel("Name"), gbc);
        gbc.gridx = 1;
        card.add(new JLabel(":"), gbc);
        gbc.gridx = 2;
        nameField = new JTextField(18);
        nameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        card.add(nameField, gbc);

        // Gallons
        gbc.gridx = 0; gbc.gridy = 1;
        card.add(createLabel("Gallons Delivered"), gbc);
        gbc.gridx = 1;
        card.add(new JLabel(":"), gbc);
        gbc.gridx = 2;
        JPanel gallonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        gallonsPanel.setOpaque(false);
        gallonsField = new JTextField(10);
        gallonsField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gallonsPanel.add(gallonsField);
        gallonsPanel.add(new JLabel("Gallons"));
        card.add(gallonsPanel, gbc);

        // Amount (auto-calculated)
        gbc.gridx = 0; gbc.gridy = 2;
        card.add(createLabel("Amount Received"), gbc);
        gbc.gridx = 1;
        card.add(new JLabel(":"), gbc);
        gbc.gridx = 2;
        amountLabel = new JLabel("₱ 0");
        amountLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        amountLabel.setForeground(new Color(0, 100, 60));
        card.add(amountLabel, gbc);

        // Live calculation
        gallonsField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { updateAmount(); }
            public void removeUpdate(DocumentEvent e) { updateAmount(); }
            public void changedUpdate(DocumentEvent e) { updateAmount(); }
        });

        // Buttons
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        btnPanel.setOpaque(false);
        btnPanel.setBorder(new EmptyBorder(20, 0, 0, 0));

        JButton saveBtn = createButton("SAVE SALE", new Color(60, 160, 80));
        saveBtn.addActionListener(e -> saveSale());

        JButton backBtn = createButton("BACK", new Color(30, 60, 100));
        backBtn.addActionListener(e -> {
            dispose();
            new EmployeeHomeFrame().setVisible(true);
        });

        btnPanel.add(saveBtn);
        btnPanel.add(backBtn);

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setOpaque(false);
        center.setBorder(new EmptyBorder(40, 60, 20, 60));
        center.add(card);
        center.add(btnPanel);

        main.add(header, BorderLayout.NORTH);
        main.add(center, BorderLayout.CENTER);

        setContentPane(main);
    }

    private void updateAmount() {
        String text = gallonsField.getText().trim();
        if (text.isEmpty()) {
            amountLabel.setText("₱ 0");
            return;
        }
        try {
            int gallons = Integer.parseInt(text);
            if (gallons < 0) {
                amountLabel.setText("Invalid");
                return;
            }
            int amount = gallons * PRICE_PER_GALLON;
            amountLabel.setText("₱ " + amount);
        } catch (NumberFormatException e) {
            amountLabel.setText("Invalid");
        }
    }

    private void saveSale() {
        String name = nameField.getText().trim();
        String gallonsText = gallonsField.getText().trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the customer name.", "Missing Data", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (gallonsText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the number of gallons.", "Missing Data", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int gallons = Integer.parseInt(gallonsText);
            if (gallons <= 0) {
                JOptionPane.showMessageDialog(this, "Gallons must be a positive whole number.", "Invalid", JOptionPane.ERROR_MESSAGE);
                return;
            }

            store.addSale(name, gallons);
            int amount = gallons * PRICE_PER_GALLON;

            JOptionPane.showMessageDialog(this,
                    "Sale recorded successfully!\n\n" +
                            "Customer: " + name + "\n" +
                            "Gallons: " + gallons + "\n" +
                            "Amount: ₱ " + amount,
                    "Success", JOptionPane.INFORMATION_MESSAGE);

            // Clear fields
            nameField.setText("");
            gallonsField.setText("");
            amountLabel.setText("₱ 0");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Gallons must be a whole number (no decimals).", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        return lbl;
    }

    private JButton createButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(130, 38));
        return btn;
    }
}
