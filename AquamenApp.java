package aquamen;

import aquamen.ui.LoginFrame;

import javax.swing.*;

/**
 * Aquamen Water Refilling Station Monitoring System
 * Main entry point.
 *
 * Credentials:
 *   Admin    → username: admin    | password: admin123
 *   Employee → username: employee | password: emp123
 *
 * Price: ₱40 per gallon (integer only)
 */
public class AquamenApp {
    public static void main(String[] args) {
        // Use system look and feel for better appearance
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // fallback to default
        }

        SwingUtilities.invokeLater(() -> {
            LoginFrame login = new LoginFrame();
            login.setVisible(true);
        });
    }
}
