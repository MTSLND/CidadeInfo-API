module com.cidadeinfoapi {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires json.simple;
    requires com.fasterxml.jackson.databind;

    opens com.cidadeinfo.cidadeinfoapi to javafx.fxml;
    exports com.cidadeinfo.cidadeinfoapi;
}