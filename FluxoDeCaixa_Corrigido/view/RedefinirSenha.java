package view;

import control.Controller;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RedefinirSenha extends JFrame {

    public RedefinirSenha(Controller controller) {
        setTitle("Redefinir Senha");
        setSize(350, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(5, 2, 10, 10));

        JLabel nomeLabel = new JLabel("Nome de usuário:");
        JTextField nomeField = new JTextField();

        JLabel novaSenhaLabel = new JLabel("Nova senha:");
        JPasswordField novaSenhaField = new JPasswordField();

        JLabel confirmarSenhaLabel = new JLabel("Confirmar senha:");
        JPasswordField confirmarSenhaField = new JPasswordField();

        JButton confirmarBtn = new JButton("Confirmar");

        confirmarBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nome = nomeField.getText();
                String novaSenha = new String(novaSenhaField.getPassword());
                String confirmarSenha = new String(confirmarSenhaField.getPassword());

                if (!novaSenha.equals(confirmarSenha)) {
                    JOptionPane.showMessageDialog(null, "As senhas não coincidem.");
                    return;
                }

                boolean sucesso = controller.atualizarSenha(nome, novaSenha);
                if (sucesso) {
                    JOptionPane.showMessageDialog(null, "Senha redefinida com sucesso!");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Usuário não encontrado.");
                }
            }
        });

        add(nomeLabel);
        add(nomeField);
        add(novaSenhaLabel);
        add(novaSenhaField);
        add(confirmarSenhaLabel);
        add(confirmarSenhaField);
        add(new JLabel());
        add(confirmarBtn);

        setVisible(true);
    }
}
