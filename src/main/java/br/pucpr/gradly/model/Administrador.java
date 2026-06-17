package br.pucpr.gradly.model;

public class Administrador extends Usuario {
    private int nivelAcesso;

    public Administrador() {
        super(0, "", "", "");
    }

    public Administrador(int id, String nome, String email, String senha, int nivelAcesso) {
        super(id, nome, email, senha);
        this.nivelAcesso = nivelAcesso;
    }

    public int getNivelAcesso() {
        return nivelAcesso;
    }

    public void setNivelAcesso(int nivelAcesso) {
        this.nivelAcesso = nivelAcesso;
    }
}
