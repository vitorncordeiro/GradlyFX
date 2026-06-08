package br.pucpr.gradly.model;

import java.io.Serializable;

public class Projeto implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String titulo;
    private String descricao;
    private String objetivo;
    private String temas;
    private String estado;

    public Projeto() {
    }

    public Projeto(
            int id,
            String titulo,
            String descricao,
            String objetivo,
            String temas,
            String estado) {

        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.objetivo = objetivo;
        this.temas = temas;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getTemas() {
        return temas;
    }

    public void setTemas(String temas) {
        this.temas = temas;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
