package com.gpcompanion.ui;

import com.gpcompanion.auth.*;
import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {
    public LoginPanel(AuthService authService, Runnable onSuccess) {
        Color bgColor = new Color(26, 26, 26);
        Color fieldBg = Color.DARK_GRAY;
        Color neonYellow = new Color(204, 255, 0);
        
        UIManager.put("OptionPane.background", bgColor);
        UIManager.put("Panel.background", bgColor);
        UIManager.put("OptionPane.messageForeground", Color.WHITE);
        UIManager.put("Button.background", Color.DARK_GRAY);
        UIManager.put("Button.foreground", Color.WHITE);
        
        setBackground(bgColor);
        setLayout(new GridBagLayout());
        
        JPanel form = new JPanel(new GridLayout(3, 2, 10, 10));
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
        
        JButton loginBtn = new JButton("Login");
        loginBtn.setBackground(neonYellow);
        loginBtn.setForeground(Color.BLACK);
        loginBtn.setFocusPainted(false);
        
        loginBtn.addActionListener(e -> {
            try {
                authService.login(userField.getText(), new String(passField.getPassword()));
                onSuccess.run();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Login Failed");
            }
        });
        
        JButton registerBtn = new JButton("Register");
        registerBtn.setBackground(Color.GRAY);
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setFocusPainted(false);
        
        registerBtn.addActionListener(e -> {
            String u = userField.getText().trim();
            String p = new String(passField.getPassword());
            if (u.isEmpty() || p.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username and password cannot be empty.");
                return;
            }
            JPasswordField confirmField = new JPasswordField(10);
            confirmField.setBackground(fieldBg);
            confirmField.setForeground(Color.WHITE);
            confirmField.setCaretColor(Color.WHITE);
            confirmField.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            int option = JOptionPane.showConfirmDialog(this, confirmField, "Confirm Password to Register", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (option == JOptionPane.OK_OPTION) {
                if (!p.equals(new String(confirmField.getPassword()))) {
                    JOptionPane.showMessageDialog(this, "Passwords do not match. Registration failed.");
                    return;
                }
                try {
                    authService.register(u, p);
                    JOptionPane.showMessageDialog(this, "Registration Successful. Please log in.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Registration Failed: " + ex.getMessage());
                }
            }
        });
        
        form.add(userLabel);
        form.add(userField);
        form.add(passLabel);
        form.add(passField);
        form.add(loginBtn);
        form.add(registerBtn);
        
        add(form);
    }
}
