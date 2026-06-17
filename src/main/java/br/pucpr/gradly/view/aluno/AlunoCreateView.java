package br.pucpr.gradly.view.aluno;

import br.pucpr.gradly.dao.AlunoDAO;
import br.pucpr.gradly.model.Aluno;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class AlunoCreateView {

    private Stage stage;
    private AlunoDAO dao = new AlunoDAO();

    public AlunoCreateView(Stage stage) {
        this.stage = stage;
    }

    public void mostrar() {

        GridPane grid = new GridPane();

        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);

        TextField txtMatricula = new TextField();

        TextField txtNome = new TextField();

        TextField txtCurso = new TextField();

        TextField txtEmail = new TextField();

        PasswordField txtSenha = new PasswordField();

        grid.add(new Label("Matrícula"),0,0);
        grid.add(txtMatricula,1,0);

        grid.add(new Label("Nome"),0,1);
        grid.add(txtNome,1,1);

        grid.add(new Label("Curso"),0,2);
        grid.add(txtCurso,1,2);

        grid.add(new Label("Email"),0,3);
        grid.add(txtEmail,1,3);

        grid.add(new Label("Senha"),0,4);
        grid.add(txtSenha,1,4);

        Button salvar = new Button("Salvar");

        Button voltar = new Button("Voltar");

        salvar.setOnAction(e -> {

            Aluno aluno = new Aluno();

            aluno.setMatricula(Integer.parseInt(txtMatricula.getText()));

            aluno.setNome(txtNome.getText());

            aluno.setCurso(txtCurso.getText());

            aluno.setEmail(txtEmail.getText());

            aluno.setSenha(txtSenha.getText());

            dao.inserir(aluno);

            new AlunoListView().mostrar();
        });

        voltar.setOnAction(e -> {
            new AlunoListView().mostrar();
            stage.close();
        });

        HBox botoes = new HBox(10, salvar, voltar);

        VBox root = new VBox(15, grid, botoes);

        root.setPadding(new Insets(20));

        stage.setScene(new Scene(root, 900, 500));
    }
}