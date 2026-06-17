package br.pucpr.gradly.view.instituicao;

import br.pucpr.gradly.dao.InstituicaoDAO;
import br.pucpr.gradly.model.Instituicao;
import br.pucpr.gradly.view.MainMenuView;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class InstituicaoListView {
    public void mostrar() {
        Stage stage = new Stage();

        InstituicaoDAO dao = new InstituicaoDAO();

        TableView<Instituicao> tabelaInstituicoes = new TableView<>();
        TableColumn<Instituicao, String> colNome = new TableColumn<>("Nome");
        TableColumn<Instituicao, String> colSigla = new TableColumn<>("Sigla");
        TableColumn<Instituicao, String> colEndereco = new TableColumn<>("Endereço");
        TableColumn<Instituicao, String> colTelefone = new TableColumn<>("Telefone");

        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colSigla.setCellValueFactory(new PropertyValueFactory<>("sigla"));
        colEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));

        tabelaInstituicoes.getColumns().addAll(colNome, colSigla, colEndereco, colTelefone);

        try {
            tabelaInstituicoes.getItems().addAll(dao.listarTodos());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

        Button btnAdicionar = new Button("Adicionar Instituição");
        btnAdicionar.setMaxWidth(Double.MAX_VALUE);
        btnAdicionar.setOnAction(e -> {
            new InstituicaoFormView().mostrar();
            tabelaInstituicoes.getItems().clear();
            try {
                tabelaInstituicoes.getItems().addAll(dao.listarTodos());
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
            Instituicao selecionada = tabelaInstituicoes.getSelectionModel().getSelectedItem();
            if (selecionada == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Aviso");
                alert.setHeaderText(null);
                alert.setContentText("Selecione uma instituição.");
                alert.showAndWait();
                return;
            }
            new InstituicaoFormView(selecionada).mostrar();

            tabelaInstituicoes.getItems().clear();

            try {
                tabelaInstituicoes.getItems().addAll(dao.listarTodos());
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
            Instituicao selecionada =
                    tabelaInstituicoes.getSelectionModel().getSelectedItem();
            if (selecionada == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Aviso");
                alert.setHeaderText(null);
                alert.setContentText("Selecione uma instituição.");
                alert.showAndWait();
                return;
            }

            try {
                dao.excluir(selecionada.getId());
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText(null);
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
                return;
            }

            tabelaInstituicoes.getItems().remove(selecionada);
        });

        Button btnVoltar = new Button("Voltar");
        btnVoltar.setOnAction(e -> {
            stage.close();
            new MainMenuView().start(new Stage());
        });

        HBox botoesBox = new HBox(10, btnEditar, btnExcluir, btnVoltar);

        VBox root = new VBox(10, btnAdicionar, tabelaInstituicoes, botoesBox);

        Scene scene = new Scene(root, 600, 400);

        stage.setTitle("Lista de Instituições");
        stage.setScene(scene);
        stage.show();
    }
}

