package dk.sdu.cbse.core;

import dk.sdu.cbse.data.Entity;
import dk.sdu.cbse.data.GameData;
import dk.sdu.cbse.data.World;
import dk.sdu.cbse.services.IEntityProcessingService;
import dk.sdu.cbse.services.IPostEntityProcessingService;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ServiceLoader;

public class GameLoop extends AnimationTimer {

    private final GameData gameData;
    private final World world;
    private final Canvas canvas;

    public GameLoop(GameData gameData, World world, Canvas canvas) {
        this.gameData = gameData;
        this.world = world;
        this.canvas = canvas;
    }

    @Override
    public void handle(long now) {
        // Update key states
        gameData.getKeys().update();

        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Clear screen
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, gameData.getDisplayWidth(), gameData.getDisplayHeight());

        // Run all entity processors
        for (IEntityProcessingService processor : ServiceLoader.load(IEntityProcessingService.class)) {
            processor.process(gameData, world);
        }

        // Run all post processors (collision etc.)
        for (IPostEntityProcessingService postProcessor : ServiceLoader.load(IPostEntityProcessingService.class)) {
            postProcessor.process(gameData, world);
        }

        // Draw all entities
        for (Entity entity : world.getEntities()) {
            drawEntity(gc, entity);
        }
    }

    private void drawEntity(GraphicsContext gc, Entity entity) {
        double[] coords = entity.getPolygonCoordinates();
        if (coords == null) return;

        int numPoints = coords.length / 2;
        double[] xPoints = new double[numPoints];
        double[] yPoints = new double[numPoints];

        double cos = Math.cos(Math.toRadians(entity.getRotation()));
        double sin = Math.sin(Math.toRadians(entity.getRotation()));

        for (int i = 0; i < numPoints; i++) {
            double localX = coords[i * 2];
            double localY = coords[i * 2 + 1];
            // Rotate and translate to world position
            xPoints[i] = entity.getX() + (localX * cos - localY * sin);
            yPoints[i] = entity.getY() + (localX * sin + localY * cos);
        }

        gc.setStroke(Color.WHITE);
        gc.setLineWidth(1);
        gc.strokePolygon(xPoints, yPoints, numPoints);
    }
}