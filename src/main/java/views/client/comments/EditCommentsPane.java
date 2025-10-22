package views.client.comments;

import db.CommentsDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import models.CommentsModel;
import org.jetbrains.annotations.NotNull;
import utils.Dialogs;

public class EditCommentsPane {

    private final VBox root;

    public EditCommentsPane(@NotNull CommentsModel commentModel) {
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

        Label title = new Label("Editar comentario ID: " + commentModel.getId());
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #222;");

        GridPane form = new GridPane();
        form.setVgap(25);
        form.setHgap(20);
        form.setAlignment(Pos.CENTER);
        form.setPadding(new Insets(30));

        Label commentLabel = new Label("Comentario:");
        commentLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        TextArea commentArea = new TextArea(commentModel.getComment());
        commentArea.setPrefWidth(400);
        commentArea.setPrefHeight(120);

        Label ratingLabel = new Label("Rating:");
        ratingLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        Spinner<Integer> ratingSpinner = new Spinner<>(0, 5, commentModel.getRating());
        ratingSpinner.setPrefWidth(100);

        form.addRow(0, commentLabel, commentArea);
        form.addRow(1, ratingLabel, ratingSpinner);

        Button saveButton = getButton(commentModel, commentArea, ratingSpinner);

        root.getChildren().addAll(title, form, saveButton);
    }

    private @NotNull Button getButton(@NotNull CommentsModel commentModel, TextArea commentArea, Spinner<Integer> ratingSpinner) {
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
                String commentText = commentArea.getText().trim();
                int rating = ratingSpinner.getValue();

                CommentsDAO.updateComment(
                        commentModel.getId(),
                        commentText,
                        rating
                );

                Dialogs.AlertDialog.showInfo("Actualizado", "Comentario actualizado");
                CommentsPane.setItems();
                ((Stage) root.getScene().getWindow()).close();

            } catch (NumberFormatException ex) {
                Dialogs.AlertDialog.showError("Error", "ID de ruta y usuario deben ser números válidos");
            }
        });
        return saveButton;
    }

    public VBox getPane() {
        return root;
    }

    public static void openEditor(@NotNull CommentsModel commentModel) {
        EditCommentsPane pane = new EditCommentsPane(commentModel);

        Scene scene = new Scene(pane.getPane(), 1000, 700);

        Stage stage = new Stage();
        stage.setTitle("Editar comentario");
        stage.setScene(scene);
        stage.setMinWidth(900);
        stage.setMinHeight(650);
        stage.centerOnScreen();
        stage.show();
    }
}
