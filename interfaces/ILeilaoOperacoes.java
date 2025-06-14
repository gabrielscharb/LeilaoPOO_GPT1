package leilao.interfaces;

import leilao.modelo.Item;
import leilao.modelo.Lance;
import leilao.modelo.Leilao;
import leilao.modelo.Participante;

public interface ILeilaoOperacoes {

    // Adiciona um item a um leilão específico
    void adicionarItem(Leilao leilao, Item item);

    // Adiciona um participante a um leilão específico
    void adicionarParticipante(Leilao leilao, Participante participante);

    // Inicia um leilão, tornando-o ativo para lances
    void iniciarLeilao(Leilao leilao);

    // A operação principal: um participante dá um lance em um item
    void realizarLance(Participante participante, Item item, double valor);

    // Encerra um leilão e determina os vencedores
    void finalizarLeilao(Leilao leilao);

    // Retorna o lance vencedor de um item específico
    Lance obterLanceVencedor(Item item);
}