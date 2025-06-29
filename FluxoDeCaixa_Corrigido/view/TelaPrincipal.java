package view;

import model.Transacao;
import control.Controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Stack;

public class TelaPrincipal extends JFrame {
    private Controller controller;
    private JTable tabela;
    private JLabel lblSaldo;
    private DefaultTableModel modelo;

    private boolean isDarkMode = true;

    private Stack<List<Transacao>> historicoUndo = new Stack<>();
    private Stack<List<Transacao>> historicoRedo = new Stack<>();

    public TelaPrincipal(Controller controller) {
        this.controller = controller;
        setTitle("Painel Principal - Fluxo de Caixa");
        setSize(1000, 600); // Ajustado para um tamanho maior, mas ainda flexível
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponents();
        aplicarModoEscuro();
        setVisible(true);
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Título
        JLabel titulo = new JLabel("Bem-vindo ao sistema de Fluxo de Caixa!", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(titulo, BorderLayout.NORTH);

        // Tabela
        modelo = new DefaultTableModel(new String[]{"Descrição", "Valor", "Tipo", "Data"}, 0);
        tabela = new JTable(modelo);
        tabela.setFillsViewportHeight(true);
        JScrollPane scroll = new JScrollPane(tabela);
        add(scroll, BorderLayout.CENTER);

        atualizarTabela();

        // Rodapé
        JPanel rodape = new JPanel(new BorderLayout());
        lblSaldo = new JLabel();
        atualizarSaldo();
        rodape.add(lblSaldo, BorderLayout.WEST);

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnAdicionar = new JButton("➕ Adicionar");
        JButton btnEditar = new JButton("✏️ Editar");
        JButton btnRemover = new JButton("🗑️ Remover");
        JButton btnUndo = new JButton("↩️ Desfazer");
        JButton btnRedo = new JButton("↪️ Refazer");
        JButton btnDownload = new JButton("⬇️ Download");
        JButton btnModo = new JButton("🌙");
        JButton btnLogout = new JButton("🚪 Logout");

        botoes.add(btnAdicionar);
        botoes.add(btnEditar);
        botoes.add(btnRemover);
        botoes.add(btnUndo);
        botoes.add(btnRedo);
        botoes.add(btnDownload);
        botoes.add(btnModo);
        botoes.add(btnLogout);

        rodape.add(botoes, BorderLayout.EAST);
        add(rodape, BorderLayout.SOUTH);

        // Ações
        btnAdicionar.addActionListener(e -> {
            salvarEstado();
            adicionarTransacao();
        });
        btnEditar.addActionListener(e -> {
            salvarEstado();
            editarTransacao();
        });
        btnRemover.addActionListener(e -> {
            salvarEstado();
            removerTransacao();
        });
        btnUndo.addActionListener(e -> desfazer());
        btnRedo.addActionListener(e -> refazer());
        btnDownload.addActionListener(e -> downloadTransacoes());
        btnModo.addActionListener(e -> alternarModo(btnModo));
        btnLogout.addActionListener(e -> {
            dispose();
            new Login(controller);
        });
    }

    private void atualizarTabela() {
        modelo.setRowCount(0);
        for (Transacao t : controller.getTransacoes()) {
            modelo.addRow(new Object[]{
                    t.getDescricao(),
                    String.format("R$ %.2f", t.getValor()),
                    t.isEntrada() ? "Entrada" : "Saída",
                    t.getDataHoraFormatada()
            });
        }
    }

    private void atualizarSaldo() {
        double saldo = controller.getSaldoAtual();
        lblSaldo.setText(String.format("💰 Saldo atual: R$ %.2f", saldo));
    }

    private void adicionarTransacao() {
        String descricao = JOptionPane.showInputDialog(this, "Descrição:");
        String valorStr = JOptionPane.showInputDialog(this, "Valor:");
        String[] tipos = {"Entrada", "Saída"};
        int tipo = JOptionPane.showOptionDialog(this, "Tipo:", "Tipo de Transação",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, tipos, tipos[0]);

        if (descricao != null && valorStr != null && tipo != -1) {
            try {
                double valor = Double.parseDouble(valorStr);
                boolean entrada = tipo == 0;
                Transacao nova = new Transacao(descricao, valor, entrada);
                controller.adicionarTransacao(nova);
                atualizarTabela();
                atualizarSaldo();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Valor inválido.");
            }
        }
    }

    private void editarTransacao() {
        int row = tabela.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma transação para editar.");
            return;
        }

        Transacao antiga = controller.getTransacoes().get(row);
        String novaDesc = JOptionPane.showInputDialog(this, "Nova descrição:", antiga.getDescricao());
        String novoValorStr = JOptionPane.showInputDialog(this, "Novo valor:", antiga.getValor());
        String[] tipos = {"Entrada", "Saída"};
        int novoTipo = JOptionPane.showOptionDialog(this, "Tipo:", "Tipo de Transação",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, tipos, antiga.isEntrada() ? tipos[0] : tipos[1]);

        if (novaDesc != null && novoValorStr != null && novoTipo != -1) {
            try {
                double novoValor = Double.parseDouble(novoValorStr);
                boolean entrada = novoTipo == 0;
                Transacao nova = new Transacao(novaDesc, novoValor, entrada);
                controller.editarTransacao(antiga, nova);
                atualizarTabela();
                atualizarSaldo();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Valor inválido.");
            }
        }
    }

    private void removerTransacao() {
        int row = tabela.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma transação para remover.");
            return;
        }

        Transacao t = controller.getTransacoes().get(row);
        controller.removerTransacao(t);
        atualizarTabela();
        atualizarSaldo();
    }

    private void salvarEstado() {
        historicoUndo.push(controller.clonarTransacoes());
        historicoRedo.clear();
    }

    private void desfazer() {
        if (!historicoUndo.isEmpty()) {
            historicoRedo.push(controller.clonarTransacoes());
            controller.setTransacoes(historicoUndo.pop());
            atualizarTabela();
            atualizarSaldo();
        }
    }

    private void refazer() {
        if (!historicoRedo.isEmpty()) {
            historicoUndo.push(controller.clonarTransacoes());
            controller.setTransacoes(historicoRedo.pop());
            atualizarTabela();
            atualizarSaldo();
        }
    }

    // Alterado para função de Download de Transações
    private void downloadTransacoes() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Escolha onde salvar o arquivo");
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try (FileWriter writer = new FileWriter(chooser.getSelectedFile())) {
                for (Transacao t : controller.getTransacoes()) {
                    writer.write(String.format("%s | %.2f | %s | %s%n",
                            t.getDescricao(), t.getValor(),
                            t.isEntrada() ? "Entrada" : "Saída",
                            t.getDataHoraFormatada()));
                }
                JOptionPane.showMessageDialog(this, "Transações baixadas com sucesso!");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Erro ao realizar o download.");
            }
        }
    }

    private void alternarModo(JButton btnModo) {
        isDarkMode = !isDarkMode;
        if (isDarkMode) {
            aplicarModoEscuro();
            btnModo.setText("🌙");
        } else {
            aplicarModoClaro();
            btnModo.setText("☀️");
        }
    }

    private void aplicarModoEscuro() {
        Color fundo = new Color(40, 40, 40);
        Color texto = Color.WHITE;
        Color btn = new Color(70, 70, 70);

        getContentPane().setBackground(fundo);
        tabela.setBackground(fundo);
        tabela.setForeground(texto);
        tabela.setSelectionBackground(new Color(90, 90, 90));
        tabela.setSelectionForeground(Color.WHITE);
        tabela.setGridColor(Color.GRAY);

        // Atualizando a cor do texto no JLabel do saldo
        lblSaldo.setForeground(texto);

        for (Component c : getContentPane().getComponents()) {
            c.setBackground(fundo);
            c.setForeground(texto);
        }
    }

    private void aplicarModoClaro() {
        Color fundo = Color.WHITE;
        Color texto = Color.BLACK;
        Color btn = new Color(230, 230, 230);

        getContentPane().setBackground(fundo);
        tabela.setBackground(fundo);
        tabela.setForeground(texto);
        tabela.setSelectionBackground(new Color(200, 200, 255));
        tabela.setSelectionForeground(Color.BLACK);
        tabela.setGridColor(Color.LIGHT_GRAY);

        // Atualizando a cor do texto no JLabel do saldo
        lblSaldo.setForeground(texto);

        for (Component c : getContentPane().getComponents()) {
            c.setBackground(fundo);
            c.setForeground(texto);
        }
    }
}
