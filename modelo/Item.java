
package leilao.modelo;

import java.util.ArrayList;

public class Item {
    private String descricao;
    private double valorBase;
    private ArrayList<Lance> lances;

    public Item(String descricao, double valorBase) {
        this.descricao = descricao;
        this.valorBase = valorBase;
        this.lances = new ArrayList<>();
    }

    public String getDescricao() { return descricao; }
    public double getValorBase() { return valorBase; }
    public ArrayList<Lance> getLances() { return lances; }
}
