package view;

import control.Controller;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {
    private Controller ctrl;

    public MainView(Controller ctrl) {
        this.ctrl = ctrl;

        setTitle("Painel Principal - Fluxo de Caixa");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Painel principal
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel welcomeLabel = new JLabel("Bem-vindo ao sistema de Fluxo de Caixa!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton logoutButton = new JButton("🔙 Logout");
        logoutButton.setPreferredSize(new Dimension(100, 40));

        logoutButton.addActionListener(e -> {
            dispose();
            new Login(ctrl);
        });

        panel.add(welcomeLabel, BorderLayout.CENTER);
        panel.add(logoutButton, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }
}
