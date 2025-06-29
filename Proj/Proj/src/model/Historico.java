package model;

import java.util.Stack;

public class Historico {
    private Stack<Transacao> desfazer = new Stack<>();
    private Stack<Transacao> refazer = new Stack<>();

    public void registrarTransacao(Transacao t) {
        desfazer.push(t);
        refazer.clear();
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
}
