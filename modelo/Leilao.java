package leilao.modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Leilao implements Serializable {
    private int id;
    private String descricao; // <-- NOVO ATRIBUTO
    private Leiloeiro leiloeiro;
    private ArrayList<Item> itens;
    private ArrayList<Participante> participantes;

    // Construtor atualizado para receber a descrição
    public Leilao(int id, String descricao, Leiloeiro leiloeiro) {
        this.id = id;
        this.descricao = descricao; // <-- ATRIBUIÇÃO DA DESCRIÇÃO
        this.leiloeiro = leiloeiro;
        this.itens = new ArrayList<>();
        this.participantes = new ArrayList<>();
    }

    // Getters e Setters
    public int getId() { return id; }
    public String getDescricao() { return descricao; } // <-- NOVO GETTER
    public void setDescricao(String descricao) { this.descricao = descricao; } // <-- NOVO SETTER
    public Leiloeiro getLeiloeiro() { return leiloeiro; }
    public ArrayList<Item> getItens() { return itens; }
    public ArrayList<Participante> getParticipantes() { return participantes; }
}