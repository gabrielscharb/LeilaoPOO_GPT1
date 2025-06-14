package leilao.servico;

import leilao.interfaces.ICrud;
import leilao.modelo.Leiloeiro;

import java.util.ArrayList;

public class LeiloeiroService implements ICrud<Leiloeiro> {

    private ArrayList<Leiloeiro> leiloeiros;

    public LeiloeiroService(ArrayList<Leiloeiro> leiloeiros) {
        this.leiloeiros = leiloeiros;
    }

    @Override
    public void inserir(Leiloeiro leiloeiro) {
        this.leiloeiros.add(leiloeiro);
    }

    @Override
    public void editar(int id, Leiloeiro leiloeiroAtualizado) {
        Leiloeiro leiloeiroExistente = this.buscarPorId(id);
        if (leiloeiroExistente != null) {
            leiloeiroExistente.setNome(leiloeiroAtualizado.getNome());
            leiloeiroExistente.setCpf(leiloeiroAtualizado.getCpf());
        }
    }

    @Override
    public void deletar(int id) {
        // Usamos removeIf por ser mais moderno e seguro em algumas situações
        this.leiloeiros.removeIf(leiloeiro -> leiloeiro.getId() == id);
    }

    @Override
    public ArrayList<Leiloeiro> listar() {
        return this.leiloeiros;
    }

    @Override
    public Leiloeiro buscarPorId(int id) {
        for (Leiloeiro leiloeiro : this.leiloeiros) {
            if (leiloeiro.getId() == id) {
                return leiloeiro;
            }
        }
        return null;
    }
}