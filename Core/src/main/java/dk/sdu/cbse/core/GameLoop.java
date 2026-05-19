package dk.sdu.cbse.core;

import dk.sdu.cbse.common.bullet.Bullet;
import dk.sdu.cbse.common.player.Player;
import dk.sdu.cbse.data.Entity;
import dk.sdu.cbse.data.GameData;
import dk.sdu.cbse.data.World;
import dk.sdu.cbse.services.IEntityProcessingService;
import dk.sdu.cbse.services.IPostEntityProcessingService;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class GameLoop extends AnimationTimer {

    private final GameData gameData;
    private final World world;
    private final Canvas canvas;
    private final List<IEntityProcessingService> entityProcessors;
    private final List<IPostEntityProcessingService> postProcessors;

    public GameLoop(GameData gameData, World world, Canvas canvas) {
        this.gameData = gameData;
        this.world = world;
        this.canvas = canvas;
        // Load once, reuse every frame
        this.entityProcessors = ServiceLoader.load(IEntityProcessingService.class)
                .stream().map(ServiceLoader.Provider::get).collect(toList());
        this.postProcessors = ServiceLoader.load(IPostEntityProcessingService.class)
                .stream().map(ServiceLoader.Provider::get).collect(toList());
    }

    @Override
    public void handle(long now) {
        gameData.getKeys().update();

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, gameData.getDisplayWidth(), gameData.getDisplayHeight());

        for (IEntityProcessingService processor : entityProcessors) {
            processor.process(gameData, world);
        }

        for (IPostEntityProcessingService postProcessor : postProcessors) {
            postProcessor.process(gameData, world);
        }

        for (Entity entity : world.getEntities()) {
            drawEntity(gc, entity);
        }

        for (Entity entity : world.getEntities(Player.class)) {
            Player player = (Player) entity;
            gc.setFill(Color.WHITE);
            gc.setFont(javafx.scene.text.Font.font(16));
            gc.fillText("Lives: " + player.getLives(), 10, 20);
        }
    }

    private void drawEntity(GraphicsContext gc, Entity entity) {
        double[] coords = entity.getPolygonCoordinates();
        if (coords == null) return;

        double cos = Math.cos(Math.toRadians(entity.getRotation()));
        double sin = Math.sin(Math.toRadians(entity.getRotation()));

        if (entity instanceof Bullet) {
            // Draw as a laser line
            Bullet bullet = (Bullet) entity;
            gc.setStroke(bullet.getOwner() == Bullet.Owner.PLAYER ? Color.GREEN : Color.RED);
            gc.setLineWidth(2);

            double x1 = entity.getX();
            double y1 = entity.getY();
            double x2 = entity.getX() + (coords[2] * cos - coords[3] * sin);
            double y2 = entity.getY() + (coords[2] * sin + coords[3] * cos);
            gc.strokeLine(x1, y1, x2, y2);

        } else {
            // Draw as a polygon
            gc.setStroke(Color.WHITE);
            gc.setLineWidth(1);

            int numPoints = coords.length / 2;
            double[] xPoints = new double[numPoints];
            double[] yPoints = new double[numPoints];

            for (int i = 0; i < numPoints; i++) {
                double localX = coords[i * 2];
                double localY = coords[i * 2 + 1];
                xPoints[i] = entity.getX() + (localX * cos - localY * sin);
                yPoints[i] = entity.getY() + (localX * sin + localY * cos);
            }

            gc.strokePolygon(xPoints, yPoints, numPoints);
        }
    }
}