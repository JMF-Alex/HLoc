package views.admin.locations;

import db.LocationsDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import models.LocationsModel;
import org.jetbrains.annotations.NotNull;
import utils.Dialogs;

import java.math.BigDecimal;

public class EditLocationsPane {

    private final VBox root;

    public EditLocationsPane(@NotNull LocationsModel locationModel) {
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

        Label title = new Label("Editar Ubicación: " + (locationModel.getCity() != null ? locationModel.getCity() : "Sin ciudad"));
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #222;");

        GridPane form = new GridPane();
        form.setVgap(25);
        form.setHgap(20);
        form.setAlignment(Pos.CENTER);
        form.setPadding(new Insets(30));

        // Campos
        Label countryLabel = new Label("País:");
        TextField countryField = new TextField(locationModel.getCountry());
        countryField.setPrefWidth(400);
        countryField.setStyle("-fx-font-size: 15px; -fx-padding: 10;");

        Label regionLabel = new Label("Región:");
        TextField regionField = new TextField(locationModel.getRegion() != null ? locationModel.getRegion() : "");
        regionField.setPrefWidth(400);
        regionField.setStyle("-fx-font-size: 15px; -fx-padding: 10;");

        Label cityLabel = new Label("Ciudad:");
        TextField cityField = new TextField(locationModel.getCity() != null ? locationModel.getCity() : "");
        cityField.setPrefWidth(400);
        cityField.setStyle("-fx-font-size: 15px; -fx-padding: 10;");

        Label latLabel = new Label("Latitud:");
        TextField latField = new TextField(locationModel.getLat() != null ? locationModel.getLat().toString() : "");
        latField.setPrefWidth(400);
        latField.setStyle("-fx-font-size: 15px; -fx-padding: 10;");

        Label lonLabel = new Label("Longitud:");
        TextField lonField = new TextField(locationModel.getLon() != null ? locationModel.getLon().toString() : "");
        lonField.setPrefWidth(400);
        lonField.setStyle("-fx-font-size: 15px; -fx-padding: 10;");

        form.addRow(0, countryLabel, countryField);
        form.addRow(1, regionLabel, regionField);
        form.addRow(2, cityLabel, cityField);
        form.addRow(3, latLabel, latField);
        form.addRow(4, lonLabel, lonField);

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
                String country = countryField.getText().trim();
                String region = regionField.getText().trim().isEmpty() ? null : regionField.getText().trim();
                String city = cityField.getText().trim().isEmpty() ? null : cityField.getText().trim();

                if (country.isEmpty()) {
                    Dialogs.AlertDialog.showError("Error", "El país es obligatorio.");
                    return;
                }

                BigDecimal lat = latField.getText().trim().isEmpty() ? null : new BigDecimal(latField.getText().trim());
                BigDecimal lon = lonField.getText().trim().isEmpty() ? null : new BigDecimal(lonField.getText().trim());

                locationModel.setCountry(country);
                locationModel.setRegion(region);
                locationModel.setCity(city);
                locationModel.setLat(lat);
                locationModel.setLon(lon);

                LocationsDAO.updateLocation(locationModel);

                Dialogs.AlertDialog.showInfo("Actualizado", "Ubicación actualizada correctamente.");
                LocationsPane.setItems();
                ((Stage) root.getScene().getWindow()).close();

            } catch (NumberFormatException ex) {
                Dialogs.AlertDialog.showError("Error", "Verifica los campos de latitud y longitud.");
            } catch (Exception ex) {
                Dialogs.AlertDialog.showError("Error", "No se pudo actualizar la ubicación: " + ex.getMessage());
            }
        });

        root.getChildren().addAll(title, form, saveButton);
    }

    public VBox getPane() {
        return root;
    }

    public static void openEditor(@NotNull LocationsModel locationModel) {
        EditLocationsPane pane = new EditLocationsPane(locationModel);

        Scene scene = new Scene(pane.getPane(), 700, 600);

        Stage stage = new Stage();
        stage.setTitle("Editar ubicación");
        stage.setScene(scene);
        stage.setMinWidth(700);
        stage.setMinHeight(600);
        stage.centerOnScreen();
        stage.show();
    }
}
