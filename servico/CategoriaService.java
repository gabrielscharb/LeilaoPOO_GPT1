package leilao.servico;

import leilao.interfaces.ICrud;
import leilao.modelo.Categoria;
import java.util.ArrayList;

// 1. A classe implementa ICrud, especificando que o tipo <T> é Categoria.
public class CategoriaService implements ICrud<Categoria> {

    // 2. A lista de dados agora é um atributo desta classe.
    private ArrayList<Categoria> categorias;

    // 3. O construtor recebe a lista de categorias. Isso permite que
    //    o Menu carregue os dados do arquivo e "injete" aqui.
    public CategoriaService(ArrayList<Categoria> categorias) {
        this.categorias = categorias;
    }

    // 4. Implementação dos métodos obrigatórios da interface ICrud.

    @Override
    public void inserir(Categoria categoria) {
        // A lógica de negócio (adicionar à lista) fica aqui.
        this.categorias.add(categoria);
    }

    @Override
    public void editar(int id, Categoria categoriaAtualizada) {
        // Primeiro, buscamos o objeto que queremos editar.
        Categoria categoriaExistente = this.buscarPorId(id);

        // Se ele existe, atualizamos seus dados.
        if (categoriaExistente != null) {
            categoriaExistente.setNome(categoriaAtualizada.getNome());
        }
    }

    @Override
    public void deletar(int id) {
        // Usamos o método buscarPorId para encontrar o objeto e removê-lo.
        Categoria categoriaParaDeletar = this.buscarPorId(id);
        if (categoriaParaDeletar != null) {
            this.categorias.remove(categoriaParaDeletar);
        }
    }

    @Override
    public ArrayList<Categoria> listar() {
        // Simplesmente retorna a lista que a classe gerencia.
        return this.categorias;
    }

    @Override
    public Categoria buscarPorId(int id) {
        // Percorre a lista para encontrar uma categoria com o ID correspondente.
        for (Categoria cat : this.categorias) {
            if (cat.getId() == id) {
                return cat;
            }
        }
        return null; // Retorna null se não encontrar.
    }
}