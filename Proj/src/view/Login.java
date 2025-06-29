package view;

import control.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Login extends JFrame {
    private JTextField userField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    private Controller ctrl;

    public Login(Controller ctrl) {
        super("Login");
        this.ctrl = ctrl;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 200);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(3, 2, 10, 10));

        form.add(new JLabel("Usuário:"));
        userField = new JTextField();
        form.add(userField);

        form.add(new JLabel("Senha:"));
        passwordField = new JPasswordField();
        form.add(passwordField);

        loginButton = new JButton("Entrar");
        registerButton = new JButton("Registrar");

        form.add(loginButton);
        form.add(registerButton);

        add(form, BorderLayout.CENTER);

        loginButton.addActionListener(this::fazerLogin);
        registerButton.addActionListener(e -> {
            dispose();
            new RegistrationView(ctrl);
        });

        setVisible(true);
    }

    private void fazerLogin(ActionEvent e) {
        String usuario = userField.getText();
        String senha = new String(passwordField.getPassword());

        String resultado = ctrl.login(usuario, senha);

        if (resultado.equals("Login bem-sucedido!")) {
            JOptionPane.showMessageDialog(this, resultado);
            dispose();
            new MainView(ctrl);
        } else {
            JOptionPane.showMessageDialog(this, resultado);
        }
    }
}
