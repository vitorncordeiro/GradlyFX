package br.pucpr.gradly.dao;

import br.pucpr.gradly.exception.GradlyException;
import br.pucpr.gradly.model.Comentario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ComentarioDAO {

    private static final String ARQUIVO = "dados/comentarios.dat";

    public void inserir(Comentario c) throws GradlyException {
        List<Comentario> lista = listarTodos();
        c.setId(gerarNovoId(lista));
        lista.add(c);
        salvarArquivo(lista);
    }

    public List<Comentario> listarTodos() throws GradlyException {
        return lerArquivo();
    }

    public void atualizar(Comentario c) throws GradlyException {
        List<Comentario> lista = listarTodos();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == c.getId()) {
                lista.set(i, c);
                salvarArquivo(lista);
                return;
            }
        }
        throw new GradlyException("Comentário não encontrado.");
    }

    public void excluir(int id) throws GradlyException {
        List<Comentario> lista = listarTodos();
        boolean removido = lista.removeIf(c -> c.getId() == id);
        if (!removido) {
            throw new GradlyException("Comentário não encontrado.");
        }
        salvarArquivo(lista);
    }

    private int gerarNovoId(List<Comentario> lista) {
        int maiorId = 0;
        for (Comentario c : lista) {
            if (c.getId() > maiorId) {
                maiorId = c.getId();
            }
        }
        return maiorId + 1;
    }

    private List<Comentario> lerArquivo() throws GradlyException {
        File arquivo = new File(ARQUIVO);
        if (!arquivo.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            Object objeto = ois.readObject();
            if (objeto instanceof ArrayList<?>) {
                List<Comentario> comentarios = new ArrayList<>();
                for (Object item : (ArrayList<?>) objeto) {
                    comentarios.add((Comentario) item);
                }
                return comentarios;
            }
            throw new GradlyException("Arquivo inválido.");
        } catch (EOFException e) {
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            throw new GradlyException("Erro ao ler arquivo de comentários.");
        }
    }

    private void salvarArquivo(List<Comentario> lista) throws GradlyException {
        try {
            File pasta = new File("dados");
            if (!pasta.exists()) {
                pasta.mkdirs();
            }
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {
                oos.writeObject(new ArrayList<>(lista));
            }
        } catch (IOException e) {
            throw new GradlyException("Erro ao salvar arquivo de comentários.");
        }
    }
}
