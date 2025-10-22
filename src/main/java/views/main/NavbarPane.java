package views.main;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.Priority;
import models.CurrentUserModel;
import models.UserModel;
import org.jetbrains.annotations.NotNull;

public class NavbarPane extends HBox {

    public final Button planButton;
    public final Button loginButton;
    public final Button registerButton;
    public final Button logoutButton;
    public final Button adminButton;
    public final Button clientButton;

    public NavbarPane() {
        super(20);
        setPadding(new Insets(10, 30, 10, 30));
        setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0;");

        Label logo = new Label("HLoc");
        logo.setStyle("-fx-font-weight: bold; -fx-font-size: 24px; -fx-text-fill: #3CB371;");

        TextField searchField = new TextField();
        searchField.setPromptText("Buscar rutas, lugares o usuarios...");
        searchField.setPrefWidth(350);
        searchField.setStyle("""
            -fx-background-radius: 25px;
            -fx-border-radius: 25px;
            -fx-border-color: #ccc;
            -fx-padding: 8 14 8 14;
        """);

        planButton = new Button("Planifica tu ruta");
        planButton.setStyle("""
            -fx-background-color: #3CB371;
            -fx-text-fill: white;
            -fx-font-size: 15px;
            -fx-background-radius: 20px;
            -fx-padding: 8 16 8 16;
        """);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Botones del navbar integrando el estilo directamente
        loginButton = createNavButton("Iniciar sesión", "#3CB371");
        registerButton = createNavButton("Registrarse", "#2E8B57");
        logoutButton = createNavButton("Cerrar sesión", "#B22222");
        adminButton = createNavButton("Administración", "#4682B4");
        clientButton = createNavButton("Mi panel", "#4CAF50");

        UserModel user = CurrentUserModel.getUser();

        HBox rightButtons = new HBox(10);
        rightButtons.setAlignment(Pos.CENTER_RIGHT);
        if (user == null) rightButtons.getChildren().addAll(loginButton, registerButton);
        else if (user.isAdmin()) rightButtons.getChildren().addAll(clientButton, adminButton, logoutButton);
        else rightButtons.getChildren().addAll(clientButton, logoutButton);

        getChildren().addAll(logo, searchField, planButton, spacer, rightButtons);
    }

    private @NotNull Button createNavButton(String text, String color) {
        Button btn = new Button(text);
        btn.setStyle("""
            -fx-background-radius: 20px;
            -fx-font-size: 14px;
            -fx-text-fill: white;
            -fx-padding: 6 14 6 14;
        """);
        btn.setStyle(btn.getStyle() + "-fx-background-color: " + color + ";");
        return btn;
    }
}
