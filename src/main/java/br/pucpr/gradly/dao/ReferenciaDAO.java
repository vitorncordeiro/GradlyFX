package br.pucpr.gradly.dao;



import br.pucpr.gradly.model.Referencia;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReferenciaDAO {
    private static final String ARQUIVO =
            "dados/referencia.dat";

    private List<Referencia> lerArquivo(){
        File arquivo = new File(ARQUIVO);

        if (!arquivo.exists()){
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (List<Referencia>) ois.readObject();
        } catch (EOFException e) {
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Erro ao carregar projetos: ", e);
        }
    }

    private void gravarArquivo(List<Referencia> referencia){
        try {
            File pasta = new File ("dados");
            if (!pasta.exists()) {
                pasta.mkdirs();
            }
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO));
            oos.writeObject(referencia);
            oos.close();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar projetos", e);
        }
    }

    public void inserir(Referencia referencia) {
        List<Referencia> referencias = lerArquivo();
        referencia.setId(gerarNovoId(referencias));
        referencias.add(referencia);
        gravarArquivo(referencias);
    }

    public List<Referencia> listarTodos(){ return lerArquivo(); }

    public void atualizar(Referencia tarefaAtualizada) {

        List<Referencia> tarefas = lerArquivo();

        for (int i = 0; i < tarefas.size(); i++) {

            Referencia referencia = tarefas.get(i);
            if (referencia.getId() == tarefaAtualizada.getId()) {

                tarefas.set(i, tarefaAtualizada);
                gravarArquivo(tarefas);
                return;
            }
        }
    }

    public void excluir(int id) {
        List<Referencia> referencias = lerArquivo();
        referencias.removeIf(t -> t.getId() == id);
        gravarArquivo(referencias);
    }

    public List<Referencia> buscarPorAutor(String estado) {

        List<Referencia> resultado = new ArrayList<>();

        for (Referencia referencia : lerArquivo()) {
            if (referencia.getAutor() != null &&
                    referencia.getAutor().equalsIgnoreCase(estado)) {

                resultado.add(referencia);
            }
        }

        return resultado;
    }

    public List<Referencia> buscarPorTipo(String estado) {

        List<Referencia> resultado = new ArrayList<>();

        for (Referencia referencia : lerArquivo()) {
            if (referencia.getTipo() != null &&
                    referencia.getTipo().equalsIgnoreCase(estado)) {

                resultado.add(referencia);
            }
        }

        return resultado;
    }

    private int gerarNovoId(List<Referencia> referencias){
        int maiorId = 0;

        for (Referencia referencia : referencias) {
            if (referencia.getId() > maiorId) {
                maiorId = referencia.getId();
            }
        }
        return maiorId + 1;
    }
}

