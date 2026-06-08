module br.pucpr.gradly {
    requires javafx.controls;
    requires javafx.fxml;


    opens br.pucpr.gradly to javafx.fxml;
    exports br.pucpr.gradly;
}