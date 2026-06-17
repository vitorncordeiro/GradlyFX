package br.pucpr.gradly.dao;

import br.pucpr.gradly.model.Banca;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BancaDAO {
    private static final String ARQUIVO = "dados/bancas.dat";

    @SuppressWarnings("unchecked")
    private List<Banca> lerArquivo() {
        File arquivo = new File(ARQUIVO);
        if (!arquivo.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (List<Banca>) ois.readObject();
        } catch (EOFException e) {
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Erro ao carregar bancas: ", e);
        }
    }

    private void gravarArquivo(List<Banca> lista) {
        try {
            File pasta = new File("dados");
            if (!pasta.exists()) {
                pasta.mkdirs();
            }
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO));
            oos.writeObject(lista);
            oos.close();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar bancas", e);
        }
    }

    public void inserir(Banca banca) {
        List<Banca> lista = lerArquivo();
        banca.setId(gerarNovoId(lista));
        lista.add(banca);
        gravarArquivo(lista);
    }

    public List<Banca> listarTodos() {
        return lerArquivo();
    }

    public void atualizar(Banca bancaAtualizada) {
        List<Banca> lista = lerArquivo();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == bancaAtualizada.getId()) {
                lista.set(i, bancaAtualizada);
                gravarArquivo(lista);
                return;
            }
        }
    }

    public void excluir(int id) {
        List<Banca> lista = lerArquivo();
        lista.removeIf(b -> b.getId() == id);
        gravarArquivo(lista);
    }

    public List<Banca> buscarPorProjeto(int projetoId) {
        List<Banca> resultado = new ArrayList<>();
        for (Banca b : lerArquivo()) {
            if (b.getProjetoId() == projetoId) {
                resultado.add(b);
            }
        }
        return resultado;
    }

    private int gerarNovoId(List<Banca> lista) {
        int maiorId = 0;
        for (Banca b : lista) {
            if (b.getId() > maiorId) maiorId = b.getId();
        }
        return maiorId + 1;
    }
}
