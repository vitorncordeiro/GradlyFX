package br.pucpr.gradly.model;

import java.io.Serializable;

public class Aluno implements Serializable {

    private static final long serialVersionUID = 1L;

    //Atributos
    private int id;
    private int matricula;
    private String curso;
    private String nome;
    private String email;
    private String senha;

    //Constutores
    public Aluno(){
    }
    public Aluno(
            int matricula,
            String curso) {
        this.matricula = matricula;
        this.curso = curso;
    }

    //Setters & Getters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getMatricula() {
        return matricula;
    }
    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public String getCurso() {
        return curso;
    }
    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }

}
