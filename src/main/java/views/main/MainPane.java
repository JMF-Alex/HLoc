package views.main;

import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import utils.Utils;
import views.auth.LoginPane;
import views.auth.RegisterPane;
import views.admin.AdminPane;
import views.client.ClientPane;
import views.RoutesPane;
import models.CurrentUserModel;

public class MainPane {

    public static void app(@NotNull Stage primaryStage) {
        primaryStage.setTitle("HLoc | Rutas del Mundo");

        NavbarPane navbar = new NavbarPane();

        VBox mainContent = new VBox(30);
        mainContent.getChildren().addAll(new HomePane(), new MobileAppPane());

        ScrollPane scrollPane = new ScrollPane(mainContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setPannable(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        RoutesPane routesPane = new RoutesPane();
        AdminPane adminPane = new AdminPane();
        ClientPane clientPane = new ClientPane();

        navbar.planButton.setOnAction(e -> switchPane(mainContent, routesPane.getPane()));
        navbar.adminButton.setOnAction(e -> switchPane(mainContent, adminPane.getPane()));
        navbar.clientButton.setOnAction(e -> switchPane(mainContent, clientPane.getPane()));
        navbar.loginButton.setOnAction(e -> LoginPane.app(primaryStage));
        navbar.registerButton.setOnAction(e -> RegisterPane.app(primaryStage));
        navbar.logoutButton.setOnAction(e -> {
            CurrentUserModel.clearAll();
            app(primaryStage);
        });

        BorderPane root = new BorderPane();
        root.setTop(navbar);
        root.setCenter(scrollPane);

        Scene scene = new Scene(root, 1280, 800);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    private static void switchPane(@NotNull VBox container, javafx.scene.layout.Pane newPane) {
        container.getChildren().setAll(newPane);
        Utils.showAnimation(newPane);
    }
}
