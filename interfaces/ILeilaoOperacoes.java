package leilao.interfaces;

import leilao.modelo.Item;
import leilao.modelo.Lance;
import leilao.modelo.Leilao;
import leilao.modelo.Participante;

public interface ILeilaoOperacoes {

    // Métodos existentes
    void adicionarItem(Leilao leilao, Item item);
    void adicionarParticipante(Leilao leilao, Participante participante);
    void realizarLance(Participante participante, Item item, double valor);
    Lance obterLanceVencedor(Item item);

    // --- MÉTODOS NOVOS ---
    // Ação para editar os dados de um item existente.
    void editarItem(Item itemParaEditar, String novaDescricao, double novoValor);

    // Ação para remover um item de dentro de um leilão.
    void deletarItem(Leilao leilao, Item itemParaDeletar);
}