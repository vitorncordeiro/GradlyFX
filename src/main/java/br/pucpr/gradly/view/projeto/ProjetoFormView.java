package br.pucpr.gradly.view.projeto;

import br.pucpr.gradly.dao.ProjetoDAO;
import br.pucpr.gradly.model.Projeto;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ProjetoFormView {
    private Projeto projeto;

    public ProjetoFormView() {
    }

    public ProjetoFormView(Projeto projeto) {
        this.projeto = projeto;
    }

    public void mostrar() {
        Stage stage = new Stage();
        Label lblTitulo = new Label("Título");
        TextField txtTitulo = new TextField();

        Label lblDescricao = new Label("Descrição");
        TextField txtDescricao = new TextField();

        Label lblObjetivo = new Label("Objetivo");
        TextField txtObjetivo = new TextField();

        Label lblTemas = new Label("Temas");
        TextField txtTemas = new TextField();

        Label lblEstado = new Label("Estado");
        TextField txtEstado = new TextField();

        Button btnSalvar = new Button("Salvar");

        if (projeto != null) {
            txtTitulo.setText(projeto.getTitulo());
            txtDescricao.setText(projeto.getDescricao());
            txtObjetivo.setText(projeto.getObjetivo());
            txtTemas.setText(projeto.getTemas());
            txtEstado.setText(projeto.getEstado());
        }

        btnSalvar.setOnAction(e -> {
            if (txtTitulo.getText().trim().isEmpty() || txtDescricao.getText().trim().isEmpty() ||
                    txtObjetivo.getText().trim().isEmpty() || txtTemas.getText().trim().isEmpty() || txtEstado.getText().trim().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Aviso");
                alert.setHeaderText(null);
                alert.setContentText("Preencha todos os campos.");
                alert.showAndWait();
                return;
            }

            ProjetoDAO dao = new ProjetoDAO();

            if (projeto == null) {
                Projeto novoProjeto = new Projeto(0, txtTitulo.getText(), txtDescricao.getText(), txtObjetivo.getText(), txtTemas.getText(), txtEstado.getText());
                try {
                    dao.inserir(novoProjeto);
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
                projeto.setTitulo(txtTitulo.getText());
                projeto.setDescricao(txtDescricao.getText());
                projeto.setObjetivo(txtObjetivo.getText());
                projeto.setTemas(txtTemas.getText());
                projeto.setEstado(txtEstado.getText());
                try {
                    dao.atualizar(projeto);
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

        root.add(lblTitulo, 0, 0);
        root.add(txtTitulo, 1, 0);

        root.add(lblDescricao, 0, 1);
        root.add(txtDescricao, 1, 1);

        root.add(lblObjetivo, 0, 2);
        root.add(txtObjetivo, 1, 2);

        root.add(lblTemas, 0, 3);
        root.add(txtTemas, 1, 3);

        root.add(lblEstado, 0, 4);
        root.add(txtEstado, 1, 4);

        root.add(btnSalvar, 1, 5);

        Scene scene = new Scene(root, 400, 300);

        stage.setTitle(projeto == null ? "Cadastrar Projeto" : "Editar Projeto");

        stage.setScene(scene);
        stage.show();
    }
}
