module main {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires org.jetbrains.annotations;
    requires java.sql;
    requires java.desktop;
    requires de.mkammerer.argon2.nolibs;
    requires org.slf4j;

    opens main to javafx.fxml;
    exports main;
    exports db;
    opens db to javafx.fxml;
}