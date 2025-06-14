
package leilao.modelo;

import java.util.ArrayList;

public class Leilao {
    private int id;
    private Leiloeiro leiloeiro;
    private ArrayList<Item> itens;
    private ArrayList<Participante> participantes;
    private boolean ativo;

    public Leilao(int id, Leiloeiro leiloeiro) {
        this.id = id;
        this.leiloeiro = leiloeiro;
        this.itens = new ArrayList<>();
        this.participantes = new ArrayList<>();
        this.ativo = false;
    }

    public int getId() { return id; }
    public Leiloeiro getLeiloeiro() { return leiloeiro; }
    public ArrayList<Item> getItens() { return itens; }
    public ArrayList<Participante> getParticipantes() { return participantes; }
    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
}
