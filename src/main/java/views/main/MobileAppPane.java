package views.main;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class MobileAppPane extends HBox {

    public MobileAppPane() {
        super(20);
        setPadding(new Insets(40));
        setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: #f9f9f9; -fx-border-radius: 12px; -fx-background-radius: 12px;");

        Image image = new Image("https://sc.wklcdn.com/wikiloc/assets/styles/images/home/mobile-app.png", true);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(300);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);

        VBox textBox = new VBox(15);
        textBox.setAlignment(Pos.CENTER_LEFT);

        Label title = new Label("Wikiloc para iPhone & Android");
        title.setFont(Font.font("Arial", 24));
        title.setTextFill(Color.web("#555"));
        title.setStyle("-fx-font-weight: bold;");

        Label description = new Label(
                "Crea tus propias rutas GPS con tu smartphone.\n" +
                        "Súbelas directamente a Wikiloc.\n\n" +
                        "• Navegación Outdoor\n" +
                        "• Mapas offline gratuitos para tus aventuras al aire libre\n" +
                        "• Seguimiento en Vivo\n" +
                        "• Búsqueda por Zona de Paso"
        );
        description.setFont(Font.font("Arial", 14));
        description.setTextFill(Color.web("#666"));

        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER_LEFT);

        Button appStore = new Button("App Store");
        appStore.setStyle("""
            -fx-background-color: black;
            -fx-text-fill: white;
            -fx-font-size: 14px;
            -fx-padding: 8 16 8 16;
            -fx-background-radius: 6px;
        """);

        Button googlePlay = new Button("Google Play");
        googlePlay.setStyle("""
            -fx-background-color: black;
            -fx-text-fill: white;
            -fx-font-size: 14px;
            -fx-padding: 8 16 8 16;
            -fx-background-radius: 6px;
        """);

        Button downloadApp = new Button("Descárgate la App");
        downloadApp.setStyle("""
            -fx-background-color: orange;
            -fx-text-fill: white;
            -fx-font-size: 14px;
            -fx-padding: 8 16 8 16;
            -fx-background-radius: 6px;
        """);

        buttons.getChildren().addAll(appStore, googlePlay, downloadApp);

        textBox.getChildren().addAll(title, description, buttons);

        getChildren().addAll(imageView, textBox);
    }
}
