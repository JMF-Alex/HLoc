package main;

import javafx.application.Application;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import views.main.MainPane;

public class Main extends Application {

    @Override
    public void start(@NotNull Stage stage) {
        MainPane.app(stage);
    }
}
