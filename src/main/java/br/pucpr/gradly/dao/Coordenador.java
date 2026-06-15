package br.pucpr.gradly.dao;

import br.pucpr.gradly.model.Usuario;

public class Coordenador extends Usuario {
    private String departamento;
    private String instituicao;

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(String instituicao) {
        this.instituicao = instituicao;
    }

    public Coordenador(int id, String nome, String email, String senha, String departamento, String instituicao){
        super(id, nome, email, senha);
        this.departamento = departamento;
        this.instituicao = instituicao;
    }
}
