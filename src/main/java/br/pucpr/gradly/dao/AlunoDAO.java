package br.pucpr.gradly.dao;

import br.pucpr.gradly.model.Aluno;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {
    private static final String ARQUIVO = "dados/alunos.dat";

    //Leitura & Gravado do arquivo
    private List<Aluno> lerArquivo(){
        File arquivo = new File(ARQUIVO);

        if (!arquivo.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (List<Aluno>) ois.readObject();
        } catch (EOFException e) {
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Erro no carregamento de alunos: ", e);
        }
    }
    private void gravarArquivo(List<Aluno> aluno){
        try {
            File pasta = new File ("aluno");
            if (!pasta.exists()) {
                pasta.mkdirs();
            }
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO));
            oos.writeObject(aluno);
            oos.close();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar alunos", e);
        }
    }


    //Create
    public void inserir(Aluno aluno) {
        List<Aluno> alunos = lerArquivo();
        aluno.setId(gerarNovoId(alunos));
        alunos.add(aluno);
        gravarArquivo(alunos);
    }

    //Read
    public List<Aluno> listarTodos(){ return lerArquivo(); }

    //Update
    public void atualizar(Aluno alunoAtualizado) {

        List<Aluno> alunos = lerArquivo();

        for (int i = 0; i < alunos.size(); i++) {

            Aluno aluno = alunos.get(i);
            if (aluno.getId() == alunoAtualizado.getId()) {

                alunos.set(i, alunoAtualizado);
                gravarArquivo(alunos);
                return;
            }
        }
    }

    //Delete
    public void excluir(int id) {
        List<Aluno> alunos = lerArquivo();
        alunos.removeIf(t -> t.getId() == id);
        gravarArquivo(alunos);
    }

    private int gerarNovoId(List<Aluno> alunos){
        int maiorId = 0;

        for (Aluno aluno : alunos) {
            if (aluno.getId() > maiorId) {
                maiorId = aluno.getId();
            }
        }
        return maiorId + 1;
    }
}
