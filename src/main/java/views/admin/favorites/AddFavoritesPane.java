package views.admin.favorites;

import db.FavoritesDAO;
import db.RoutesDAO;
import db.UserDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

public class AddFavoritesPane {

    private final VBox root;

    public AddFavoritesPane() {
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

        Label title = new Label("Añadir favorito");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #222;");

        GridPane form = new GridPane();
        form.setVgap(25);
        form.setHgap(20);
        form.setAlignment(Pos.CENTER);
        form.setPadding(new Insets(30));

        Label userLabel = new Label("ID de Usuario:");
        userLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        TextField userField = new TextField();
        userField.setPromptText("Introduce el ID del usuario");
        userField.setPrefWidth(400);

        Label routeLabel = new Label("ID de Ruta:");
        routeLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        TextField routeField = new TextField();
        routeField.setPromptText("Introduce el ID de la ruta");
        routeField.setPrefWidth(400);

        Label status = new Label();
        status.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");

        form.addRow(0, userLabel, userField);
        form.addRow(1, routeLabel, routeField);

        Button saveButton = getButton(userField, routeField, status);

        root.getChildren().addAll(title, form, saveButton, status);
    }

    private @NotNull Button getButton(TextField userField, TextField routeField, Label status) {
        Button saveButton = new Button("Añadir favorito");
        saveButton.setStyle("""
            -fx-background-color: #10B981;
            -fx-text-fill: white;
            -fx-font-weight: bold;
            -fx-background-radius: 10px;
            -fx-padding: 15 40;
            -fx-font-size: 16px;
        """);

        saveButton.setOnAction(e -> {
            String userIdText = userField.getText().trim();
            String routeIdText = routeField.getText().trim();

            if (userIdText.isEmpty() || routeIdText.isEmpty()) {
                status.setText("Rellena todos los campos obligatorios");
                return;
            }

            if (!UserDAO.userExists(Integer.parseInt(userIdText))) {
                status.setText("El usuario no existe");
                return;
            }

            if (!RoutesDAO.routeExists(Integer.parseInt(routeIdText))) {
                status.setText("La ruta no existe");
                return;
            }

            try {
                int userId = Integer.parseInt(userIdText);
                int routeId = Integer.parseInt(routeIdText);

                FavoritesDAO.insertFavorite(userId, routeId);

                FavoritesPane.setItems();
                ((Stage) root.getScene().getWindow()).close();

            } catch (NumberFormatException ex) {
                status.setText("ID de usuario y de ruta deben ser números válidos");
            }
        });

        return saveButton;
    }

    public VBox getPane() {
        return root;
    }

    public static void openAdder() {
        AddFavoritesPane pane = new AddFavoritesPane();

        Scene scene = new Scene(pane.getPane(), 800, 600);

        Stage stage = new Stage();
        stage.setTitle("Añadir favorito");
        stage.setScene(scene);
        stage.setMinWidth(800);
        stage.setMinHeight(500);
        stage.centerOnScreen();
        stage.show();
    }
}
