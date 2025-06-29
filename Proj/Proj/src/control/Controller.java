package control;

import model.FluxoCaixa;
import model.Historico;
import model.Transacao;
import model.GerenciadorUsuarios;

import javax.swing.*;
import java.time.LocalDate;
import java.util.List;

public class Controller {
    private FluxoCaixa caixa;
    private Historico historico;
    private GerenciadorUsuarios gerenciadorUsuarios;

    public Controller() {
        this.caixa = new FluxoCaixa();
        this.historico = new Historico();
        this.gerenciadorUsuarios = new GerenciadorUsuarios();
    }

    public void registrarEntrada(double valor, String descricao) {
        if (valor <= 0) {
            JOptionPane.showMessageDialog(null, "O valor da entrada deve ser maior que zero.");
            return;
        }
        Transacao t = new Transacao("Entrada", valor, descricao, LocalDate.now());
        caixa.adicionarTransacao(t);
        historico.registrarTransacao(t);
    }

    public void registrarSaida(double valor, String descricao) {
        if (valor <= 0) {
            JOptionPane.showMessageDialog(null, "O valor da saída deve ser maior que zero.");
            return;
        }

        if (valor > caixa.getSaldoAtual()) {
            JOptionPane.showMessageDialog(null, "Saldo insuficiente para esta transação.");
            return;
        }

        Transacao t = new Transacao("Saida", valor, descricao, LocalDate.now());
        caixa.adicionarTransacao(t);
        historico.registrarTransacao(t);
    }

    public boolean desfazerTransacao() {
        Transacao t = historico.desfazer();
        if (t != null) {
            caixa.removerTransacao(t);
            return true;
        }
        return false;
    }

    public boolean refazerTransacao() {
        Transacao t = historico.refazer();
        if (t != null) {
            caixa.adicionarTransacao(t);
            return true;
        }
        return false;
    }

    public double pegarSaldoAtual() {
        return caixa.getSaldoAtual();
    }

    public List<Transacao> pegarHistorico() {
        return caixa.listarTransacoes();
    }

    public String login(String nome, String senha) {
        return gerenciadorUsuarios.login(nome, senha) ? "Login bem-sucedido!" : "Usuário ou senha incorretos.";
    }

    public void logout() {
        gerenciadorUsuarios.logout();
    }

    public boolean estaLogado() {
        return gerenciadorUsuarios.estaLogado();
    }

    public String getUsuarioAtual() {
        return gerenciadorUsuarios.getUsuarioLogado();
    }
}
