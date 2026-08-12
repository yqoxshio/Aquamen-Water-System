package aquamen.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Shared UI helpers: logout confirmation and consistent button styling.
 */
public class UIHelper {

    /**
     * Shows a confirmation dialog before logging out.
     * Returns true if the user confirms, false if they cancel.
     */
    public static boolean confirmLogout(Component parent) {
        int choice = JOptionPane.showConfirmDialog(
                parent,
                "Are you sure you want to log out?",
                "Confirm Logout",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
        return choice == JOptionPane.YES_OPTION;
    }

    /**
     * Performs logout only after user confirmation.
     */
    public static void logout(JFrame currentFrame) {
        if (confirmLogout(currentFrame)) {
            currentFrame.dispose();
            new LoginFrame().setVisible(true);
        }
    }

    public static JButton createStyledButton(String text, Color bg) {
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
