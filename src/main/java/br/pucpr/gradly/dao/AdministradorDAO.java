package br.pucpr.gradly.dao;

import br.pucpr.gradly.model.Administrador;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AdministradorDAO {
    private static final String ARQUIVO = "dados/administradores.dat";

    @SuppressWarnings("unchecked")
    private List<Administrador> lerArquivo() {
        File arquivo = new File(ARQUIVO);
        if (!arquivo.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (List<Administrador>) ois.readObject();
        } catch (EOFException e) {
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Erro ao carregar administradores: ", e);
        }
    }

    private void gravarArquivo(List<Administrador> lista) {
        try {
            File pasta = new File("dados");
            if (!pasta.exists()) {
                pasta.mkdirs();
            }
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO));
            oos.writeObject(lista);
            oos.close();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar administradores", e);
        }
    }

    public void inserir(Administrador adm) {
        List<Administrador> lista = lerArquivo();
        adm.setId(gerarNovoId(lista));
        lista.add(adm);
        gravarArquivo(lista);
    }

    public List<Administrador> listarTodos() {
        return lerArquivo();
    }

    public void atualizar(Administrador admAtualizado) {
        List<Administrador> lista = lerArquivo();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == admAtualizado.getId()) {
                lista.set(i, admAtualizado);
                gravarArquivo(lista);
                return;
            }
        }
    }

    public void excluir(int id) {
        List<Administrador> lista = lerArquivo();
        lista.removeIf(a -> a.getId() == id);
        gravarArquivo(lista);
    }

    public List<Administrador> buscarPorNivel(int nivel) {
        List<Administrador> resultado = new ArrayList<>();
        for (Administrador a : lerArquivo()) {
            if (a.getNivelAcesso() == nivel) {
                resultado.add(a);
            }
        }
        return resultado;
    }

    private int gerarNovoId(List<Administrador> lista) {
        int maiorId = 0;
        for (Administrador a : lista) {
            if (a.getId() > maiorId) maiorId = a.getId();
        }
        return maiorId + 1;
    }
}
