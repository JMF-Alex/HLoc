package models;

import org.jetbrains.annotations.Nullable;

public class CurrentUserModel {
    private static UserModel user;

    public static void setUser(UserModel u) {
        user = u;
    }

    public static UserModel getUser() {
        return user;
    }

    public static void clearAll() {
        user = null;
    }

    public static boolean isAdmin() {
        return user != null && user.isAdmin();
    }

    public static @Nullable String getUsername() {
        return user != null ? user.getName() : null;
    }
}
