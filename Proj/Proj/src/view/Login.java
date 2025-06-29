package view;

import control.Controller;
import view.MainView;
import view.RegistrationView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Login extends JFrame {
    private JTextField userField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    private Controller ctrl;

    public Login() {
        super("Login");

        ctrl = new Controller();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 200);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(4, 2, 10, 10));

        form.add(new JLabel("Usuário:"));
        userField = new JTextField();
        form.add(userField);

        form.add(new JLabel("Senha:"));
        passwordField = new JPasswordField();
        form.add(passwordField);

        loginButton = new JButton("Entrar");
        form.add(new JLabel()); // espaço para alinhamento
        form.add(loginButton);

        registerButton = new JButton("Registrar");
        form.add(new JLabel()); // espaço para alinhamento
        form.add(registerButton);

        add(form, BorderLayout.CENTER);

        loginButton.addActionListener(this::efetuarLogin);
        registerButton.addActionListener(this::abrirTelaRegistro);

        setVisible(true);
    }

    private void efetuarLogin(ActionEvent e) {
        String usuario = userField.getText();
        String senha = new String(passwordField.getPassword());

        if (usuario.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Usuário e senha não podem estar vazios.");
            return;
        }

        boolean loginValido = ctrl.validarLogin(usuario, senha);

        if (loginValido) {
            this.dispose();
            new MainView(ctrl); // corrigido
        } else {
            JOptionPane.showMessageDialog(this, "Usuário ou senha incorretos.");
        }
    }

    private void abrirTelaRegistro(ActionEvent e) {
        this.dispose();
        new RegistrationView(ctrl);
    }
}
