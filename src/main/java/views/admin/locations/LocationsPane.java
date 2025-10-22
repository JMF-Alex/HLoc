package views.admin.locations;

import db.LocationsDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import models.LocationsModel;
import org.jetbrains.annotations.NotNull;
import utils.Dialogs;

import java.math.BigDecimal;

public class LocationsPane {

    private final VBox root;
    private static TableView<LocationsModel> table = new TableView<>();

    public LocationsPane() {
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

        Label title = new Label("Ubicaciones registradas");
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

        TableColumn<LocationsModel, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(c -> c.getValue().idProperty().asObject());

        TableColumn<LocationsModel, String> countryCol = new TableColumn<>("País");
        countryCol.setCellValueFactory(c -> c.getValue().countryProperty());

        TableColumn<LocationsModel, String> regionCol = new TableColumn<>("Región");
        regionCol.setCellValueFactory(c -> c.getValue().regionProperty());

        TableColumn<LocationsModel, String> cityCol = new TableColumn<>("Ciudad");
        cityCol.setCellValueFactory(c -> c.getValue().cityProperty());

        TableColumn<LocationsModel, BigDecimal> latCol = new TableColumn<>("Latitud");
        latCol.setCellValueFactory(c -> c.getValue().latProperty());

        TableColumn<LocationsModel, BigDecimal> lonCol = new TableColumn<>("Longitud");
        lonCol.setCellValueFactory(c -> c.getValue().lonProperty());

        TableColumn<LocationsModel, Void> actionsCol = getLocationVoidTableColumn();
        actionsCol.setPrefWidth(150);

        table.getColumns().addAll(idCol, countryCol, regionCol, cityCol, latCol, lonCol, actionsCol);

        setItems();

        refreshButton.setOnAction(e -> setItems());
        addButton.setOnAction(e -> AddLocationsPane.openAdder());

        root.getChildren().addAll(header, table);
    }

    public static void setItems() {
        table.setItems(LocationsDAO.getAllLocations());
    }

    private static @NotNull TableColumn<LocationsModel, Void> getLocationVoidTableColumn() {
        TableColumn<LocationsModel, Void> actionsCol = new TableColumn<>("Acciones");
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
                    LocationsModel location = getTableView().getItems().get(getIndex());
                    EditLocationsPane.openEditor(location);
                });

                deleteButton.setStyle("""
                    -fx-background-color: #EF4444;
                    -fx-text-fill: white;
                    -fx-font-size: 13px;
                    -fx-background-radius: 6px;
                    -fx-padding: 4 10 4 10;
                """);
                deleteButton.setOnAction(e -> {
                    LocationsModel location = getTableView().getItems().get(getIndex());
                    boolean confirmed = Dialogs.ConfirmationDialog.show("Eliminar ubicación",
                            "¿Estás seguro de que quieres eliminar la ubicación \"" + location.getCity() + "\"?");
                    if (confirmed) {
                        LocationsDAO.deleteLocation(location.getId());
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
