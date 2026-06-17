package br.pucpr.gradly.view;

import br.pucpr.gradly.view.aluno.AlunoListView;
import br.pucpr.gradly.view.referencia.ReferenciaFormView;
import br.pucpr.gradly.view.referencia.ReferenciaListView;
import br.pucpr.gradly.view.tarefa.TarefaFormView;
import br.pucpr.gradly.view.tarefa.TarefaListView;
import br.pucpr.gradly.view.orientador.OrientadorListView;
import br.pucpr.gradly.view.coordenador.CoordenadorListView;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainMenuView extends Application {

    @Override
    public void start(Stage stage) {

        Button btnOrientador = new Button("Gerenciar Orientador");
        btnOrientador.setOnAction(e -> {
            new OrientadorListView().mostrar();
        });

        Button btnCoordenador = new Button("Gerenciar Coordenador");
        btnCoordenador.setOnAction(e -> {
            new CoordenadorListView().mostrar();
        });

        Button btnTarefa =
                new Button("Cadastrar Tarefa");

        btnTarefa.setOnAction(e -> {
            new TarefaFormView().mostrar();
        });

        Button btnReferencia =
                new Button("Cadastrar Referencia");

        btnReferencia.setOnAction(e -> {
            new ReferenciaFormView().mostrar();
        });

        Button btnListTarefa =
                new Button("Listar Tarefa");

        btnListTarefa.setOnAction(e -> {
            new TarefaListView().mostrar();
        });

        Button btnListReferencia =
                new Button("Listar Referencia");

        btnListReferencia.setOnAction(e -> {
            new ReferenciaListView().mostrar();
        });

        Button btnAluno = new Button("Alunos");
        btnAluno.setOnAction(e -> {
            new AlunoListView().mostrar();
        });

        Label title = new Label("Gradly Menu");
        title.setFont(new Font("Arial",24));
        title.setAlignment(Pos.CENTER);
        VBox root = new VBox(5);
        root.setAlignment(Pos.CENTER);

        btnTarefa.setPrefWidth(200);
        btnListTarefa.setPrefWidth(200);
        btnReferencia.setPrefWidth(200);
        btnListReferencia.setPrefWidth(200);
        btnAluno.setPrefWidth(200);
        btnOrientador.setPrefWidth(200);
        btnCoordenador.setPrefWidth(200);

        root.getChildren().addAll(
                title,
                btnOrientador,
                btnCoordenador,
                btnTarefa,
                btnListTarefa,
                btnReferencia,
                btnListReferencia,
                btnAluno
        );

        Scene scene =
                new Scene(root, 400, 300);

        stage.setTitle("Gradly");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}