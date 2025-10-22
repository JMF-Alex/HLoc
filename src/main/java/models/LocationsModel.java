package models;

import javafx.beans.property.*;

import java.math.BigDecimal;

public class LocationsModel {
    private final IntegerProperty id;
    private final StringProperty country;
    private final StringProperty region;
    private final StringProperty city;
    private final ObjectProperty<BigDecimal> lat;
    private final ObjectProperty<BigDecimal> lon;

    public LocationsModel(int id, String country, String region, String city,
                          BigDecimal lat, BigDecimal lon) {
        this.id = new SimpleIntegerProperty(id);
        this.country = new SimpleStringProperty(country);
        this.region = new SimpleStringProperty(region);
        this.city = new SimpleStringProperty(city);
        this.lat = new SimpleObjectProperty<>(lat);
        this.lon = new SimpleObjectProperty<>(lon);
    }

    public IntegerProperty idProperty() { return id; }
    public StringProperty countryProperty() { return country; }
    public StringProperty regionProperty() { return region; }
    public StringProperty cityProperty() { return city; }
    public ObjectProperty<BigDecimal> latProperty() { return lat; }
    public ObjectProperty<BigDecimal> lonProperty() { return lon; }

    public int getId() { return id.get(); }
    public String getCountry() { return country.get(); }
    public String getRegion() { return region.get(); }
    public String getCity() { return city.get(); }
    public BigDecimal getLat() { return lat.get(); }
    public BigDecimal getLon() { return lon.get(); }

    public void setId(int id) { this.id.set(id); }
    public void setCountry(String country) { this.country.set(country); }
    public void setRegion(String region) { this.region.set(region); }
    public void setCity(String city) { this.city.set(city); }
    public void setLat(BigDecimal lat) { this.lat.set(lat); }
    public void setLon(BigDecimal lon) { this.lon.set(lon); }
}
