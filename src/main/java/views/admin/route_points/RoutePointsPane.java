package views.admin.route_points;

import db.RoutePointsDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import views.admin.route_points.AddRoutePointsPane;
import models.RoutePointsModel;
import org.jetbrains.annotations.NotNull;
import utils.Dialogs;

public class RoutePointsPane {

    private final VBox root;
    private static TableView<RoutePointsModel> table = new TableView<>();

    public RoutePointsPane() {
        root = new VBox(10);
        root.setPadding(new Insets(20));

        Button refreshButton = new Button("Recargar");
        refreshButton.setStyle("""
            -fx-background-color: #4A90E2;
            -fx-text-fill: white;
            -fx-font-weight: bold;
            -fx-background-radius: 8px;
            -fx-padding: 6 12 6 12;
        """);

        Button addButton = new Button("Añadir");
        addButton.setStyle("""
            -fx-background-color: #10B981;
            -fx-text-fill: white;
            -fx-font-weight: bold;
            -fx-background-radius: 8px;
            -fx-padding: 6 12 6 12;
        """);

        Label title = new Label("Puntos de ruta registrados");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox header = new HBox(20, title, spacer, refreshButton, addButton);
        header.setAlignment(Pos.CENTER_LEFT);

        table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setStyle("""
            -fx-background-color: #ffffff;
            -fx-background-radius: 10px;
            -fx-border-radius: 10px;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10,0,0,5);
            -fx-font-size: 14px;
        """);

        TableColumn<RoutePointsModel, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(c -> c.getValue().idProperty().asObject());

        TableColumn<RoutePointsModel, Integer> routeIdCol = new TableColumn<>("Route ID");
        routeIdCol.setCellValueFactory(c -> c.getValue().routeIdProperty().asObject());

        TableColumn<RoutePointsModel, Integer> orderCol = new TableColumn<>("Orden");
        orderCol.setCellValueFactory(c -> c.getValue().pointOrderProperty().asObject());

        TableColumn<RoutePointsModel, String> latCol = new TableColumn<>("Latitud");
        latCol.setCellValueFactory(c -> c.getValue().latProperty().asString());

        TableColumn<RoutePointsModel, String> lonCol = new TableColumn<>("Longitud");
        lonCol.setCellValueFactory(c -> c.getValue().lonProperty().asString());

        TableColumn<RoutePointsModel, String> altCol = new TableColumn<>("Altitud (m)");
        altCol.setCellValueFactory(c -> c.getValue().altitudeMProperty().asString());

        TableColumn<RoutePointsModel, Void> actionsCol = getRoutePointActionsColumn();
        actionsCol.setPrefWidth(150);

        table.getColumns().addAll(idCol, routeIdCol, orderCol, latCol, lonCol, altCol, actionsCol);

        setItems();

        refreshButton.setOnAction(e -> setItems());
        addButton.setOnAction(e -> AddRoutePointsPane.openAdder());

        root.getChildren().addAll(header, table);
    }

    public static void setItems() {
        table.setItems(RoutePointsDAO.getAllRoutePoints());
    }

    private static @NotNull TableColumn<RoutePointsModel, Void> getRoutePointActionsColumn() {
        TableColumn<RoutePointsModel, Void> actionsCol = new TableColumn<>("Acciones");
        actionsCol.setCellFactory(col -> new TableCell<>() {
            private final Button editButton = new Button("Editar");
            private final Button deleteButton = new Button("Eliminar");
            private final HBox buttonsBox = new HBox(10, editButton, deleteButton);

            {
                editButton.setStyle("""
                    -fx-background-color: #4A90E2;
                    -fx-text-fill: white;
                    -fx-font-size: 13px;
                    -fx-background-radius: 6px;
                    -fx-padding: 4 10 4 10;
                """);
                editButton.setOnAction(e -> {
                    RoutePointsModel point = getTableView().getItems().get(getIndex());
                    EditRoutePointsPane.openEditor(point);
                });

                deleteButton.setStyle("""
                    -fx-background-color: #EF4444;
                    -fx-text-fill: white;
                    -fx-font-size: 13px;
                    -fx-background-radius: 6px;
                    -fx-padding: 4 10 4 10;
                """);
                deleteButton.setOnAction(e -> {
                    RoutePointsModel point = getTableView().getItems().get(getIndex());
                    boolean confirmed = Dialogs.ConfirmationDialog.show("Eliminar punto",
                            "¿Seguro que quieres eliminar el punto #" + point.getId() +
                                    " (orden " + point.getPointOrder() + ")?");
                    if (confirmed) {
                        RoutePointsDAO.deleteRoutePoint(point.getId());
                        setItems();
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : buttonsBox);
            }
        });
        return actionsCol;
    }

    public VBox getPane() {
        return root;
    }
}
