package leilao.servico;

import leilao.interfaces.ICrud;
import leilao.interfaces.ILeilaoOperacoes;
import leilao.modelo.*;

import javax.swing.JOptionPane; // Adicione esta importação
import java.util.ArrayList;
import java.util.Comparator;

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
        // A lógica de edição agora atualiza a descrição.
        Leilao leilaoExistente = buscarPorId(id);
        if (leilaoExistente != null) {
            leilaoExistente.setDescricao(leilaoAtualizado.getDescricao());
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

    // Os métodos iniciarLeilao e finalizarLeilao foram REMOVIDOS.

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
    public Lance obterLanceVencedor(Item item) {
        return item.getLances().stream()
                .max(Comparator.comparing(Lance::getValor))
                .orElse(null);
    }
    // Adicione estes dois métodos à classe LeilaoService

    @Override
    public void editarItem(Item itemParaEditar, String novaDescricao, double novoValor) {
        // A lógica é simples: se o item existir, atualiza seus dados com os novos valores.
        if (itemParaEditar != null) {
            itemParaEditar.setDescricao(novaDescricao);
            itemParaEditar.setValorBase(novoValor);
        }
    }

    @Override
    public void deletarItem(Leilao leilao, Item itemParaDeletar) {
        // Acessa a lista de itens do leilão e remove o item especificado.
        if (leilao != null && itemParaDeletar != null) {
            leilao.getItens().remove(itemParaDeletar);
        }
    }
}