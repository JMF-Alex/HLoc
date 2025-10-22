package models;

import javafx.beans.property.*;

public class UserModel {
    private final IntegerProperty id;
    private final StringProperty name;
    private final StringProperty country;
    private final StringProperty city;
    private final BooleanProperty admin;

    public UserModel(int id, String name, String country, String city, boolean admin) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.country = new SimpleStringProperty(country);
        this.city = new SimpleStringProperty(city);
        this.admin = new SimpleBooleanProperty(admin);
    }

    public IntegerProperty idProperty() { return id; }
    public StringProperty nameProperty() { return name; }
    public StringProperty countryProperty() { return country; }
    public StringProperty cityProperty() { return city; }
    public BooleanProperty adminProperty() { return admin; }

    public int getId() { return id.get(); }
    public String getName() { return name.get(); }
    public String getCountry() { return country.get(); }
    public String getCity() { return city.get(); }
    public boolean isAdmin() { return admin.get(); }

    public void setName(String name) { this.name.set(name); }
    public void setCountry(String country) { this.country.set(country); }
    public void setCity(String city) { this.city.set(city); }
    public void setAdmin(boolean admin) { this.admin.set(admin); }
}
