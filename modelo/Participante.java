package leilao.modelo;

// A importação da interface ILance não é mais necessária.
import java.util.ArrayList;

// A cláusula "implements ILance" foi removida.
public class Participante extends Pessoa {
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

    // O método darLance(Item item) foi completamente removido.

    public int getId() { return id; }
    public ArrayList<Lance> getLances() { return lances; }
}