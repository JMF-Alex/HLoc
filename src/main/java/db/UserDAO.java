package db;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.UserModel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import utils.PasswordUtils;

import java.sql.*;

public class UserDAO {

    public static boolean checkLogin(String user, String pass) {
        String sql = "SELECT password_hash, is_admin FROM users WHERE name=?";
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String storedHash = rs.getString("password_hash");
                return PasswordUtils.verifyPassword(storedHash, pass);
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean registerUser(String user, String hashedPassword, String country, String city, boolean isAdmin) {
        String sql = "INSERT INTO users (name, password_hash, country, city, is_admin) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user);
            stmt.setString(2, hashedPassword);
            stmt.setString(3, country.isEmpty() ? null : country);
            stmt.setString(4, city.isEmpty() ? null : city);
            stmt.setBoolean(5, isAdmin);
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id=?";
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean userExists(String username) {
        String sql = "SELECT id FROM users WHERE name=?";
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }

    public static boolean userExists(int id) {
        String sql = "SELECT id FROM users WHERE id=?";
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }

    public static @NotNull ObservableList<UserModel> getAllUsers() {
        ObservableList<UserModel> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM users";

        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(new UserModel(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("country"),
                        rs.getString("city"),
                        rs.getBoolean("is_admin")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public static void updateUser(int id, String name, String country, String city, boolean isAdmin) {
        String sql = "UPDATE users SET name = ?, country = ?, city = ?, is_admin = ? WHERE id = ?";

        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setString(2, (country == null || country.isEmpty()) ? null : country);
            stmt.setString(3, (city == null || city.isEmpty()) ? null : city);
            stmt.setBoolean(4, isAdmin);
            stmt.setInt(5, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static @Nullable UserModel getUserByUsername(String username) {
        String sql = "SELECT * FROM users WHERE name=?";
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new UserModel(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("country"),
                        rs.getString("city"),
                        rs.getBoolean("is_admin")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getTotalUsers() {
        String sql = "SELECT COUNT(*) AS total FROM users";
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
}
