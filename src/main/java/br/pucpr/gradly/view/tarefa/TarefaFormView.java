package br.pucpr.gradly.view.tarefa;

import br.pucpr.gradly.dao.TarefaDAO;
import br.pucpr.gradly.model.Tarefa;
import br.pucpr.gradly.view.MainMenuView;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class TarefaFormView {

    private Tarefa tarefa;

    public TarefaFormView() {
    }

    public TarefaFormView(Tarefa tarefa) {
        this.tarefa = tarefa;
    }

    public void mostrar() {

        Stage stage = new Stage();

        Label lblDescricao = new Label("Descrição");
        TextField txtDescricao = new TextField();

        Label lblEstado = new Label("Estado");

        ComboBox<String> cbEstado = new ComboBox<>();

        cbEstado.getItems().addAll(
                "A Fazer",
                "Fazendo",
                "Concluída"
        );

        Label lblInicio = new Label("Data Início");
        DatePicker dpInicio = new DatePicker();

        Label lblFim = new Label("Data Fim");
        DatePicker dpFim = new DatePicker();

        Button btnSalvar = new Button("Salvar");

        Button btnCancelar = new Button("Cancelar");

        btnCancelar.setOnAction(e -> {
            stage.close();
        });

        // Se estiver editando, preenche os campos
        if (tarefa != null) {

            txtDescricao.setText(
                    tarefa.getDescricao()
            );

            cbEstado.setValue(
                    tarefa.getEstado()
            );

            dpInicio.setValue(
                    tarefa.getDataInicio()
            );

            dpFim.setValue(
                    tarefa.getDataFim()
            );
        }

        btnSalvar.setOnAction(e -> {

            TarefaDAO dao = new TarefaDAO();

            // Cadastro
            if (tarefa == null) {

                Tarefa novaTarefa = new Tarefa();

                novaTarefa.setDescricao(
                        txtDescricao.getText()
                );

                novaTarefa.setEstado(
                        cbEstado.getValue()
                );

                novaTarefa.setDataInicio(
                        dpInicio.getValue()
                );

                novaTarefa.setDataFim(
                        dpFim.getValue()
                );

                dao.inserir(novaTarefa);

            }
            // Edição
            else {

                tarefa.setDescricao(
                        txtDescricao.getText()
                );

                tarefa.setEstado(
                        cbEstado.getValue()
                );

                tarefa.setDataInicio(
                        dpInicio.getValue()
                );

                tarefa.setDataFim(
                        dpFim.getValue()
                );

                dao.atualizar(tarefa);
            }

            stage.close();
        });

        GridPane root = new GridPane();

        root.setHgap(10);
        root.setVgap(10);

        root.add(lblDescricao, 0, 0);
        root.add(txtDescricao, 1, 0);

        root.add(lblEstado, 0, 1);
        root.add(cbEstado, 1, 1);

        root.add(lblInicio, 0, 2);
        root.add(dpInicio, 1, 2);

        root.add(lblFim, 0, 3);
        root.add(dpFim, 1, 3);

        HBox botoesBox = new HBox(
                10,
                btnSalvar, btnCancelar
        );

        root.add(botoesBox, 1, 5);

        Scene scene = new Scene(root, 400, 250);

        stage.setTitle(
                tarefa == null ?
                        "Cadastrar Tarefa" :
                        "Editar Tarefa"
        );

        stage.setScene(scene);
        stage.show();
    }
}