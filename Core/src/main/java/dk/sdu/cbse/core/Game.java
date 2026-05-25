package dk.sdu.cbse.core;

import dk.sdu.cbse.data.GameData;
import dk.sdu.cbse.data.GameKeys;
import dk.sdu.cbse.data.World;
import dk.sdu.cbse.services.IEntityProcessingService;
import dk.sdu.cbse.services.IGamePluginService;
import dk.sdu.cbse.services.IPostEntityProcessingService;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Collection;
import java.util.List;

public class Game {

    private final GameData gameData;
    private final World world;
    private final ScoreClient scoreClient;
    private final Collection<? extends IGamePluginService> pluginServices;
    private final List<IEntityProcessingService> entityProcessors;
    private final List<IPostEntityProcessingService> postProcessors;
    private Canvas canvas;

    public Game(
            GameData gameData,
            World world,
            ScoreClient scoreClient,
            Collection<? extends IGamePluginService> pluginServices,
            List<IEntityProcessingService> entityProcessors,
            List<IPostEntityProcessingService> postProcessors
    ) {
        this.gameData = gameData;
        this.world = world;
        this.scoreClient = scoreClient;
        this.pluginServices = pluginServices;
        this.entityProcessors = entityProcessors;
        this.postProcessors = postProcessors;
    }

    public void start(Stage stage) {
        scoreClient.resetScore();
        canvas = new Canvas(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        Pane root = new Pane(canvas);
        Scene scene = new Scene(root, gameData.getDisplayWidth(), gameData.getDisplayHeight());

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

        for (IGamePluginService plugin : pluginServices) {
            plugin.start(gameData, world);
        }

        GameLoop loop = new GameLoop(gameData, world, canvas, scoreClient, entityProcessors, postProcessors);
        loop.start();

        stage.setTitle("AsteroidsFX");
        stage.setScene(scene);
        stage.show();

        stage.requestFocus();
        canvas.requestFocus();
        canvas.setFocusTraversable(true);
    }

    public void stop() {
        for (IGamePluginService plugin : pluginServices) {
            plugin.stop(gameData, world);
        }
    }
}
