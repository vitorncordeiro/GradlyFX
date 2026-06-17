package br.pucpr.gradly.view.banca;

import br.pucpr.gradly.dao.BancaDAO;
import br.pucpr.gradly.dao.ProjetoDAO;
import br.pucpr.gradly.model.Banca;
import br.pucpr.gradly.model.Projeto;
import br.pucpr.gradly.view.MainMenuView;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;

public class BancaListView {

    private final DateTimeFormatter formato =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public void mostrar() {
        Stage stage = new Stage();

        BancaDAO dao = new BancaDAO();
        ProjetoDAO projetoDAO = new ProjetoDAO();

        TableView<Banca> tabelaBancas = new TableView<>();

        TableColumn<Banca, String> colData = new TableColumn<>("Data");
        colData.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(
                c.getValue().getData() == null ? "" : formato.format(c.getValue().getData())));

        TableColumn<Banca, String> colSala = new TableColumn<>("Sala");
        colSala.setCellValueFactory(new PropertyValueFactory<>("sala"));

        TableColumn<Banca, Double> colNota = new TableColumn<>("Nota");
        colNota.setCellValueFactory(new PropertyValueFactory<>("nota"));

        TableColumn<Banca, String> colParecer = new TableColumn<>("Parecer");
        colParecer.setCellValueFactory(new PropertyValueFactory<>("parecer"));

        TableColumn<Banca, String> colProjeto = new TableColumn<>("Projeto");
        colProjeto.setCellValueFactory(c -> {
            String titulo = "";
            for (Projeto p : projetoDAO.listarTodos()) {
                if (p.getId() == c.getValue().getProjetoId()) {
                    titulo = p.getTitulo();
                    break;
                }
            }
            return new javafx.beans.property.SimpleStringProperty(titulo);
        });

        tabelaBancas.getColumns().addAll(colData, colSala, colNota, colParecer, colProjeto);

        tabelaBancas.getItems().addAll(dao.listarTodos());

        Button btnAdicionar = new Button("Adicionar Banca");
        btnAdicionar.setMaxWidth(Double.MAX_VALUE);
        btnAdicionar.setOnAction(e -> {
            new BancaFormView().mostrar();
            tabelaBancas.getItems().clear();
            tabelaBancas.getItems().addAll(dao.listarTodos());
        });

        Button btnEditar = new Button("Editar");
        btnEditar.setOnAction(e -> {
            Banca selecionada = tabelaBancas.getSelectionModel().getSelectedItem();
            if (selecionada == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Aviso");
                alert.setHeaderText(null);
                alert.setContentText("Selecione uma banca.");
                alert.showAndWait();
                return;
            }
            new BancaFormView(selecionada).mostrar();

            tabelaBancas.getItems().clear();
            tabelaBancas.getItems().addAll(dao.listarTodos());
        });

        Button btnExcluir = new Button("Excluir");
        btnExcluir.setOnAction(e -> {
            Banca selecionada =
                    tabelaBancas.getSelectionModel().getSelectedItem();
            if (selecionada == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Aviso");
                alert.setHeaderText(null);
                alert.setContentText("Selecione uma banca.");
                alert.showAndWait();
                return;
            }

            dao.excluir(selecionada.getId());

            tabelaBancas.getItems().remove(selecionada);
        });

        Button btnVoltar = new Button("Voltar");
        btnVoltar.setOnAction(e -> {
            stage.close();
            new MainMenuView().start(new Stage());
        });

        HBox botoesBox = new HBox(10, btnEditar, btnExcluir, btnVoltar);

        VBox root = new VBox(10, btnAdicionar, tabelaBancas, botoesBox);

        Scene scene = new Scene(root, 700, 400);

        stage.setTitle("Lista de Bancas");
        stage.setScene(scene);
        stage.show();
    }
}