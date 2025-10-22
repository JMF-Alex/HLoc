package views.main;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class HomePane extends StackPane {

    public HomePane() {
        VBox home = new VBox(25);
        home.setAlignment(Pos.TOP_CENTER);
        home.setPadding(new Insets(40, 0, 0, 0));
        home.setStyle("-fx-background-color: white;");

        Label title = new Label("Rutas del Mundo");
        title.setFont(Font.font("Arial", 36));
        title.setTextFill(Color.web("#333"));
        title.setStyle("-fx-font-weight: bold;");

        Label desc = new Label("Descubre rutas al aire libre a pie, en bici y mucho más");
        desc.setFont(Font.font("Arial", 16));
        desc.setTextFill(Color.web("#555"));

        VBox stats = new VBox(15);
        stats.setAlignment(Pos.CENTER);
        stats.setPadding(new Insets(20));
        stats.setStyle("-fx-background-color: #3CB371; -fx-background-radius: 12px;");

        Label counter = new Label("69.869.491 rutas");
        counter.setStyle("""
            -fx-font-size: 40px;
            -fx-font-weight: bold;
            -fx-text-fill: white;
        """);

        Label text = new Label("Ya somos más de 18 millones de aventureros compartiendo experiencias al aire libre.");
        text.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");

        Button upload = new Button("⬆ Sube tu ruta");
        upload.setStyle("""
            -fx-background-color: white;
            -fx-text-fill: #3CB371;
            -fx-font-size: 16px;
            -fx-font-weight: bold;
            -fx-background-radius: 25px;
            -fx-padding: 10 22 10 22;
        """);

        stats.getChildren().addAll(counter, text, upload);

        MapPane mapPane = new MapPane();
        home.getChildren().addAll(title, desc, mapPane, stats);
        getChildren().add(home);
    }
}
