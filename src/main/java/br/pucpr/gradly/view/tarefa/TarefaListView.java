package br.pucpr.gradly.view.tarefa;

import br.pucpr.gradly.dao.TarefaDAO;
import br.pucpr.gradly.model.Tarefa;
import br.pucpr.gradly.view.MainMenuView;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TarefaListView {

    public void mostrar() {

        Stage stage = new Stage();

        TarefaDAO dao = new TarefaDAO();

        TableView<Tarefa> tabela = new TableView<>();

        TableColumn<Tarefa, String> colDescricao =
                new TableColumn<>("Descrição");

        TableColumn<Tarefa, String> colEstado =
                new TableColumn<>("Estado");

        colDescricao.setCellValueFactory(
                new PropertyValueFactory<>("descricao")
        );

        colEstado.setCellValueFactory(
                new PropertyValueFactory<>("estado")
        );

        tabela.getColumns().addAll(
                colDescricao,
                colEstado
        );

        tabela.getItems().addAll(
                dao.listarTodos()
        );

        ComboBox<String> cbEstado = new ComboBox<>();

        cbEstado.getItems().addAll(
                "Todos",
                "A Fazer",
                "Fazendo",
                "Concluída"
        );

        cbEstado.setValue("Todos");

        Button btnFiltrar = new Button("Filtrar");

        btnFiltrar.setOnAction(e -> {

            tabela.getItems().clear();

            if (cbEstado.getValue().equals("Todos")) {

                tabela.getItems().addAll(
                        dao.listarTodos()
                );

            } else {

                tabela.getItems().addAll(
                        dao.buscarPorEstado(
                                cbEstado.getValue()
                        )
                );
            }
        });

        Button btnExcluir = new Button("Excluir");

        btnExcluir.setOnAction(e -> {

            Tarefa selecionada =
                    tabela.getSelectionModel()
                            .getSelectedItem();

            if (selecionada == null) {

                Alert alert = new Alert(
                        Alert.AlertType.WARNING
                );

                alert.setTitle("Aviso");
                alert.setHeaderText(null);
                alert.setContentText(
                        "Selecione uma tarefa."
                );

                alert.showAndWait();
                return;
            }

            dao.excluir(
                    selecionada.getId()
            );

            tabela.getItems().remove(
                    selecionada
            );
        });

        Button btnEditar = new Button("Editar");

        btnEditar.setOnAction(e -> {

            Tarefa selecionada =
                    tabela.getSelectionModel()
                            .getSelectedItem();

            if (selecionada == null) {

                Alert alert = new Alert(
                        Alert.AlertType.WARNING
                );

                alert.setTitle("Aviso");
                alert.setHeaderText(null);
                alert.setContentText(
                        "Selecione uma tarefa."
                );

                alert.showAndWait();
                return;
            }

            new TarefaFormView(
                    selecionada
            ).mostrar();

            tabela.getItems().clear();
            tabela.getItems().addAll(
                    dao.listarTodos()
            );
        });

        Button btnAdicionar = new Button("Adicionar");

        btnAdicionar.setOnAction(e -> {
            new TarefaFormView().mostrar();
        });

        Button btnVoltar = new Button("Voltar");

        btnVoltar.setOnAction(e -> {
            stage.close();
            new MainMenuView().start(new Stage());
        });

        HBox filtroBox = new HBox(
                10,
                cbEstado,
                btnFiltrar,
                btnAdicionar
        );

        HBox botoesBox = new HBox(
                10,
                btnEditar,
                btnExcluir,
                btnVoltar
        );

        VBox root = new VBox(
                10,
                filtroBox,
                tabela,
                botoesBox
        );

        Scene scene =
                new Scene(root, 600, 400);

        stage.setTitle("Lista de Tarefas");
        stage.setScene(scene);
        stage.show();
    }
}