package br.pucpr.gradly.view.referencia;

import br.pucpr.gradly.dao.ReferenciaDAO;
import br.pucpr.gradly.model.Referencia;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ReferenciaFormView {

    private Referencia referencia;

    public ReferenciaFormView() {
    }

    public ReferenciaFormView(Referencia referencia) {
        this.referencia = referencia;
    }

    public void mostrar() {

        Stage stage = new Stage();

        Label lblTitulo = new Label("Titulo");
        TextField txtTitulo = new TextField();

        Label lblAutor = new Label("Autor");
        TextField txtAutor = new TextField();

        Label lblAno = new Label("Ano");
        Spinner<Integer> spAno = new Spinner<>(1900, 2100, 2026);

        Label lblTipo = new Label("Tipo");

        ComboBox<String> cbTipo = new ComboBox<>();

        cbTipo.getItems().addAll(
                "Artigo",
                "Livro",
                "Site"
        );

        Button btnSalvar = new Button("Salvar");

        // Se estiver editando, preenche os campos
        if (referencia != null) {

            txtTitulo.setText(
                    referencia.getTitulo()
            );

            txtAutor.setText(
                    referencia.getAutor()
            );

            spAno.getValueFactory().setValue(
                    referencia.getAno()
            );

            cbTipo.setValue(
                    referencia.getTipo()
            );
        }

        btnSalvar.setOnAction(e -> {

            ReferenciaDAO dao = new ReferenciaDAO();

            // Cadastro
            if (referencia == null) {

                Referencia novaReferencia = new Referencia();

                novaReferencia.setTitulo(
                        txtTitulo.getText()
                );

                novaReferencia.setAutor(
                        txtAutor.getText()
                );

                novaReferencia.setAno(
                        spAno.getValue()
                );

                novaReferencia.setTipo(
                        cbTipo.getValue()
                );

                dao.inserir(novaReferencia);

            }
            // Edição
            else {

                referencia.setTitulo(
                        txtTitulo.getText()
                );

                referencia.setAutor(
                        txtAutor.getText()
                );

                referencia.setAno(
                        spAno.getValue()
                );

                referencia.setTipo(
                        cbTipo.getValue()
                );

                dao.atualizar(referencia);
            }

            stage.close();
        });

        GridPane root = new GridPane();

        root.setHgap(10);
        root.setVgap(10);

        root.add(lblTitulo, 0, 0);
        root.add(txtTitulo, 1, 0);

        root.add(lblAutor, 0, 2);
        root.add(txtAutor, 1, 2);

        root.add(lblAno, 0, 3);
        root.add(spAno, 1, 3);

        root.add(lblTipo, 0, 1);
        root.add(cbTipo, 1, 1);

        root.add(btnSalvar, 1, 4);

        Scene scene = new Scene(root, 400, 250);

        stage.setTitle(
                referencia == null ?
                        "Cadastrar Referencia" :
                        "Editar Referencia"
        );

        stage.setScene(scene);
        stage.show();
    }
}
