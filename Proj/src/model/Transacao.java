package model;

import java.time.LocalDate;

public class Transacao {
    private String tipo;
    private double valor;
    private String descricao;
    private LocalDate data;

    public Transacao(String tipo, double valor, String descricao, LocalDate data) {
        this.tipo = tipo;
        this.valor = valor;
        this.descricao = descricao;
        this.data = data;
    }

    public double getValor() {
        return valor;
    }

    public String getTipo() {
        return tipo;
    }

    public String toString() {
        return data + " - " + tipo + ": R$" + valor + " (" + descricao + ")";
    }
}
