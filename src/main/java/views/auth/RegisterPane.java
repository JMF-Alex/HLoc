package views.auth;

import db.UserDAO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import models.CurrentUserModel;
import models.UserModel;
import org.jetbrains.annotations.NotNull;
import utils.PasswordUtils;
import views.main.MainPane;

public class RegisterPane {

    public static void app(@NotNull Stage stage) {
        VBox root = createVBox(stage);
        root.setPadding(new Insets(20));

        Scene scene = new Scene(root, 400, 400);
        stage.setScene(scene);
        stage.setTitle("Register - HLoc");

        stage.show();
        stage.setX((javafx.stage.Screen.getPrimary().getBounds().getWidth() - stage.getWidth()) / 2);
        stage.setY((javafx.stage.Screen.getPrimary().getBounds().getHeight() - stage.getHeight()) / 2);
    }

    private static @NotNull VBox createVBox(@NotNull Stage stage) {
        Label userLabel = new Label("Usuario:");
        TextField usernameField = new TextField();

        Label passLabel = new Label("Contraseña:");
        PasswordField passwordField = new PasswordField();

        Label countryLabel = new Label("País:");
        TextField countryField = new TextField();

        Label cityLabel = new Label("Ciudad:");
        TextField cityField = new TextField();

        Label status = new Label();
        Button registerButton = new Button("Crear cuenta");

        registerButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
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

            boolean success = UserDAO.registerUser(username, hashedPassword, country, city, false);

            if (success) {
                UserModel currentUser = UserDAO.getUserByUsername(username);
                if (currentUser != null) {
                    CurrentUserModel.setUser(currentUser);
                    MainPane.app(stage);
                } else {
                    status.setText("Error al cargar datos del usuario");
                }
            } else {
                status.setText("Error al registrar usuario");
            }
        });

        return new VBox(10,
                userLabel, usernameField,
                passLabel, passwordField,
                countryLabel, countryField,
                cityLabel, cityField,
                registerButton, status);
    }
}
