package views.admin.users;

import db.UserDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import utils.PasswordUtils;

public class AddUsersPane {

    private final VBox root;

    public AddUsersPane() {
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

        Label title = new Label("Añadir nuevo usuario");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #222;");

        GridPane form = new GridPane();
        form.setVgap(25);
        form.setHgap(20);
        form.setAlignment(Pos.CENTER);
        form.setPadding(new Insets(30));

        Label nameLabel = new Label("Nombre de usuario:");
        nameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        TextField nameField = new TextField();
        nameField.setPromptText("Introduce el nombre de usuario");
        nameField.setPrefWidth(400);

        Label passwordLabel = new Label("Contraseña:");
        passwordLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Introduce la contraseña");
        passwordField.setPrefWidth(400);

        Label countryLabel = new Label("País:");
        countryLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        TextField countryField = new TextField();
        countryField.setPromptText("Introduce el país");
        countryField.setPrefWidth(400);

        Label cityLabel = new Label("Ciudad:");
        cityLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        TextField cityField = new TextField();
        cityField.setPromptText("Introduce la ciudad");
        cityField.setPrefWidth(400);

        Label adminLabel = new Label("Permisos:");
        adminLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        CheckBox adminCheck = new CheckBox("Administrador");

        Label status = new Label();
        status.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");

        form.addRow(0, nameLabel, nameField);
        form.addRow(1, passwordLabel, passwordField);
        form.addRow(2, countryLabel, countryField);
        form.addRow(3, cityLabel, cityField);
        form.addRow(4, adminLabel, adminCheck);

        Button saveButton = getButton(nameField, passwordField, countryField, cityField, adminCheck, status);

        root.getChildren().addAll(title, form, saveButton, status);
    }

    private @NotNull Button getButton(TextField nameField, PasswordField passwordField, TextField countryField,
                                      TextField cityField, CheckBox adminCheck, Label status) {
        Button saveButton = new Button("💾 Añadir usuario");
        saveButton.setStyle("""
            -fx-background-color: #10B981;
            -fx-text-fill: white;
            -fx-font-weight: bold;
            -fx-background-radius: 10px;
            -fx-padding: 15 40;
            -fx-font-size: 16px;
        """);

        saveButton.setOnAction(e -> {
            String username = nameField.getText().trim();
            String password = passwordField.getText().trim();
            String country = countryField.getText().trim();
            String city = cityField.getText().trim();

            if (username.isEmpty() || password.isEmpty()) {
                status.setText("Rellena todos los campos obligatorios");
                return;
            }

            if (UserDAO.userExists(username)) {
                status.setText("El usuario ya existe");
                return;
            }

            String hashedPassword = PasswordUtils.hashPassword(password);

            UserDAO.registerUser(
                    username,
                    hashedPassword,
                    country,
                    city,
                    adminCheck.isSelected()
            );

            UsersPane.setItems();
            ((Stage) root.getScene().getWindow()).close();
        });

        return saveButton;
    }

    public VBox getPane() {
        return root;
    }

    public static void openAdder() {
        AddUsersPane pane = new AddUsersPane();

        Scene scene = new Scene(pane.getPane(), 800, 650);

        Stage stage = new Stage();
        stage.setTitle("Añadir usuario");
        stage.setScene(scene);
        stage.setMinWidth(900);
        stage.setMinHeight(650);
        stage.centerOnScreen();
        stage.show();
    }
}
