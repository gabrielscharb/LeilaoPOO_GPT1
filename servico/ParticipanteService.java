package leilao.servico;

import leilao.interfaces.ICrud;
import leilao.modelo.Participante;

import java.util.ArrayList;

public class ParticipanteService implements ICrud<Participante> {

    private ArrayList<Participante> participantes;

    public ParticipanteService(ArrayList<Participante> participantes) {
        this.participantes = participantes;
    }

    @Override
    public void inserir(Participante participante) {
        this.participantes.add(participante);
    }

    @Override
    public void editar(int id, Participante participanteAtualizado) {
        Participante participanteExistente = this.buscarPorId(id);
        if (participanteExistente != null) {
            participanteExistente.setNome(participanteAtualizado.getNome());
            participanteExistente.setCpf(participanteAtualizado.getCpf());
        }
    }

    @Override
    public void deletar(int id) {
        Participante participanteParaDeletar = this.buscarPorId(id);
        if (participanteParaDeletar != null) {
            this.participantes.remove(participanteParaDeletar);
        }
    }

    @Override
    public ArrayList<Participante> listar() {
        return this.participantes;
    }

    @Override
    public Participante buscarPorId(int id) {
        for (Participante participante : this.participantes) {
            if (participante.getId() == id) {
                return participante;
            }
        }
        return null;
    }
}