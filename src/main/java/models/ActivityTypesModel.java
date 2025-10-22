package models;

import javafx.beans.property.*;

public class ActivityTypesModel {

    private final IntegerProperty id;
    private final StringProperty name;
    private final StringProperty description;

    public ActivityTypesModel(int id, String name, String description) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
    }

    public IntegerProperty idProperty() { return id; }
    public StringProperty nameProperty() { return name; }
    public StringProperty descriptionProperty() { return description; }

    public int getId() { return id.get(); }
    public String getName() { return name.get(); }
    public String getDescription() { return description.get(); }

    public void setName(String name) { this.name.set(name); }
    public void setDescription(String description) { this.description.set(description); }
}
