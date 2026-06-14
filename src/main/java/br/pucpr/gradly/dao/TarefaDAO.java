package br.pucpr.gradly.dao;

import br.pucpr.gradly.model.Tarefa;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TarefaDAO {
    private static final String ARQUIVO =
            "dados/tarefa.dat";

    private List<Tarefa> lerArquivo(){
        File arquivo = new File(ARQUIVO);

        if (!arquivo.exists()){
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (List<Tarefa>) ois.readObject();
        } catch (EOFException e) {
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Erro ao carregar projetos: ", e);
        }
    }

    private void gravarArquivo(List<Tarefa> tarefa){
        try {
            File pasta = new File ("dados");
            if (!pasta.exists()) {
                pasta.mkdirs();
            }
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO));
            oos.writeObject(tarefa);
            oos.close();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar projetos", e);
        }
    }

    public void inserir(Tarefa tarefa) {
        List<Tarefa> tarefas = lerArquivo();
        tarefa.setId(gerarNovoId(tarefas));
        tarefas.add(tarefa);
        gravarArquivo(tarefas);
    }

    public List<Tarefa> listarTodos(){ return lerArquivo(); }

    public void atualizar(Tarefa tarefaAtualizada) {

        List<Tarefa> tarefas = lerArquivo();

        for (int i = 0; i < tarefas.size(); i++) {

            Tarefa tarefa = tarefas.get(i);
            if (tarefa.getId() == tarefaAtualizada.getId()) {

                tarefas.set(i, tarefaAtualizada);
                gravarArquivo(tarefas);
                return;
            }
        }
    }

    public void excluir(int id) {
        List<Tarefa> tarefas = lerArquivo();
        tarefas.removeIf(t -> t.getId() == id);
        gravarArquivo(tarefas);
    }

    public List<Tarefa> buscarPorEstado(String estado) {

        List<Tarefa> resultado = new ArrayList<>();

        for (Tarefa tarefa : lerArquivo()) {
            if (tarefa.getEstado() != null &&
                    tarefa.getEstado().equalsIgnoreCase(estado)) {

                resultado.add(tarefa);
            }
        }

        return resultado;
    }

    public void alterarEstado(int id, String novoEstado) {

        List<Tarefa> tarefas = lerArquivo();

        for (Tarefa tarefa : tarefas) {

            if (tarefa.getId() == id) {
                tarefa.setEstado(novoEstado);
                gravarArquivo(tarefas);
                return;
            }
        }
    }

    private int gerarNovoId(List<Tarefa> tarefas){
        int maiorId = 0;

        for (Tarefa tarefa : tarefas) {
            if (tarefa.getId() > maiorId) {
                maiorId = tarefa.getId();
            }
        }
        return maiorId + 1;
    }
}
