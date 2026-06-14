package br.pucpr.gradly.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Tarefa implements Serializable{
    private int id;
    private String descricao;
    private String estado;
    private LocalDate dataInicio;
    private LocalDate dataFim;

    public Tarefa(){

    }

    public Tarefa(
            int id,
            String descricao,
            String estado,
            LocalDate dataInicio,
            LocalDate dataFim){

        this.id = id;
        this.descricao = descricao;
        this.estado = estado;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    public int getId(){
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao){
        this.descricao = descricao;
    }

    public String getEstado(){
        return estado;
    }

    public void setEstado(String estado){
        this.estado = estado;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }
}
