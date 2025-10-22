package views.client.routes;

import db.RoutesDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import models.CurrentUserModel;
import models.RoutesModel;
import models.UserModel;
import org.jetbrains.annotations.NotNull;
import utils.Dialogs;

public class RoutesPane {

    private final VBox root;
    private static TableView<RoutesModel> table = new TableView<>();

    public RoutesPane() {
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

        Label title = new Label("Rutas registradas");
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

        TableColumn<RoutesModel, String> titleCol = new TableColumn<>("Título");
        titleCol.setCellValueFactory(c -> c.getValue().titleProperty());

        TableColumn<RoutesModel, String> difficultyCol = new TableColumn<>("Dificultad");
        difficultyCol.setCellValueFactory(c -> c.getValue().difficultyProperty());

        TableColumn<RoutesModel, String> visibilityCol = new TableColumn<>("Visibilidad");
        visibilityCol.setCellValueFactory(c -> c.getValue().visibilityProperty());

        TableColumn<RoutesModel, String> publicationCol = new TableColumn<>("Fecha publicación");
        publicationCol.setCellValueFactory(c -> c.getValue().publicationDateProperty().asString());

        TableColumn<RoutesModel, Void> actionsCol = getRouteVoidTableColumn();
        actionsCol.setPrefWidth(150);

        table.getColumns().addAll(titleCol, difficultyCol, visibilityCol, publicationCol, actionsCol);

        setItems();

        refreshButton.setOnAction(e -> setItems());
        addButton.setOnAction(e -> AddRoutesPane.openAdder());

        root.getChildren().addAll(header, table);
    }

    public static void setItems() {
        table.setItems(RoutesDAO.getRoutesByUserId(CurrentUserModel.getUser().getId()));
    }

    private static @NotNull TableColumn<RoutesModel, Void> getRouteVoidTableColumn() {
        TableColumn<RoutesModel, Void> actionsCol = new TableColumn<>("Acciones");
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
                    RoutesModel route = getTableView().getItems().get(getIndex());
                    EditRoutesPane.openEditor(route);
                });

                deleteButton.setStyle("""
                    -fx-background-color: #EF4444;
                    -fx-text-fill: white;
                    -fx-font-size: 13px;
                    -fx-background-radius: 6px;
                    -fx-padding: 4 10 4 10;
                """);
                deleteButton.setOnAction(e -> {
                    RoutesModel route = getTableView().getItems().get(getIndex());
                    boolean confirmed = Dialogs.ConfirmationDialog.show("Eliminar ruta",
                            "¿Estás seguro de que quieres eliminar la ruta \"" + route.getTitle() + "\"?");
                    if (confirmed) {
                        RoutesDAO.deleteRoute(route.getId());
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
