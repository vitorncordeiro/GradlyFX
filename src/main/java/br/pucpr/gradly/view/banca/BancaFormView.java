package br.pucpr.gradly.view.banca;

import br.pucpr.gradly.dao.BancaDAO;
import br.pucpr.gradly.dao.ProjetoDAO;
import br.pucpr.gradly.model.Banca;
import br.pucpr.gradly.model.Projeto;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BancaFormView {

    private Banca banca;

    // Formato de data brasileiro DD/MM/AAAA
    private final DateTimeFormatter formato =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public BancaFormView() {
    }

    public BancaFormView(Banca banca) {
        this.banca = banca;
    }

    public void mostrar() {

        Stage stage = new Stage();

        Label lblData = new Label("Data (DD/MM/AAAA)");
        DatePicker dpData = new DatePicker();
        configurarFormatoData(dpData);

        Label lblSala = new Label("Sala");
        TextField txtSala = new TextField();

        Label lblNota = new Label("Nota (0 a 10)");
        TextField txtNota = new TextField();

        Label lblParecer = new Label("Parecer");
        TextArea txtParecer = new TextArea();
        txtParecer.setPrefRowCount(3);

        Label lblProjeto = new Label("Projeto");
        ComboBox<Projeto> cbProjeto = new ComboBox<>();
        ProjetoDAO projetoDAO = new ProjetoDAO();
        cbProjeto.getItems().addAll(projetoDAO.listarTodos());
        // Mostra o título do projeto no ComboBox em vez do objeto cru
        cbProjeto.setConverter(new StringConverter<Projeto>() {
            @Override
            public String toString(Projeto p) {
                return (p == null) ? "" : p.getId() + " - " + p.getTitulo();
            }
            @Override
            public Projeto fromString(String s) {
                return null;
            }
        });

        Button btnSalvar = new Button("Salvar");

        // Modo edição: preenche os campos
        if (banca != null) {
            dpData.setValue(banca.getData());
            txtSala.setText(banca.getSala());
            txtNota.setText(String.valueOf(banca.getNota()));
            txtParecer.setText(banca.getParecer());
            for (Projeto p : cbProjeto.getItems()) {
                if (p.getId() == banca.getProjetoId()) {
                    cbProjeto.setValue(p);
                    break;
                }
            }
        }

        btnSalvar.setOnAction(e -> {

            // Validação de campos obrigatórios
            if (dpData.getValue() == null
                    || txtSala.getText().isBlank()
                    || txtNota.getText().isBlank()
                    || txtParecer.getText().isBlank()
                    || cbProjeto.getValue() == null) {

                mostrarAlerta(Alert.AlertType.WARNING,
                        "Preencha todos os campos.");
                return;
            }

            // Tratamento do campo numérico (nota)
            double nota;
            try {
                nota = Double.parseDouble(txtNota.getText().replace(",", "."));
            } catch (NumberFormatException ex) {
                mostrarAlerta(Alert.AlertType.ERROR,
                        "A nota deve ser um número (ex: 8.5).");
                return;
            }

            if (nota < 0 || nota > 10) {
                mostrarAlerta(Alert.AlertType.ERROR,
                        "A nota deve estar entre 0 e 10.");
                return;
            }

            BancaDAO dao = new BancaDAO();

            if (banca == null) {
                Banca nova = new Banca();
                nova.setData(dpData.getValue());
                nova.setSala(txtSala.getText());
                nova.setNota(nota);
                nova.setParecer(txtParecer.getText());
                nova.setProjetoId(cbProjeto.getValue().getId());
                dao.inserir(nova);
            } else {
                banca.setData(dpData.getValue());
                banca.setSala(txtSala.getText());
                banca.setNota(nota);
                banca.setParecer(txtParecer.getText());
                banca.setProjetoId(cbProjeto.getValue().getId());
                dao.atualizar(banca);
            }

            stage.close();
        });

        GridPane root = new GridPane();
        root.setHgap(10);
        root.setVgap(10);

        root.add(lblData, 0, 0);      root.add(dpData, 1, 0);
        root.add(lblSala, 0, 1);      root.add(txtSala, 1, 1);
        root.add(lblNota, 0, 2);      root.add(txtNota, 1, 2);
        root.add(lblParecer, 0, 3);   root.add(txtParecer, 1, 3);
        root.add(lblProjeto, 0, 4);   root.add(cbProjeto, 1, 4);
        root.add(btnSalvar, 1, 5);

        Scene scene = new Scene(root, 450, 320);

        stage.setTitle(banca == null ? "Cadastrar Banca" : "Editar Banca");
        stage.setScene(scene);
        stage.show();
    }

    // Garante exibição/parse da data no formato brasileiro
    private void configurarFormatoData(DatePicker dp) {
        dp.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                return (date == null) ? "" : formato.format(date);
            }
            @Override
            public LocalDate fromString(String texto) {
                if (texto == null || texto.isBlank()) {
                    return null;
                }
                return LocalDate.parse(texto, formato);
            }
        });
    }

    private void mostrarAlerta(Alert.AlertType tipo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle("Aviso");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}