package utils;


import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class PasswordUtils {

    public static String hashPassword(String password) {
        Argon2 argon2 = Argon2Factory.create();
        try {
            return argon2.hash(2, 65536, 1, password);
        } finally {
            argon2.wipeArray(password.toCharArray());
        }
    }

    public static boolean verifyPassword(String hash, String password) {
        Argon2 argon2 = Argon2Factory.create();
        return argon2.verify(hash, password);
    }

}
