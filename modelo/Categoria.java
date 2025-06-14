package leilao.modelo;

import java.io.Serializable;

public class Categoria implements Serializable {

    private int id;
    private String nome;

    public Categoria(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        // Isso ajuda a exibir o nome da categoria facilmente em listagens.
        return nome;
    }
}