package br.pucpr.gradly.dao;

import br.pucpr.gradly.model.Grupo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GrupoDAO {
    private static final String ARQUIVO = "dados/grupos.dat";

    //Leitura & Gravado no arquivo
    private List<Grupo> lerArquivo(){
        File arquivo = new File(ARQUIVO);

        if (!arquivo.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (List<Grupo>) ois.readObject();
        } catch (EOFException e) {
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Erro no carregamento de grupos: ", e);
        }
    }
    private void gravarArquivo(List<Grupo> grupo){
        try {
            File pasta = new File ("grupo");
            if (!pasta.exists()) {
                pasta.mkdirs();
            }
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO));
            oos.writeObject(grupo);
            oos.close();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar grupos", e);
        }
    }

    //Create
    public void inserir(Grupo grupo) {
        List<Grupo> grupos = lerArquivo();
        grupo.setId(gerarNovoId(grupos));
        grupos.add(grupo);
        gravarArquivo(grupos);
    }

    //Read
    public List<Grupo> listarTodos(){ return lerArquivo(); }

    //Update
    public void atualizar(Grupo grupoAtualizado) {

        List<Grupo> grupos = lerArquivo();

        for (int i = 0; i < grupos.size(); i++) {

            Grupo grupo = grupos.get(i);
            if (grupo.getId() == grupoAtualizado.getId()) {

                grupos.set(i, grupoAtualizado);
                gravarArquivo(grupos);
                return;
            }
        }
    }

    //Delete
    public void excluir(int id) {
        List<Grupo> grupos = lerArquivo();
        grupos.removeIf(t -> t.getId() == id);
        gravarArquivo(grupos);
    }

    //Atribuindo id automatico;
    private int gerarNovoId(List<Grupo> grupos){
        int maiorId = 0;

        for (Grupo grupo : grupos) {
            if (grupo.getId() > maiorId) {
                maiorId = grupo.getId();
            }
        }
        return maiorId + 1;
    }
}
