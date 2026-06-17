package br.pucpr.gradly.view.administrador;

import br.pucpr.gradly.dao.AdministradorDAO;
import br.pucpr.gradly.model.Administrador;
import br.pucpr.gradly.view.MainMenuView;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdministradorListView {
    public void mostrar() {
        Stage stage = new Stage();

        AdministradorDAO dao = new AdministradorDAO();

        TableView<Administrador> tabelaAdministradores = new TableView<>();
        TableColumn<Administrador, String> colNome = new TableColumn<>("Nome");
        TableColumn<Administrador, String> colEmail = new TableColumn<>("Email");
        TableColumn<Administrador, Integer> colNivel = new TableColumn<>("Nível de Acesso");

        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colNivel.setCellValueFactory(new PropertyValueFactory<>("nivelAcesso"));

        tabelaAdministradores.getColumns().addAll(colNome, colEmail, colNivel);

        tabelaAdministradores.getItems().addAll(dao.listarTodos());

        Button btnAdicionar = new Button("Adicionar Administrador");
        btnAdicionar.setMaxWidth(Double.MAX_VALUE);
        btnAdicionar.setOnAction(e -> {
            new AdministradorFormView().mostrar();
            tabelaAdministradores.getItems().clear();
            tabelaAdministradores.getItems().addAll(dao.listarTodos());
        });

        Button btnEditar = new Button("Editar");
        btnEditar.setOnAction(e -> {
            Administrador selecionado = tabelaAdministradores.getSelectionModel().getSelectedItem();
            if (selecionado == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Aviso");
                alert.setHeaderText(null);
                alert.setContentText("Selecione um administrador.");
                alert.showAndWait();
                return;
            }
            new AdministradorFormView(selecionado).mostrar();

            tabelaAdministradores.getItems().clear();
            tabelaAdministradores.getItems().addAll(dao.listarTodos());
        });

        Button btnExcluir = new Button("Excluir");
        btnExcluir.setOnAction(e -> {
            Administrador selecionado =
                    tabelaAdministradores.getSelectionModel().getSelectedItem();
            if (selecionado == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Aviso");
                alert.setHeaderText(null);
                alert.setContentText("Selecione um administrador.");
                alert.showAndWait();
                return;
            }

            dao.excluir(selecionado.getId());

            tabelaAdministradores.getItems().remove(selecionado);
        });

        Button btnVoltar = new Button("Voltar");
        btnVoltar.setOnAction(e -> {
            stage.close();
            new MainMenuView().start(new Stage());
        });

        HBox botoesBox = new HBox(10, btnEditar, btnExcluir, btnVoltar);

        VBox root = new VBox(10, btnAdicionar, tabelaAdministradores, botoesBox);

        Scene scene = new Scene(root, 600, 400);

        stage.setTitle("Lista de Administradores");
        stage.setScene(scene);
        stage.show();
    }
}