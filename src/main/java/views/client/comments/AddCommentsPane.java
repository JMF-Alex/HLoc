package views.client.comments;

import db.CommentsDAO;
import db.RoutesDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import models.CurrentUserModel;
import org.jetbrains.annotations.NotNull;

public class AddCommentsPane {

    private final VBox root;

    public AddCommentsPane() {
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

        Label title = new Label("Añadir nuevo comentario");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #222;");

        GridPane form = new GridPane();
        form.setVgap(25);
        form.setHgap(20);
        form.setAlignment(Pos.CENTER);
        form.setPadding(new Insets(30));

        Label routeLabel = new Label("ID de Ruta:");
        routeLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        TextField routeField = new TextField();
        routeField.setPromptText("Introduce el ID de la ruta");
        routeField.setPrefWidth(400);

        Label commentLabel = new Label("Comentario:");
        commentLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        TextArea commentArea = new TextArea();
        commentArea.setPromptText("Introduce el comentario");
        commentArea.setPrefWidth(400);
        commentArea.setPrefHeight(120);

        Label ratingLabel = new Label("Puntuación:");
        ratingLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        Spinner<Integer> ratingSpinner = new Spinner<>(0, 5, 0);
        ratingSpinner.setPrefWidth(100);

        Label status = new Label();
        status.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");

        form.addRow(0, routeLabel, routeField);
        form.addRow(1, commentLabel, commentArea);
        form.addRow(2, ratingLabel, ratingSpinner);

        Button saveButton = getButton(routeField, commentArea, ratingSpinner, status);

        root.getChildren().addAll(title, form, saveButton, status);
    }

    private @NotNull Button getButton(TextField routeField, TextArea commentArea,
                                      Spinner<Integer> ratingSpinner, Label status) {
        Button saveButton = new Button("Añadir comentario");
        saveButton.setStyle("""
            -fx-background-color: #10B981;
            -fx-text-fill: white;
            -fx-font-weight: bold;
            -fx-background-radius: 10px;
            -fx-padding: 15 40;
            -fx-font-size: 16px;
        """);

        saveButton.setOnAction(e -> {
            String routeIdText = routeField.getText().trim();
            String comment = commentArea.getText().trim();
            int rating = ratingSpinner.getValue();

            if (routeIdText.isEmpty() || comment.isEmpty()) {
                status.setText("Rellena todos los campos obligatorios");
                return;
            }

            if (!RoutesDAO.routeExists(Integer.parseInt(routeIdText))) {
                status.setText("La ruta no existe");
                return;
            }

            try {
                int routeId = Integer.parseInt(routeIdText);
                int userId = CurrentUserModel.getUser().getId();

                CommentsDAO.insertComment(routeId, userId, comment, rating);

                CommentsPane.setItems();
                ((Stage) root.getScene().getWindow()).close();

            } catch (NumberFormatException ex) {
                status.setText("ID de ruta y de usuario deben ser números válidos");
            }
        });

        return saveButton;
    }

    public VBox getPane() {
        return root;
    }

    public static void openAdder() {
        AddCommentsPane pane = new AddCommentsPane();

        Scene scene = new Scene(pane.getPane(), 800, 650);

        Stage stage = new Stage();
        stage.setTitle("Añadir comentario");
        stage.setScene(scene);
        stage.setMinWidth(800);
        stage.setMinHeight(650);
        stage.centerOnScreen();
        stage.show();
    }
}
