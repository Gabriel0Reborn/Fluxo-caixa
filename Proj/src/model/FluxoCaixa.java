package model;

import java.util.ArrayList;
import java.util.List;

public class FluxoCaixa {
    private List<Transacao> transacoes = new ArrayList<>();

    public void adicionarTransacao(Transacao t) {
        transacoes.add(t);
    }

    public void removerTransacao(Transacao t) {
        transacoes.remove(t);
    }

    public double getSaldoAtual() {
        double saldo = 0;
        for (Transacao t : transacoes) {
            if (t.getTipo().equals("Entrada")) saldo += t.getValor();
            else saldo -= t.getValor();
        }
        return saldo;
    }

    public List<Transacao> listarTransacoes() {
        return new ArrayList<>(transacoes);
    }
}
