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

    // ✅ Construtor que recebe os modelos
    public Controller(FluxoCaixa caixa, Historico historico, GerenciadorUsuarios gerenciadorUsuarios) {
        this.caixa = caixa;
        this.historico = historico;
        this.gerenciadorUsuarios = gerenciadorUsuarios;
    }

    // ✅ Registro de entrada
    public void registrarEntrada(double valor, String descricao) {
        if (valor <= 0) {
            JOptionPane.showMessageDialog(null, "O valor da entrada deve ser maior que zero.");
            return;
        }
        Transacao t = new Transacao("Entrada", valor, descricao, LocalDate.now());
        caixa.adicionarTransacao(t);
        historico.registrarTransacao(t);
    }

    // ✅ Registro de saída
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

    // ✅ Desfazer transação
    public boolean desfazerTransacao() {
        Transacao t = historico.desfazer();
        if (t != null) {
            caixa.removerTransacao(t);
            return true;
        }
        return false;
    }

    // ✅ Refazer transação
    public boolean refazerTransacao() {
        Transacao t = historico.refazer();
        if (t != null) {
            caixa.adicionarTransacao(t);
            return true;
        }
        return false;
    }

    // ✅ Consultar saldo
    public double pegarSaldoAtual() {
        return caixa.getSaldoAtual();
    }

    // ✅ Obter histórico
    public List<Transacao> pegarHistorico() {
        return caixa.listarTransacoes();
    }

    // ✅ Autenticação: Login
    public String login(String nome, String senha) {
        if (gerenciadorUsuarios.login(nome, senha)) {
            return "Login bem-sucedido!";
        } else {
            return "Usuário ou senha incorretos.";
        }
    }

    // ✅ Logout
    public void logout() {
        gerenciadorUsuarios.logout();
    }

    public boolean estaLogado() {
        return gerenciadorUsuarios.estaLogado();
    }

    public String getUsuarioAtual() {
        return gerenciadorUsuarios.getUsuarioLogado();
    }

    // ✅ Registro de novo usuário
    public boolean registrar(String nome, String senha) {
        return gerenciadorUsuarios.registrar(nome, senha);
    }
}
