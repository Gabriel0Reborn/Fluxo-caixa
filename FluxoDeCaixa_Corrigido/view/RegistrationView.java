package view;

import control.Controller;

import javax.swing.*;
import java.awt.*;

public class RegistrationView extends JFrame {
    private JPanel panel;
    private JTextField userField;
    private JTextField emailField;
    private JPasswordField passField;
    private JButton registerBtn, voltarBtn;
    private boolean isDarkMode = true;

    public RegistrationView(Controller ctrl) {
        setTitle("Registrar Usuário");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initComponents(ctrl);
        applyDarkMode();
        setVisible(true);
    }

    private void initComponents(Controller ctrl) {
        panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel userLabel = new JLabel("👤 Usuário:");
        userField = new JTextField(20);

        JLabel emailLabel = new JLabel("📧 Email:");
        emailField = new JTextField(20);

        JLabel passLabel = new JLabel("🔒 Senha:");
        passField = new JPasswordField(20);

        registerBtn = new JButton("✅ Registrar");
        voltarBtn = new JButton("↩️ Voltar");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(userLabel, gbc);
        gbc.gridx = 1;
        panel.add(userField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(emailLabel, gbc);
        gbc.gridx = 1;
        panel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(passLabel, gbc);
        gbc.gridx = 1;
        panel.add(passField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(registerBtn, gbc);
        gbc.gridx = 1;
        panel.add(voltarBtn, gbc);

        add(panel);

        registerBtn.addActionListener(e -> {
            String nome = userField.getText().trim();
            String email = emailField.getText().trim();
            String senha = new String(passField.getPassword());

            if (nome.isEmpty() || senha.isEmpty()) {
                JOptionPane.showMessageDialog(this, "⚠️ Preencha todos os campos.");
                return;
            }

            boolean sucesso = ctrl.registrarUsuario(nome, senha, email);
            if (sucesso) {
                JOptionPane.showMessageDialog(this, "✅ Registro realizado com sucesso!");
                dispose();
                new Login(ctrl);
            } else {
                JOptionPane.showMessageDialog(this, "❌ Usuário já existe.");
            }
        });

        voltarBtn.addActionListener(e -> {
            dispose();
            new Login(ctrl);
        });
    }

    private void applyDarkMode() {
        Color bg = new Color(40, 40, 40);
        Color fg = Color.WHITE;
        Color btnBg = new Color(70, 70, 70);

        panel.setBackground(bg);
        for (Component comp : panel.getComponents()) {
            comp.setForeground(fg);
            comp.setBackground(bg);
        }

        registerBtn.setBackground(btnBg);
        registerBtn.setForeground(fg);
        registerBtn.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

        voltarBtn.setBackground(btnBg);
        voltarBtn.setForeground(fg);
        voltarBtn.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
    }
}