package models;

import javafx.beans.property.*;
import java.time.LocalDateTime;

public class CommentsModel {

    private final IntegerProperty id;
    private final IntegerProperty routeId;
    private final IntegerProperty userId;
    private final StringProperty comment;
    private final IntegerProperty rating;
    private final ObjectProperty<LocalDateTime> createdAt;

    public CommentsModel(int id, int routeId, int userId, String comment, int rating, LocalDateTime createdAt) {
        this.id = new SimpleIntegerProperty(id);
        this.routeId = new SimpleIntegerProperty(routeId);
        this.userId = new SimpleIntegerProperty(userId);
        this.comment = new SimpleStringProperty(comment);
        this.rating = new SimpleIntegerProperty(rating);
        this.createdAt = new SimpleObjectProperty<>(createdAt);
    }

    public IntegerProperty idProperty() { return id; }
    public IntegerProperty routeIdProperty() { return routeId; }
    public IntegerProperty userIdProperty() { return userId; }
    public StringProperty commentProperty() { return comment; }
    public IntegerProperty ratingProperty() { return rating; }
    public ObjectProperty<LocalDateTime> createdAtProperty() { return createdAt; }

    public int getId() { return id.get(); }
    public int getRouteId() { return routeId.get(); }
    public int getUserId() { return userId.get(); }
    public String getComment() { return comment.get(); }
    public int getRating() { return rating.get(); }
    public LocalDateTime getCreatedAt() { return createdAt.get(); }

    public void setRouteId(int routeId) { this.routeId.set(routeId); }
    public void setUserId(int userId) { this.userId.set(userId); }
    public void setComment(String comment) { this.comment.set(comment); }
    public void setRating(int rating) { this.rating.set(rating); }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt.set(createdAt); }
}
