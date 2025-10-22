package views.client.routes;

import db.ActivityTypeDAO;
import db.LocationsDAO;
import db.RoutesDAO;
import db.UserDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import models.CurrentUserModel;
import models.RoutesModel;
import models.UserModel;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.time.LocalDateTime;

public class AddRoutesPane {

    private final VBox root;

    public AddRoutesPane() {
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

        Label title = new Label("Añadir nueva ruta");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #222;");

        GridPane form = new GridPane();
        form.setVgap(20);
        form.setHgap(20);
        form.setAlignment(Pos.CENTER);
        form.setPadding(new Insets(30));

        Label activityLabel = new Label("ID de actividad:");
        TextField activityField = new TextField();
        activityField.setPromptText("Introduce el ID de la actividad");

        Label locationLabel = new Label("ID de ubicación:");
        TextField locationField = new TextField();
        locationField.setPromptText("Introduce el ID de la ubicación (opcional)");

        Label titleLabel = new Label("Título:");
        TextField titleField = new TextField();
        titleField.setPromptText("Título de la ruta");

        Label descriptionLabel = new Label("Descripción:");
        TextArea descriptionArea = new TextArea();
        descriptionArea.setPromptText("Descripción de la ruta");
        descriptionArea.setPrefRowCount(4);

        Label distanceLabel = new Label("Distancia (km):");
        TextField distanceField = new TextField();
        distanceField.setPromptText("Ejemplo: 12.50");

        Label elevationLabel = new Label("Desnivel (m):");
        TextField elevationField = new TextField();
        elevationField.setPromptText("Ejemplo: 800");

        Label difficultyLabel = new Label("Dificultad:");
        ComboBox<String> difficultyBox = new ComboBox<>();
        difficultyBox.getItems().addAll("Easy", "Moderate", "Hard", "Expert");
        difficultyBox.setValue("Easy");

        Label visibilityLabel = new Label("Visibilidad:");
        ComboBox<String> visibilityBox = new ComboBox<>();
        visibilityBox.getItems().addAll("public", "private");
        visibilityBox.setValue("public");

        Label status = new Label();
        status.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");

        form.addRow(0, activityLabel, activityField);
        form.addRow(1, locationLabel, locationField);
        form.addRow(2, titleLabel, titleField);
        form.addRow(3, descriptionLabel, descriptionArea);
        form.addRow(4, distanceLabel, distanceField);
        form.addRow(5, elevationLabel, elevationField);
        form.addRow(6, difficultyLabel, difficultyBox);
        form.addRow(7, visibilityLabel, visibilityBox);

        Button saveButton = getButton(
                activityField, locationField, titleField, descriptionArea,
                distanceField, elevationField, difficultyBox, visibilityBox, status
        );

        root.getChildren().addAll(title, form, saveButton, status);
    }

    private @NotNull Button getButton(TextField activityField, TextField locationField,
                                      TextField titleField, TextArea descriptionArea, TextField distanceField,
                                      TextField elevationField, ComboBox<String> difficultyBox,
                                      ComboBox<String> visibilityBox, Label status) {

        Button saveButton = new Button("Añadir ruta");
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
                int activityId = Integer.parseInt(activityField.getText().trim());
                Integer locationId = locationField.getText().trim().isEmpty() ? null :
                        Integer.parseInt(locationField.getText().trim());
                String title = titleField.getText().trim();
                String description = descriptionArea.getText().trim();
                Double distance = distanceField.getText().trim().isEmpty() ? null :
                        Double.parseDouble(distanceField.getText().trim());
                Integer elevation = elevationField.getText().trim().isEmpty() ? null :
                        Integer.parseInt(elevationField.getText().trim());
                String difficulty = difficultyBox.getValue();
                String visibility = visibilityBox.getValue();

                if (title.isEmpty()) {
                    status.setText("El título es obligatorio");
                    return;
                }

                if (RoutesDAO.routeExists(title)) {
                    status.setText("Ya existe una ruta con este nombre");
                    return;
                }

                if (!ActivityTypeDAO.activityTypesExists(activityId)) {
                    status.setText("La actividad no existe");
                    return;
                }

                if (!LocationsDAO.locationExists(locationId)) {
                    status.setText("La ubicación no existe");
                    return;
                }

                int userId = CurrentUserModel.getUser().getId();
                RoutesModel route = new RoutesModel(
                        0, userId, activityId, locationId, title, description,
                        distance, elevation, difficulty, LocalDateTime.now(), visibility
                );

                RoutesDAO.insertRoute(route);

                RoutesPane.setItems();
                ((Stage) root.getScene().getWindow()).close();

            } catch (NumberFormatException ex) {
                status.setText("Verifica los campos numéricos (IDs, distancia, desnivel)");
            } catch (Exception ex) {
                status.setText("Error al guardar la ruta: " + ex.getMessage());
            }
        });

        return saveButton;
    }

    public VBox getPane() {
        return root;
    }

    public static void openAdder() {
        AddRoutesPane pane = new AddRoutesPane();
        Scene scene = new Scene(pane.getPane(), 900, 800);

        Stage stage = new Stage();
        stage.setTitle("Añadir nueva ruta");
        stage.setScene(scene);
        stage.setMinWidth(900);
        stage.setMinHeight(800);
        stage.centerOnScreen();
        stage.show();
    }
}
