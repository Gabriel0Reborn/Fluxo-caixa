package view;

import control.Controller;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class RegistrationView extends JFrame {
    private JTextField userField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private Controller ctrl;

    public RegistrationView(Controller ctrl) {
        super("Registrar");
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

        registerButton = new JButton("Registrar");
        form.add(new JLabel()); // espaço vazio para alinhamento
        form.add(registerButton);

        add(form, BorderLayout.CENTER);

        registerButton.addActionListener(this::efetuarRegistro);

        setVisible(true);
    }

    private void efetuarRegistro(ActionEvent e) {
        String usuario = userField.getText();
        String senha = new String(passwordField.getPassword());

        if (usuario.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
            return;
        }

        boolean sucesso = ctrl.registrar(usuario, senha);

        if (sucesso) {
            JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!");
            this.dispose();
            new Login(ctrl); // Certifique-se de que Login tem esse construtor
        } else {
            JOptionPane.showMessageDialog(this, "Erro no cadastro. Tente novamente.");
        }
    }
}
