package com.biblioteca;

import java.sql.Date;

public class Reserva {
    private int id;
    private Livro livro;
    private Usuario usuario;
    private Date dataReserva;

    public Reserva(int id, Livro livro, Usuario usuario, Date dataReserva) {
        this.id = id;
        this.livro = livro;
        this.usuario = usuario;
        this.dataReserva = dataReserva;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Livro getLivro() { return livro; }
    public void setLivro(Livro livro) { this.livro = livro; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public Date getDataReserva() { return dataReserva; }
    public void setDataReserva(Date dataReserva) { this.dataReserva = dataReserva; }

    @Override
    public String toString() {
        return "Reserva{" +
               "id=" + id +
               ", livro='" + livro.getTitulo() + '\'' +
               ", usuario='" + usuario.getNome() + '\'' +
               ", dataReserva=" + dataReserva +
               '}';
    }
}