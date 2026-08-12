package aquamen.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton togglePasswordBtn;
    private boolean passwordVisible = false;

    public LoginFrame() {
        setTitle("Aquamen Water Refilling Station - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setMinimumSize(new Dimension(400, 500));
        setLocationRelativeTo(null);
        setResizable(true);

        // Main panel with water-like background
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(220, 240, 255));

        // Center content
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);
        centerPanel.setBorder(new EmptyBorder(40, 40, 40, 40));

        // Logo placeholder (water drop + person)
        JLabel logoLabel = new JLabel("AQUAMEN", SwingConstants.CENTER);
        logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 42));
        logoLabel.setForeground(new Color(0, 90, 160));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Title
        JLabel titleLabel = new JLabel("<html><div style='text-align:center'>AQUAMEN WATER REFILLING STATION<br>MONITORING SYSTEM</div></html>", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(0, 70, 140));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(new EmptyBorder(15, 0, 30, 0));

        // Login card
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 210, 240), 1),
                new EmptyBorder(25, 30, 25, 30)
        ));
        card.setMaximumSize(new Dimension(400, 280));
        card.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Username
        JPanel userPanel = createInputPanel("ID", "Employee or Admin");
        usernameField = (JTextField) ((JPanel) userPanel.getComponent(1)).getComponent(0);

        // Password
        JPanel passPanel = new JPanel(new BorderLayout(8, 0));
        passPanel.setOpaque(false);
        passPanel.setMaximumSize(new Dimension(340, 45));
        passPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lockIcon = new JLabel("PW");
        lockIcon.setFont(new Font("Segoe UI", Font.BOLD, 12));

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(8, 10, 8, 10)
        ));

        togglePasswordBtn = new JButton("Show");
        togglePasswordBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        togglePasswordBtn.setFocusPainted(false);
        togglePasswordBtn.setBorderPainted(false);
        togglePasswordBtn.setContentAreaFilled(false);
        togglePasswordBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        togglePasswordBtn.addActionListener(e -> togglePasswordVisibility());

        JPanel passFieldPanel = new JPanel(new BorderLayout());
        passFieldPanel.setOpaque(false);
        passFieldPanel.add(passwordField, BorderLayout.CENTER);
        passFieldPanel.add(togglePasswordBtn, BorderLayout.EAST);

        passPanel.add(lockIcon, BorderLayout.WEST);
        passPanel.add(passFieldPanel, BorderLayout.CENTER);

        // Login button
        JButton loginBtn = new JButton("LOGIN");
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        loginBtn.setBackground(new Color(100, 170, 210));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);
        loginBtn.setBorderPainted(false);
        loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginBtn.setMaximumSize(new Dimension(340, 45));
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginBtn.addActionListener(new LoginAction());

        card.add(userPanel);
        card.add(Box.createVerticalStrut(15));
        card.add(passPanel);
        card.add(Box.createVerticalStrut(25));
        card.add(loginBtn);

        centerPanel.add(logoLabel);
        centerPanel.add(titleLabel);
        centerPanel.add(card);

        mainPanel.add(centerPanel, BorderLayout.CENTER);
        setContentPane(mainPanel);

        // Enter key support
        getRootPane().setDefaultButton(loginBtn);
    }

    private JPanel createInputPanel(String iconText, String placeholder) {
        JPanel panel = new JPanel(new BorderLayout(8, 0));
        panel.setOpaque(false);
        panel.setMaximumSize(new Dimension(340, 45));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel icon = new JLabel(iconText);
        icon.setFont(new Font("Segoe UI", Font.BOLD, 12));

        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(8, 10, 8, 10)
        ));

        // Simple placeholder simulation
        field.setText(placeholder);
        field.setForeground(Color.GRAY);
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }
            public void focusLost(java.awt.event.FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(Color.GRAY);
                }
            }
        });

        JPanel fieldPanel = new JPanel(new BorderLayout());
        fieldPanel.setOpaque(false);
        fieldPanel.add(field, BorderLayout.CENTER);

        panel.add(icon, BorderLayout.WEST);
        panel.add(fieldPanel, BorderLayout.CENTER);
        return panel;
    }

    private void togglePasswordVisibility() {
        if (passwordVisible) {
            passwordField.setEchoChar('*');
            togglePasswordBtn.setText("Show");
            passwordVisible = false;
        } else {
            passwordField.setEchoChar((char) 0);
            togglePasswordBtn.setText("Hide");
            passwordVisible = true;
        }
    }

    private class LoginAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            // Clear placeholder if still present
            if (username.equals("Employee or Admin")) {
                username = "";
            }

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(LoginFrame.this,
                        "Please enter both username and password.",
                        "Login Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (username.equals("admin") && password.equals("admin123")) {
                dispose();
                new AdminHomeFrame().setVisible(true);
            } else if (username.equals("employee") && password.equals("emp123")) {
                dispose();
                new EmployeeHomeFrame().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(LoginFrame.this,
                        "Invalid username or password.",
                        "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
