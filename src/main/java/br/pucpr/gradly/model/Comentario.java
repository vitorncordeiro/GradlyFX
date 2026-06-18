package br.pucpr.gradly.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Comentario implements Serializable {

    private int id;
    private String texto;
    private String autor;
    private LocalDate dataCriacao;
    private int idDocumento;

    public Comentario() {
    }

    public Comentario(int id, String texto, String autor, LocalDate dataCriacao, int idDocumento) {
        this.id = id;
        this.texto = texto;
        this.autor = autor;
        this.dataCriacao = dataCriacao;
        this.idDocumento = idDocumento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public int getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(int idDocumento) {
        this.idDocumento = idDocumento;
    }
}
