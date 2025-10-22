package utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class Dialogs {

    public static class ConfirmationDialog {
        public static boolean show(String title, String message) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.YES, ButtonType.NO);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.showAndWait();
            return alert.getResult() == ButtonType.YES;
        }
    }

    public static class AlertDialog {
        public static void showInfo(String title, String message) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        }

        public static void showError(String title, String message) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        }

        public static void showWarning(String title, String message) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        }
    }
}
