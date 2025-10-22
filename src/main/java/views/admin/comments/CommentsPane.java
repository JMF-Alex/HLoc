package views.admin.comments;

import db.CommentsDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import models.CommentsModel;
import org.jetbrains.annotations.NotNull;
import utils.Dialogs;

public class CommentsPane {

    private final VBox root;
    private static TableView<CommentsModel> table = new TableView<>();

    public CommentsPane() {
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

        Label title = new Label("Comentarios");
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

        TableColumn<CommentsModel, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(c -> c.getValue().idProperty().asObject());

        TableColumn<CommentsModel, Integer> routeCol = new TableColumn<>("Ruta ID");
        routeCol.setCellValueFactory(c -> c.getValue().routeIdProperty().asObject());

        TableColumn<CommentsModel, Integer> userCol = new TableColumn<>("Usuario ID");
        userCol.setCellValueFactory(c -> c.getValue().userIdProperty().asObject());

        TableColumn<CommentsModel, String> commentCol = new TableColumn<>("Comentario");
        commentCol.setCellValueFactory(c -> c.getValue().commentProperty());

        TableColumn<CommentsModel, Integer> ratingCol = new TableColumn<>("Rating");
        ratingCol.setCellValueFactory(c -> c.getValue().ratingProperty().asObject());

        TableColumn<CommentsModel, String> dateCol = new TableColumn<>("Fecha");
        dateCol.setCellValueFactory(c -> c.getValue().createdAtProperty().asString());

        TableColumn<CommentsModel, Void> actionsCol = getCommentsVoidTableColumn();
        actionsCol.setPrefWidth(120);

        table.getColumns().addAll(idCol, routeCol, userCol, commentCol, ratingCol, dateCol, actionsCol);

        setItems();

        refreshButton.setOnAction(e -> setItems());
        addButton.setOnAction(e -> AddCommentsPane.openAdder());

        root.getChildren().addAll(header, table);
    }

    public static void setItems() {
        table.setItems(CommentsDAO.getAllComments());
    }

    private static @NotNull TableColumn<CommentsModel, Void> getCommentsVoidTableColumn() {
        TableColumn<CommentsModel, Void> actionsCol = new TableColumn<>("Acciones");
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
                    CommentsModel comment = getTableView().getItems().get(getIndex());
                    EditCommentsPane.openEditor(comment);
                });

                deleteButton.setStyle("""
                    -fx-background-color: #EF4444;
                    -fx-text-fill: white;
                    -fx-font-size: 13px;
                    -fx-background-radius: 6px;
                    -fx-padding: 4 10 4 10;
                """);
                deleteButton.setOnAction(e -> {
                    CommentsModel comment = getTableView().getItems().get(getIndex());
                    boolean confirmed = Dialogs.ConfirmationDialog.show("Eliminar comentario",
                            "¿Estás seguro de que quieres eliminar este comentario?");
                    if (confirmed) {
                        CommentsDAO.deleteComment(comment.getId());
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
