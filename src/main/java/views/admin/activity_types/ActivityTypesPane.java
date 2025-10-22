package views.admin.activity_types;

import db.ActivityTypeDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import models.ActivityTypesModel;
import org.jetbrains.annotations.NotNull;
import utils.Dialogs;

import java.sql.SQLException;

public class ActivityTypesPane {

    private final VBox root;
    private static TableView<ActivityTypesModel> table = new TableView<>();

    public ActivityTypesPane() {
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

        Label title = new Label("Tipos de actividad");
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

        TableColumn<ActivityTypesModel, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(c -> c.getValue().idProperty().asObject());

        TableColumn<ActivityTypesModel, String> nameCol = new TableColumn<>("Nombre");
        nameCol.setCellValueFactory(c -> c.getValue().nameProperty());

        TableColumn<ActivityTypesModel, String> descCol = new TableColumn<>("Descripción");
        descCol.setCellValueFactory(c -> c.getValue().descriptionProperty());

        TableColumn<ActivityTypesModel, Void> actionsCol = getActionsColumn();
        actionsCol.setPrefWidth(60);

        table.getColumns().addAll(idCol, nameCol, descCol, actionsCol);

        setItems();

        refreshButton.setOnAction(e -> setItems());
        addButton.setOnAction(e -> AddActivityTypesPane.openAdder());

        root.getChildren().addAll(header, table);
    }

    public static void setItems() {
        table.setItems(ActivityTypeDAO.getAllActivityTypes());
    }

    private static @NotNull TableColumn<ActivityTypesModel, Void> getActionsColumn() {
        TableColumn<ActivityTypesModel, Void> actionsCol = new TableColumn<>("Acciones");
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
                    ActivityTypesModel type = getTableView().getItems().get(getIndex());
                    EditActivityTypesPane.openEditor(type);
                });

                deleteButton.setStyle("""
                    -fx-background-color: #EF4444;
                    -fx-text-fill: white;
                    -fx-font-size: 13px;
                    -fx-background-radius: 6px;
                    -fx-padding: 4 10 4 10;
                """);
                deleteButton.setOnAction(e -> {
                    ActivityTypesModel type = getTableView().getItems().get(getIndex());
                    boolean confirmed = Dialogs.ConfirmationDialog.show("Eliminar tipo de actividad",
                            "¿Estás seguro de que quieres eliminar " + type.getName() + "?");
                    if (confirmed) {
                        try {
                            ActivityTypeDAO.deleteActivityType(type.getId());
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
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
