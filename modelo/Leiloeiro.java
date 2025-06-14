
package leilao.modelo;

import java.util.ArrayList;

public class Leiloeiro extends Pessoa {
    private int id;
    private ArrayList<Leilao> leiloes;

    public Leiloeiro(String nome, String cpf, int id) {
        super(nome, cpf);
        this.id = id;
        this.leiloes = new ArrayList<>();
    }

    @Override
    public void exibirInformacoes() {
        System.out.println("Leiloeiro: " + nome + " CPF: " + cpf);
    }

    public int getId() { return id; }
    public ArrayList<Leilao> getLeiloes() { return leiloes; }
}
