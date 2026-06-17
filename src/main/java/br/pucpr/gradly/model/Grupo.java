package br.pucpr.gradly.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Grupo implements Serializable{

    private static final long serialVersionUID = 1L;

    //Atributos
    private int id;
    private String nome;
    private String descricao;
    private LocalDate datacriacao;

    //Constutores
    public Grupo(){
    }
    public Grupo(
            String nome,
            String descricao,
            LocalDate datacriacao) {
        this.nome = nome;
        this.descricao = descricao;
        this.datacriacao = datacriacao;
    }

    //Setters e Getters
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

    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDatacriacao() {
        return datacriacao;
    }
    public void setDatacriacao(LocalDate datacriacao) {
        this.datacriacao = datacriacao;
    }


}
