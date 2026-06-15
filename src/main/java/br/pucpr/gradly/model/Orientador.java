package br.pucpr.gradly.model;

public class Orientador extends Usuario{
    private String areaAtuacao;
    private String titulo;

    public Orientador(int id, String nome, String email, String senha, String areaAtuacao, String titulo){
        super(id, nome, email, senha);
        this.areaAtuacao = areaAtuacao;
        this.titulo = titulo;
    }

    public String getAreaAtuacao() {
        return areaAtuacao;
    }

    public void setAreaAtuacao(String areaAtuacao) {
        this.areaAtuacao = areaAtuacao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
