package br.pucpr.gradly.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Documento implements Serializable {

    private int id;
    private String titulo;
    private String conteudo;
    private int versao;
    private LocalDate dataCriacao;

    public Documento() {
    }

    public Documento(int id, String titulo, String conteudo, int versao, LocalDate dataCriacao) {
        this.id = id;
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.versao = versao;
        this.dataCriacao = dataCriacao;
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

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public int getVersao() {
        return versao;
    }

    public void setVersao(int versao) {
        this.versao = versao;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
