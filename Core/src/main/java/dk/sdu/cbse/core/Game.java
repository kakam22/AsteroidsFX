package dk.sdu.cbse.core;

import dk.sdu.cbse.data.GameData;
import dk.sdu.cbse.data.GameKeys;
import dk.sdu.cbse.data.World;
import dk.sdu.cbse.services.IEntityProcessingService;
import dk.sdu.cbse.services.IGamePluginService;
import dk.sdu.cbse.services.IPostEntityProcessingService;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ServiceLoader;

public class Game extends Application {

    private final GameData gameData = new GameData();
    private final World world = new World();
    private Canvas canvas;

    @Override
    public void start(Stage stage) {
        canvas = new Canvas(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        Pane root = new Pane(canvas);
        Scene scene = new Scene(root, gameData.getDisplayWidth(), gameData.getDisplayHeight());

        // Key press handling
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.LEFT)  gameData.getKeys().setKey(GameKeys.LEFT, true);
            if (e.getCode() == KeyCode.RIGHT) gameData.getKeys().setKey(GameKeys.RIGHT, true);
            if (e.getCode() == KeyCode.UP)    gameData.getKeys().setKey(GameKeys.UP, true);
            if (e.getCode() == KeyCode.DOWN)  gameData.getKeys().setKey(GameKeys.DOWN, true);
            if (e.getCode() == KeyCode.SPACE) gameData.getKeys().setKey(GameKeys.SPACE, true);
        });
        scene.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.LEFT)  gameData.getKeys().setKey(GameKeys.LEFT, false);
            if (e.getCode() == KeyCode.RIGHT) gameData.getKeys().setKey(GameKeys.RIGHT, false);
            if (e.getCode() == KeyCode.UP)    gameData.getKeys().setKey(GameKeys.UP, false);
            if (e.getCode() == KeyCode.DOWN)  gameData.getKeys().setKey(GameKeys.DOWN, false);
            if (e.getCode() == KeyCode.SPACE) gameData.getKeys().setKey(GameKeys.SPACE, false);
        });

        // Start all plugins
        for (IGamePluginService plugin : ServiceLoader.load(IGamePluginService.class)) {
            plugin.start(gameData, world);
        }

        // Start game loop
        GameLoop loop = new GameLoop(gameData, world, canvas);
        loop.start();

        stage.setTitle("AsteroidsFX");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        // Stop all plugins on window close
        for (IGamePluginService plugin : ServiceLoader.load(IGamePluginService.class)) {
            plugin.stop(gameData, world);
        }
    }
}