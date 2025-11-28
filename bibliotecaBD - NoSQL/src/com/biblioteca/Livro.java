package com.biblioteca;

import java.util.List;
import java.util.stream.Collectors;

public class Livro {
    private int id;
    private String titulo;
    private int anoPublicacao;
    private Editora editora; 
    private Categoria categoria;
    private List<Autor> autores; 

    public Livro(int id, String titulo, int anoPublicacao, Editora editora, Categoria categoria) {
        this.id = id;
        this.titulo = titulo;
        this.anoPublicacao = anoPublicacao;
        this.editora = editora;
        this.categoria = categoria;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public int getAnoPublicacao() { return anoPublicacao; }
    public void setAnoPublicacao(int anoPublicacao) { this.anoPublicacao = anoPublicacao; }
    public Editora getEditora() { return editora; } 
    public void setEditora(Editora editora) { this.editora = editora; } 
    public Categoria getCategoria() { return categoria; } 
    public void setCategoria(Categoria categoria) { this.categoria = categoria; } 
    public List<Autor> getAutores() { return autores; }
    public void setAutores(List<Autor> autores) { this.autores = autores; }
    
    public String getAutoresAsString() {
        if (autores == null || autores.isEmpty()) {
            return "N/A";
        }
        return autores.stream()
                .map(Autor::getNome)
                .collect(Collectors.joining(", "));
    }

    @Override
    public String toString() {
        String editoraNome = (editora != null) ? editora.getNome() : "N/A";
        String categoriaNome = (categoria != null) ? categoria.getNome() : "N/A";

        return "Livro{" +
               "id=" + id +
               ", titulo='" + titulo + '\'' +
               ", anoPublicacao=" + anoPublicacao +
               ", editora='" + editoraNome + '\'' +
               ", categoria='" + categoriaNome + '\'' +
               ", autores='" + getAutoresAsString() + '\'' + 
               '}';
    }
}