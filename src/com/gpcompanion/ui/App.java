package com.gpcompanion.ui;

import com.gpcompanion.auth.*;
import com.gpcompanion.race.RaceEngine;
import com.gpcompanion.race.RaceLoader;
import javax.swing.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Grand Prix Companion");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);

            UserCredentialStore store = new FileUserCredentialStore("users.txt");
            SessionContext session = new SessionContext();
            AuthService authService = new AuthService(store, session);
            AuthController authController = new AuthController(authService, session);

            LoginPanel login = new LoginPanel(authController, () -> {
                frame.getContentPane().removeAll();

                String loggedInUsername = authController.getSession().getCurrentUser().getUsername();
                frame.setTitle("Grand Prix Companion — " + loggedInUsername);

                RaceLoader loader = new RaceLoader();
                RaceEngine engine = new RaceEngine(loader.load("race_data.csv"));

                frame.add(new RaceUI(engine));
                frame.revalidate();
                frame.repaint();
            });
            frame.add(login);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}