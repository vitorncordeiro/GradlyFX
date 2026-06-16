package br.pucpr.gradly.dao;
import java.io.*;
import java.util.ArrayList;

import br.pucpr.gradly.exception.GradlyException;
import br.pucpr.gradly.model.Coordenador;

public class CoordenadorDAO {
    private static final String ARQUIVO = "dados/coordenadores.dat";

    public void criar(Coordenador coordenador) throws GradlyException {
        ArrayList<Coordenador> coordenadores = lerArquivo();
        coordenador.setId(gerarNovoId(coordenadores));
        coordenadores.add(coordenador);
        salvarArquivo(coordenadores);
    }

    public ArrayList<Coordenador> ler() throws GradlyException {
        return lerArquivo();
    }

    public void editar(Coordenador coordenadorAtualizado) throws GradlyException {
        ArrayList<Coordenador> coordenadores = lerArquivo();
        for (int i = 0; i < coordenadores.size(); i++) {
            if (coordenadores.get(i).getId() == coordenadorAtualizado.getId()) {
                coordenadores.set(i, coordenadorAtualizado);
                salvarArquivo(coordenadores);
                return;
            }
        }
        throw new GradlyException("Coordenador não encontrado.");
    }

    public void excluir(int id) throws GradlyException {
        ArrayList<Coordenador> coordenadores = lerArquivo();
        boolean removido = coordenadores.removeIf(o -> o.getId() == id);
        if (!removido) {
            throw new GradlyException("Coordenador não encontrado.");
        }
        salvarArquivo(coordenadores);
    }

    private int gerarNovoId(ArrayList<Coordenador> coordenadores) {
        int maiorId = 0;
        for (Coordenador coordenador : coordenadores) {
            if (coordenador.getId() > maiorId) {
                maiorId = coordenador.getId();
            }
        }
        return maiorId + 1;
    }

    private ArrayList<Coordenador> lerArquivo() throws GradlyException {
        File arquivo = new File(ARQUIVO);
        if (!arquivo.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            Object objeto = ois.readObject();
            if (objeto instanceof ArrayList<?>) {
                ArrayList<Coordenador> coordenadores = new ArrayList<>();
                for (Object item : (ArrayList<?>) objeto) {
                    coordenadores.add((Coordenador) item);
                }
                return coordenadores;
            }
            throw new GradlyException("Arquivo inválido.");

        } catch (EOFException e) {
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            throw new GradlyException("Erro ao ler arquivo.");
        }
    }

    private void salvarArquivo(ArrayList<Coordenador> coordenadores) throws GradlyException {
        try {
            File pasta = new File("dados");
            if (!pasta.exists()) {
                pasta.mkdirs();
            }
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {
                oos.writeObject(coordenadores);
            }
        } catch (IOException e) {
            throw new GradlyException("Erro ao salvar arquivo.");
        }
    }
}