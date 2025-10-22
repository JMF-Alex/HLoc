package views.admin.route_points;

import db.RoutePointsDAO;
import db.RoutesDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import models.RoutePointsModel;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public class AddRoutePointsPane {

    private final VBox root;

    public AddRoutePointsPane() {
        root = new VBox(30);
        root.setPadding(new Insets(50));
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("""
            -fx-background-color: #f3f6fa;
            -fx-border-color: #d0d0d0;
            -fx-border-radius: 15px;
            -fx-background-radius: 15px;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 25, 0, 0, 8);
        """);

        Label title = new Label("Añadir punto de ruta");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #222;");

        GridPane form = new GridPane();
        form.setVgap(20);
        form.setHgap(20);
        form.setAlignment(Pos.CENTER);
        form.setPadding(new Insets(30));

        Label routeIdLabel = new Label("ID de la ruta:");
        TextField routeIdField = new TextField();
        routeIdField.setPromptText("Introduce el ID de la ruta");

        Label orderLabel = new Label("Orden del punto:");
        TextField orderField = new TextField();
        orderField.setPromptText("Ejemplo: 1, 2, 3...");

        Label latLabel = new Label("Latitud:");
        TextField latField = new TextField();
        latField.setPromptText("Ejemplo: 40.4167750");

        Label lonLabel = new Label("Longitud:");
        TextField lonField = new TextField();
        lonField.setPromptText("Ejemplo: -3.7037900");

        Label altLabel = new Label("Altitud (m):");
        TextField altField = new TextField();
        altField.setPromptText("Ejemplo: 250.50 (opcional)");

        Label status = new Label();
        status.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");

        form.addRow(0, routeIdLabel, routeIdField);
        form.addRow(1, orderLabel, orderField);
        form.addRow(2, latLabel, latField);
        form.addRow(3, lonLabel, lonField);
        form.addRow(4, altLabel, altField);

        Button saveButton = getButton(routeIdField, orderField, latField, lonField, altField, status);

        root.getChildren().addAll(title, form, saveButton, status);
    }

    private @NotNull Button getButton(TextField routeIdField, TextField orderField, TextField latField,
                                      TextField lonField, TextField altField, Label status) {

        Button saveButton = new Button("Añadir punto");
        saveButton.setStyle("""
            -fx-background-color: #10B981;
            -fx-text-fill: white;
            -fx-font-weight: bold;
            -fx-background-radius: 10px;
            -fx-padding: 15 40;
            -fx-font-size: 16px;
        """);

        saveButton.setOnAction(e -> {
            try {
                int routeId = Integer.parseInt(routeIdField.getText().trim());
                int pointOrder = Integer.parseInt(orderField.getText().trim());
                BigDecimal lat = new BigDecimal(latField.getText().trim());
                BigDecimal lon = new BigDecimal(lonField.getText().trim());
                BigDecimal altitude = altField.getText().trim().isEmpty() ? null :
                        new BigDecimal(altField.getText().trim());

                if (lat.compareTo(BigDecimal.valueOf(-90)) < 0 || lat.compareTo(BigDecimal.valueOf(90)) > 0 ||
                        lon.compareTo(BigDecimal.valueOf(-180)) < 0 || lon.compareTo(BigDecimal.valueOf(180)) > 0) {
                    status.setText("Latitud o longitud fuera de rango válido.");
                    return;
                }

                if (!RoutesDAO.routeExists(routeId)) {
                    status.setText("La ruta no existe");
                    return;
                }

                RoutePointsModel point = new RoutePointsModel(
                        0, routeId, pointOrder, lat, lon, altitude
                );

                RoutePointsDAO.insertRoutePoint(point);

                RoutePointsPane.setItems();

                ((Stage) root.getScene().getWindow()).close();

            } catch (NumberFormatException ex) {
                status.setText("Verifica los campos numéricos (IDs, lat, lon, altitud)");
            } catch (Exception ex) {
                status.setText("Error al guardar el punto: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        return saveButton;
    }

    public VBox getPane() {
        return root;
    }

    public static void openAdder() {
        AddRoutePointsPane pane = new AddRoutePointsPane();
        Scene scene = new Scene(pane.getPane(), 700, 600);

        Stage stage = new Stage();
        stage.setTitle("Añadir nuevo punto de ruta");
        stage.setScene(scene);
        stage.setMinWidth(700);
        stage.setMinHeight(600);
        stage.centerOnScreen();
        stage.show();
    }
}
