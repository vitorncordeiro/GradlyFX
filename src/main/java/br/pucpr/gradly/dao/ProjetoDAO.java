package br.pucpr.gradly.dao;

import br.pucpr.gradly.model.Projeto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProjetoDAO {
    private static final String ARQUIVO =
            "dados/projetos.dat";

    private List<Projeto> lerArquivo() {

        File arquivo = new File(ARQUIVO);

        if (!arquivo.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (List<Projeto>) ois.readObject();
        } catch (EOFException e) {
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Erro ao carregar projetos: ", e);
        }
    }

    private void gravarArquivo(
            List<Projeto> projetos) {

        try {
            File pasta = new File("dados");
            if (!pasta.exists()) {
                pasta.mkdirs();
            }
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO));
            oos.writeObject(projetos);
            oos.close();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar projetos", e);
        }
    }

    public void inserir(Projeto projeto) {
        List<Projeto> projetos = lerArquivo();
        projeto.setId(gerarNovoId(projetos));
        projetos.add(projeto);
        gravarArquivo(projetos);
    }

    public List<Projeto> listarTodos() {
        return lerArquivo();
    }

    public void atualizar(Projeto projetoAtualizado) {

        List<Projeto> projetos = lerArquivo();

        for (int i = 0; i < projetos.size(); i++) {

            Projeto projeto = projetos.get(i);
            if (projeto.getId() == projetoAtualizado.getId()) {

                projetos.set(i, projetoAtualizado);
                gravarArquivo(projetos);
                return;
            }
        }
    }


    public void excluir(int id) {
        List<Projeto> projetos = lerArquivo();
        projetos.removeIf(p -> p.getId() == id);
        gravarArquivo(projetos);
    }

    public List<Projeto> buscarPorEstado(String estado) {

        List<Projeto> resultado = new ArrayList<>();

        for (Projeto projeto : lerArquivo()) {
            if (projeto.getEstado().equalsIgnoreCase(estado)) {

                resultado.add(projeto);
            }
        }

        return resultado;
    }

    public void alterarEstado(int id, String novoEstado) {

        List<Projeto> projetos = lerArquivo();

        for (Projeto projeto : projetos) {

            if (projeto.getId() == id) {
                projeto.setEstado(novoEstado);
                gravarArquivo(projetos);
                return;
            }
        }
    }

    private int gerarNovoId(List<Projeto> projetos) {

        int maiorId = 0;

        for (Projeto projeto : projetos) {
            if (projeto.getId() > maiorId) {
                maiorId = projeto.getId();
            }
        }
        return maiorId + 1;
    }
}
