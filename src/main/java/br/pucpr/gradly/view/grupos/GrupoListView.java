package br.pucpr.gradly.view.grupos;

import br.pucpr.gradly.dao.GrupoDAO;
import br.pucpr.gradly.model.Grupo;
import br.pucpr.gradly.view.MainMenuView;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.time.LocalDate;

public class GrupoListView {
    public void mostrar() {
        GrupoDAO dao = new GrupoDAO();
        Stage stage = new Stage();

        TableView<Grupo> tabela;

        VBox root = new VBox(15);
        root.setPadding(new Insets(15));

        Label titulo = new Label("Lista de Grupos");

        Button btnNovo = new Button("Novo Grupo");
        Button btnMenu = new Button("Voltar Inicio");
        btnMenu.setOnAction(e->{
            stage.close();
            new MainMenuView().start(new Stage());
        });

        btnNovo.setOnAction(e -> new GrupoCreateView(stage).mostrar());

        tabela = new TableView<>();

        // criando cada colunas da tabela
        TableColumn<Grupo, Number> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getId()));

        TableColumn<Grupo, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNome()));

        TableColumn<Grupo, String> colDescricao = new TableColumn<>("Descricao");
        colDescricao.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDescricao()));

        TableColumn<Grupo, LocalDate> colDatacriacao = new TableColumn<>("Data de Criação");
        colDatacriacao.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getDatacriacao()));


        TableColumn<Grupo, Void> colAcoes = new TableColumn<>("Ações");
        colAcoes.setCellFactory(tc -> new TableCell<>() {

            private Button editar = new Button("Editar");
            private Button excluir = new Button("Excluir");

            private HBox pane = new HBox(5, editar, excluir);

            {
                editar.setOnAction(e -> {

                    Grupo grupo =
                            getTableView()
                                    .getItems()
                                    .get(getIndex());

                    new GrupoEditView(stage, grupo).mostrar();
                });

                excluir.setOnAction(e -> {

                    Grupo grupo =
                            getTableView()
                                    .getItems()
                                    .get(getIndex());

                    dao.excluir(grupo.getId());

                    carregarTabela(tabela, dao);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {

                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(pane);
                }
            }
        });

        tabela.getColumns().addAll(
                colId,
                colNome,
                colDescricao,
                colDatacriacao,
                colAcoes
        );

        carregarTabela(tabela, dao);

        root.getChildren().addAll(titulo, btnMenu, btnNovo, tabela);

        stage.setTitle("Lista de Grupos");
        stage.setScene(new Scene(root, 900, 500));
        stage.show();
    }
    private void carregarTabela(TableView<Grupo> grupo, GrupoDAO dao) {
        grupo.setItems(FXCollections.observableArrayList(dao.listarTodos()));
    }

}
