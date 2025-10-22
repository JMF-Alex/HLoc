package views.client;

import db.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;
import models.CurrentUserModel;
import models.UserModel;
import org.jetbrains.annotations.NotNull;
import utils.Utils;
import views.client.comments.CommentsPane;
import views.client.favorites.FavoritesPane;
import views.client.routes.RoutesPane;

public class ClientPane {

    private final BorderPane root;
    private final VBox content;

    public ClientPane() {
        root = new BorderPane();
        root.setStyle("-fx-background-color: #f7f7f7;");

        VBox sidebar = new VBox(10);
        sidebar.setPadding(new Insets(25, 15, 25, 15));
        sidebar.setStyle("""
            -fx-background-color: linear-gradient(to bottom, #3CB371, #2E8B57);
        """);
        sidebar.setPrefWidth(240);
        sidebar.setAlignment(Pos.TOP_CENTER);

        Label titleSidebar = new Label("Panel Cliente");
        titleSidebar.setStyle("""
            -fx-text-fill: white;
            -fx-font-size: 20px;
            -fx-font-weight: bold;
        """);

        Separator sep = new Separator();
        sep.setStyle("-fx-background-color: white; -fx-opacity: 0.4;");

        Button dashboardButton = createNavButton("Dashboard");
        Button routesButton = createNavButton("Rutas");
        Button commentsButton = createNavButton("Mis comentarios");
        Button favoritesButton = createNavButton("Favoritos");

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        sidebar.getChildren().addAll(
                titleSidebar, sep,
                dashboardButton, routesButton,
                commentsButton, favoritesButton,
                spacer
        );

        content = new VBox(30);
        content.setAlignment(Pos.TOP_CENTER);
        content.setPadding(new Insets(40, 40, 40, 40));

        showDashboard();

        dashboardButton.setOnAction(e -> {
            showDashboard();
            Utils.showAnimation(content);
        });

        routesButton.setOnAction(e -> switchPane(new RoutesPane().getPane()));
        commentsButton.setOnAction(e -> switchPane(new CommentsPane().getPane()));
        favoritesButton.setOnAction(e -> switchPane(new FavoritesPane().getPane()));

        HBox innerLayout = new HBox(sidebar, content);
        HBox.setHgrow(content, Priority.ALWAYS);
        root.setCenter(innerLayout);
    }

    private @NotNull Button createNavButton(String text) {
        Button b = new Button(text);
        b.setStyle("""
            -fx-background-color: transparent;
            -fx-text-fill: white;
            -fx-font-size: 15px;
            -fx-font-weight: bold;
            -fx-alignment: CENTER_LEFT;
            -fx-padding: 10 0 10 15;
        """);
        b.setMaxWidth(Double.MAX_VALUE);
        b.setOnMouseEntered(e -> b.setStyle("""
            -fx-background-color: rgba(255,255,255,0.15);
            -fx-text-fill: white;
            -fx-font-size: 15px;
            -fx-font-weight: bold;
            -fx-alignment: CENTER_LEFT;
            -fx-padding: 10 0 10 15;
        """));
        b.setOnMouseExited(e -> b.setStyle("""
            -fx-background-color: transparent;
            -fx-text-fill: white;
            -fx-font-size: 15px;
            -fx-font-weight: bold;
            -fx-alignment: CENTER_LEFT;
            -fx-padding: 10 0 10 15;
        """));
        return b;
    }

    private void switchPane(Pane pane) {
        content.getChildren().clear();
        content.getChildren().add(pane);
        Utils.showAnimation(pane);
    }

    private void showDashboard() {
        content.getChildren().clear();

        Label title = new Label("Panel de Cliente");
        title.setStyle("""
            -fx-font-size: 30px;
            -fx-text-fill: #2E8B57;
            -fx-font-weight: bold;
        """);

        GridPane statsGrid = new GridPane();
        statsGrid.setHgap(25);
        statsGrid.setVgap(25);
        statsGrid.setAlignment(Pos.CENTER);

        UserModel user = CurrentUserModel.getUser();
        if (user != null) {
            int id = user.getId();
            int comments = CommentsDAO.getTotalCommentsByUser(id);
            int routes = RoutesDAO.getTotalRoutesByUser(id);
            int favorites = FavoritesDAO.getTotalFavoritesByUser(id);

            statsGrid.add(createCard("Comentarios", String.valueOf(comments)), 0, 0);
            statsGrid.add(createCard("Rutas", String.valueOf(routes)), 1, 0);
            statsGrid.add(createCard("Favoritos", String.valueOf(favorites)), 2, 0);
        }

        content.getChildren().addAll(title, statsGrid);
    }

    private @NotNull VBox createCard(String title, String value) {
        VBox card = new VBox(8);
        card.setPadding(new Insets(20));
        card.setStyle("""
            -fx-background-color: white;
            -fx-background-radius: 10px;
            -fx-border-radius: 10px;
            -fx-border-color: #dcdcdc;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 6,0,0,3);
        """);
        card.setPrefSize(220, 110);
        card.setAlignment(Pos.CENTER);

        Label lblTitle = new Label(title);
        lblTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #333;");

        Label lblValue = new Label(value);
        lblValue.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: #3CB371;");

        card.getChildren().addAll(lblTitle, lblValue);
        return card;
    }

    public Pane getPane() {
        return root;
    }
}
