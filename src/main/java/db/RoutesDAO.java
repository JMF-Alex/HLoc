package db;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.RoutesModel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.*;
import java.time.LocalDateTime;

public class RoutesDAO {

    public static @NotNull ObservableList<RoutesModel> getAllRoutes() {
        ObservableList<RoutesModel> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM routes";

        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Timestamp ts = rs.getTimestamp("publication_date");

                list.add(new RoutesModel(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("activity_id"),
                        rs.getObject("location_id") != null ? rs.getInt("location_id") : null,
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getObject("distance_km") != null ? rs.getDouble("distance_km") : null,
                        rs.getObject("elevation_m") != null ? rs.getInt("elevation_m") : null,
                        rs.getString("difficulty"),
                        ts != null ? ts.toLocalDateTime() : null,
                        rs.getString("visibility")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static @NotNull ObservableList<RoutesModel> getRoutesByUserId(int userId) {
        ObservableList<RoutesModel> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM routes WHERE user_id = ?";

        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Timestamp ts = rs.getTimestamp("publication_date");

                    list.add(new RoutesModel(
                            rs.getInt("id"),
                            rs.getInt("user_id"),
                            rs.getInt("activity_id"),
                            rs.getObject("location_id") != null ? rs.getInt("location_id") : null,
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getObject("distance_km") != null ? rs.getDouble("distance_km") : null,
                            rs.getObject("elevation_m") != null ? rs.getInt("elevation_m") : null,
                            rs.getString("difficulty"),
                            ts != null ? ts.toLocalDateTime() : null,
                            rs.getString("visibility")
                    ));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static boolean routeExists(String title) {
        String sql = "SELECT id FROM routes WHERE title=?";
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, title);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }

    public static boolean routeExists(int id) {
        String sql = "SELECT 1 FROM routes WHERE id=?";

        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void insertRoute(@NotNull RoutesModel route) {
        String sql = """
                INSERT INTO routes (user_id, activity_id, location_id, title, description,
                                    distance_km, elevation_m, difficulty, publication_date, visibility)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, route.getUserId());
            stmt.setInt(2, route.getActivityId());
            stmt.setObject(3, route.getLocationId() == 0 ? null : route.getLocationId());
            stmt.setString(4, route.getTitle());
            stmt.setString(5, route.getDescription());
            stmt.setObject(6, route.getDistanceKm());
            stmt.setObject(7, route.getElevationM());
            stmt.setString(8, route.getDifficulty());
            stmt.setTimestamp(9, route.getPublicationDate() != null ? Timestamp.valueOf(route.getPublicationDate()) : Timestamp.valueOf(LocalDateTime.now()));
            stmt.setString(10, route.getVisibility());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateRoute(@NotNull RoutesModel route) {
        String sql = """
                UPDATE routes
                SET user_id=?, activity_id=?, location_id=?, title=?, description=?,
                    distance_km=?, elevation_m=?, difficulty=?, publication_date=?, visibility=?
                WHERE id=?
                """;

        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, route.getUserId());
            stmt.setInt(2, route.getActivityId());
            stmt.setObject(3, route.getLocationId() == 0 ? null : route.getLocationId());
            stmt.setString(4, route.getTitle());
            stmt.setString(5, route.getDescription());
            stmt.setObject(6, route.getDistanceKm());
            stmt.setObject(7, route.getElevationM());
            stmt.setString(8, route.getDifficulty());
            stmt.setTimestamp(9, route.getPublicationDate() != null ? Timestamp.valueOf(route.getPublicationDate()) : null);
            stmt.setString(10, route.getVisibility());
            stmt.setInt(11, route.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteRoute(int id) {
        String sql = "DELETE FROM routes WHERE id=?";

        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static @Nullable RoutesModel getRouteById(int id) {
        String sql = "SELECT * FROM routes WHERE id=?";

        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Timestamp ts = rs.getTimestamp("publication_date");

                return new RoutesModel(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("activity_id"),
                        rs.getObject("location_id") != null ? rs.getInt("location_id") : null,
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getObject("distance_km") != null ? rs.getDouble("distance_km") : null,
                        rs.getObject("elevation_m") != null ? rs.getInt("elevation_m") : null,
                        rs.getString("difficulty"),
                        ts != null ? ts.toLocalDateTime() : null,
                        rs.getString("visibility")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static int getTotalRoutes() {
        String sql = "SELECT COUNT(*) AS total FROM routes";

        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static int getTotalRoutesByUser(int userId) {
        String sql = "SELECT COUNT(*) AS total FROM routes WHERE user_id = ?";

        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static @NotNull ObservableList<RoutesModel> getRoutesByUser(int userId) {
        ObservableList<RoutesModel> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM routes WHERE user_id=?";

        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Timestamp ts = rs.getTimestamp("publication_date");

                list.add(new RoutesModel(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("activity_id"),
                        rs.getObject("location_id") != null ? rs.getInt("location_id") : null,
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getObject("distance_km") != null ? rs.getDouble("distance_km") : null,
                        rs.getObject("elevation_m") != null ? rs.getInt("elevation_m") : null,
                        rs.getString("difficulty"),
                        ts != null ? ts.toLocalDateTime() : null,
                        rs.getString("visibility")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
