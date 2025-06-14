
package leilao.modelo;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Lance implements Serializable {
    private double valor;
    private Participante participante;
    private LocalDateTime dataHora;

    public Lance(double valor, Participante participante) {
        this.valor = valor;
        this.participante = participante;
        this.dataHora = LocalDateTime.now();
    }

    public double getValor() { return valor; }
    public Participante getParticipante() { return participante; }
    public LocalDateTime getDataHora() { return dataHora; }
}
