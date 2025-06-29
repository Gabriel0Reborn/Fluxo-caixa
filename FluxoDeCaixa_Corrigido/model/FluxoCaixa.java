package model;

import java.util.List;
import java.util.ArrayList;

public class FluxoCaixa {
    private List<Transacao> transacoes;

    public FluxoCaixa() {
        this.transacoes = new ArrayList<>();
    }

    public void adicionarTransacao(Transacao t) {
        transacoes.add(t);
    }

    public void removerTransacao(Transacao t) {
        transacoes.remove(t);
    }

    public double getSaldoAtual() {
        double saldo = 0;
        for (Transacao t : transacoes) {
            saldo += t.isEntrada() ? t.getValor() : -t.getValor();
        }
        return saldo;
    }

    public List<Transacao> listarTransacoes() {
        return transacoes;
    }

    // Método para clonar as transações
    public List<Transacao> clonarTransacoes() {
        return new ArrayList<>(transacoes);  // Retorna uma cópia das transações
    }

    // Método para definir as transações
    public void setTransacoes(List<Transacao> transacoes) {
        this.transacoes = transacoes;
    }
}
