package model;

import java.util.*;

public class Historico {
    private Stack<Transacao> desfazer = new Stack<>();
    private Stack<Transacao> refazer = new Stack<>();

    public void adicionarTransacao(Transacao t) {
        if (t != null) {
            desfazer.push(t);
            refazer.clear();
        }
    }

    public void removerTransacao(Transacao t) {
        desfazer.remove(t);
        refazer.remove(t);
    }

    public void editarTransacao(Transacao antiga, Transacao nova) {
        int index = desfazer.indexOf(antiga);
        if (index != -1) {
            desfazer.set(index, nova);
        }
    }

    public List<Transacao> getTransacoes() {
        return new ArrayList<>(desfazer);
    }

    public Transacao desfazer() {
        if (!desfazer.isEmpty()) {
            Transacao t = desfazer.pop();
            refazer.push(t);
            return t;
        }
        return null;
    }

    public Transacao refazer() {
        if (!refazer.isEmpty()) {
            Transacao t = refazer.pop();
            desfazer.push(t);
            return t;
        }
        return null;
    }

    public boolean podeDesfazer() {
        return !desfazer.isEmpty();
    }

    public boolean podeRefazer() {
        return !refazer.isEmpty();
    }

    public void limpar() {
        desfazer.clear();
        refazer.clear();
    }

    public Stack<Transacao> getTransacoesDesfeitas() {
        return (Stack<Transacao>) desfazer.clone();
    }

    public Stack<Transacao> getTransacoesRefaziveis() {
        return (Stack<Transacao>) refazer.clone();
    }
}

