
package leilao.ui;

import javax.swing.*;

public class Menu {

    public static void exibirMenu() {
        String[] opcoes = {"Cadastrar Leiloeiro", "Cadastrar Participante", "Sair"};
        int escolha;

        do {
            escolha = JOptionPane.showOptionDialog(null, "Menu Principal", "Sistema de Leilões",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoes, opcoes[0]);

            switch (escolha) {
                case 0:
                    JOptionPane.showMessageDialog(null, "Cadastro de Leiloeiro em desenvolvimento.");
                    break;
                case 1:
                    JOptionPane.showMessageDialog(null, "Cadastro de Participante em desenvolvimento.");
                    break;
                case 2:
                    JOptionPane.showMessageDialog(null, "Saindo...");
                    break;
            }
        } while (escolha != 2);
    }
}
