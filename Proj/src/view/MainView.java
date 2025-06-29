package view;

import control.Controller;
import model.Transacao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.PrintWriter;

public class MainView extends JFrame {
    private Controller ctrl;

    private JLabel saldoLabel;
    private JTextArea historicoArea;

    private JButton entradaBtn;
    private JButton saidaBtn;
    private JButton desfazerBtn;
    private JButton refazerBtn;
    private JButton logoutBtn;
    private JButton exportarBtn; // NOVO

    public MainView(Controller ctrl) {
        super("Gerenciador de fluxo de caixa");
        this.ctrl = ctrl;

        realizarLogin();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 400);
        setLocationRelativeTo(null);

        saldoLabel = new JLabel("Saldo atual: R$ " + ctrl.pegarSaldoAtual());
        historicoArea = new JTextArea();
        historicoArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(historicoArea);

        entradaBtn = new JButton("Registrar Entrada");
        saidaBtn = new JButton("Registrar Saída");
        desfazerBtn = new JButton("Desfazer");
        refazerBtn = new JButton("Refazer");
        logoutBtn = new JButton("Logout");
        exportarBtn = new JButton("Exportar TXT"); // NOVO

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(saldoLabel, BorderLayout.CENTER);
        topPanel.add(logoutBtn, BorderLayout.EAST);

        JPanel bottomPanel = new JPanel(new GridLayout(1, 5)); // Alterado para 5 botões
        bottomPanel.add(entradaBtn);
        bottomPanel.add(saidaBtn);
        bottomPanel.add(desfazerBtn);
        bottomPanel.add(refazerBtn);
        bottomPanel.add(exportarBtn); // NOVO

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        entradaBtn.addActionListener(this::registrarEntrada);
        saidaBtn.addActionListener(this::registrarSaida);
        desfazerBtn.addActionListener(e -> {
            if (ctrl.estaLogado()) {
                if (ctrl.desfazerTransacao()) {
                    atualizarTela();
                } else {
                    JOptionPane.showMessageDialog(this, "Nada para desfazer.");
                }
            }
        });
        refazerBtn.addActionListener(e -> {
            if (ctrl.estaLogado()) {
                if (ctrl.refazerTransacao()) {
                    atualizarTela();
                } else {
                    JOptionPane.showMessageDialog(this, "Nada para refazer.");
                }
            }
        });
        logoutBtn.addActionListener(e -> logout());
        exportarBtn.addActionListener(e -> exportarParaTxt()); // NOVO

        atualizarTela();
        setVisible(true);
    }

    private void realizarLogin() {
        boolean sucesso = false;

        while (!sucesso) {
            String nome = JOptionPane.showInputDialog("Digite seu nome de usuário:");
            if (nome == null) {
                JOptionPane.showMessageDialog(this, "Login cancelado.");
                dispose();
                new Login(ctrl);
                return;
            }

            String senha = JOptionPane.showInputDialog("Digite sua senha:");
            if (senha == null) {
                JOptionPane.showMessageDialog(this, "Login cancelado.");
                dispose();
                new Login(ctrl);
                return;
            }

            String resultado = ctrl.login(nome, senha);
            if (resultado.equals("Login bem-sucedido!")) {
                JOptionPane.showMessageDialog(null, resultado);
                sucesso = true;
            } else {
                JOptionPane.showMessageDialog(null, resultado);
            }
        }
    }

    private void logout() {
        ctrl.logout();
        JOptionPane.showMessageDialog(this, "Logout realizado.");
        dispose();
        new Login(ctrl);
    }

    private void registrarEntrada(ActionEvent e) {
        if (!ctrl.estaLogado()) return;

        String valorStr = JOptionPane.showInputDialog("Valor da entrada:");
        if (valorStr != null) {
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
    }

    private void registrarSaida(ActionEvent e) {
        if (!ctrl.estaLogado()) return;

        String valorStr = JOptionPane.showInputDialog("Valor da saída:");
        if (valorStr != null) {
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
    }

    private void atualizarTela() {
        saldoLabel.setText("Saldo atual: R$ " + ctrl.pegarSaldoAtual());
        StringBuilder sb = new StringBuilder();
        for (Transacao t : ctrl.pegarHistorico()) {
            sb.append(t.toString()).append("\n");
        }
        historicoArea.setText(sb.toString());
    }

    // NOVO
    private void exportarParaTxt() {
        if (!ctrl.estaLogado()) return;

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Salvar histórico como .txt");

        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            try (PrintWriter writer = new PrintWriter(fileToSave)) {
                writer.println("Usuário: " + ctrl.getUsuarioAtual());
                writer.println("Saldo atual: R$ " + ctrl.pegarSaldoAtual());
                writer.println("Histórico de transações:\n");

                for (Transacao t : ctrl.pegarHistorico()) {
                    writer.println(t.toString());
                }

                JOptionPane.showMessageDialog(this, "Histórico exportado com sucesso!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar o arquivo.");
            }
        }
    }
}
