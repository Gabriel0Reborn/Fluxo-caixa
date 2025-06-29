package view;

import control.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Login extends JFrame {
    private Controller ctrl;

    public Login(Controller ctrl) {
        this.ctrl = ctrl;
        setTitle("Login");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Color bgColor = new Color(30, 30, 30);
        Color fgColor = Color.WHITE;
        Color buttonColor = new Color(60, 60, 60);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(bgColor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel userLabel = new JLabel("Usuário:");
        userLabel.setForeground(fgColor);
        JTextField userField = new JTextField(20);

        JLabel passLabel = new JLabel("Senha:");
        passLabel.setForeground(fgColor);
        JPasswordField passField = new JPasswordField(20);

        userField.setBackground(Color.WHITE);
        passField.setBackground(Color.WHITE);
        userField.setForeground(Color.BLACK);
        passField.setForeground(Color.BLACK);

        JButton loginBtn = new JButton("Login");
        JButton registrarBtn = new JButton("Registrar-se");
        JButton forgotPasswordBtn = new JButton("Esqueci minha senha");

        Dimension buttonSize = new Dimension(120, 35);
        loginBtn.setPreferredSize(buttonSize);
        registrarBtn.setPreferredSize(buttonSize);
        forgotPasswordBtn.setPreferredSize(buttonSize);

        for (JButton btn : new JButton[]{loginBtn, registrarBtn, forgotPasswordBtn}) {
            btn.setBackground(buttonColor);
            btn.setForeground(fgColor);
            btn.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        }

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(userLabel, gbc);
        gbc.gridy++;
        panel.add(userField, gbc);
        gbc.gridy++;
        panel.add(passLabel, gbc);
        gbc.gridy++;
        panel.add(passField, gbc);

        JPanel btnPanel = new JPanel(new FlowLayout());
        btnPanel.setBackground(bgColor);
        btnPanel.add(loginBtn);
        btnPanel.add(registrarBtn);
        btnPanel.add(forgotPasswordBtn);

        add(panel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        loginBtn.addActionListener(e -> {
            String nome = userField.getText();
            String senha = new String(passField.getPassword());

            if (ctrl.login(nome, senha)) {
                JOptionPane.showMessageDialog(this, "Login bem-sucedido!");
                dispose();
                new TelaPrincipal(ctrl);
            } else {
                JOptionPane.showMessageDialog(this, "Usuário ou senha incorretos.");
            }
        });

        registrarBtn.addActionListener(e -> {
            dispose();
            new RegistrationView(ctrl);
        });

        forgotPasswordBtn.addActionListener(e -> {
            new RedefinirSenha(ctrl);
        });

        for (JButton btn : new JButton[]{loginBtn, registrarBtn, forgotPasswordBtn}) {
            btn.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent evt) {
                    btn.setBackground(btn.getBackground().darker());
                }

                public void mouseExited(MouseEvent evt) {
                    btn.setBackground(buttonColor);
                }
            });
        }

        setVisible(true);
    }
}
