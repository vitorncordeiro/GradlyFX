package br.pucpr.gradly.view.projeto;

import br.pucpr.gradly.dao.ProjetoDAO;
import br.pucpr.gradly.model.Projeto;
import br.pucpr.gradly.view.MainMenuView;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ProjetoListView {
    public void mostrar() {
        Stage stage = new Stage();

        ProjetoDAO dao = new ProjetoDAO();

        TableView<Projeto> tabelaProjetos = new TableView<>();
        TableColumn<Projeto, String> colTitulo = new TableColumn<>("Título");
        TableColumn<Projeto, String> colDescricao = new TableColumn<>("Descrição");
        TableColumn<Projeto, String> colObjetivo = new TableColumn<>("Objetivo");
        TableColumn<Projeto, String> colTemas = new TableColumn<>("Temas");
        TableColumn<Projeto, String> colEstado = new TableColumn<>("Estado");

        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colObjetivo.setCellValueFactory(new PropertyValueFactory<>("objetivo"));
        colTemas.setCellValueFactory(new PropertyValueFactory<>("temas"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        tabelaProjetos.getColumns().addAll(colTitulo, colDescricao, colObjetivo, colTemas, colEstado);

        try {
            tabelaProjetos.getItems().addAll(dao.listarTodos());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

        Button btnAdicionar = new Button("Adicionar Projeto");
        btnAdicionar.setMaxWidth(Double.MAX_VALUE);
        btnAdicionar.setOnAction(e -> {
            new ProjetoFormView().mostrar();
            tabelaProjetos.getItems().clear();
            try {
                tabelaProjetos.getItems().addAll(dao.listarTodos());
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText(null);
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }
        });

        Button btnEditar = new Button("Editar");
        btnEditar.setOnAction(e -> {
            Projeto selecionado = tabelaProjetos.getSelectionModel().getSelectedItem();
            if (selecionado == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Aviso");
                alert.setHeaderText(null);
                alert.setContentText("Selecione um projeto.");
                alert.showAndWait();
                return;
            }
            new ProjetoFormView(selecionado).mostrar();

            tabelaProjetos.getItems().clear();

            try {
                tabelaProjetos.getItems().addAll(dao.listarTodos());
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText(null);
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }
        });

        Button btnExcluir = new Button("Excluir");
        btnExcluir.setOnAction(e -> {
            Projeto selecionado =
                    tabelaProjetos.getSelectionModel().getSelectedItem();
            if (selecionado == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Aviso");
                alert.setHeaderText(null);
                alert.setContentText("Selecione um projeto.");
                alert.showAndWait();
                return;
            }

            try {
                dao.excluir(selecionado.getId());
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText(null);
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
                return;
            }

            tabelaProjetos.getItems().remove(selecionado);
        });

        Button btnVoltar = new Button("Voltar");
        btnVoltar.setOnAction(e -> {
            stage.close();
            new MainMenuView().start(new Stage());
        });

        HBox botoesBox = new HBox(10, btnEditar, btnExcluir, btnVoltar);

        VBox root = new VBox(10, btnAdicionar, tabelaProjetos, botoesBox);

        Scene scene = new Scene(root, 600, 400);

        stage.setTitle("Lista de Projetos");
        stage.setScene(scene);
        stage.show();
    }
}
