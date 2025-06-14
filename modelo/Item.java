package leilao.modelo;

import java.io.Serializable; // Adicione esta linha se não existir
import java.util.ArrayList;

public class Item implements Serializable { // Adicione "implements Serializable" se não existir
    private String descricao;
    private double valorBase;
    private Categoria categoria; // Este atributo foi adicionado em passos anteriores
    private ArrayList<Lance> lances;

    // O construtor permanece o mesmo
    public Item(String descricao, double valorBase, Categoria categoria) {
        this.descricao = descricao;
        this.valorBase = valorBase;
        this.categoria = categoria;
        this.lances = new ArrayList<>();
    }

    // Getters existentes
    public String getDescricao() { return descricao; }
    public double getValorBase() { return valorBase; }
    public Categoria getCategoria() { return categoria; }
    public ArrayList<Lance> getLances() { return lances; }

    // --- MÉTODOS SETTERS (NOVOS) ---
    // Adicione os métodos abaixo para permitir a edição

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setValorBase(double valorBase) {
        this.valorBase = valorBase;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}