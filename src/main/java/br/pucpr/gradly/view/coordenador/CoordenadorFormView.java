package br.pucpr.gradly.view.coordenador;

import br.pucpr.gradly.dao.CoordenadorDAO;
import br.pucpr.gradly.exception.GradlyException;
import br.pucpr.gradly.model.Coordenador;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class CoordenadorFormView {
    private Coordenador coordenador;

    public CoordenadorFormView() {
    }

    public CoordenadorFormView(Coordenador coordenador) {
        this.coordenador = coordenador;
    }

    public void mostrar() {
        Stage stage = new Stage();
        Label lblNome = new Label("Nome");
        TextField txtNome = new TextField();

        Label lblEmail = new Label("Email");
        TextField txtEmail = new TextField();

        Label lblSenha = new Label("Senha");
        TextField txtSenha = new TextField();

        Label lblDepartamento = new Label("Departamento");
        TextField txtDepartamento = new TextField();

        Label lblInstituicao = new Label("Instituição");
        TextField txtInstituicao = new TextField();

        Button btnSalvar = new Button("Salvar");

        if (coordenador != null) {
            txtNome.setText(coordenador.getNome());
            txtEmail.setText(coordenador.getEmail());
            txtSenha.setText(coordenador.getSenha());
            txtDepartamento.setText(coordenador.getDepartamento());
            txtInstituicao.setText(coordenador.getInstituicao());
        }

        btnSalvar.setOnAction(e -> {
            if (txtNome.getText().trim().isEmpty() || txtEmail.getText().trim().isEmpty() ||
                    txtSenha.getText().trim().isEmpty() || txtDepartamento.getText().trim().isEmpty() || txtInstituicao.getText().trim().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Aviso");
                alert.setHeaderText(null);
                alert.setContentText("Preencha todos os campos.");
                alert.showAndWait();
                return;
            }

            CoordenadorDAO dao = new CoordenadorDAO();

            if (coordenador == null) {
                Coordenador novoCoordenador = new Coordenador(0, txtNome.getText(), txtEmail.getText(), txtSenha.getText(), txtDepartamento.getText(), txtInstituicao.getText());
                try {
                    dao.criar(novoCoordenador);
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
                coordenador.setNome(txtNome.getText());
                coordenador.setEmail(txtEmail.getText());
                coordenador.setSenha(txtSenha.getText());
                coordenador.setDepartamento(txtDepartamento.getText());
                coordenador.setInstituicao(txtInstituicao.getText());
                try {
                    dao.editar(coordenador);
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

        root.add(lblDepartamento, 0, 3);
        root.add(txtDepartamento, 1, 3);

        root.add(lblInstituicao, 0, 4);
        root.add(txtInstituicao, 1, 4);

        root.add(btnSalvar, 1, 5);

        Scene scene = new Scene(root, 400, 300);

        stage.setTitle(coordenador == null ? "Cadastrar Coordenador" : "Editar Coordenador");

        stage.setScene(scene);
        stage.show();
    }
}