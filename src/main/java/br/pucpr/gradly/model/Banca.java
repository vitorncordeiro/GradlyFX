package br.pucpr.gradly.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Banca implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private LocalDate data;
    private String sala;
    private double nota;
    private String parecer;
    private int projetoId;

    public Banca() {
    }

    public Banca(int id, LocalDate data, String sala, double nota, String parecer, int projetoId) {
        this.id = id;
        this.data = data;
        this.sala = sala;
        this.nota = nota;
        this.parecer = parecer;
        this.projetoId = projetoId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public String getParecer() {
        return parecer;
    }

    public void setParecer(String parecer) {
        this.parecer = parecer;
    }

    public int getProjetoId() {
        return projetoId;
    }

    public void setProjetoId(int projetoId) {
        this.projetoId = projetoId;
    }
}
