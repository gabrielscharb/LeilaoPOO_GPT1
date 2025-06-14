package leilao.ui;

import leilao.interfaces.ICrud;
import leilao.interfaces.ILeilaoOperacoes;
import leilao.modelo.*;
import leilao.servico.CategoriaService;
import leilao.servico.LeiloeiroService;
import leilao.servico.LeilaoService;
import leilao.servico.ParticipanteService;
import leilao.util.ArquivoUtil;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Menu {

    // Serviços de CRUD
    private static ICrud<Categoria> categoriaService;
    private static ICrud<Leiloeiro> leiloeiroService;
    private static ICrud<Participante> participanteService;
    private static ICrud<Leilao> leilaoCrudService;

    // Serviço de Operações de Leilão
    private static ILeilaoOperacoes leilaoOperacaoService;

    // Arquivos de persistência
    private static final String ARQUIVO_CATEGORias = "categorias.dat";
    private static final String ARQUIVO_LEILOEIROS = "leiloeiros.dat";
    private static final String ARQUIVO_PARTICIPANTES = "participantes.dat";
    private static final String ARQUIVO_LEILOES = "leiloes.dat";

    public static void main(String[] args) {
        // Carregar dados e instanciar serviços
        ArrayList<Categoria> categoriasDados = ArquivoUtil.carregarDados(ARQUIVO_CATEGORias);
        if (categoriasDados == null) categoriasDados = new ArrayList<>();
        categoriaService = new CategoriaService(categoriasDados);

        ArrayList<Leiloeiro> leiloeirosDados = ArquivoUtil.carregarDados(ARQUIVO_LEILOEIROS);
        if (leiloeirosDados == null) leiloeirosDados = new ArrayList<>();
        leiloeiroService = new LeiloeiroService(leiloeirosDados);

        ArrayList<Participante> participantesDados = ArquivoUtil.carregarDados(ARQUIVO_PARTICIPANTES);
        if (participantesDados == null) participantesDados = new ArrayList<>();
        participanteService = new ParticipanteService(participantesDados);

        ArrayList<Leilao> leiloesDados = ArquivoUtil.carregarDados(ARQUIVO_LEILOES);
        if (leiloesDados == null) leiloesDados = new ArrayList<>();
        LeilaoService leilaoServiceCompleto = new LeilaoService(leiloesDados);
        leilaoCrudService = leilaoServiceCompleto;
        leilaoOperacaoService = leilaoServiceCompleto;

        exibirMenuPrincipal();
    }

    public static void exibirMenuPrincipal() {
        String[] opcoes = {"Gerenciar Leilões", "Gerenciar Leiloeiros", "Gerenciar Participantes", "Gerenciar Categorias", "Sair"};
        int escolha;
        do {
            escolha = JOptionPane.showOptionDialog(null, "Menu Principal", "Sistema de Leilões",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoes, opcoes[0]);

            switch (escolha) {
                case 0: exibirMenuLeiloes(); break;
                case 1: exibirMenuLeiloeiros(); break;
                case 2: exibirMenuParticipantes(); break;
                case 3: exibirMenuCategorias(); break;
                case 4:
                    // Salvar todos os dados
                    ArquivoUtil.salvarDados(categoriaService.listar(), ARQUIVO_CATEGORias);
                    ArquivoUtil.salvarDados(leiloeiroService.listar(), ARQUIVO_LEILOEIROS);
                    ArquivoUtil.salvarDados(participanteService.listar(), ARQUIVO_PARTICIPANTES);
                    ArquivoUtil.salvarDados(leilaoCrudService.listar(), ARQUIVO_LEILOES);
                    JOptionPane.showMessageDialog(null, "Saindo e salvando dados...");
                    break;
            }
        } while (escolha != 4);
    }

    // --- GERENCIAMENTO DE LEILÕES ---
    public static void exibirMenuLeiloes() {
        String[] opcoes = {"Criar Novo Leilão", "Listar Leilões", "Adicionar Item a um Leilão", "Dar Lance em Item", "Ver Resultado", "Voltar"};
        int escolha;
        do {
            escolha = JOptionPane.showOptionDialog(null, "Gerenciar Leilões", "Sistema de Leilões",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);
            switch (escolha) {
                case 0: criarLeilao(); break;
                case 1: listarLeiloes(); break;
                case 2: adicionarItemAoLeilao(); break;
                case 3: darLanceEmItem(); break;
                case 4: verResultadoLeilao(); break; // <-- MUDANÇA FINAL AQUI
                case 5: break;
            }
        } while (escolha != 5);
    }

    private static void criarLeilao() {
        try {
            int idLeilao = Integer.parseInt(JOptionPane.showInputDialog("ID do novo Leilão:"));

            listarLeiloeiros(); // Mostra leiloeiros disponíveis
            int idLeiloeiro = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID do Leiloeiro para este leilão:"));
            Leiloeiro leiloeiroEscolhido = leiloeiroService.buscarPorId(idLeiloeiro);

            if (leiloeiroEscolhido != null) {
                Leilao novoLeilao = new Leilao(idLeilao, leiloeiroEscolhido);
                leilaoCrudService.inserir(novoLeilao);
                JOptionPane.showMessageDialog(null, "Leilão criado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Leiloeiro não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao criar leilão: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void listarLeiloes() {
        ArrayList<Leilao> leiloes = leilaoCrudService.listar();
        if(leiloes.isEmpty()) { JOptionPane.showMessageDialog(null, "Nenhum leilão cadastrado."); return; }
        StringBuilder sb = new StringBuilder("Leilões Cadastrados:\n\n");
        for (Leilao l : leiloes) {
            sb.append("ID: ").append(l.getId())
                    .append(" | Leiloeiro: ").append(l.getLeiloeiro().getNome())
                    .append(" | Ativo: ").append(l.isAtivo())
                    .append(" | Itens: ").append(l.getItens().size())
                    .append("\n");
        }
        exibirEmAreaDeTexto(sb.toString(), "Lista de Leilões");
    }

    private static void adicionarItemAoLeilao() {
        try {
            listarLeiloes();
            int idLeilao = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID do Leilão para adicionar o item:"));
            Leilao leilaoEscolhido = leilaoCrudService.buscarPorId(idLeilao);

            if (leilaoEscolhido == null) {
                JOptionPane.showMessageDialog(null, "Leilão não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String descricaoItem = JOptionPane.showInputDialog("Descrição do novo item:");
            double valorBase = Double.parseDouble(JOptionPane.showInputDialog("Valor base do item (ex: 150.99):"));

            listarCategorias();
            int idCategoria = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID da Categoria para este item:"));
            Categoria categoriaEscolhida = categoriaService.buscarPorId(idCategoria);

            if (categoriaEscolhida == null) {
                JOptionPane.showMessageDialog(null, "Categoria não encontrada!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Item novoItem = new Item(descricaoItem, valorBase, categoriaEscolhida);
            leilaoOperacaoService.adicionarItem(leilaoEscolhido, novoItem);

            JOptionPane.showMessageDialog(null, "Item '" + descricaoItem + "' adicionado ao leilão " + idLeilao + " com sucesso!");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Valor ou ID inválido. Por favor, use números.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    private static void darLanceEmItem() {
        try {
            // 1. Escolher o Leilão
            listarLeiloes();
            int idLeilao = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID do Leilão no qual deseja dar um lance:"));
            Leilao leilaoEscolhido = leilaoCrudService.buscarPorId(idLeilao);

            if (leilaoEscolhido == null) {
                JOptionPane.showMessageDialog(null, "Leilão não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 2. Listar e escolher o Item daquele leilão específico
            ArrayList<Item> itensDoLeilao = leilaoEscolhido.getItens();
            if (itensDoLeilao.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Este leilão não possui itens!", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            StringBuilder sb = new StringBuilder("Itens disponíveis no Leilão " + leilaoEscolhido.getId() + ":\n\n");
            for (Item item : itensDoLeilao) {
                sb.append("Item: ").append(item.getDescricao())
                        .append(" | Valor Base: R$").append(item.getValorBase())
                        .append("\n");
            }
            exibirEmAreaDeTexto(sb.toString(), "Itens do Leilão");

            // Para simplificar, vamos buscar o item pela descrição.
            // Em um sistema real, o ideal seria que cada Item também tivesse um ID único.
            String descItem = JOptionPane.showInputDialog("Digite a descrição EXATA do item em que deseja dar o lance:");
            Item itemEscolhido = null;
            for (Item item : itensDoLeilao) {
                if (item.getDescricao().equalsIgnoreCase(descItem)) {
                    itemEscolhido = item;
                    break;
                }
            }

            if (itemEscolhido == null) {
                JOptionPane.showMessageDialog(null, "Item não encontrado neste leilão!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 3. Escolher o Participante
            listarParticipantes();
            int idParticipante = Integer.parseInt(JOptionPane.showInputDialog("Digite o SEU ID de Participante:"));
            Participante participante = participanteService.buscarPorId(idParticipante);

            if (participante == null) {
                JOptionPane.showMessageDialog(null, "Participante não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 4. Informar o valor do lance
            double valorLance = Double.parseDouble(JOptionPane.showInputDialog("Digite o valor do seu lance para '" + itemEscolhido.getDescricao() + "':"));

            // 5. Realizar a operação através do serviço
            leilaoOperacaoService.realizarLance(participante, itemEscolhido, valorLance);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Valor ou ID inválido. Por favor, use números.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    private static void verResultadoLeilao() {
        try {
            // 1. Escolher o Leilão
            listarLeiloes();
            int idLeilao = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID do Leilão para ver os resultados:"));
            Leilao leilaoEscolhido = leilaoCrudService.buscarPorId(idLeilao);

            if (leilaoEscolhido == null) {
                JOptionPane.showMessageDialog(null, "Leilão não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 2. Montar o relatório de resultados
            ArrayList<Item> itensDoLeilao = leilaoEscolhido.getItens();
            if (itensDoLeilao.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Este leilão não possui itens!", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            StringBuilder relatorio = new StringBuilder("--- Resultado do Leilão " + leilaoEscolhido.getId() + " ---\n\n");

            for (Item item : itensDoLeilao) {
                relatorio.append("Item: ").append(item.getDescricao()).append("\n");
                relatorio.append("Valor Base: R$").append(item.getValorBase()).append("\n");

                // 3. Chamar o serviço para obter o lance vencedor
                Lance lanceVencedor = leilaoOperacaoService.obterLanceVencedor(item);

                if (lanceVencedor != null) {
                    relatorio.append("  Vencedor: ").append(lanceVencedor.getParticipante().getNome()).append("\n");
                    relatorio.append("  Lance Vencedor: R$").append(lanceVencedor.getValor()).append("\n");
                } else {
                    relatorio.append("  * Nenhum lance foi dado para este item.\n");
                }
                relatorio.append("--------------------------------------\n");
            }

            // 4. Exibir o relatório
            exibirEmAreaDeTexto(relatorio.toString(), "Resultado do Leilão");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID inválido. Por favor, use números.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // --- CRUD CATEGORIAS ---
    public static void exibirMenuCategorias() {
        String[] opcoes = {"Cadastrar", "Listar", "Editar", "Deletar", "Voltar"};
        int escolha;
        do {
            escolha = JOptionPane.showOptionDialog(null, "Gerenciar Categorias", "Sistema de Leilões",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);
            switch (escolha) {
                case 0: cadastrarCategoria(); break;
                case 1: listarCategorias(); break;
                case 2: editarCategoria(); break;
                case 3: deletarCategoria(); break;
            }
        } while (escolha != 4);
    }

    private static void cadastrarCategoria() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog("ID da Categoria:"));
            String nome = JOptionPane.showInputDialog("Nome da Categoria:");
            categoriaService.inserir(new Categoria(id, nome));
            JOptionPane.showMessageDialog(null, "Categoria cadastrada!");
        } catch (Exception e) { JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE); }
    }

    private static void listarCategorias() {
        ArrayList<Categoria> lista = categoriaService.listar();
        if(lista.isEmpty()) { JOptionPane.showMessageDialog(null, "Nenhuma categoria cadastrada."); return; }
        StringBuilder sb = new StringBuilder("Categorias:\n\n");
        for (Categoria c : lista) sb.append("ID: ").append(c.getId()).append(" | Nome: ").append(c.getNome()).append("\n");
        exibirEmAreaDeTexto(sb.toString(), "Lista de Categorias");
    }

    private static void editarCategoria() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog("ID da categoria a editar:"));
            if (categoriaService.buscarPorId(id) != null) {
                String nome = JOptionPane.showInputDialog("Novo nome:");
                categoriaService.editar(id, new Categoria(id, nome));
                JOptionPane.showMessageDialog(null, "Categoria atualizada!");
            } else { JOptionPane.showMessageDialog(null, "Categoria não encontrada."); }
        } catch (Exception e) { JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE); }
    }

    private static void deletarCategoria() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog("ID da categoria a deletar:"));
            categoriaService.deletar(id);
            JOptionPane.showMessageDialog(null, "Operação de deleção enviada.");
        } catch (Exception e) { JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE); }
    }


    // --- CRUD LEILOEIROS ---
    public static void exibirMenuLeiloeiros() {
        String[] opcoes = {"Cadastrar", "Listar", "Editar", "Deletar", "Voltar"};
        int escolha;
        do {
            escolha = JOptionPane.showOptionDialog(null, "Gerenciar Leiloeiros", "Sistema de Leilões",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);
            switch (escolha) {
                case 0: cadastrarLeiloeiro(); break;
                case 1: listarLeiloeiros(); break;
                case 2: editarLeiloeiro(); break;
                case 3: deletarLeiloeiro(); break;
            }
        } while (escolha != 4);
    }

    private static void cadastrarLeiloeiro() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog("ID do Leiloeiro:"));
            String nome = JOptionPane.showInputDialog("Nome do Leiloeiro:");
            String cpf = JOptionPane.showInputDialog("CPF do Leiloeiro:");
            leiloeiroService.inserir(new Leiloeiro(nome, cpf, id));
            JOptionPane.showMessageDialog(null, "Leiloeiro cadastrado!");
        } catch (Exception e) { JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE); }
    }

    private static void listarLeiloeiros() {
        ArrayList<Leiloeiro> lista = leiloeiroService.listar();
        if(lista.isEmpty()) { JOptionPane.showMessageDialog(null, "Nenhum leiloeiro cadastrado."); return; }
        StringBuilder sb = new StringBuilder("Leiloeiros:\n\n");
        for (Leiloeiro l : lista) sb.append("ID: ").append(l.getId()).append(" | Nome: ").append(l.getNome()).append(" | CPF: ").append(l.getCpf()).append("\n");
        exibirEmAreaDeTexto(sb.toString(), "Lista de Leiloeiros");
    }

    private static void editarLeiloeiro() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog("ID do leiloeiro a editar:"));
            Leiloeiro l = leiloeiroService.buscarPorId(id);
            if (l != null) {
                String nome = JOptionPane.showInputDialog("Novo nome:", l.getNome());
                String cpf = JOptionPane.showInputDialog("Novo CPF:", l.getCpf());
                leiloeiroService.editar(id, new Leiloeiro(nome, cpf, id));
                JOptionPane.showMessageDialog(null, "Leiloeiro atualizado!");
            } else { JOptionPane.showMessageDialog(null, "Leiloeiro não encontrado."); }
        } catch (Exception e) { JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE); }
    }

    private static void deletarLeiloeiro() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog("ID do leiloeiro a deletar:"));
            leiloeiroService.deletar(id);
            JOptionPane.showMessageDialog(null, "Operação de deleção enviada.");
        } catch (Exception e) { JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE); }
    }

    // --- CRUD PARTICIPANTES ---
    public static void exibirMenuParticipantes() {
        String[] opcoes = {"Cadastrar", "Listar", "Editar", "Deletar", "Voltar"};
        int escolha;
        do {
            escolha = JOptionPane.showOptionDialog(null, "Gerenciar Participantes", "Sistema de Leilões",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);
            switch (escolha) {
                case 0: cadastrarParticipante(); break;
                case 1: listarParticipantes(); break;
                case 2: editarParticipante(); break;
                case 3: deletarParticipante(); break;
            }
        } while (escolha != 4);
    }

    private static void cadastrarParticipante() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog("ID do Participante:"));
            String nome = JOptionPane.showInputDialog("Nome do Participante:");
            String cpf = JOptionPane.showInputDialog("CPF do Participante:");
            participanteService.inserir(new Participante(nome, cpf, id));
            JOptionPane.showMessageDialog(null, "Participante cadastrado!");
        } catch (Exception e) { JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE); }
    }

    private static void listarParticipantes() {
        ArrayList<Participante> lista = participanteService.listar();
        if(lista.isEmpty()) { JOptionPane.showMessageDialog(null, "Nenhum participante cadastrado."); return; }
        StringBuilder sb = new StringBuilder("Participantes:\n\n");
        for (Participante p : lista) sb.append("ID: ").append(p.getId()).append(" | Nome: ").append(p.getNome()).append(" | CPF: ").append(p.getCpf()).append("\n");
        exibirEmAreaDeTexto(sb.toString(), "Lista de Participantes");
    }

    private static void editarParticipante() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog("ID do participante a editar:"));
            Participante p = participanteService.buscarPorId(id);
            if (p != null) {
                String nome = JOptionPane.showInputDialog("Novo nome:", p.getNome());
                String cpf = JOptionPane.showInputDialog("Novo CPF:", p.getCpf());
                participanteService.editar(id, new Participante(nome, cpf, id));
                JOptionPane.showMessageDialog(null, "Participante atualizado!");
            } else { JOptionPane.showMessageDialog(null, "Participante não encontrado."); }
        } catch (Exception e) { JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE); }
    }

    private static void deletarParticipante() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog("ID do participante a deletar:"));
            participanteService.deletar(id);
            JOptionPane.showMessageDialog(null, "Operação de deleção enviada.");
        } catch (Exception e) { JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE); }
    }

    // --- MÉTODO UTILITÁRIO PARA EXIBIÇÃO ---
    private static void exibirEmAreaDeTexto(String texto, String titulo) {
        JTextArea textArea = new JTextArea(texto);
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        JOptionPane.showMessageDialog(null, scrollPane, titulo, JOptionPane.INFORMATION_MESSAGE);
    }
}