package aquamen.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AdminHomeFrame extends JFrame {

    public AdminHomeFrame() {
        setTitle("Aquamen - Admin Home");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 650);
        setMinimumSize(new Dimension(500, 450));
        setLocationRelativeTo(null);

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(new Color(230, 245, 255));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(100, 170, 210));
        header.setPreferredSize(new Dimension(0, 60));
        header.setBorder(new EmptyBorder(10, 20, 10, 20));

        JLabel title = new JLabel("ADMIN HOME", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(Color.WHITE);

        JButton logoutBtn = UIHelper.createStyledButton("LOG OUT", new Color(220, 60, 60));
        logoutBtn.addActionListener(e -> UIHelper.logout(this));

        header.add(title, BorderLayout.CENTER);
        header.add(logoutBtn, BorderLayout.EAST);

        JLabel welcome = new JLabel("Welcome, Admin!", SwingConstants.CENTER);
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 20));
        welcome.setBorder(new EmptyBorder(30, 0, 20, 0));

        JPanel cardsPanel = new JPanel(new GridLayout(1, 2, 30, 0));
        cardsPanel.setOpaque(false);
        cardsPanel.setBorder(new EmptyBorder(20, 60, 40, 60));

        JPanel salesCard = createCard("SALES", "SALES RECORD",
                "View total sales and gallons refilled by date.",
                new Color(180, 210, 240), new Color(0, 60, 120));
        salesCard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                dispose();
                new AdminSalesRecordFrame().setVisible(true);
            }
        });

        JPanel clientCard = createCard("CLIENTS", "CLIENT LIST",
                "View the list of all clients.",
                new Color(180, 230, 180), new Color(0, 100, 40));
        clientCard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                dispose();
                new AdminClientListFrame().setVisible(true);
            }
        });

        cardsPanel.add(salesCard);
        cardsPanel.add(clientCard);

        JPanel center = new JPanel(new BorderLayout());
        center.setOpaque(false);
        center.add(welcome, BorderLayout.NORTH);
        center.add(cardsPanel, BorderLayout.CENTER);

        main.add(header, BorderLayout.NORTH);
        main.add(center, BorderLayout.CENTER);
        setContentPane(main);
    }

    private JPanel createCard(String badge, String title, String desc, Color bg, Color titleColor) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(bg);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(bg.darker(), 1),
                new EmptyBorder(30, 25, 30, 25)
        ));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel badgeLabel = new JLabel(badge, SwingConstants.CENTER);
        badgeLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        badgeLabel.setForeground(titleColor);
        badgeLabel.setOpaque(true);
        badgeLabel.setBackground(Color.WHITE);
        badgeLabel.setBorder(new EmptyBorder(6, 14, 6, 14));
        badgeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(titleColor);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(new EmptyBorder(15, 0, 10, 0));

        JLabel descLabel = new JLabel("<html><div style='text-align:center'>" + desc + "</div></html>", SwingConstants.CENTER);
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        descLabel.setForeground(new Color(50, 50, 50));
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(badgeLabel);
        card.add(titleLabel);
        card.add(descLabel);
        return card;
    }
}
