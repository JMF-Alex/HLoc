package db;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.LocationsModel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.*;

public class LocationsDAO {

    public static @NotNull ObservableList<LocationsModel> getAllLocations() {
        ObservableList<LocationsModel> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM locations";

        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(new LocationsModel(
                        rs.getInt("id"),
                        rs.getString("country"),
                        rs.getString("region"),
                        rs.getString("city"),
                        rs.getBigDecimal("lat"),
                        rs.getBigDecimal("lon")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static boolean locationExists(int id) {
        String sql = "SELECT 1 FROM locations WHERE id=?";

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

    public static void insertLocation(@NotNull LocationsModel location) {
        String sql = """
                INSERT INTO locations (country, region, city, lat, lon)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, location.getCountry());
            stmt.setString(2, location.getRegion());
            stmt.setString(3, location.getCity());
            stmt.setBigDecimal(4, location.getLat());
            stmt.setBigDecimal(5, location.getLon());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    location.setId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateLocation(@NotNull LocationsModel location) {
        String sql = """
                UPDATE locations
                SET country=?, region=?, city=?, lat=?, lon=?
                WHERE id=?
                """;

        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, location.getCountry());
            stmt.setString(2, location.getRegion());
            stmt.setString(3, location.getCity());
            stmt.setBigDecimal(4, location.getLat());
            stmt.setBigDecimal(5, location.getLon());
            stmt.setInt(6, location.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteLocation(int id) {
        String sql = "DELETE FROM locations WHERE id=?";

        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static @Nullable LocationsModel getLocationById(int id) {
        String sql = "SELECT * FROM locations WHERE id=?";

        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new LocationsModel(
                        rs.getInt("id"),
                        rs.getString("country"),
                        rs.getString("region"),
                        rs.getString("city"),
                        rs.getBigDecimal("lat"),
                        rs.getBigDecimal("lon")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static int getTotalLocations() {
        String sql = "SELECT COUNT(*) AS total FROM locations";

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

    public static @NotNull ObservableList<LocationsModel> getLocationsByCountry(@NotNull String country) {
        ObservableList<LocationsModel> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM locations WHERE country=?";

        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, country);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new LocationsModel(
                        rs.getInt("id"),
                        rs.getString("country"),
                        rs.getString("region"),
                        rs.getString("city"),
                        rs.getBigDecimal("lat"),
                        rs.getBigDecimal("lon")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
