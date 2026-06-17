package br.pucpr.gradly.dao;

import br.pucpr.gradly.model.Instituicao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class InstituicaoDAO {
    private static final String ARQUIVO =
            "dados/instituicoes.dat";

    private List<Instituicao> lerArquivo() {

        File arquivo = new File(ARQUIVO);

        if (!arquivo.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (List<Instituicao>) ois.readObject();
        } catch (EOFException e) {
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Erro ao carregar instituições: ", e);
        }
    }

    private void gravarArquivo(
            List<Instituicao> instituicoes) {

        try {
            File pasta = new File("dados");
            if (!pasta.exists()) {
                pasta.mkdirs();
            }
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO));
            oos.writeObject(instituicoes);
            oos.close();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar instituições", e);
        }
    }

    public void inserir(Instituicao instituicao) {
        List<Instituicao> instituicoes = lerArquivo();
        instituicao.setId(gerarNovoId(instituicoes));
        instituicoes.add(instituicao);
        gravarArquivo(instituicoes);
    }

    public List<Instituicao> listarTodos() {
        return lerArquivo();
    }

    public void atualizar(Instituicao instituicaoAtualizada) {

        List<Instituicao> instituicoes = lerArquivo();

        for (int i = 0; i < instituicoes.size(); i++) {

            Instituicao instituicao = instituicoes.get(i);
            if (instituicao.getId() == instituicaoAtualizada.getId()) {

                instituicoes.set(i, instituicaoAtualizada);
                gravarArquivo(instituicoes);
                return;
            }
        }
    }


    public void excluir(int id) {
        List<Instituicao> instituicoes = lerArquivo();
        instituicoes.removeIf(i -> i.getId() == id);
        gravarArquivo(instituicoes);
    }

    private int gerarNovoId(List<Instituicao> instituicoes) {

        int maiorId = 0;

        for (Instituicao instituicao : instituicoes) {
            if (instituicao.getId() > maiorId) {
                maiorId = instituicao.getId();
            }
        }
        return maiorId + 1;
    }
}
