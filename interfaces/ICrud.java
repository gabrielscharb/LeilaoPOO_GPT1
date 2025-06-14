package leilao.interfaces;

import java.util.ArrayList;

// 1. Adicionamos <T> para tornar a interface genérica.
//    "T" é um marcador de posição que será substituído por Categoria, Leiloeiro, etc.
public interface ICrud<T> {

    // 2. O método agora recebe um objeto do tipo T para inserir.
    void inserir(T objeto);

    // 3. Para editar, precisamos do ID do objeto e dos novos dados (em outro objeto T).
    void editar(int id, T objeto);

    // 4. Para deletar, só precisamos do ID.
    void deletar(int id);

    // 5. O método listar agora retorna uma lista de objetos do tipo T.
    ArrayList<T> listar();

    // 6. Adicionamos um método extra, muito útil para buscar um objeto específico.
    T buscarPorId(int id);
}