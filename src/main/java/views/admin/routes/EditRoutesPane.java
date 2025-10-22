package views.admin.routes;

import db.RoutesDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import models.RoutesModel;
import org.jetbrains.annotations.NotNull;
import utils.Dialogs;

import java.time.LocalDateTime;

public class EditRoutesPane {

    private final VBox root;

    public EditRoutesPane(@NotNull RoutesModel routeModel) {
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

        Label title = new Label("Editar Ruta: " + routeModel.getTitle());
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #222;");

        GridPane form = new GridPane();
        form.setVgap(25);
        form.setHgap(20);
        form.setAlignment(Pos.CENTER);
        form.setPadding(new Insets(30));

        Label userLabel = new Label("ID de Usuario:");
        TextField userField = new TextField(String.valueOf(routeModel.getUserId()));
        userField.setPrefWidth(400);
        userField.setStyle("-fx-font-size: 15px; -fx-padding: 10;");

        Label activityLabel = new Label("ID de Actividad:");
        TextField activityField = new TextField(String.valueOf(routeModel.getActivityId()));
        activityField.setPrefWidth(400);
        activityField.setStyle("-fx-font-size: 15px; -fx-padding: 10;");

        Label locationLabel = new Label("ID de Ubicación:");
        TextField locationField = new TextField(String.valueOf(routeModel.getLocationId()));
        locationField.setPrefWidth(400);
        locationField.setStyle("-fx-font-size: 15px; -fx-padding: 10;");

        Label titleLabel = new Label("Título:");
        TextField titleField = new TextField(routeModel.getTitle());
        titleField.setPrefWidth(400);
        titleField.setStyle("-fx-font-size: 15px; -fx-padding: 10;");

        Label descriptionLabel = new Label("Descripción:");
        TextArea descriptionArea = new TextArea(routeModel.getDescription());
        descriptionArea.setPrefRowCount(4);
        descriptionArea.setPrefWidth(400);
        descriptionArea.setStyle("-fx-font-size: 15px; -fx-padding: 10;");

        Label distanceLabel = new Label("Distancia (km):");
        TextField distanceField = new TextField(String.valueOf(routeModel.getDistanceKm()));
        distanceField.setPrefWidth(400);
        distanceField.setStyle("-fx-font-size: 15px; -fx-padding: 10;");

        Label elevationLabel = new Label("Desnivel (m):");
        TextField elevationField = new TextField(String.valueOf(routeModel.getElevationM()));
        elevationField.setPrefWidth(400);
        elevationField.setStyle("-fx-font-size: 15px; -fx-padding: 10;");

        Label difficultyLabel = new Label("Dificultad:");
        ComboBox<String> difficultyBox = new ComboBox<>();
        difficultyBox.getItems().addAll("Easy", "Moderate", "Hard", "Expert");
        difficultyBox.setValue(routeModel.getDifficulty() != null ? routeModel.getDifficulty() : "Easy");
        difficultyBox.setPrefWidth(400);

        Label visibilityLabel = new Label("Visibilidad:");
        ComboBox<String> visibilityBox = new ComboBox<>();
        visibilityBox.getItems().addAll("public", "private");
        visibilityBox.setValue(routeModel.getVisibility() != null ? routeModel.getVisibility() : "public");
        visibilityBox.setPrefWidth(400);

        form.addRow(0, userLabel, userField);
        form.addRow(1, activityLabel, activityField);
        form.addRow(2, locationLabel, locationField);
        form.addRow(3, titleLabel, titleField);
        form.addRow(4, descriptionLabel, descriptionArea);
        form.addRow(5, distanceLabel, distanceField);
        form.addRow(6, elevationLabel, elevationField);
        form.addRow(7, difficultyLabel, difficultyBox);
        form.addRow(8, visibilityLabel, visibilityBox);

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
                int userId = Integer.parseInt(userField.getText().trim());
                int activityId = Integer.parseInt(activityField.getText().trim());
                Integer locationId = locationField.getText().trim().isEmpty() ? null :
                        Integer.parseInt(locationField.getText().trim());
                String titleText = titleField.getText().trim();
                String desc = descriptionArea.getText().trim();
                Double distance = distanceField.getText().trim().isEmpty() ? null :
                        Double.parseDouble(distanceField.getText().trim());
                Integer elevation = elevationField.getText().trim().isEmpty() ? null :
                        Integer.parseInt(elevationField.getText().trim());
                String difficulty = difficultyBox.getValue();
                String visibility = visibilityBox.getValue();

                if (titleText.isEmpty()) {
                    Dialogs.AlertDialog.showError("Error", "El título no puede estar vacío.");
                    return;
                }

                routeModel.setUserId(userId);
                routeModel.setActivityId(activityId);
                routeModel.setLocationId(locationId);
                routeModel.setTitle(titleText);
                routeModel.setDescription(desc);
                routeModel.setDistanceKm(distance);
                routeModel.setElevationM(elevation);
                routeModel.setDifficulty(difficulty);
                routeModel.setPublicationDate(LocalDateTime.now());
                routeModel.setVisibility(visibility);

                RoutesDAO.updateRoute(routeModel);

                Dialogs.AlertDialog.showInfo("Actualizado", "Ruta actualizada correctamente.");
                RoutesPane.setItems();
                ((Stage) root.getScene().getWindow()).close();

            } catch (NumberFormatException ex) {
                Dialogs.AlertDialog.showError("Error", "Verifica los campos numéricos (IDs, distancia, desnivel).");
            } catch (Exception ex) {
                Dialogs.AlertDialog.showError("Error", "No se pudo actualizar la ruta: " + ex.getMessage());
            }
        });

        root.getChildren().addAll(title, form, saveButton);
    }

    public VBox getPane() {
        return root;
    }

    public static void openEditor(@NotNull RoutesModel routeModel) {
        EditRoutesPane pane = new EditRoutesPane(routeModel);

        Scene scene = new Scene(pane.getPane(), 1000, 800);

        Stage stage = new Stage();
        stage.setTitle("Editar ruta");
        stage.setScene(scene);
        stage.setMinWidth(900);
        stage.setMinHeight(750);
        stage.centerOnScreen();
        stage.show();
    }
}
