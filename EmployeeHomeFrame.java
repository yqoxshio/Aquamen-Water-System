package aquamen.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class EmployeeHomeFrame extends JFrame {

    public EmployeeHomeFrame() {
        setTitle("Aquamen - Employee Home");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 650);
        setMinimumSize(new Dimension(500, 450));
        setLocationRelativeTo(null);

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(new Color(240, 248, 255));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(30, 60, 100));
        header.setPreferredSize(new Dimension(0, 60));
        header.setBorder(new EmptyBorder(10, 20, 10, 20));

        JLabel title = new JLabel("EMPLOYEE HOME", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(Color.WHITE);

        JButton logoutBtn = UIHelper.createStyledButton("LOG OUT", new Color(220, 60, 60));
        logoutBtn.addActionListener(e -> UIHelper.logout(this));

        header.add(title, BorderLayout.CENTER);
        header.add(logoutBtn, BorderLayout.EAST);

        JLabel welcome = new JLabel("Welcome, Employee!", SwingConstants.CENTER);
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 20));
        welcome.setBorder(new EmptyBorder(30, 0, 25, 0));

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setOpaque(false);
        menuPanel.setBorder(new EmptyBorder(10, 80, 40, 80));

        JPanel customerBtn = createMenuButton("CUSTOMER DETAILS",
                "View customer details for delivery.",
                new Color(160, 200, 230));
        customerBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                dispose();
                new EmployeeCustomerDetailsFrame().setVisible(true);
            }
        });

        JPanel inputSalesBtn = createMenuButton("INPUT SALES",
                "Input gallons delivered and money received.",
                new Color(160, 220, 160));
        inputSalesBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                dispose();
                new EmployeeInputSalesFrame().setVisible(true);
            }
        });

        JPanel orderListBtn = createMenuButton("ORDER LIST",
                "View list of pending orders to be delivered.",
                new Color(240, 220, 120));
        orderListBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                dispose();
                new EmployeeOrderListFrame().setVisible(true);
            }
        });

        menuPanel.add(customerBtn);
        menuPanel.add(Box.createVerticalStrut(18));
        menuPanel.add(inputSalesBtn);
        menuPanel.add(Box.createVerticalStrut(18));
        menuPanel.add(orderListBtn);

        JPanel center = new JPanel(new BorderLayout());
        center.setOpaque(false);
        center.add(welcome, BorderLayout.NORTH);
        center.add(menuPanel, BorderLayout.CENTER);

        main.add(header, BorderLayout.NORTH);
        main.add(center, BorderLayout.CENTER);
        setContentPane(main);
    }

    private JPanel createMenuButton(String title, String desc, Color bg) {
        JPanel panel = new JPanel(new BorderLayout(15, 0));
        panel.setBackground(bg);
        panel.setBorder(new EmptyBorder(18, 25, 18, 25));
        panel.setMaximumSize(new Dimension(700, 90));
        panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(new Color(30, 30, 30));

        JLabel descLabel = new JLabel(desc);
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        descLabel.setForeground(new Color(60, 60, 60));

        textPanel.add(titleLabel);
        textPanel.add(Box.createVerticalStrut(4));
        textPanel.add(descLabel);

        panel.add(textPanel, BorderLayout.CENTER);
        return panel;
    }
}
