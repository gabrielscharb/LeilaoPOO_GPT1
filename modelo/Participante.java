
package leilao.modelo;

import leilao.interfaces.ILance;
import java.util.ArrayList;

public class Participante extends Pessoa implements ILance {
    private int id;
    private ArrayList<Lance> lances;

    public Participante(String nome, String cpf, int id) {
        super(nome, cpf);
        this.id = id;
        this.lances = new ArrayList<>();
    }

    @Override
    public void exibirInformacoes() {
        System.out.println("Participante: " + nome + " CPF: " + cpf);
    }

    @Override
    public void darLance(Item item) {
        // Implementação virá na lógica do sistema
    }

    public int getId() { return id; }
    public ArrayList<Lance> getLances() { return lances; }
}
