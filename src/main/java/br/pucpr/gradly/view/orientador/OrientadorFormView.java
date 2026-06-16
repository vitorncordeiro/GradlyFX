package br.pucpr.gradly.view.orientador;

import br.pucpr.gradly.dao.OrientadorDAO;
import br.pucpr.gradly.exception.GradlyException;
import br.pucpr.gradly.model.Orientador;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class OrientadorFormView {
    private Orientador orientador;

    public OrientadorFormView() {
    }

    public OrientadorFormView(Orientador orientador) {
        this.orientador = orientador;
    }

    public void mostrar() {
        Stage stage = new Stage();
        Label lblNome = new Label("Nome");
        TextField txtNome = new TextField();

        Label lblEmail = new Label("Email");
        TextField txtEmail = new TextField();

        Label lblSenha = new Label("Senha");
        TextField txtSenha = new TextField();

        Label lblAreaAtuacao = new Label("Área de Atuação");
        TextField txtAreaAtuacao = new TextField();

        Label lblTitulo = new Label("Título");
        TextField txtTitulo = new TextField();

        Button btnSalvar = new Button("Salvar");

        if (orientador != null) {
            txtNome.setText(orientador.getNome());
            txtEmail.setText(orientador.getEmail());
            txtSenha.setText(orientador.getSenha());
            txtAreaAtuacao.setText(orientador.getAreaAtuacao());
            txtTitulo.setText(orientador.getTitulo());
        }

        btnSalvar.setOnAction(e -> {
            if (txtNome.getText().trim().isEmpty() || txtEmail.getText().trim().isEmpty() ||
            txtSenha.getText().trim().isEmpty() || txtAreaAtuacao.getText().trim().isEmpty() || txtTitulo.getText().trim().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Aviso");
                alert.setHeaderText(null);
                alert.setContentText("Preencha todos os campos.");
                alert.showAndWait();
                return;
            }

            OrientadorDAO dao = new OrientadorDAO();

            if (orientador == null) {
                Orientador novoOrientador = new Orientador(0, txtNome.getText(), txtEmail.getText(), txtSenha.getText(), txtAreaAtuacao.getText(), txtTitulo.getText());
                try {
                    dao.criar(novoOrientador);
                } catch (GradlyException ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro");
                    alert.setHeaderText(null);
                    alert.setContentText(ex.getMessage());
                    alert.showAndWait();
                    return;
                }
            }
            else {
                orientador.setNome(txtNome.getText());
                orientador.setEmail(txtEmail.getText());
                orientador.setSenha(txtSenha.getText());
                orientador.setAreaAtuacao(txtAreaAtuacao.getText());
                orientador.setTitulo(txtTitulo.getText());
                try {
                    dao.editar(orientador);
                } catch (GradlyException ex) {
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

        root.add(lblEmail, 0, 1);
        root.add(txtEmail, 1, 1);

        root.add(lblSenha, 0, 2);
        root.add(txtSenha, 1, 2);

        root.add(lblAreaAtuacao, 0, 3);
        root.add(txtAreaAtuacao, 1, 3);

        root.add(lblTitulo, 0, 4);
        root.add(txtTitulo, 1, 4);

        root.add(btnSalvar, 1, 5);

        Scene scene = new Scene(root, 400, 300);

        stage.setTitle(orientador == null ? "Cadastrar Orientador" : "Editar Orientador");

        stage.setScene(scene);
        stage.show();
    }
}