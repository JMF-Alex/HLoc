package views.admin;

import db.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;
import org.jetbrains.annotations.NotNull;
import utils.Utils;
import views.admin.locations.LocationsPane;
import views.admin.route_points.RoutePointsPane;
import views.admin.routes.RoutesPane;
import views.admin.activity_types.ActivityTypesPane;
import views.admin.comments.CommentsPane;
import views.admin.favorites.FavoritesPane;
import views.admin.users.UsersPane;

public class AdminPane {

    private final BorderPane root;
    private final VBox content;

    public AdminPane() {
        root = new BorderPane();
        root.setStyle("-fx-background-color: #f7f7f7;");

        VBox sidebar = new VBox(10);
        sidebar.setPadding(new Insets(25, 15, 25, 15));
        sidebar.setStyle("""
            -fx-background-color: linear-gradient(to bottom, #3CB371, #2E8B57);
        """);
        sidebar.setPrefWidth(240);
        sidebar.setAlignment(Pos.TOP_CENTER);

        Label titleSidebar = new Label("Panel Admin");
        titleSidebar.setStyle("""
            -fx-text-fill: white;
            -fx-font-size: 20px;
            -fx-font-weight: bold;
        """);

        Separator sep = new Separator();
        sep.setStyle("-fx-background-color: white; -fx-opacity: 0.4;");

        Button dashboardButton = createNavButton("Dashboard");
        Button usersButton = createNavButton("Usuarios");
        Button activityTypesButton = createNavButton("Actividades");
        Button commentsButton = createNavButton("Comentarios");
        Button favoritesButton = createNavButton("Favoritos");
        Button routesButton = createNavButton("Rutas");
        Button locationsButton = createNavButton("Ubicaciones");
        Button routePointsButton = createNavButton("Puntos de ruta");
        Button reportsButton = createNavButton("Reportes");

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        sidebar.getChildren().addAll(
                titleSidebar, sep,
                dashboardButton, usersButton, activityTypesButton,
                commentsButton, favoritesButton,
                routesButton, locationsButton, routePointsButton,
                spacer, reportsButton
        );

        content = new VBox(30);
        content.setAlignment(Pos.TOP_CENTER);
        content.setPadding(new Insets(40, 40, 40, 40));

        showDashboard();

        dashboardButton.setOnAction(e -> {
            showDashboard();
            Utils.showAnimation(content);
        });

        usersButton.setOnAction(e -> switchPane(new UsersPane().getPane()));
        activityTypesButton.setOnAction(e -> switchPane(new ActivityTypesPane().getPane()));
        commentsButton.setOnAction(e -> switchPane(new CommentsPane().getPane()));
        favoritesButton.setOnAction(e -> switchPane(new FavoritesPane().getPane()));
        routesButton.setOnAction(e -> switchPane(new RoutesPane().getPane()));
        locationsButton.setOnAction(e -> switchPane(new LocationsPane().getPane()));
        routePointsButton.setOnAction(e -> switchPane(new RoutePointsPane().getPane()));

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

        Label title = new Label("Panel de Administración");
        title.setStyle("""
            -fx-font-size: 30px;
            -fx-text-fill: #2E8B57;
            -fx-font-weight: bold;
        """);

        GridPane statsGrid = new GridPane();
        statsGrid.setHgap(25);
        statsGrid.setVgap(25);
        statsGrid.setAlignment(Pos.CENTER);

        int users = UserDAO.getTotalUsers();
        int activity_types = ActivityTypeDAO.getTotalActivityTypes();
        int comments = CommentsDAO.getTotalComments();
        int routes = RoutesDAO.getTotalRoutes();
        int locations = LocationsDAO.getTotalLocations();
        int favorites = FavoritesDAO.getTotalFavorites();
        int route_points = RoutePointsDAO.getTotalRoutePoints();

        statsGrid.add(createCard("Usuarios", String.valueOf(users)), 0, 0);
        statsGrid.add(createCard("Actividades", String.valueOf(activity_types)), 1, 0);
        statsGrid.add(createCard("Comentarios", String.valueOf(comments)), 2, 0);
        statsGrid.add(createCard("Rutas", String.valueOf(routes)), 0, 1);
        statsGrid.add(createCard("Ubicaciones", String.valueOf(locations)), 1, 1);
        statsGrid.add(createCard("Favoritos", String.valueOf(favorites)), 2, 1);
        statsGrid.add(createCard("Puntos de ruta", String.valueOf(route_points)), 0, 2);

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
