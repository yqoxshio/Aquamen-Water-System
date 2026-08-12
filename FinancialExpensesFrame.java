package aquamen.ui;

import aquamen.data.DataStore;
import aquamen.data.ExcelExporter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;

public class FinancialExpensesFrame extends JFrame {

    private JTextField salesField;
    private JTextField gallonsField;
    private JTextField expensesField;

    public FinancialExpensesFrame() {
        setTitle("Aquamen - Totaled Financial Expenses");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(720, 520);
        setMinimumSize(new Dimension(420, 420));
        setLocationRelativeTo(null);

        DataStore store = DataStore.getInstance();

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(Color.WHITE);

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(140, 190, 220));
        header.setPreferredSize(new Dimension(0, 50));

        JLabel title = new JLabel("TOTALED FINANCIAL EXPENSES", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(new Color(20, 50, 80));
        header.add(title, BorderLayout.CENTER);

        // Form
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(new EmptyBorder(30, 50, 15, 50));

        JPanel formCard = new JPanel(new GridBagLayout());
        formCard.setBackground(Color.WHITE);
        formCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 210, 220)),
                new EmptyBorder(25, 30, 25, 30)
        ));
        formCard.setMaximumSize(new Dimension(420, 200));
        formCard.setAlignmentX(Component.CENTER_ALIGNMENT);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        formCard.add(new JLabel("SALES"), gbc);
        gbc.gridx = 1;
        salesField = new JTextField(12);
        salesField.setText(String.valueOf(store.getTotalSalesToday()));
        formCard.add(salesField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formCard.add(new JLabel("GALLONS"), gbc);
        gbc.gridx = 1;
        gallonsField = new JTextField(12);
        gallonsField.setText(String.valueOf(store.getTotalGallonsToday()));
        formCard.add(gallonsField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formCard.add(new JLabel("EXPENSES"), gbc);
        gbc.gridx = 1;
        expensesField = new JTextField(12);
        expensesField.setText(String.valueOf(store.getTotalExpenses()));
        formCard.add(expensesField, gbc);

        // Info label
        JLabel infoLabel = new JLabel("<html><center>On CONFIRM the data is saved and automatically<br>added as a new row in the Excel spreadsheet.</center></html>");
        infoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        infoLabel.setForeground(new Color(80, 80, 80));
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoLabel.setBorder(new EmptyBorder(15, 0, 10, 0));

        // Buttons
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 8));
        btnPanel.setOpaque(false);

        JButton confirmBtn = createButton("CONFIRM & EXPORT", new Color(100, 170, 210));
        confirmBtn.setPreferredSize(new Dimension(160, 38));
        confirmBtn.addActionListener(e -> confirmAndExport());

        JButton openSheetBtn = createButton("OPEN SPREADSHEET", new Color(46, 125, 50));
        openSheetBtn.setPreferredSize(new Dimension(160, 38));
        openSheetBtn.addActionListener(e -> openSpreadsheet());

        JButton backBtn = createButton("BACK", new Color(30, 60, 100));
        backBtn.addActionListener(e -> {
            dispose();
            new AdminSalesRecordFrame().setVisible(true);
        });

        btnPanel.add(confirmBtn);
        btnPanel.add(openSheetBtn);
        btnPanel.add(backBtn);

        formPanel.add(formCard);
        formPanel.add(infoLabel);
        formPanel.add(btnPanel);

        main.add(header, BorderLayout.NORTH);
        main.add(formPanel, BorderLayout.CENTER);

        setContentPane(main);
    }

    private void confirmAndExport() {
        try {
            int sales = Integer.parseInt(salesField.getText().trim());
            int gallons = Integer.parseInt(gallonsField.getText().trim());
            int expenses = Integer.parseInt(expensesField.getText().trim());

            if (sales < 0 || gallons < 0 || expenses < 0) {
                JOptionPane.showMessageDialog(this,
                        "Values cannot be negative.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 1. Save to in-memory database
            DataStore.getInstance().recordFinancials(sales, gallons, expenses);

            // 2. Export / append to professional spreadsheet
            String filePath = ExcelExporter.exportFinancialRecord(sales, gallons, expenses);

            int net = sales - expenses;
            String message = String.format(
                    "Financial data saved successfully!\n\n" +
                    "Sales: ₱ %,d\n" +
                    "Gallons: %,d\n" +
                    "Expenses: ₱ %,d\n" +
                    "Net Profit: ₱ %,d\n\n" +
                    "Record has been added to the spreadsheet:\n%s",
                    sales, gallons, expenses, net, filePath
            );

            JOptionPane.showMessageDialog(this, message, "Success – Exported to Spreadsheet",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Please enter valid whole numbers only.",
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                    "Data was saved in memory, but could not write to spreadsheet:\n" + ex.getMessage(),
                    "Export Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void openSpreadsheet() {
        try {
            ExcelExporter.openSpreadsheet();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage() + "\n\nFile location:\n" + ExcelExporter.getSpreadsheetPath(),
                    "Cannot Open Spreadsheet", JOptionPane.WARNING_MESSAGE);
        }
    }

    private JButton createButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(120, 38));
        return btn;
    }
}
