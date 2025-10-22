package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import org.jetbrains.annotations.NotNull;

public class RoutesPane {

    private final VBox pane;

    public RoutesPane() {
        pane = new VBox(20);
        pane.setAlignment(Pos.TOP_CENTER);
        pane.setPadding(new Insets(20));
        pane.setStyle("-fx-background-color: transparent;");

        Label title = new Label("Explora Rutas");
        title.setStyle("-fx-font-size: 28px; -fx-text-fill: #333; -fx-font-weight: bold;");

        GridPane routesGrid = new GridPane();
        routesGrid.setHgap(20);
        routesGrid.setVgap(20);
        routesGrid.setAlignment(Pos.CENTER);

        for (int i = 1; i <= 8; i++) {
            VBox routeCard = createRouteCard("Ruta " + i, "Duración: " + (30+i*5) + " min, Nivel: Medio");
            routesGrid.add(routeCard, (i-1)%4, (i-1)/4);
        }

        pane.getChildren().addAll(title, routesGrid);
    }

    private @NotNull VBox createRouteCard(String title, String info) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(15));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 10px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10,0,0,5);");
        card.setPrefSize(200, 120);

        Label lblTitle = new Label(title);
        lblTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label lblInfo = new Label(info);
        lblInfo.setWrapText(true);
        lblInfo.setStyle("-fx-font-size: 14px; -fx-text-fill: #555;");

        card.getChildren().addAll(lblTitle, lblInfo);
        return card;
    }

    public VBox getPane() {
        return pane;
    }
}
