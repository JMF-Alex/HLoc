package views.auth;

import db.UserDAO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import models.CurrentUserModel;
import models.UserModel;
import org.jetbrains.annotations.NotNull;
import views.main.MainPane;

public class LoginPane {

    public static void app(@NotNull Stage stage) {
        VBox root = getVBox(stage);

        root.setPadding(new Insets(20));
        stage.setScene(new Scene(root, 300, 250));
        stage.setTitle("Login - HLoc");
        stage.show();
        stage.setX((Screen.getPrimary().getBounds().getWidth() - stage.getWidth()) / 2);
        stage.setY((Screen.getPrimary().getBounds().getHeight() - stage.getHeight()) / 2);
    }

    private static @NotNull VBox getVBox(@NotNull Stage stage) {
        Label userLabel = new Label("Usuario:");
        TextField userField = new TextField();
        Label passLabel = new Label("Contraseña:");
        PasswordField passwordField = new PasswordField();

        Button loginButton = new Button("Iniciar sesión");
        Label status = new Label();

        loginButton.setOnAction(e -> {
            String username = userField.getText().trim();
            String password = passwordField.getText().trim();

            if (username.isEmpty() || password.isEmpty()) {
                status.setText("Rellena todos los campos");
                return;
            }

            if (UserDAO.checkLogin(username, password)) {
                UserModel currentUser = UserDAO.getUserByUsername(username);
                if (currentUser != null) {
                    CurrentUserModel.setUser(currentUser);
                    MainPane.app(stage);
                } else {
                    status.setText("Error al cargar datos del usuario");
                }
            } else {
                status.setText("Usuario o contraseña incorrectos");
            }
        });

        return new VBox(10, userLabel, userField, passLabel, passwordField, loginButton, status);
    }
}
