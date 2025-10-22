package views.admin.users;

import db.UserDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import models.UserModel;
import org.jetbrains.annotations.NotNull;
import utils.Dialogs;

public class EditUsersPane {

    private final VBox root;

    public EditUsersPane(@NotNull UserModel userModel) {
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

        Label title = new Label("Editar Usuario: " + userModel.getName());
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #222;");

        GridPane form = new GridPane();
        form.setVgap(25);
        form.setHgap(20);
        form.setAlignment(Pos.CENTER);
        form.setPadding(new Insets(30));

        Label nameLabel = new Label("Nombre de usuario:");
        nameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        TextField nameField = new TextField(userModel.getName());
        nameField.setPromptText("Introduce el nombre de usuario");
        nameField.setPrefWidth(400);
        nameField.setStyle("-fx-font-size: 15px; -fx-padding: 10;");

        Label countryLabel = new Label("País:");
        countryLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        TextField countryField = new TextField(userModel.getCountry());
        countryField.setPromptText("Introduce el país");
        countryField.setPrefWidth(400);
        countryField.setStyle("-fx-font-size: 15px; -fx-padding: 10;");

        Label cityLabel = new Label("Ciudad:");
        cityLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        TextField cityField = new TextField(userModel.getCity());
        cityField.setPromptText("Introduce la ciudad");
        cityField.setPrefWidth(400);
        cityField.setStyle("-fx-font-size: 15px; -fx-padding: 10;");

        Label adminLabel = new Label("Permisos:");
        adminLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        CheckBox adminCheck = new CheckBox("Administrador");
        adminCheck.setSelected(userModel.isAdmin());
        adminCheck.setStyle("-fx-font-size: 15px;");

        form.addRow(0, nameLabel, nameField);
        form.addRow(1, countryLabel, countryField);
        form.addRow(2, cityLabel, cityField);
        form.addRow(3, adminLabel, adminCheck);

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
            UserDAO.updateUser(
                    userModel.getId(),
                    nameField.getText(),
                    countryField.getText(),
                    cityField.getText(),
                    adminCheck.isSelected()
            );

            Dialogs.AlertDialog.showInfo("Actualizado", "Usuario actualizado");

            UsersPane.setItems();

            ((Stage) root.getScene().getWindow()).close();
        });

        root.getChildren().addAll(title, form, saveButton);
    }

    public VBox getPane() {
        return root;
    }

    public static void openEditor(@NotNull UserModel userModel) {
        EditUsersPane pane = new EditUsersPane(userModel);

        Scene scene = new Scene(pane.getPane(), 1000, 700);

        Stage stage = new Stage();
        stage.setTitle("Editar usuario");
        stage.setScene(scene);
        stage.setMinWidth(900);
        stage.setMinHeight(650);
        stage.centerOnScreen();
        stage.show();
    }
}
