package com.gpcompanion.ui;

import com.gpcompanion.auth.*;
import javax.swing.*;
import java.awt.*;

public class LoginFormPanel extends JPanel {
    public LoginFormPanel(AuthController authController, Runnable onSuccess, Runnable onBack) {
        Color bgColor = new Color(26, 26, 26);
        Color fieldBg = Color.DARK_GRAY;
        Color neonYellow = new Color(204, 255, 0);
        Color errorRed = new Color(255, 90, 90);

        setBackground(bgColor);
        setLayout(new GridBagLayout());

        JPanel form = new JPanel(new GridLayout(0, 2, 10, 10));
        form.setBackground(bgColor);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setForeground(Color.WHITE);
        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(Color.WHITE);

        JTextField userField = new JTextField(15);
        userField.setBackground(fieldBg);
        userField.setForeground(Color.WHITE);
        userField.setCaretColor(Color.WHITE);
        userField.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        JPasswordField passField = new JPasswordField(15);
        passField.setBackground(fieldBg);
        passField.setForeground(Color.WHITE);
        passField.setCaretColor(Color.WHITE);
        passField.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        JLabel statusLabel = new JLabel(" ");
        statusLabel.setForeground(errorRed);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBackground(neonYellow);
        loginBtn.setForeground(Color.BLACK);
        loginBtn.setFocusPainted(false);

        JButton backBtn = new JButton("Back");
        backBtn.setBackground(Color.GRAY);
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);
        backBtn.addActionListener(e -> onBack.run());

        loginBtn.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());
            try {
                authController.handleLogin(username, password);
                onSuccess.run();
            } catch (AuthenticationException | IllegalArgumentException ex) {
                statusLabel.setText(ex.getMessage());
            }
        });

        form.add(userLabel);
        form.add(userField);
        form.add(passLabel);
        form.add(passField);
        form.add(new JLabel());
        form.add(statusLabel);
        form.add(backBtn);
        form.add(loginBtn);

        add(form);
    }
}