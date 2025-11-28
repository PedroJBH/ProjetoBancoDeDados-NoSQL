package com.biblioteca;

public class Exemplar {
    private int id;
    private Livro livro;
    private String status;

    public Exemplar(int id, Livro livro, String status) {
        this.id = id;
        this.livro = livro;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Exemplar{" +
                "id=" + id +
                ", livro=" + livro.getTitulo() +
                ", status='" + status + '\'' +
                '}';
    }
}