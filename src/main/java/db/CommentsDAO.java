package db;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.CommentsModel;
import org.jetbrains.annotations.NotNull;

import java.sql.*;

public class CommentsDAO {

    public static @NotNull ObservableList<CommentsModel> getAllComments() {
        ObservableList<CommentsModel> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM comments";

        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(new CommentsModel(
                        rs.getInt("id"),
                        rs.getInt("route_id"),
                        rs.getInt("user_id"),
                        rs.getString("comment"),
                        rs.getInt("rating"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static @NotNull ObservableList<CommentsModel> getCommentsByUserId(int userId) {
        ObservableList<CommentsModel> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM comments WHERE user_id = ?";

        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(new CommentsModel(
                            rs.getInt("id"),
                            rs.getInt("route_id"),
                            rs.getInt("user_id"),
                            rs.getString("comment"),
                            rs.getInt("rating"),
                            rs.getTimestamp("created_at").toLocalDateTime()
                    ));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static void insertComment(int routeId, int userId, @NotNull String comment, int rating) {
        String sql = "INSERT INTO comments (route_id, user_id, comment, rating) VALUES (?, ?, ?, ?)";

        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, routeId);
            stmt.setInt(2, userId);
            stmt.setString(3, comment.isEmpty() ? null : comment);
            stmt.setInt(4, rating);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateComment(int id, @NotNull String comment, int rating) {
        String sql = "UPDATE comments SET comment = ?, rating = ? WHERE id = ?";

        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, comment.isEmpty() ? null : comment);
            stmt.setInt(2, rating);
            stmt.setInt(3, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteComment(int id) {
        String sql = "DELETE FROM comments WHERE id=?";

        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getTotalComments() {
        String sql = "SELECT COUNT(*) AS total FROM comments";

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

    public static int getTotalCommentsByUser(int userId) {
        String sql = "SELECT COUNT(*) AS total FROM comments WHERE user_id = ?";

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
}
