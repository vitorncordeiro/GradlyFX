package br.pucpr.gradly.dao;
import java.io.*;
import java.util.ArrayList;

import br.pucpr.gradly.exception.GradlyException;
import br.pucpr.gradly.model.Orientador;

public class OrientadorDAO {
    private static final String ARQUIVO = "dados/orientadores.dat";

    public void criar(Orientador orientador) throws GradlyException {
        ArrayList<Orientador> orientadores = lerArquivo();
        orientador.setId(gerarNovoId(orientadores));
        orientadores.add(orientador);
        salvarArquivo(orientadores);
    }

    public ArrayList<Orientador> ler() throws GradlyException {
        return lerArquivo();
    }

    public void editar(Orientador orientadorAtualizado) throws GradlyException {
        ArrayList<Orientador> orientadores = lerArquivo();
        for (int i = 0; i < orientadores.size(); i++) {
            if (orientadores.get(i).getId() == orientadorAtualizado.getId()) {
                orientadores.set(i, orientadorAtualizado);
                salvarArquivo(orientadores);
                return;
            }
        }
        throw new GradlyException("Orientador não encontrado.");
    }

    public void excluir(int id) throws GradlyException {
        ArrayList<Orientador> orientadores = lerArquivo();
        boolean removido = orientadores.removeIf(o -> o.getId() == id);
        if (!removido) {
            throw new GradlyException("Orientador não encontrado.");
        }
        salvarArquivo(orientadores);
    }

    private int gerarNovoId(ArrayList<Orientador> orientadores) {
        int maiorId = 0;
        for (Orientador orientador : orientadores) {
            if (orientador.getId() > maiorId) {
                maiorId = orientador.getId();
            }
        }
        return maiorId + 1;
    }

    private ArrayList<Orientador> lerArquivo() throws GradlyException {
        File arquivo = new File(ARQUIVO);
        if (!arquivo.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            Object objeto = ois.readObject();
            if (objeto instanceof ArrayList<?>) {
                ArrayList<Orientador> orientadores = new ArrayList<>();
                for (Object item : (ArrayList<?>) objeto) {
                    orientadores.add((Orientador) item);
                }
                return orientadores;
            }
            throw new GradlyException("Arquivo inválido.");

        } catch (EOFException e) {
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            throw new GradlyException("Erro ao ler arquivo.");
        }
    }

    private void salvarArquivo(ArrayList<Orientador> orientadores) throws GradlyException {
        try {
            File pasta = new File("dados");
            if (!pasta.exists()) {
                pasta.mkdirs();
            }
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {
                oos.writeObject(orientadores);
            }
        } catch (IOException e) {
            throw new GradlyException("Erro ao salvar arquivo.");
        }
    }
}