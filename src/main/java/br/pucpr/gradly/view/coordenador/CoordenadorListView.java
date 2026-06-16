package br.pucpr.gradly.view.coordenador;

import br.pucpr.gradly.dao.CoordenadorDAO;
import br.pucpr.gradly.exception.GradlyException;
import br.pucpr.gradly.model.Coordenador;
import br.pucpr.gradly.view.MainMenuView;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CoordenadorListView {
    public void mostrar() {
        Stage stage = new Stage();

        CoordenadorDAO dao = new CoordenadorDAO();

        TableView<Coordenador> tabelaCoordenadores = new TableView<>();
        TableColumn<Coordenador, String> colNome = new TableColumn<>("Nome");
        TableColumn<Coordenador, String> colEmail = new TableColumn<>("Email");
        TableColumn<Coordenador, String> colDepartamento = new TableColumn<>("Departamento");
        TableColumn<Coordenador, String> colInstituicao = new TableColumn<>("Instituição");

        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colDepartamento.setCellValueFactory(new PropertyValueFactory<>("departamento"));
        colInstituicao.setCellValueFactory(new PropertyValueFactory<>("instituicao"));

        tabelaCoordenadores.getColumns().addAll(colNome, colEmail, colDepartamento, colInstituicao);

        try {
            tabelaCoordenadores.getItems().addAll(dao.ler());
        } catch (GradlyException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

        Button btnAdicionar = new Button("Adicionar Coordenador");
        btnAdicionar.setMaxWidth(Double.MAX_VALUE);
        btnAdicionar.setOnAction(e -> {
            new CoordenadorFormView().mostrar();
            tabelaCoordenadores.getItems().clear();
            try {
                tabelaCoordenadores.getItems().addAll(dao.ler());
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
            Coordenador selecionado = tabelaCoordenadores.getSelectionModel().getSelectedItem();
            if (selecionado == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Aviso");
                alert.setHeaderText(null);
                alert.setContentText("Selecione um coordenador.");
                alert.showAndWait();
                return;
            }
            new CoordenadorFormView(selecionado).mostrar();

            tabelaCoordenadores.getItems().clear();

            try {
                tabelaCoordenadores.getItems().addAll(dao.ler());
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
            Coordenador selecionado =
                    tabelaCoordenadores.getSelectionModel().getSelectedItem();
            if (selecionado == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Aviso");
                alert.setHeaderText(null);
                alert.setContentText("Selecione um coordenador.");
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

            tabelaCoordenadores.getItems().remove(selecionado);
        });

        Button btnVoltar = new Button("Voltar");
        btnVoltar.setOnAction(e -> {
            stage.close();
            new MainMenuView().start(new Stage());
        });

        HBox botoesBox = new HBox(10, btnEditar, btnExcluir, btnVoltar);

        VBox root = new VBox(10, btnAdicionar, tabelaCoordenadores, botoesBox);

        Scene scene = new Scene(root, 600, 400);

        stage.setTitle("Lista de Coordenadores");
        stage.setScene(scene);
        stage.show();
    }
}