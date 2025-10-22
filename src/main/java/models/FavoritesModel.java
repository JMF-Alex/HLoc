package models;

import javafx.beans.property.*;
import java.time.LocalDateTime;

public class FavoritesModel {
    private final IntegerProperty id;
    private final IntegerProperty userId;
    private final IntegerProperty routeId;
    private final ObjectProperty<LocalDateTime> addedAt;

    public FavoritesModel(int id, int userId, int routeId, LocalDateTime addedAt) {
        this.id = new SimpleIntegerProperty(id);
        this.userId = new SimpleIntegerProperty(userId);
        this.routeId = new SimpleIntegerProperty(routeId);
        this.addedAt = new SimpleObjectProperty<>(addedAt);
    }

    public IntegerProperty idProperty() { return id; }
    public IntegerProperty userIdProperty() { return userId; }
    public IntegerProperty routeIdProperty() { return routeId; }
    public ObjectProperty<LocalDateTime> addedAtProperty() { return addedAt; }

    public int getId() { return id.get(); }
    public int getUserId() { return userId.get(); }
    public int getRouteId() { return routeId.get(); }
    public LocalDateTime getAddedAt() { return addedAt.get(); }

    public void setUserId(int userId) { this.userId.set(userId); }
    public void setRouteId(int routeId) { this.routeId.set(routeId); }
    public void setAddedAt(LocalDateTime addedAt) { this.addedAt.set(addedAt); }
}
