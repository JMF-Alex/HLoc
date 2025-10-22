package views.admin.activity_types;

import db.ActivityTypeDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import models.ActivityTypesModel;
import org.jetbrains.annotations.NotNull;

public class EditActivityTypesPane {

    private final VBox root;

    public EditActivityTypesPane(@NotNull ActivityTypesModel activityTypeModel) {
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

        Label title = new Label("Editar tipo de actividad: " + activityTypeModel.getName());
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #222;");

        GridPane form = new GridPane();
        form.setVgap(25);
        form.setHgap(20);
        form.setAlignment(Pos.CENTER);
        form.setPadding(new Insets(30));

        Label nameLabel = new Label("Nombre:");
        nameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        TextField nameField = new TextField(activityTypeModel.getName());
        nameField.setPromptText("Introduce el nombre del tipo de actividad");
        nameField.setPrefWidth(400);
        nameField.setStyle("-fx-font-size: 15px; -fx-padding: 10;");

        Label descLabel = new Label("Descripción:");
        descLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        TextArea descArea = new TextArea(activityTypeModel.getDescription());
        descArea.setPromptText("Introduce una descripción breve");
        descArea.setPrefWidth(400);
        descArea.setPrefHeight(150);
        descArea.setWrapText(true);
        descArea.setStyle("-fx-font-size: 15px; -fx-padding: 10;");

        form.addRow(0, nameLabel, nameField);
        form.addRow(1, descLabel, descArea);

        Button saveButton = getButton(activityTypeModel, nameField, descArea);

        root.getChildren().addAll(title, form, saveButton);
    }

    private @NotNull Button getButton(@NotNull ActivityTypesModel activityTypeModel, TextField nameField, TextArea descArea) {
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
            ActivityTypeDAO.updateActivityType(
                    activityTypeModel.getId(),
                    nameField.getText(),
                    descArea.getText()
            );

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Tipo de actividad actualizado");
            alert.setHeaderText(null);
            alert.setContentText("Los cambios se han guardado correctamente.");
            alert.showAndWait();

            ActivityTypesPane.setItems();

            ((Stage) root.getScene().getWindow()).close();
        });
        return saveButton;
    }

    public VBox getPane() {
        return root;
    }

    public static void openEditor(@NotNull ActivityTypesModel activityTypesModel) {
        EditActivityTypesPane pane = new EditActivityTypesPane(activityTypesModel);

        Scene scene = new Scene(pane.getPane(), 1000, 700);

        Stage stage = new Stage();
        stage.setTitle("Editar tipo de actividad");
        stage.setScene(scene);
        stage.setMinWidth(900);
        stage.setMinHeight(650);
        stage.centerOnScreen();
        stage.show();
    }
}
