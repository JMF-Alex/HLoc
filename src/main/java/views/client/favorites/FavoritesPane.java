package views.client.favorites;

import db.FavoritesDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import models.CurrentUserModel;
import models.FavoritesModel;
import org.jetbrains.annotations.NotNull;
import utils.Dialogs;

public class FavoritesPane {

    private final VBox root;
    private static TableView<FavoritesModel> table = new TableView<>();

    public FavoritesPane() {
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

        Label title = new Label("Favoritos");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox header = new HBox(20, title, spacer, refreshButton);
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

        TableColumn<FavoritesModel, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(c -> c.getValue().idProperty().asObject());

        TableColumn<FavoritesModel, Integer> routeCol = new TableColumn<>("Ruta ID");
        routeCol.setCellValueFactory(c -> c.getValue().routeIdProperty().asObject());

        TableColumn<FavoritesModel, String> dateCol = new TableColumn<>("Fecha añadida");
        dateCol.setCellValueFactory(c -> c.getValue().addedAtProperty().asString());

        TableColumn<FavoritesModel, Void> actionsCol = getFavoritesVoidTableColumn();
        actionsCol.setPrefWidth(60);

        table.getColumns().addAll(idCol, routeCol, dateCol, actionsCol);

        setItems();

        refreshButton.setOnAction(e -> setItems());

        root.getChildren().addAll(header, table);
    }

    public static void setItems() {
        table.setItems(FavoritesDAO.getFavoritesByUserId(CurrentUserModel.getUser().getId()));
    }

    private static @NotNull TableColumn<FavoritesModel, Void> getFavoritesVoidTableColumn() {
        TableColumn<FavoritesModel, Void> actionsCol = new TableColumn<>("Acciones");
        actionsCol.setCellFactory(col -> new TableCell<>() {
            private final Button deleteButton = new Button("Eliminar");
            private final HBox buttonsBox = new HBox(deleteButton);

            {
                deleteButton.setStyle("""
                    -fx-background-color: #EF4444;
                    -fx-text-fill: white;
                    -fx-font-size: 13px;
                    -fx-background-radius: 6px;
                    -fx-padding: 4 10 4 10;
                """);
                deleteButton.setOnAction(e -> {
                    FavoritesModel favorite = getTableView().getItems().get(getIndex());
                    boolean confirmed = Dialogs.ConfirmationDialog.show("Eliminar favorito",
                            "¿Estás seguro de que quieres eliminar este favorito?");
                    if (confirmed) {
                        FavoritesDAO.deleteFavorite(favorite.getId());
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
