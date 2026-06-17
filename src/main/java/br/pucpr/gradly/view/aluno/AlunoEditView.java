package br.pucpr.gradly.view.aluno;

import br.pucpr.gradly.dao.AlunoDAO;
import br.pucpr.gradly.model.Aluno;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class AlunoEditView {

    private Stage stage;
    private Aluno aluno;
    private AlunoDAO dao = new AlunoDAO();

    public AlunoEditView(
            Stage stage,
            Aluno aluno) {

        this.stage = stage;
        this.aluno = aluno;
    }

    public void mostrar() {

        GridPane grid = new GridPane();

        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);

        TextField txtId = new TextField(String.valueOf(aluno.getId()));

        txtId.setDisable(true);

        TextField txtMatricula = new TextField(String.valueOf(aluno.getMatricula()));

        TextField txtNome = new TextField(aluno.getNome());

        TextField txtCurso = new TextField(aluno.getCurso());

        TextField txtEmail = new TextField(aluno.getEmail());

        PasswordField txtSenha = new PasswordField();

        txtSenha.setText(aluno.getSenha());

        grid.add(new Label("ID"),0,0);
        grid.add(txtId,1,0);

        grid.add(new Label("Matrícula"),0,1);
        grid.add(txtMatricula,1,1);

        grid.add(new Label("Nome"),0,2);
        grid.add(txtNome,1,2);

        grid.add(new Label("Curso"),0,3);
        grid.add(txtCurso,1,3);

        grid.add(new Label("Email"),0,4);
        grid.add(txtEmail,1,4);

        grid.add(new Label("Senha"),0,5);
        grid.add(txtSenha,1,5);

        Button atualizar = new Button("Atualizar");

        Button voltar = new Button("Voltar");

        atualizar.setOnAction(e -> {

            Aluno atualizado = new Aluno();

            atualizado.setId(aluno.getId());

            atualizado.setMatricula(Integer.parseInt(txtMatricula.getText()));

            atualizado.setNome(txtNome.getText());

            atualizado.setCurso(txtCurso.getText());

            atualizado.setEmail(txtEmail.getText());

            atualizado.setSenha(txtSenha.getText());

            dao.atualizar(atualizado);

            new AlunoListView().mostrar();
            stage.close();
        });

        voltar.setOnAction(e -> {new AlunoListView().mostrar(); stage.close();});

        HBox botoes = new HBox(10, atualizar, voltar);

        VBox root = new VBox(15, grid, botoes);

        root.setPadding(new Insets(20));

        stage.setScene(new Scene(root, 900, 500));
    }
}