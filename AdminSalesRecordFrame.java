package aquamen.ui;

import aquamen.data.DailyRecord;
import aquamen.data.DataStore;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class AdminSalesRecordFrame extends JFrame {

    private final DataStore store = DataStore.getInstance();
    private JLabel salesValueLabel;
    private JLabel gallonsValueLabel;
    private JLabel profitValueLabel;
    private JLabel dateDisplayLabel;
    private JSpinner dateSpinner;

    public AdminSalesRecordFrame() {
        setTitle("Aquamen - Admin Sales Record");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 620);
        setMinimumSize(new Dimension(480, 420));
        setLocationRelativeTo(null);

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(Color.WHITE);

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(140, 190, 220));
        header.setPreferredSize(new Dimension(0, 55));
        header.setBorder(new EmptyBorder(10, 20, 10, 20));

        JLabel title = new JLabel("ADMIN SALES RECORD", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(new Color(20, 50, 80));
        header.add(title, BorderLayout.CENTER);

        // Content
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(Color.WHITE);
        content.setBorder(new EmptyBorder(25, 50, 25, 50));

        JLabel sectionTitle = new JLabel("SALES RECORD AND EXPENSES", SwingConstants.CENTER);
        sectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        sectionTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Date selector row
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        datePanel.setOpaque(false);
        datePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel pickLabel = new JLabel("Select Date:");
        pickLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        SpinnerDateModel dateModel = new SpinnerDateModel(new Date(), null, null, java.util.Calendar.DAY_OF_MONTH);
        dateSpinner = new JSpinner(dateModel);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd");
        dateSpinner.setEditor(editor);
        dateSpinner.setPreferredSize(new Dimension(140, 28));
        dateSpinner.addChangeListener(e -> refreshForSelectedDate());

        JButton todayBtn = UIHelper.createStyledButton("Today", new Color(70, 130, 180));
        todayBtn.setPreferredSize(new Dimension(80, 28));
        todayBtn.addActionListener(e -> {
            dateSpinner.setValue(new Date());
            refreshForSelectedDate();
        });

        datePanel.add(pickLabel);
        datePanel.add(dateSpinner);
        datePanel.add(todayBtn);

        dateDisplayLabel = new JLabel(" ", SwingConstants.CENTER);
        dateDisplayLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        dateDisplayLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        dateDisplayLabel.setBorder(new EmptyBorder(5, 0, 15, 0));

        // Info card
        JPanel infoCard = new JPanel();
        infoCard.setLayout(new BoxLayout(infoCard, BoxLayout.Y_AXIS));
        infoCard.setBackground(new Color(245, 250, 255));
        infoCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 220, 240)),
                new EmptyBorder(25, 30, 25, 30)
        ));
        infoCard.setMaximumSize(new Dimension(520, 220));
        infoCard.setAlignmentX(Component.CENTER_ALIGNMENT);

        salesValueLabel = new JLabel();
        gallonsValueLabel = new JLabel();
        profitValueLabel = new JLabel();

        infoCard.add(createInfoRow("TOTAL SALES", salesValueLabel));
        infoCard.add(Box.createVerticalStrut(14));
        infoCard.add(new JSeparator());
        infoCard.add(Box.createVerticalStrut(14));
        infoCard.add(createInfoRow("TOTAL GALLONS REFILLED", gallonsValueLabel));
        infoCard.add(Box.createVerticalStrut(14));
        infoCard.add(new JSeparator());
        infoCard.add(Box.createVerticalStrut(14));
        infoCard.add(createInfoRow("NET PROFIT", profitValueLabel));

        // Buttons
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        btnPanel.setOpaque(false);
        btnPanel.setBorder(new EmptyBorder(25, 0, 0, 0));

        JButton financialBtn = UIHelper.createStyledButton("Financial Overview", new Color(100, 180, 220));
        financialBtn.setPreferredSize(new Dimension(160, 40));
        financialBtn.addActionListener(e -> {
            dispose();
            new FinancialExpensesFrame().setVisible(true);
        });

        JButton backBtn = UIHelper.createStyledButton("BACK", new Color(30, 60, 100));
        backBtn.setPreferredSize(new Dimension(120, 40));
        backBtn.addActionListener(e -> {
            dispose();
            new AdminHomeFrame().setVisible(true);
        });

        btnPanel.add(financialBtn);
        btnPanel.add(backBtn);

        content.add(sectionTitle);
        content.add(Box.createVerticalStrut(12));
        content.add(datePanel);
        content.add(dateDisplayLabel);
        content.add(infoCard);
        content.add(btnPanel);

        main.add(header, BorderLayout.NORTH);
        main.add(content, BorderLayout.CENTER);
        setContentPane(main);

        refreshForSelectedDate();
    }

    private JPanel createInfoRow(String label, JLabel valueLabel) {
        JPanel row = new JPanel(new BorderLayout(15, 0));
        row.setOpaque(false);

        JLabel labelLbl = new JLabel(label);
        labelLbl.setFont(new Font("Segoe UI", Font.BOLD, 14));

        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        valueLabel.setForeground(new Color(0, 80, 140));
        valueLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        row.add(labelLbl, BorderLayout.WEST);
        row.add(valueLabel, BorderLayout.EAST);
        return row;
    }

    private void refreshForSelectedDate() {
        Date selected = (Date) dateSpinner.getValue();
        LocalDate date = selected.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        DailyRecord record = store.getRecordForDate(date);

        dateDisplayLabel.setText("Showing data for: " + date.toString()
                + (date.equals(LocalDate.now()) ? "  (Today)" : ""));

        salesValueLabel.setText("PHP " + String.format("%,d", record.getSales()));
        gallonsValueLabel.setText(String.format("%,d", record.getGallons()) + " GALLONS");
        profitValueLabel.setText("PHP " + String.format("%,d", record.getNetProfit()));
    }
}
