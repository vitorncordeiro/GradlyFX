package br.pucpr.gradly.view.administrador;

import br.pucpr.gradly.dao.AdministradorDAO;
import br.pucpr.gradly.model.Administrador;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AdministradorFormView {
    private Administrador administrador;

    public AdministradorFormView() {
    }

    public AdministradorFormView(Administrador administrador) {
        this.administrador = administrador;
    }

    public void mostrar() {
        Stage stage = new Stage();
        Label lblNome = new Label("Nome");
        TextField txtNome = new TextField();

        Label lblEmail = new Label("Email");
        TextField txtEmail = new TextField();

        Label lblSenha = new Label("Senha");
        PasswordField txtSenha = new PasswordField();

        Label lblNivel = new Label("Nível de Acesso");
        ComboBox<Integer> cbNivel = new ComboBox<>();
        cbNivel.getItems().addAll(1, 2, 3);

        Button btnSalvar = new Button("Salvar");

        if (administrador != null) {
            txtNome.setText(administrador.getNome());
            txtEmail.setText(administrador.getEmail());
            txtSenha.setText(administrador.getSenha());
            cbNivel.setValue(administrador.getNivelAcesso());
        }

        btnSalvar.setOnAction(e -> {
            if (txtNome.getText().trim().isEmpty() || txtEmail.getText().trim().isEmpty() ||
                    txtSenha.getText().trim().isEmpty() || cbNivel.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Aviso");
                alert.setHeaderText(null);
                alert.setContentText("Preencha todos os campos.");
                alert.showAndWait();
                return;
            }

            AdministradorDAO dao = new AdministradorDAO();

            if (administrador == null) {
                Administrador novoAdministrador = new Administrador();
                novoAdministrador.setNome(txtNome.getText());
                novoAdministrador.setEmail(txtEmail.getText());
                novoAdministrador.setSenha(txtSenha.getText());
                novoAdministrador.setNivelAcesso(cbNivel.getValue());
                dao.inserir(novoAdministrador);
            }
            else {
                administrador.setNome(txtNome.getText());
                administrador.setEmail(txtEmail.getText());
                administrador.setSenha(txtSenha.getText());
                administrador.setNivelAcesso(cbNivel.getValue());
                dao.atualizar(administrador);
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

        root.add(lblNivel, 0, 3);
        root.add(cbNivel, 1, 3);

        root.add(btnSalvar, 1, 4);

        Scene scene = new Scene(root, 400, 300);

        stage.setTitle(administrador == null ? "Cadastrar Administrador" : "Editar Administrador");

        stage.setScene(scene);
        stage.show();
    }
}