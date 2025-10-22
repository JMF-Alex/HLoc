package views.admin.route_points;

import db.RoutePointsDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import models.RoutePointsModel;
import org.jetbrains.annotations.NotNull;
import utils.Dialogs;

import java.math.BigDecimal;

public class EditRoutePointsPane {

    private final VBox root;

    public EditRoutePointsPane(@NotNull RoutePointsModel pointModel) {
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

        Label title = new Label("Editar Punto ID: " + pointModel.getId());
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #222;");

        GridPane form = new GridPane();
        form.setVgap(25);
        form.setHgap(20);
        form.setAlignment(Pos.CENTER);
        form.setPadding(new Insets(30));

        // Campos principales
        Label routeIdLabel = new Label("ID de Ruta:");
        TextField routeIdField = new TextField(String.valueOf(pointModel.getRouteId()));
        routeIdField.setPrefWidth(400);
        routeIdField.setStyle("-fx-font-size: 15px; -fx-padding: 10;");

        Label orderLabel = new Label("Orden del Punto:");
        TextField orderField = new TextField(String.valueOf(pointModel.getPointOrder()));
        orderField.setPrefWidth(400);
        orderField.setStyle("-fx-font-size: 15px; -fx-padding: 10;");

        Label latLabel = new Label("Latitud:");
        TextField latField = new TextField(pointModel.getLat() != null ? pointModel.getLat().toString() : "");
        latField.setPrefWidth(400);
        latField.setStyle("-fx-font-size: 15px; -fx-padding: 10;");

        Label lonLabel = new Label("Longitud:");
        TextField lonField = new TextField(pointModel.getLon() != null ? pointModel.getLon().toString() : "");
        lonField.setPrefWidth(400);
        lonField.setStyle("-fx-font-size: 15px; -fx-padding: 10;");

        Label altLabel = new Label("Altitud (m):");
        TextField altField = new TextField(pointModel.getAltitudeM() != null ? pointModel.getAltitudeM().toString() : "");
        altField.setPrefWidth(400);
        altField.setStyle("-fx-font-size: 15px; -fx-padding: 10;");

        form.addRow(0, routeIdLabel, routeIdField);
        form.addRow(1, orderLabel, orderField);
        form.addRow(2, latLabel, latField);
        form.addRow(3, lonLabel, lonField);
        form.addRow(4, altLabel, altField);

        Button saveButton = new Button("Guardar cambios");
        saveButton.setStyle("""
            -fx-background-color: #3b82f6;
            -fx-text-fill: white;
            -fx-font-weight: bold;
            -fx-background-radius: 10px;
            -fx-padding: 15 40;
            -fx-font-size: 16px;
        """);

        saveButton.setOnAction(e -> {
            try {
                int routeId = Integer.parseInt(routeIdField.getText().trim());
                int order = Integer.parseInt(orderField.getText().trim());
                BigDecimal lat = new BigDecimal(latField.getText().trim());
                BigDecimal lon = new BigDecimal(lonField.getText().trim());
                BigDecimal altitude = altField.getText().trim().isEmpty() ? null :
                        new BigDecimal(altField.getText().trim());

                if (lat.compareTo(BigDecimal.valueOf(-90)) < 0 || lat.compareTo(BigDecimal.valueOf(90)) > 0 ||
                        lon.compareTo(BigDecimal.valueOf(-180)) < 0 || lon.compareTo(BigDecimal.valueOf(180)) > 0) {
                    Dialogs.AlertDialog.showError("Error", "Latitud o longitud fuera de rango válido.");
                    return;
                }

                pointModel.setRouteId(routeId);
                pointModel.setPointOrder(order);
                pointModel.setLat(lat);
                pointModel.setLon(lon);
                pointModel.setAltitudeM(altitude);

                RoutePointsDAO.updateRoutePoint(pointModel);

                Dialogs.AlertDialog.showInfo("Actualizado", "Punto de ruta actualizado correctamente.");
                RoutePointsPane.setItems();
                ((Stage) root.getScene().getWindow()).close();

            } catch (NumberFormatException ex) {
                Dialogs.AlertDialog.showError("Error", "Verifica los campos numéricos (IDs, lat, lon, altitud).");
            } catch (Exception ex) {
                Dialogs.AlertDialog.showError("Error", "No se pudo actualizar el punto: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        root.getChildren().addAll(title, form, saveButton);
    }

    public VBox getPane() {
        return root;
    }

    public static void openEditor(@NotNull RoutePointsModel pointModel) {
        EditRoutePointsPane pane = new EditRoutePointsPane(pointModel);
        Scene scene = new Scene(pane.getPane(), 800, 700);

        Stage stage = new Stage();
        stage.setTitle("Editar punto de ruta");
        stage.setScene(scene);
        stage.setMinWidth(750);
        stage.setMinHeight(650);
        stage.centerOnScreen();
        stage.show();
    }
}
