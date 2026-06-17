package br.pucpr.gradly.model;

import java.io.Serializable;

public class Instituicao implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String nome;
    private String sigla;
    private String endereco;
    private String telefone;

    public Instituicao() {
    }

    public Instituicao(int id, String nome, String sigla, String endereco, String telefone) {
        this.id = id;
        this.nome = nome;
        this.sigla = sigla;
        this.endereco = endereco;
        this.telefone = telefone;
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

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
