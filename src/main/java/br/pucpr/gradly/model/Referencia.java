package br.pucpr.gradly.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Referencia implements Serializable {
    private int id;
    private String titulo;
    private String autor;
    private int ano;
    private String tipo;

    public Referencia(){

    }

    public Referencia(
            int id,
            String titulo,
            String autor,
            int ano,
            String tipo){

        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.ano = ano;
        this.tipo = tipo;
    }

    public int getId(){
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo){
        this.titulo = titulo;
    }

    public String getAutor(){
        return autor;
    }

    public void setAutor(String autor){
        this.autor = autor;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
