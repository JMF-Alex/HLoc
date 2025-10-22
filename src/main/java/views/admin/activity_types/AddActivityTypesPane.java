package views.admin.activity_types;

import db.ActivityTypeDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

public class AddActivityTypesPane {

    private final VBox root;

    public AddActivityTypesPane() {
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

        Label title = new Label("Añadir nuevo tipo de actividad");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #222;");

        GridPane form = new GridPane();
        form.setVgap(25);
        form.setHgap(20);
        form.setAlignment(Pos.CENTER);
        form.setPadding(new Insets(30));

        Label nameLabel = new Label("Nombre del tipo:");
        nameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        TextField nameField = new TextField();
        nameField.setPromptText("Introduce el nombre de la actividad");
        nameField.setPrefWidth(400);

        Label descLabel = new Label("Descripción:");
        descLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        TextArea descArea = new TextArea();
        descArea.setPromptText("Introduce una descripción breve");
        descArea.setWrapText(true);
        descArea.setPrefWidth(400);
        descArea.setPrefHeight(150);

        Label status = new Label();
        status.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");

        form.addRow(0, nameLabel, nameField);
        form.addRow(1, descLabel, descArea);

        Button saveButton = getButton(nameField, descArea, status);

        root.getChildren().addAll(title, form, saveButton, status);
    }

    private @NotNull Button getButton(TextField nameField, TextArea descArea, Label status) {
        Button saveButton = new Button("Añadir tipo de actividad");
        saveButton.setStyle("""
            -fx-background-color: #10B981;
            -fx-text-fill: white;
            -fx-font-weight: bold;
            -fx-background-radius: 10px;
            -fx-padding: 15 40;
            -fx-font-size: 16px;
        """);

        saveButton.setOnAction(e -> {
            String name = nameField.getText().trim();
            String desc = descArea.getText().trim();

            if (name.isEmpty()) {
                status.setText("El nombre es obligatorio");
                return;
            }

            if (ActivityTypeDAO.activityTypesExists(name)) {
                status.setText("Este nombre ya esta en uso");
                return;
            }

            try {
                ActivityTypeDAO.addActivityType(name, desc.isEmpty() ? null : desc);
                ActivityTypesPane.setItems();
                ((Stage) root.getScene().getWindow()).close();
            } catch (Exception ex) {
                ex.printStackTrace();
                status.setText("Error al guardar el tipo de actividad");
            }
        });

        return saveButton;
    }

    public VBox getPane() {
        return root;
    }

    public static void openAdder() {
        AddActivityTypesPane pane = new AddActivityTypesPane();

        Scene scene = new Scene(pane.getPane(), 800, 500);

        Stage stage = new Stage();
        stage.setTitle("Añadir tipo de actividad");
        stage.setScene(scene);
        stage.setMinWidth(850);
        stage.setMinHeight(500);
        stage.centerOnScreen();
        stage.show();
    }
}
