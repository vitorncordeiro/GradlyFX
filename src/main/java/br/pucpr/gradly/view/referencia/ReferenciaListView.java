package br.pucpr.gradly.view.referencia;

import br.pucpr.gradly.dao.ReferenciaDAO;
import br.pucpr.gradly.model.Referencia;
import br.pucpr.gradly.view.MainMenuView;
import br.pucpr.gradly.view.tarefa.TarefaFormView;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ReferenciaListView {

    public void mostrar() {

        Stage stage = new Stage();

        ReferenciaDAO dao = new ReferenciaDAO();

        TableView<Referencia> referencia = new TableView<>();

        TableColumn<Referencia, String> colTitulo =
                new TableColumn<>("Título");

        TableColumn<Referencia, String> colTipo =
                new TableColumn<>("Tipo");

        TableColumn<Referencia, String> colAutor =
                new TableColumn<>("Autor");

        colTitulo.setCellValueFactory(
                new PropertyValueFactory<>("titulo")
        );

        colTipo.setCellValueFactory(
                new PropertyValueFactory<>("tipo")
        );

        colAutor.setCellValueFactory(
                new PropertyValueFactory<>("Autor")
        );

        referencia.getColumns().addAll(
                colTitulo,
                colTipo,
                colAutor
        );

        referencia.getItems().addAll(
                dao.listarTodos()
        );

        ComboBox<String> cbTipo = new ComboBox<>();

        cbTipo.getItems().addAll(
                "Todos",
                "Livro",
                "Artigo",
                "Site"
        );

        cbTipo.setValue("Todos");

        Button btnFiltrar = new Button("Filtrar");

        btnFiltrar.setOnAction(e -> {

            referencia.getItems().clear();

            if (cbTipo.getValue().equals("Todos")) {

                referencia.getItems().addAll(
                        dao.listarTodos()
                );

            } else {

                referencia.getItems().addAll(
                        dao.buscarPorTipo(
                                cbTipo.getValue()
                        )
                );
            }
        });

        Button btnExcluir = new Button("Excluir");

        btnExcluir.setOnAction(e -> {

            Referencia selecionada =
                    referencia.getSelectionModel()
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

            referencia.getItems().remove(
                    selecionada
            );
        });

        Button btnEditar = new Button("Editar");

        btnEditar.setOnAction(e -> {

            Referencia selecionada =
                    referencia.getSelectionModel()
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

            new ReferenciaFormView(
                    selecionada
            ).mostrar();

            referencia.getItems().clear();
            referencia.getItems().addAll(
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
                cbTipo,
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
                referencia,
                botoesBox
        );

        Scene scene =
                new Scene(root, 600, 400);

        stage.setTitle("Lista de Referencias");
        stage.setScene(scene);
        stage.show();
    }
}
