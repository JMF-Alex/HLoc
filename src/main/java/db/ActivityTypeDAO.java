package db;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.ActivityTypesModel;
import org.jetbrains.annotations.NotNull;

import java.sql.*;

public class ActivityTypeDAO {

    public static @NotNull ObservableList<ActivityTypesModel> getAllActivityTypes() {
        ObservableList<ActivityTypesModel> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM activity_types";
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(new ActivityTypesModel(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public static void deleteActivityType(int id) throws SQLException {
        String sql = "DELETE FROM activity_types WHERE id=?";
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public static void addActivityType(String name, String description) throws SQLException {
        String sql = "INSERT INTO activity_types (name, description) VALUES (?, ?)";
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.executeUpdate();
        }
    }

    public static boolean activityTypesExists(String username) {
        String sql = "SELECT id FROM activity_types WHERE name=?";
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }

    public static boolean activityTypesExists(int id) {
        String sql = "SELECT 1 FROM activity_types WHERE id=?";

        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }

    public static int getTotalActivityTypes() {
        String sql = "SELECT COUNT(*) AS total FROM activity_types";
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

    public static void updateActivityType(int id, String name, String description) {
        String sql = "UPDATE activity_types SET name = ?, description = ? WHERE id = ?";
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setInt(3, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
