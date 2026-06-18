package br.pucpr.gradly.view.comentario;

import br.pucpr.gradly.dao.ComentarioDAO;
import br.pucpr.gradly.exception.GradlyException;
import br.pucpr.gradly.model.Comentario;
import br.pucpr.gradly.view.MainMenuView;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;

public class ComentarioView {

    private final ComentarioDAO dao = new ComentarioDAO();

    private TextArea txtTexto;
    private TextField txtAutor;
    private DatePicker dpData;
    private TextField txtIdDocumento;
    private TableView<Comentario> tabela;
    private Stage stage;

    public void mostrar() {
        stage = new Stage();
        stage.setTitle("GRADLY - Comentários");

        GridPane formulario = criarFormulario();
        HBox botoes = criarBotoes();
        tabela = criarTabela();

        VBox raiz = new VBox(10, formulario, botoes, tabela);
        raiz.setPadding(new Insets(15));

        Scene cena = new Scene(raiz, 750, 500);
        stage.setScene(cena);
        stage.show();

        carregarTabela();
    }

    private GridPane criarFormulario() {
        txtTexto = new TextArea();
        txtTexto.setPrefRowCount(3);
        txtAutor = new TextField();
        dpData = new DatePicker(LocalDate.now());
        txtIdDocumento = new TextField("0");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(new Label("Texto:"), 0, 0);
        grid.add(txtTexto, 1, 0);
        grid.add(new Label("Autor:"), 0, 1);
        grid.add(txtAutor, 1, 1);
        grid.add(new Label("Data:"), 0, 2);
        grid.add(dpData, 1, 2);
        grid.add(new Label("ID Documento:"), 0, 3);
        grid.add(txtIdDocumento, 1, 3);

        return grid;
    }

    private HBox criarBotoes() {
        Button btnInserir = new Button("Inserir");
        Button btnListar = new Button("Listar Todos");
        Button btnAtualizar = new Button("Atualizar");
        Button btnExcluir = new Button("Excluir");
        Button btnVoltar = new Button("Voltar");

        btnInserir.setOnAction(e -> inserir());
        btnListar.setOnAction(e -> carregarTabela());
        btnAtualizar.setOnAction(e -> atualizar());
        btnExcluir.setOnAction(e -> excluir());
        btnVoltar.setOnAction(e -> {
            stage.close();
            new MainMenuView().start(new Stage());
        });

        HBox box = new HBox(10, btnInserir, btnListar, btnAtualizar, btnExcluir, btnVoltar);
        box.setPadding(new Insets(5, 0, 5, 0));
        return box;
    }

    private TableView<Comentario> criarTabela() {
        TableView<Comentario> table = new TableView<>();

        TableColumn<Comentario, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Comentario, String> colTexto = new TableColumn<>("Texto");
        colTexto.setCellValueFactory(new PropertyValueFactory<>("texto"));
        colTexto.setPrefWidth(200);

        TableColumn<Comentario, String> colAutor = new TableColumn<>("Autor");
        colAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));

        TableColumn<Comentario, String> colData = new TableColumn<>("Data");
        colData.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(
                cell.getValue().getDataCriacao().toString()));

        TableColumn<Comentario, Integer> colDoc = new TableColumn<>("Doc.");
        colDoc.setCellValueFactory(new PropertyValueFactory<>("idDocumento"));

        table.getColumns().addAll(colId, colTexto, colAutor, colData, colDoc);
        table.setPrefHeight(200);

        table.getSelectionModel().selectedItemProperty().addListener((obs, antigo, selecionado) -> {
            if (selecionado != null) {
                preencherCampos(selecionado);
            }
        });

        return table;
    }

    private void inserir() {
        try {
            if (txtTexto.getText().trim().isEmpty()) {
                mostrarAlerta("O texto do comentário não pode estar vazio.");
                return;
            }
            if (txtAutor.getText().trim().isEmpty()) {
                mostrarAlerta("Preencha o autor.");
                return;
            }

            Comentario c = new Comentario();
            c.setTexto(txtTexto.getText().trim());
            c.setAutor(txtAutor.getText().trim());
            c.setDataCriacao(dpData.getValue());
            c.setIdDocumento(Integer.parseInt(txtIdDocumento.getText().trim()));

            dao.inserir(c);
            mostrarInfo("Comentário inserido com sucesso!");
            carregarTabela();
        } catch (NumberFormatException e) {
            mostrarAlerta("ID do documento deve ser um número.");
        } catch (GradlyException e) {
            mostrarAlerta(e.getMessage());
        }
    }

    private void atualizar() {
        try {
            Comentario selecionado = tabela.getSelectionModel().getSelectedItem();
            if (selecionado == null) {
                mostrarAlerta("Selecione um comentário na tabela.");
                return;
            }
            if (txtTexto.getText().trim().isEmpty()) {
                mostrarAlerta("O texto do comentário não pode estar vazio.");
                return;
            }

            selecionado.setTexto(txtTexto.getText().trim());
            selecionado.setAutor(txtAutor.getText().trim());
            selecionado.setDataCriacao(dpData.getValue());
            selecionado.setIdDocumento(Integer.parseInt(txtIdDocumento.getText().trim()));

            dao.atualizar(selecionado);
            mostrarInfo("Comentário atualizado!");
            carregarTabela();
        } catch (NumberFormatException e) {
            mostrarAlerta("ID do documento deve ser um número.");
        } catch (GradlyException e) {
            mostrarAlerta(e.getMessage());
        }
    }

    private void excluir() {
        try {
            Comentario selecionado = tabela.getSelectionModel().getSelectedItem();
            if (selecionado == null) {
                mostrarAlerta("Selecione um comentário na tabela.");
                return;
            }

            dao.excluir(selecionado.getId());
            mostrarInfo("Comentário excluído!");
            carregarTabela();
        } catch (GradlyException e) {
            mostrarAlerta(e.getMessage());
        }
    }

    private void carregarTabela() {
        try {
            List<Comentario> lista = dao.listarTodos();
            tabela.setItems(FXCollections.observableArrayList(lista));
        } catch (GradlyException e) {
            mostrarAlerta(e.getMessage());
        }
    }

    private void preencherCampos(Comentario c) {
        txtTexto.setText(c.getTexto());
        txtAutor.setText(c.getAutor());
        dpData.setValue(c.getDataCriacao());
        txtIdDocumento.setText(String.valueOf(c.getIdDocumento()));
    }

    private void mostrarAlerta(String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Atenção");
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

    private void mostrarInfo(String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Sucesso");
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}
