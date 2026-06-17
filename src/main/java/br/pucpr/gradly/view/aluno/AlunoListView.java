package br.pucpr.gradly.view.aluno;

import br.pucpr.gradly.dao.AlunoDAO;
import br.pucpr.gradly.model.Aluno;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class AlunoListView {

    public void mostrar() {
        AlunoDAO dao = new AlunoDAO();
        Stage stage = new Stage();

        TableView<Aluno> tabela;

        VBox root = new VBox(15);
        root.setPadding(new Insets(15));

        Label titulo = new Label("Lista de Alunos");

        Button btnNovo = new Button("Novo Aluno");

        btnNovo.setOnAction(e -> new AlunoCreateView(stage).mostrar());

        tabela = new TableView<>();

        // criando cada colunas da tabela
        TableColumn<Aluno, Number> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getId()));

        TableColumn<Aluno, Number> colMatricula = new TableColumn<>("Matrícula");
        colMatricula.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getMatricula()));

        TableColumn<Aluno, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNome()));

        TableColumn<Aluno, String> colCurso = new TableColumn<>("Curso");
        colCurso.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCurso()));

        TableColumn<Aluno, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEmail()));

        TableColumn<Aluno, Void> colAcoes = new TableColumn<>("Ações");
        colAcoes.setCellFactory(tc -> new TableCell<>() {

            private Button editar = new Button("Editar");
            private Button excluir = new Button("Excluir");

            private HBox pane = new HBox(5, editar, excluir);

            {
                editar.setOnAction(e -> {

                    Aluno aluno =
                            getTableView()
                                    .getItems()
                                    .get(getIndex());

                    new AlunoEditView(stage, aluno).mostrar();
                });

                excluir.setOnAction(e -> {

                    Aluno aluno =
                            getTableView()
                                    .getItems()
                                    .get(getIndex());

                    dao.excluir(aluno.getId());

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
                colMatricula,
                colNome,
                colCurso,
                colEmail,
                colAcoes
        );

        carregarTabela(tabela, dao);

        root.getChildren().addAll(titulo, btnNovo, tabela);

        stage.setTitle("Lista de Alunos");
        stage.setScene(new Scene(root, 900, 500));
        stage.show();
    }

    private void carregarTabela(TableView<Aluno> tabela,AlunoDAO dao) {
        tabela.setItems(FXCollections.observableArrayList(dao.listarTodos()));
    }
}
