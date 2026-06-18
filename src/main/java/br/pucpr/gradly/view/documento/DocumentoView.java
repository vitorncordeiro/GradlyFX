package br.pucpr.gradly.view.documento;

import br.pucpr.gradly.dao.DocumentoDAO;
import br.pucpr.gradly.exception.GradlyException;
import br.pucpr.gradly.model.Documento;
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

public class DocumentoView {

    private final DocumentoDAO dao = new DocumentoDAO();

    private TextField txtTitulo;
    private TextArea txtConteudo;
    private TextField txtVersao;
    private DatePicker dpData;
    private TableView<Documento> tabela;
    private Stage stage;

    public void mostrar() {
        stage = new Stage();
        stage.setTitle("GRADLY - Documentos");

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
        txtTitulo = new TextField();
        txtConteudo = new TextArea();
        txtConteudo.setPrefRowCount(3);
        txtVersao = new TextField("1");
        dpData = new DatePicker(LocalDate.now());

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(new Label("Título:"), 0, 0);
        grid.add(txtTitulo, 1, 0);
        grid.add(new Label("Conteúdo:"), 0, 1);
        grid.add(txtConteudo, 1, 1);
        grid.add(new Label("Versão:"), 0, 2);
        grid.add(txtVersao, 1, 2);
        grid.add(new Label("Data:"), 0, 3);
        grid.add(dpData, 1, 3);

        return grid;
    }

    private HBox criarBotoes() {
        Button btnInserir = new Button("Inserir");
        Button btnListar = new Button("Listar Todos");
        Button btnNovaVersao = new Button("Nova Versão");
        Button btnAtualizar = new Button("Atualizar");
        Button btnExcluir = new Button("Excluir");
        Button btnVoltar = new Button("Voltar");

        btnInserir.setOnAction(e -> inserir());
        btnListar.setOnAction(e -> carregarTabela());
        btnNovaVersao.setOnAction(e -> novaVersao());
        btnAtualizar.setOnAction(e -> atualizar());
        btnExcluir.setOnAction(e -> excluir());
        btnVoltar.setOnAction(e -> {
            stage.close();
            new MainMenuView().start(new Stage());
        });

        HBox box = new HBox(10, btnInserir, btnListar, btnNovaVersao, btnAtualizar, btnExcluir, btnVoltar);
        box.setPadding(new Insets(5, 0, 5, 0));
        return box;
    }

    private TableView<Documento> criarTabela() {
        TableView<Documento> table = new TableView<>();

        TableColumn<Documento, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Documento, String> colTitulo = new TableColumn<>("Título");
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));

        TableColumn<Documento, Integer> colVersao = new TableColumn<>("Versão");
        colVersao.setCellValueFactory(new PropertyValueFactory<>("versao"));

        TableColumn<Documento, String> colData = new TableColumn<>("Data");
        colData.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(
                cell.getValue().getDataCriacao().toString()));

        table.getColumns().addAll(colId, colTitulo, colVersao, colData);
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
            if (txtTitulo.getText().trim().isEmpty()) {
                mostrarAlerta("Preencha o título.");
                return;
            }

            Documento d = new Documento();
            d.setTitulo(txtTitulo.getText().trim());
            d.setConteudo(txtConteudo.getText());
            d.setVersao(Integer.parseInt(txtVersao.getText().trim()));
            d.setDataCriacao(dpData.getValue());

            dao.inserir(d);
            mostrarInfo("Documento inserido com sucesso!");
            carregarTabela();
        } catch (NumberFormatException e) {
            mostrarAlerta("Versão deve ser um número.");
        } catch (GradlyException e) {
            mostrarAlerta(e.getMessage());
        }
    }

    private void novaVersao() {
        try {
            Documento selecionado = tabela.getSelectionModel().getSelectedItem();
            if (selecionado == null) {
                mostrarAlerta("Selecione um documento na tabela.");
                return;
            }

            Documento nova = new Documento();
            nova.setTitulo(selecionado.getTitulo());
            nova.setConteudo(txtConteudo.getText());
            nova.setDataCriacao(LocalDate.now());

            dao.salvarNovaVersao(nova);
            mostrarInfo("Nova versão criada!");
            carregarTabela();
        } catch (GradlyException e) {
            mostrarAlerta(e.getMessage());
        }
    }

    private void atualizar() {
        try {
            Documento selecionado = tabela.getSelectionModel().getSelectedItem();
            if (selecionado == null) {
                mostrarAlerta("Selecione um documento na tabela.");
                return;
            }

            selecionado.setTitulo(txtTitulo.getText().trim());
            selecionado.setConteudo(txtConteudo.getText());
            selecionado.setVersao(Integer.parseInt(txtVersao.getText().trim()));
            selecionado.setDataCriacao(dpData.getValue());

            dao.atualizar(selecionado);
            mostrarInfo("Documento atualizado!");
            carregarTabela();
        } catch (NumberFormatException e) {
            mostrarAlerta("Versão deve ser um número.");
        } catch (GradlyException e) {
            mostrarAlerta(e.getMessage());
        }
    }

    private void excluir() {
        try {
            Documento selecionado = tabela.getSelectionModel().getSelectedItem();
            if (selecionado == null) {
                mostrarAlerta("Selecione um documento na tabela.");
                return;
            }

            dao.excluir(selecionado.getId());
            mostrarInfo("Documento excluído!");
            carregarTabela();
        } catch (GradlyException e) {
            mostrarAlerta(e.getMessage());
        }
    }

    private void carregarTabela() {
        try {
            List<Documento> lista = dao.listarTodos();
            tabela.setItems(FXCollections.observableArrayList(lista));
        } catch (GradlyException e) {
            mostrarAlerta(e.getMessage());
        }
    }

    private void preencherCampos(Documento d) {
        txtTitulo.setText(d.getTitulo());
        txtConteudo.setText(d.getConteudo());
        txtVersao.setText(String.valueOf(d.getVersao()));
        dpData.setValue(d.getDataCriacao());
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
