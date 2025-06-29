package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transacao {
    private String descricao;
    private double valor;
    private boolean entrada; // true para entrada, false para saída
    private LocalDateTime dataHora;

    public Transacao(String descricao, double valor, boolean entrada) {
        this.descricao = descricao;
        this.valor = valor;
        this.entrada = entrada;
        this.dataHora = LocalDateTime.now();
    }

    public String getDescricao() {
        return descricao;
    }

    public double getValor() {
        return valor;
    }

    public boolean isEntrada() {
        return entrada;
    }

    public String getTipo() {
        return entrada ? "entrada" : "saida";
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public String getDataHoraFormatada() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return dataHora.format(formatter);
    }

    @Override
    public String toString() {
        String tipo = entrada ? "Entrada" : "Saída";
        return String.format("[%s] %s - R$ %.2f (%s)", getDataHoraFormatada(), tipo, valor, descricao);
    }
}
