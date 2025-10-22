package views.admin.users;

import db.UserDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.jetbrains.annotations.NotNull;
import utils.Dialogs;
import models.UserModel;

public class UsersPane {

    private final VBox root;
    private static TableView<UserModel> table = new TableView<>();

    public UsersPane() {
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

        Label title = new Label("Usuarios registrados");
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

        table.lookupAll(".column-header-background").forEach(headerNode ->
                headerNode.setStyle("-fx-background-color: #2B2B2B; -fx-border-color: transparent;")
        );
        table.getColumns().forEach(col -> col.setStyle("""
            -fx-background-color: transparent;
            -fx-text-fill: #333;
            -fx-font-weight: bold;
        """));


        TableColumn<UserModel, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(c -> c.getValue().idProperty().asObject());

        TableColumn<UserModel, String> nameCol = new TableColumn<>("Username");
        nameCol.setCellValueFactory(c -> c.getValue().nameProperty());

        TableColumn<UserModel, String> countryCol = new TableColumn<>("País");
        countryCol.setCellValueFactory(c -> c.getValue().countryProperty());

        TableColumn<UserModel, String> cityCol = new TableColumn<>("Ciudad");
        cityCol.setCellValueFactory(c -> c.getValue().cityProperty());

        TableColumn<UserModel, Boolean> adminCol = new TableColumn<>("Admin");
        adminCol.setCellValueFactory(c -> c.getValue().adminProperty());

        TableColumn<UserModel, Void> actionsCol = getUserVoidTableColumn();
        actionsCol.setPrefWidth(90);

        table.getColumns().addAll(idCol, nameCol, countryCol, cityCol, adminCol, actionsCol);

        setItems();

        refreshButton.setOnAction(e -> setItems());

        addButton.setOnAction(e -> AddUsersPane.openAdder());

        root.getChildren().addAll(header, table);
    }

    public static void setItems() {
        table.setItems(UserDAO.getAllUsers());
    }

    private static @NotNull TableColumn<UserModel, Void> getUserVoidTableColumn() {
        TableColumn<UserModel, Void> actionsCol = new TableColumn<>("Acciones");
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
                    UserModel user = getTableView().getItems().get(getIndex());
                    EditUsersPane.openEditor(user);
                });

                deleteButton.setStyle("""
                    -fx-background-color: #EF4444;
                    -fx-text-fill: white;
                    -fx-font-size: 13px;
                    -fx-background-radius: 6px;
                    -fx-padding: 4 10 4 10;
                """);
                deleteButton.setOnAction(e -> {
                    UserModel user = getTableView().getItems().get(getIndex());
                    boolean confirmed = Dialogs.ConfirmationDialog.show("Eliminar usuario",
                            "¿Estás seguro de que quieres eliminar al usuario " + user.getName() + "?");
                    if (confirmed) {
                        UserDAO.deleteUser(user.getId());
                        setItems();
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(buttonsBox);
                }
            }
        });
        return actionsCol;
    }

    public VBox getPane() {
        return root;
    }
}
