package models;

import javafx.beans.property.*;
import java.time.LocalDateTime;

public class RoutesModel {
    private final IntegerProperty id;
    private final IntegerProperty userId;
    private final IntegerProperty activityId;
    private final ObjectProperty<Integer> locationId;
    private final StringProperty title;
    private final StringProperty description;
    private final ObjectProperty<Double> distanceKm;
    private final ObjectProperty<Integer> elevationM;
    private final StringProperty difficulty;
    private final ObjectProperty<LocalDateTime> publicationDate;
    private final StringProperty visibility;

    public RoutesModel(int id, int userId, int activityId, Integer locationId,
                       String title, String description, Double distanceKm,
                       Integer elevationM, String difficulty,
                       LocalDateTime publicationDate, String visibility) {

        this.id = new SimpleIntegerProperty(id);
        this.userId = new SimpleIntegerProperty(userId);
        this.activityId = new SimpleIntegerProperty(activityId);
        this.locationId = new SimpleObjectProperty<>(locationId);
        this.title = new SimpleStringProperty(title);
        this.description = new SimpleStringProperty(description);
        this.distanceKm = new SimpleObjectProperty<>(distanceKm);
        this.elevationM = new SimpleObjectProperty<>(elevationM);
        this.difficulty = new SimpleStringProperty(difficulty);
        this.publicationDate = new SimpleObjectProperty<>(publicationDate);
        this.visibility = new SimpleStringProperty(visibility);
    }

    public IntegerProperty idProperty() { return id; }
    public IntegerProperty userIdProperty() { return userId; }
    public IntegerProperty activityIdProperty() { return activityId; }
    public ObjectProperty<Integer> locationIdProperty() { return locationId; }
    public StringProperty titleProperty() { return title; }
    public StringProperty descriptionProperty() { return description; }
    public ObjectProperty<Double> distanceKmProperty() { return distanceKm; }
    public ObjectProperty<Integer> elevationMProperty() { return elevationM; }
    public StringProperty difficultyProperty() { return difficulty; }
    public ObjectProperty<LocalDateTime> publicationDateProperty() { return publicationDate; }
    public StringProperty visibilityProperty() { return visibility; }

    public int getId() { return id.get(); }
    public int getUserId() { return userId.get(); }
    public int getActivityId() { return activityId.get(); }
    public Integer getLocationId() { return locationId.get(); }
    public String getTitle() { return title.get(); }
    public String getDescription() { return description.get(); }
    public Double getDistanceKm() { return distanceKm.get(); }
    public Integer getElevationM() { return elevationM.get(); }
    public String getDifficulty() { return difficulty.get(); }
    public LocalDateTime getPublicationDate() { return publicationDate.get(); }
    public String getVisibility() { return visibility.get(); }

    public void setUserId(int userId) { this.userId.set(userId); }
    public void setActivityId(int activityId) { this.activityId.set(activityId); }
    public void setLocationId(Integer locationId) { this.locationId.set(locationId); }
    public void setTitle(String title) { this.title.set(title); }
    public void setDescription(String description) { this.description.set(description); }
    public void setDistanceKm(Double distanceKm) { this.distanceKm.set(distanceKm); }
    public void setElevationM(Integer elevationM) { this.elevationM.set(elevationM); }
    public void setDifficulty(String difficulty) { this.difficulty.set(difficulty); }
    public void setPublicationDate(LocalDateTime publicationDate) { this.publicationDate.set(publicationDate); }
    public void setVisibility(String visibility) { this.visibility.set(visibility); }
}
