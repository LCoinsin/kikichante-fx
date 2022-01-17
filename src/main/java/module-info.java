module com.kikichante.kikichantefx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens com.kikichante.kikichantefx to javafx.fxml;
    exports com.kikichante.kikichantefx;
    exports com.kikichante.controller;
    opens com.kikichante.controller to javafx.fxml;
}