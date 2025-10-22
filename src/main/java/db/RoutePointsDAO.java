package db;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.RoutePointsModel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.*;

public class RoutePointsDAO {

    public static @NotNull ObservableList<RoutePointsModel> getAllRoutePoints() {
        ObservableList<RoutePointsModel> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM route_points";

        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(new RoutePointsModel(
                        rs.getInt("id"),
                        rs.getInt("route_id"),
                        rs.getInt("point_order"),
                        rs.getBigDecimal("lat"),
                        rs.getBigDecimal("lon"),
                        rs.getBigDecimal("altitude_m")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static boolean routePointExists(int id) {
        String sql = "SELECT 1 FROM route_points WHERE id=?";

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

    public static void insertRoutePoint(@NotNull RoutePointsModel point) {
        String sql = """
                INSERT INTO route_points (route_id, point_order, lat, lon, altitude_m)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, point.getRouteId());
            stmt.setInt(2, point.getPointOrder());
            stmt.setBigDecimal(3, point.getLat());
            stmt.setBigDecimal(4, point.getLon());
            stmt.setBigDecimal(5, point.getAltitudeM());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateRoutePoint(@NotNull RoutePointsModel point) {
        String sql = """
                UPDATE route_points
                SET route_id=?, point_order=?, lat=?, lon=?, altitude_m=?
                WHERE id=?
                """;

        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, point.getRouteId());
            stmt.setInt(2, point.getPointOrder());
            stmt.setBigDecimal(3, point.getLat());
            stmt.setBigDecimal(4, point.getLon());
            stmt.setBigDecimal(5, point.getAltitudeM());
            stmt.setInt(6, point.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteRoutePoint(int id) {
        String sql = "DELETE FROM route_points WHERE id=?";

        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static @Nullable RoutePointsModel getRoutePointById(int id) {
        String sql = "SELECT * FROM route_points WHERE id=?";

        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new RoutePointsModel(
                        rs.getInt("id"),
                        rs.getInt("route_id"),
                        rs.getInt("point_order"),
                        rs.getBigDecimal("lat"),
                        rs.getBigDecimal("lon"),
                        rs.getBigDecimal("altitude_m")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static int getTotalRoutePoints() {
        String sql = "SELECT COUNT(*) AS total FROM route_points";

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

    public static @NotNull ObservableList<RoutePointsModel> getPointsByRouteId(int routeId) {
        ObservableList<RoutePointsModel> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM route_points WHERE route_id=? ORDER BY point_order ASC";

        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, routeId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new RoutePointsModel(
                        rs.getInt("id"),
                        rs.getInt("route_id"),
                        rs.getInt("point_order"),
                        rs.getBigDecimal("lat"),
                        rs.getBigDecimal("lon"),
                        rs.getBigDecimal("altitude_m")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
