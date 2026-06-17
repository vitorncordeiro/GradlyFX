package br.pucpr.gradly.view.grupos;


import br.pucpr.gradly.dao.GrupoDAO;
import br.pucpr.gradly.model.Grupo;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.time.LocalDate;

public class GrupoCreateView {
    private Stage stage;
    private GrupoDAO dao = new GrupoDAO();

    public GrupoCreateView(Stage stage) {
        this.stage = stage;
    }

    public void mostrar() {

        GridPane grid = new GridPane();

        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);

        TextField txtNome = new TextField();

        TextField txtDescricao = new TextField();

        DatePicker txtDatacriacao = new DatePicker();

        grid.add(new Label("Nome"),0,1);
        grid.add(txtNome,1,1);

        grid.add(new Label("Descricao"),0,2);
        grid.add(txtDescricao,1,2);

        grid.add(new Label("Data de Criacao"),0,3);
        grid.add(txtDatacriacao,1,3);


        Button salvar = new Button("Salvar");

        Button voltar = new Button("Voltar");

        salvar.setOnAction(e -> {

            Grupo grupo = new Grupo();

            grupo.setNome(txtNome.getText());

            grupo.setDescricao(txtDescricao.getText());

            grupo.setDatacriacao(txtDatacriacao.getValue());

            dao.inserir(grupo);

            new GrupoListView().mostrar();
            stage.close();
        });

        voltar.setOnAction(e -> {
            new GrupoListView().mostrar();
            stage.close();
        });

        HBox botoes = new HBox(10, salvar, voltar);

        VBox root = new VBox(15, grid, botoes);

        root.setPadding(new Insets(20));

        stage.setScene(new Scene(root, 900, 500));
    }

}
