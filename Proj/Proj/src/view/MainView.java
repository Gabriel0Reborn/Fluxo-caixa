package view;

import control.Controller;
import model.Transacao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainView extends JFrame {
    private Controller ctrl;
    private JLabel saldoLabel;
    private JTextArea historicoArea;

    public MainView(Controller ctrl) {
        super("Gerenciador de fluxo de caixa");
        this.ctrl = ctrl;

        realizarLogin();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        saldoLabel = new JLabel();
        historicoArea = new JTextArea();
        historicoArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(historicoArea);

        JButton entradaBtn = new JButton("Registrar Entrada");
        JButton saidaBtn = new JButton("Registrar Saída");
        JButton desfazerBtn = new JButton("Desfazer");
        JButton refazerBtn = new JButton("Refazer");
        JButton logoutBtn = new JButton("Logout");

        entradaBtn.addActionListener(this::registrarEntrada);
        saidaBtn.addActionListener(this::registrarSaida);
        desfazerBtn.addActionListener(e -> {
            if (ctrl.desfazerTransacao()) atualizarTela();
            else JOptionPane.showMessageDialog(this, "Nada para desfazer.");
        });
        refazerBtn.addActionListener(e -> {
            if (ctrl.refazerTransacao()) atualizarTela();
            else JOptionPane.showMessageDialog(this, "Nada para refazer.");
        });
        logoutBtn.addActionListener(e -> logout());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(saldoLabel, BorderLayout.CENTER);
        topPanel.add(logoutBtn, BorderLayout.EAST);

        JPanel bottomPanel = new JPanel(new GridLayout(1, 4));
        bottomPanel.add(entradaBtn);
        bottomPanel.add(saidaBtn);
        bottomPanel.add(desfazerBtn);
        bottomPanel.add(refazerBtn);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        atualizarTela();
        setVisible(true);
    }

    private void realizarLogin() {
        while (true) {
            String nome = JOptionPane.showInputDialog(this, "Usuário:");
            if (nome == null) System.exit(0);

            String senha = JOptionPane.showInputDialog(this, "Senha:");
            if (senha == null) System.exit(0);

            String resultado = ctrl.login(nome, senha);
            if (resultado.equals("Login bem-sucedido!")) {
                JOptionPane.showMessageDialog(this, resultado);
                break;
            } else {
                JOptionPane.showMessageDialog(this, resultado);
            }
        }
    }

    private void logout() {
        ctrl.logout();
        JOptionPane.showMessageDialog(this, "Logout realizado.");
        dispose();
        new MainView(ctrl);
    }

    private void registrarEntrada(ActionEvent e) {
        String valorStr = JOptionPane.showInputDialog("Valor da entrada:");
        if (valorStr == null) return;

        try {
            double valor = Double.parseDouble(valorStr);
            String descricao = JOptionPane.showInputDialog("Descrição:");
            if (descricao != null) {
                ctrl.registrarEntrada(valor, descricao);
                atualizarTela();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Valor inválido.");
        }
    }

    private void registrarSaida(ActionEvent e) {
        String valorStr = JOptionPane.showInputDialog("Valor da saída:");
        if (valorStr == null) return;

        try {
            double valor = Double.parseDouble(valorStr);
            String descricao = JOptionPane.showInputDialog("Descrição:");
            if (descricao != null) {
                ctrl.registrarSaida(valor, descricao);
                atualizarTela();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Valor inválido.");
        }
    }

    private void atualizarTela() {
        saldoLabel.setText("Saldo atual: R$ " + ctrl.pegarSaldoAtual());
        StringBuilder sb = new StringBuilder();
        for (Transacao t : ctrl.pegarHistorico()) {
            sb.append(t.toString()).append("\n");
        }
        historicoArea.setText(sb.toString());
    }
}
