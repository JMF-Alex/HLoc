package models;

import javafx.beans.property.*;
import java.math.BigDecimal;

public class RoutePointsModel {
    private final IntegerProperty id;
    private final IntegerProperty routeId;
    private final IntegerProperty pointOrder;
    private final ObjectProperty<BigDecimal> lat;
    private final ObjectProperty<BigDecimal> lon;
    private final ObjectProperty<BigDecimal> altitudeM;

    public RoutePointsModel(int id, int routeId, int pointOrder,
                            BigDecimal lat, BigDecimal lon, BigDecimal altitudeM) {
        this.id = new SimpleIntegerProperty(id);
        this.routeId = new SimpleIntegerProperty(routeId);
        this.pointOrder = new SimpleIntegerProperty(pointOrder);
        this.lat = new SimpleObjectProperty<>(lat);
        this.lon = new SimpleObjectProperty<>(lon);
        this.altitudeM = new SimpleObjectProperty<>(altitudeM);
    }

    public IntegerProperty idProperty() { return id; }
    public IntegerProperty routeIdProperty() { return routeId; }
    public IntegerProperty pointOrderProperty() { return pointOrder; }
    public ObjectProperty<BigDecimal> latProperty() { return lat; }
    public ObjectProperty<BigDecimal> lonProperty() { return lon; }
    public ObjectProperty<BigDecimal> altitudeMProperty() { return altitudeM; }

    public int getId() { return id.get(); }
    public int getRouteId() { return routeId.get(); }
    public int getPointOrder() { return pointOrder.get(); }
    public BigDecimal getLat() { return lat.get(); }
    public BigDecimal getLon() { return lon.get(); }
    public BigDecimal getAltitudeM() { return altitudeM.get(); }

    public void setRouteId(int routeId) { this.routeId.set(routeId); }
    public void setPointOrder(int pointOrder) { this.pointOrder.set(pointOrder); }
    public void setLat(BigDecimal lat) { this.lat.set(lat); }
    public void setLon(BigDecimal lon) { this.lon.set(lon); }
    public void setAltitudeM(BigDecimal altitudeM) { this.altitudeM.set(altitudeM); }
}
