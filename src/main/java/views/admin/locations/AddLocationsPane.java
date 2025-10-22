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

import java.math.BigDecimal;

public class AddLocationsPane {

    private final VBox root;

    public AddLocationsPane() {
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

        Label title = new Label("Añadir nueva ubicación");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #222;");

        GridPane form = new GridPane();
        form.setVgap(20);
        form.setHgap(20);
        form.setAlignment(Pos.CENTER);
        form.setPadding(new Insets(30));

        Label countryLabel = new Label("País:");
        TextField countryField = new TextField();
        countryField.setPromptText("Introduce el país");

        Label regionLabel = new Label("Región:");
        TextField regionField = new TextField();
        regionField.setPromptText("Introduce la región (opcional)");

        Label cityLabel = new Label("Ciudad:");
        TextField cityField = new TextField();
        cityField.setPromptText("Introduce la ciudad (opcional)");

        Label latLabel = new Label("Latitud:");
        TextField latField = new TextField();
        latField.setPromptText("Ejemplo: 40.416775");

        Label lonLabel = new Label("Longitud:");
        TextField lonField = new TextField();
        lonField.setPromptText("Ejemplo: -3.703790");

        Label status = new Label();
        status.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");

        form.addRow(0, countryLabel, countryField);
        form.addRow(1, regionLabel, regionField);
        form.addRow(2, cityLabel, cityField);
        form.addRow(3, latLabel, latField);
        form.addRow(4, lonLabel, lonField);

        Button saveButton = getButton(countryField, regionField, cityField, latField, lonField, status);

        root.getChildren().addAll(title, form, saveButton, status);
    }

    private @NotNull Button getButton(TextField countryField, TextField regionField, TextField cityField,
                                      TextField latField, TextField lonField, Label status) {

        Button saveButton = new Button("Añadir ubicación");
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
                String country = countryField.getText().trim();
                String region = regionField.getText().trim().isEmpty() ? null : regionField.getText().trim();
                String city = cityField.getText().trim().isEmpty() ? null : cityField.getText().trim();

                if (country.isEmpty()) {
                    status.setText("El país es obligatorio");
                    return;
                }

                BigDecimal lat = latField.getText().trim().isEmpty() ? null :
                        new BigDecimal(latField.getText().trim());
                BigDecimal lon = lonField.getText().trim().isEmpty() ? null :
                        new BigDecimal(lonField.getText().trim());

                LocationsModel location = new LocationsModel(0, country, region, city, lat, lon);

                LocationsDAO.insertLocation(location);

                LocationsPane.setItems();
                ((Stage) root.getScene().getWindow()).close();

            } catch (NumberFormatException ex) {
                status.setText("Verifica los campos de latitud y longitud");
            } catch (Exception ex) {
                status.setText("Error al guardar la ubicación: " + ex.getMessage());
            }
        });

        return saveButton;
    }

    public VBox getPane() {
        return root;
    }

    public static void openAdder() {
        AddLocationsPane pane = new AddLocationsPane();
        Scene scene = new Scene(pane.getPane(), 600, 600);

        Stage stage = new Stage();
        stage.setTitle("Añadir nueva ubicación");
        stage.setScene(scene);
        stage.setMinWidth(600);
        stage.setMinHeight(600);
        stage.centerOnScreen();
        stage.show();
    }
}
