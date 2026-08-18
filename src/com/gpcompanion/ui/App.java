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
            AuthService auth = new AuthService(new InMemoryUserStore());
            try {
                auth.register("admin", "admin");
            } catch (DuplicateUserException e) {
                // Ignore
            }
            LoginPanel login = new LoginPanel(auth, () -> {
                frame.getContentPane().removeAll();
                
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
