package db;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.FavoritesModel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.*;

public class FavoritesDAO {

    public static @NotNull ObservableList<FavoritesModel> getAllFavorites() {
        ObservableList<FavoritesModel> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM favorites";

        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Timestamp ts = rs.getTimestamp("added_at");
                list.add(new FavoritesModel(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("route_id"),
                        ts != null ? ts.toLocalDateTime() : null
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static @NotNull ObservableList<FavoritesModel> getFavoritesByUserId(int userId) {
        ObservableList<FavoritesModel> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM favorites WHERE user_id = ?";

        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Timestamp ts = rs.getTimestamp("added_at");
                    list.add(new FavoritesModel(
                            rs.getInt("id"),
                            rs.getInt("user_id"),
                            rs.getInt("route_id"),
                            ts != null ? ts.toLocalDateTime() : null
                    ));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static void insertFavorite(int userId, int routeId) {
        String sql = "INSERT INTO favorites (user_id, route_id) VALUES (?, ?)";

        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, routeId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteFavorite(int id) {
        String sql = "DELETE FROM favorites WHERE id=?";

        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getTotalFavorites() {
        String sql = "SELECT COUNT(*) AS total FROM favorites";

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

    public static int getTotalFavoritesByUser(int userId) {
        String sql = "SELECT COUNT(*) AS total FROM favorites WHERE user_id = ?";

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

    public static boolean favoriteExists(int userId, int routeId) {
        String sql = "SELECT id FROM favorites WHERE user_id=? AND route_id=?";

        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, routeId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
