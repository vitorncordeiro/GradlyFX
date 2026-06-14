module br.pucpr.gradly {
    requires javafx.controls;
    requires javafx.fxml;

    opens br.pucpr.gradly.model to javafx.base;
    opens br.pucpr.gradly to javafx.fxml;
    exports br.pucpr.gradly.view;
}