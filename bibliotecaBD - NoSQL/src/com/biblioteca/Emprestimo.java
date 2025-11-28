package com.biblioteca;

import java.sql.Date; 

public class Emprestimo {
    private int id;
    private int idExemplar; 
    private int idUsuario;
    private Date dataEmprestimo;
    private Date dataDevolucaoPrevista;
    private Date dataDevolucaoReal;

    public Emprestimo(int id, int idExemplar, int idUsuario, Date dataEmprestimo, Date dataDevolucaoPrevista, Date dataDevolucaoReal) {
        this.id = id;
        this.idExemplar = idExemplar;
        this.idUsuario = idUsuario;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
        this.dataDevolucaoReal = dataDevolucaoReal;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdExemplar() { return idExemplar; }
    public void setIdExemplar(int idExemplar) { this.idExemplar = idExemplar; }
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
    public Date getDataEmprestimo() { return dataEmprestimo; }
    public void setDataEmprestimo(Date dataEmprestimo) { this.dataEmprestimo = dataEmprestimo; }
    public Date getDataDevolucaoPrevista() { return dataDevolucaoPrevista; }
    public void setDataDevolucaoPrevista(Date dataDevolucaoPrevista) { this.dataDevolucaoPrevista = dataDevolucaoPrevista; }
    public Date getDataDevolucaoReal() { return dataDevolucaoReal; }
    public void setDataDevolucaoReal(Date dataDevolucaoReal) { this.dataDevolucaoReal = dataDevolucaoReal; }

    @Override
    public String toString() {
        return "Emprestimo{" +
               "id=" + id +
               ", idExemplar=" + idExemplar +
               ", idUsuario=" + idUsuario +
               ", dataEmprestimo=" + dataEmprestimo +
               ", dataDevolucaoPrevista=" + dataDevolucaoPrevista +
               ", dataDevolucaoReal=" + dataDevolucaoReal +
               '}';
    }
}