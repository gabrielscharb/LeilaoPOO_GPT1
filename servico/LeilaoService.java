package leilao.servico;

import leilao.interfaces.ICrud;
import leilao.interfaces.ILeilaoOperacoes;
import leilao.modelo.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Comparator;

// Este serviço implementa AMBAS as interfaces
public class LeilaoService implements ICrud<Leilao>, ILeilaoOperacoes {

    private ArrayList<Leilao> leiloes;

    public LeilaoService(ArrayList<Leilao> leiloes) {
        this.leiloes = leiloes;
    }

    // --- MÉTODOS DO ICrud ---

    @Override
    public void inserir(Leilao leilao) {
        this.leiloes.add(leilao);
    }

    @Override
    public ArrayList<Leilao> listar() {
        return this.leiloes;
    }

    @Override
    public Leilao buscarPorId(int id) {
        for (Leilao leilao : this.leiloes) {
            if (leilao.getId() == id) {
                return leilao;
            }
        }
        return null;
    }

    @Override
    public void editar(int id, Leilao leilaoAtualizado) {
        Leilao leilaoExistente = buscarPorId(id);
        if (leilaoExistente != null) {
            // Exemplo: poderia mudar o status ou o leiloeiro
            leilaoExistente.setAtivo(leilaoAtualizado.isAtivo());
        }
    }

    @Override
    public void deletar(int id) {
        this.leiloes.removeIf(leilao -> leilao.getId() == id);
    }


    // --- MÉTODOS DO ILeilaoOperacoes ---

    @Override
    public void adicionarItem(Leilao leilao, Item item) {
        if (leilao != null && item != null) {
            leilao.getItens().add(item);
        }
    }

    @Override
    public void adicionarParticipante(Leilao leilao, Participante participante) {
        if (leilao != null && participante != null) {
            leilao.getParticipantes().add(participante);
        }
    }

    @Override
    public void iniciarLeilao(Leilao leilao) {
        if (leilao != null) {
            leilao.setAtivo(true);
        }
    }

    @Override
    public void realizarLance(Participante participante, Item item, double valor) {
        double maiorValor = item.getLances().stream()
                .max(Comparator.comparing(Lance::getValor))
                .map(Lance::getValor)
                .orElse(item.getValorBase());

        if (valor > maiorValor) {
            item.getLances().add(new Lance(valor, participante));
            JOptionPane.showMessageDialog(null, "Lance de R$" + valor + " aceito!");
        } else {
            JOptionPane.showMessageDialog(null, "Erro: O lance deve ser maior que R$" + maiorValor, "Lance Inválido", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void finalizarLeilao(Leilao leilao) {
        if (leilao != null) {
            leilao.setAtivo(false);
        }
    }

    @Override
    public Lance obterLanceVencedor(Item item) {
        return item.getLances().stream()
                .max(Comparator.comparing(Lance::getValor))
                .orElse(null);
    }
}