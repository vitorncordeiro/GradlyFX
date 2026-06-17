package br.pucpr.gradly.view.instituicao;

import br.pucpr.gradly.dao.InstituicaoDAO;
import br.pucpr.gradly.model.Instituicao;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class InstituicaoFormView {
    private Instituicao instituicao;

    public InstituicaoFormView() {
    }

    public InstituicaoFormView(Instituicao instituicao) {
        this.instituicao = instituicao;
    }

    public void mostrar() {
        Stage stage = new Stage();
        Label lblNome = new Label("Nome");
        TextField txtNome = new TextField();

        Label lblSigla = new Label("Sigla");
        TextField txtSigla = new TextField();

        Label lblEndereco = new Label("Endereço");
        TextField txtEndereco = new TextField();

        Label lblTelefone = new Label("Telefone");
        TextField txtTelefone = new TextField();

        Button btnSalvar = new Button("Salvar");

        if (instituicao != null) {
            txtNome.setText(instituicao.getNome());
            txtSigla.setText(instituicao.getSigla());
            txtEndereco.setText(instituicao.getEndereco());
            txtTelefone.setText(instituicao.getTelefone());
        }

        btnSalvar.setOnAction(e -> {
            if (txtNome.getText().trim().isEmpty() || txtSigla.getText().trim().isEmpty() ||
                    txtEndereco.getText().trim().isEmpty() || txtTelefone.getText().trim().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Aviso");
                alert.setHeaderText(null);
                alert.setContentText("Preencha todos os campos.");
                alert.showAndWait();
                return;
            }

            InstituicaoDAO dao = new InstituicaoDAO();

            if (instituicao == null) {
                Instituicao novaInstituicao = new Instituicao(0, txtNome.getText(), txtSigla.getText(), txtEndereco.getText(), txtTelefone.getText());
                try {
                    dao.inserir(novaInstituicao);
                } catch (Exception ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro");
                    alert.setHeaderText(null);
                    alert.setContentText(ex.getMessage());
                    alert.showAndWait();
                    return;
                }
            }
            else {
                instituicao.setNome(txtNome.getText());
                instituicao.setSigla(txtSigla.getText());
                instituicao.setEndereco(txtEndereco.getText());
                instituicao.setTelefone(txtTelefone.getText());
                try {
                    dao.atualizar(instituicao);
                } catch (Exception ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro");
                    alert.setHeaderText(null);
                    alert.setContentText(ex.getMessage());
                    alert.showAndWait();
                    return;
                }
            }
            stage.close();
        });

        GridPane root = new GridPane();

        root.setHgap(10);
        root.setVgap(10);

        root.add(lblNome, 0, 0);
        root.add(txtNome, 1, 0);

        root.add(lblSigla, 0, 1);
        root.add(txtSigla, 1, 1);

        root.add(lblEndereco, 0, 2);
        root.add(txtEndereco, 1, 2);

        root.add(lblTelefone, 0, 3);
        root.add(txtTelefone, 1, 3);

        root.add(btnSalvar, 1, 4);

        Scene scene = new Scene(root, 400, 250);

        stage.setTitle(instituicao == null ? "Cadastrar Instituição" : "Editar Instituição");

        stage.setScene(scene);
        stage.show();
    }
}

