package br.pucpr.gradly.dao;

import br.pucpr.gradly.exception.GradlyException;
import br.pucpr.gradly.model.Documento;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DocumentoDAO {

    private static final String ARQUIVO = "dados/documentos.dat";

    public void inserir(Documento d) throws GradlyException {
        List<Documento> lista = listarTodos();
        d.setId(gerarNovoId(lista));
        lista.add(d);
        salvarArquivo(lista);
    }

    public List<Documento> listarTodos() throws GradlyException {
        return lerArquivo();
    }

    public void salvarNovaVersao(Documento d) throws GradlyException {
        List<Documento> lista = listarTodos();
        int maiorVersao = 0;
        for (Documento doc : lista) {
            if (doc.getTitulo().equalsIgnoreCase(d.getTitulo()) && doc.getVersao() > maiorVersao) {
                maiorVersao = doc.getVersao();
            }
        }
        d.setVersao(maiorVersao + 1);
        inserir(d);
    }

    public void atualizar(Documento d) throws GradlyException {
        List<Documento> lista = listarTodos();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == d.getId()) {
                lista.set(i, d);
                salvarArquivo(lista);
                return;
            }
        }
        throw new GradlyException("Documento não encontrado.");
    }

    public void excluir(int id) throws GradlyException {
        List<Documento> lista = listarTodos();
        boolean removido = lista.removeIf(doc -> doc.getId() == id);
        if (!removido) {
            throw new GradlyException("Documento não encontrado.");
        }
        salvarArquivo(lista);
    }

    private int gerarNovoId(List<Documento> lista) {
        int maiorId = 0;
        for (Documento d : lista) {
            if (d.getId() > maiorId) {
                maiorId = d.getId();
            }
        }
        return maiorId + 1;
    }

    private List<Documento> lerArquivo() throws GradlyException {
        File arquivo = new File(ARQUIVO);
        if (!arquivo.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            Object objeto = ois.readObject();
            if (objeto instanceof ArrayList<?>) {
                List<Documento> documentos = new ArrayList<>();
                for (Object item : (ArrayList<?>) objeto) {
                    documentos.add((Documento) item);
                }
                return documentos;
            }
            throw new GradlyException("Arquivo inválido.");
        } catch (EOFException e) {
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            throw new GradlyException("Erro ao ler arquivo de documentos.");
        }
    }

    private void salvarArquivo(List<Documento> lista) throws GradlyException {
        try {
            File pasta = new File("dados");
            if (!pasta.exists()) {
                pasta.mkdirs();
            }
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {
                oos.writeObject(new ArrayList<>(lista));
            }
        } catch (IOException e) {
            throw new GradlyException("Erro ao salvar arquivo de documentos.");
        }
    }
}
