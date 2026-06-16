package br.pucpr.gradly.view.orientador;

import br.pucpr.gradly.dao.OrientadorDAO;
import br.pucpr.gradly.exception.GradlyException;
import br.pucpr.gradly.model.Orientador;
import br.pucpr.gradly.view.MainMenuView;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OrientadorListView {
    public void mostrar() {
        Stage stage = new Stage();

        OrientadorDAO dao = new OrientadorDAO();

        TableView<Orientador> tabelaOrientadores = new TableView<>();
        TableColumn<Orientador, String> colNome = new TableColumn<>("Nome");
        TableColumn<Orientador, String> colEmail = new TableColumn<>("Email");
        TableColumn<Orientador, String> colAreaAtuacao = new TableColumn<>("Área de Atuação");
        TableColumn<Orientador, String> colTitulo = new TableColumn<>("Título");

        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colAreaAtuacao.setCellValueFactory(new PropertyValueFactory<>("areaAtuacao"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));

        tabelaOrientadores.getColumns().addAll(colNome, colEmail, colAreaAtuacao, colTitulo);

        try {
            tabelaOrientadores.getItems().addAll(dao.ler());
        } catch (GradlyException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

        Button btnAdicionar = new Button("Adicionar Orientador");
        btnAdicionar.setMaxWidth(Double.MAX_VALUE);
        btnAdicionar.setOnAction(e -> {
            new OrientadorFormView().mostrar();
            tabelaOrientadores.getItems().clear();
            try {
                tabelaOrientadores.getItems().addAll(dao.ler());
            } catch (GradlyException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText(null);
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }
        });

        Button btnEditar = new Button("Editar");
        btnEditar.setOnAction(e -> {
            Orientador selecionado = tabelaOrientadores.getSelectionModel().getSelectedItem();
            if (selecionado == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Aviso");
                alert.setHeaderText(null);
                alert.setContentText("Selecione um orientador.");
                alert.showAndWait();
                return;
            }
            new OrientadorFormView(selecionado).mostrar();

            tabelaOrientadores.getItems().clear();

            try {
                tabelaOrientadores.getItems().addAll(dao.ler());
            } catch (GradlyException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText(null);
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }
        });

        Button btnExcluir = new Button("Excluir");
        btnExcluir.setOnAction(e -> {
            Orientador selecionado =
                    tabelaOrientadores.getSelectionModel().getSelectedItem();
            if (selecionado == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Aviso");
                alert.setHeaderText(null);
                alert.setContentText("Selecione um orientador.");
                alert.showAndWait();
                return;
            }

            try {
                dao.excluir(selecionado.getId());
            } catch (GradlyException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText(null);
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
                return;
            }

            tabelaOrientadores.getItems().remove(selecionado);
        });

        Button btnVoltar = new Button("Voltar");
        btnVoltar.setOnAction(e -> {
            stage.close();
            new MainMenuView().start(new Stage());
        });

        HBox botoesBox = new HBox(10, btnEditar, btnExcluir, btnVoltar);

        VBox root = new VBox(10, btnAdicionar, tabelaOrientadores, botoesBox);

        Scene scene = new Scene(root, 600, 400);

        stage.setTitle("Lista de Orientadores");
        stage.setScene(scene);
        stage.show();
    }
}