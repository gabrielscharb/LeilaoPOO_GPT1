package leilao.modelo;

import java.io.Serializable; // Importe Serializable se ainda não o fez
import java.util.ArrayList;

// A classe Item também precisa ser Serializable
public class Item implements Serializable {
    private String descricao;
    private double valorBase;
    private Categoria categoria; // <-- NOVO ATRIBUTO
    private ArrayList<Lance> lances;

    // Construtor atualizado para receber a Categoria
    public Item(String descricao, double valorBase, Categoria categoria) {
        this.descricao = descricao;
        this.valorBase = valorBase;
        this.categoria = categoria; // <-- ATRIBUIÇÃO DA CATEGORIA
        this.lances = new ArrayList<>();
    }

    public String getDescricao() { return descricao; }
    public double getValorBase() { return valorBase; }
    public ArrayList<Lance> getLances() { return lances; }

    // Getter e Setter para a nova propriedade
    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
}