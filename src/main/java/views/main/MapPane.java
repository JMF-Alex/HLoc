package views.main;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.scene.image.Image;

public class MapPane extends StackPane {

    private final Canvas canvas = new Canvas(1000, 500);
    private final int zoom = 5;
    private final int tileSize = 256;
    private final ExecutorService executor = Executors.newFixedThreadPool(4);

    public MapPane() {
        getChildren().add(canvas);
        setStyle("-fx-background-color: #ddd; -fx-border-radius: 12px; -fx-background-radius: 12px;");
        drawMap();
    }

    private void drawMap() {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        int tilesCountX = (int) Math.ceil(canvas.getWidth() / tileSize) + 1;
        int tilesCountY = (int) Math.ceil(canvas.getHeight() / tileSize) + 1;

        double centerLon = -3.0;
        double centerTileX = lonToTileX(centerLon);
        double centerLat = 39.0;
        double centerTileY = latToTileY(centerLat);

        double offsetX = canvas.getWidth() / 2 - (centerTileX - Math.floor(centerTileX)) * tileSize;
        double offsetY = canvas.getHeight() / 2 - (centerTileY - Math.floor(centerTileY)) * tileSize;

        for (int x = -tilesCountX/2; x <= tilesCountX/2; x++) {
            for (int y = -tilesCountY/2; y <= tilesCountY/2; y++) {
                int tileX = (int) Math.floor(centerTileX) + x;
                int tileY = (int) Math.floor(centerTileY) + y;
                int drawX = (int) (offsetX + x * tileSize);
                int drawY = (int) (offsetY + y * tileSize);

                executor.submit(() -> {
                    try {
                        String urlStr = "https://tile.openstreetmap.org/" + zoom + "/" + tileX + "/" + tileY + ".png";
                        URL url = new URL(urlStr);
                        var conn = (java.net.HttpURLConnection) url.openConnection();
                        conn.setRequestProperty("User-Agent", "HLocApp/1.0 (contacto@tuhost.com)");
                        InputStream is = conn.getInputStream();
                        Image img = new Image(is);
                        Platform.runLater(() -> gc.drawImage(img, drawX, drawY, tileSize, tileSize));
                    } catch (Exception e) {
                        Platform.runLater(() -> {
                            gc.setFill(Color.LIGHTGRAY);
                            gc.fillRect(drawX, drawY, tileSize, tileSize);
                        });
                    }
                });
            }
        }
    }

    private double lonToTileX(double lon) {
        return (lon + 180) / 360 * Math.pow(2, zoom);
    }

    private double latToTileY(double lat) {
        double rad = Math.toRadians(lat);
        return (1 - Math.log(Math.tan(rad) + 1 / Math.cos(rad)) / Math.PI) / 2 * Math.pow(2, zoom);
    }
}
