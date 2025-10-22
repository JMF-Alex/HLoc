package utils;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class Utils {

    public static void showAnimation(Node node) {
        ScaleTransition scale = new ScaleTransition(Duration.millis(200), node);
        scale.setFromX(0.7);
        scale.setFromY(0.7);
        scale.setToX(1);
        scale.setToY(1);

        FadeTransition fade = new FadeTransition(Duration.millis(200), node);
        fade.setFromValue(0);
        fade.setToValue(1);

        scale.play();
        fade.play();
    }
}
