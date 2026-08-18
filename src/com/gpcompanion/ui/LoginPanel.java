package com.gpcompanion.ui;

import com.gpcompanion.auth.*;
import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {
    public LoginPanel(AuthController authController, Runnable onSuccess) {
        Color bgColor = new Color(26, 26, 26);
        Color fieldBg = Color.DARK_GRAY;
        Color neonYellow = new Color(204, 255, 0);
        Color errorRed = new Color(255, 90, 90);
        Color successGreen = new Color(140, 255, 140);

        setBackground(bgColor);
        setLayout(new GridBagLayout());

        JPanel form = new JPanel(new GridLayout(0, 2, 10, 10));
        form.setBackground(bgColor);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setForeground(Color.WHITE);
        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(Color.WHITE);
        JLabel confirmLabel = new JLabel("Confirm (register only):");
        confirmLabel.setForeground(Color.WHITE);

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

        JPasswordField confirmField = new JPasswordField(15);
        confirmField.setBackground(fieldBg);
        confirmField.setForeground(Color.WHITE);
        confirmField.setCaretColor(Color.WHITE);
        confirmField.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        JLabel statusLabel = new JLabel(" "); // reserves height, avoids layout jump
        statusLabel.setForeground(errorRed);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBackground(neonYellow);
        loginBtn.setForeground(Color.BLACK);
        loginBtn.setFocusPainted(false);

        loginBtn.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());
            try {
                authController.handleLogin(username, password);
                onSuccess.run();
            } catch (AuthenticationException | IllegalArgumentException ex) {
                statusLabel.setForeground(errorRed);
                statusLabel.setText(ex.getMessage());
            }
        });

        JButton registerBtn = new JButton("Register");
        registerBtn.setBackground(Color.GRAY);
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setFocusPainted(false);

        registerBtn.addActionListener(e -> {
            String username = userField.getText().trim();
            String password = new String(passField.getPassword());
            String confirm = new String(confirmField.getPassword());

            if (!password.equals(confirm)) {
                statusLabel.setForeground(errorRed);
                statusLabel.setText("Passwords do not match.");
                return;
            }
            try {
                authController.handleRegister(username, password);
                statusLabel.setForeground(successGreen);
                statusLabel.setText("Registration successful — you can log in now.");
            } catch (DuplicateUserException | IllegalArgumentException ex) {
                statusLabel.setForeground(errorRed);
                statusLabel.setText(ex.getMessage());
            }
        });

        form.add(userLabel);
        form.add(userField);
        form.add(passLabel);
        form.add(passField);
        form.add(confirmLabel);
        form.add(confirmField);
        form.add(new JLabel());
        form.add(statusLabel);
        form.add(loginBtn);
        form.add(registerBtn);

        add(form);
    }
}