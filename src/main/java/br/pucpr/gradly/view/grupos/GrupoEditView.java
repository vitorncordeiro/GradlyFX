package br.pucpr.gradly.view.grupos;

import br.pucpr.gradly.dao.GrupoDAO;
import br.pucpr.gradly.model.Grupo;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.time.LocalDate;

public class GrupoEditView {

    private Stage stage;
    private Grupo grupo;
    private GrupoDAO dao = new GrupoDAO();

    public GrupoEditView(
            Stage stage,
            Grupo grupo) {

        this.stage = stage;
        this.grupo = grupo;
    }

    public void mostrar() {

        GridPane grid = new GridPane();

        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);

        TextField txtId = new TextField(String.valueOf(grupo.getId()));
        txtId.setDisable(true);

        TextField txtNome = new TextField(grupo.getNome());

        TextField txtDescricao = new TextField(grupo.getDescricao());

        DatePicker txtDatacriacao = new DatePicker(grupo.getDatacriacao());

        grid.add(new Label("ID"),0,0);
        grid.add(txtId,1,0);

        grid.add(new Label("Nome do Grupo"),0,1);
        grid.add(txtNome,1,1);

        grid.add(new Label("Descricao"),0,2);
        grid.add(txtDescricao,1,2);

        grid.add(new Label("Data de Criacao"),0,3);
        grid.add(txtDatacriacao,1,3);


        Button atualizar = new Button("Atualizar");
        Button voltar = new Button("Voltar");

        atualizar.setOnAction(e -> {

            Grupo atualizado = new Grupo();

            atualizado.setId(grupo.getId());

            atualizado.setNome(txtNome.getText());

            atualizado.setDescricao(txtDescricao.getText());

            atualizado.setDatacriacao(txtDatacriacao.getValue());

            dao.atualizar(atualizado);

            new GrupoListView().mostrar();
            stage.close();
        });

        voltar.setOnAction(e -> {
            new GrupoListView().mostrar();
            stage.close();});

        HBox botoes = new HBox(10, atualizar, voltar);

        VBox root = new VBox(15, grid, botoes);

        root.setPadding(new Insets(20));

        stage.setScene(new Scene(root, 900, 500));
    }

}
