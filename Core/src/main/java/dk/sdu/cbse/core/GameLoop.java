package dk.sdu.cbse.core;

import dk.sdu.cbse.common.bullet.Bullet;
import dk.sdu.cbse.common.player.Player;
import dk.sdu.cbse.common.asteroid.Asteroid;
import dk.sdu.cbse.common.enemy.Enemy;
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

public class GameLoop extends AnimationTimer {

    private final GameData gameData;
    private final World world;
    private final Canvas canvas;
    private final ScoreClient scoreClient;
    private final List<IEntityProcessingService> entityProcessors;
    private final List<IPostEntityProcessingService> postProcessors;
    private int currentScore;
    private long lastScoreFetch;
    private boolean scoreServiceOnline;

    public GameLoop(
            GameData gameData,
            World world,
            Canvas canvas,
            ScoreClient scoreClient,
            List<IEntityProcessingService> entityProcessors,
            List<IPostEntityProcessingService> postProcessors
    ) {
        this.gameData = gameData;
        this.world = world;
        this.canvas = canvas;
        this.scoreClient = scoreClient;
        this.entityProcessors = entityProcessors;
        this.postProcessors = postProcessors;
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

        updateScore(now);
        gc.setFill(Color.WHITE);
        gc.setFont(javafx.scene.text.Font.font(16));
        gc.fillText(scoreServiceOnline ? "Score: " + currentScore : "Score: offline", 10, 40);
    }

    private void updateScore(long now) {
        if (now - lastScoreFetch < 250_000_000L) {
            return;
        }
        Integer score = scoreClient.fetchScore();
        scoreServiceOnline = score != null;
        if (score != null) {
            currentScore = score;
        }
        lastScoreFetch = now;
    }

    private void drawEntity(GraphicsContext gc, Entity entity) {
        double[] coords = entity.getPolygonCoordinates();
        if (coords == null) return;

        double cos = Math.cos(Math.toRadians(entity.getRotation()));
        double sin = Math.sin(Math.toRadians(entity.getRotation()));

        if (entity instanceof Bullet) {
            Bullet bullet = (Bullet) entity;
            gc.setStroke(bullet.getOwner() == Bullet.Owner.PLAYER ? Color.GREEN : Color.RED);
            gc.setLineWidth(2);

            double x1 = entity.getX();
            double y1 = entity.getY();
            double x2 = entity.getX() + (coords[2] * cos - coords[3] * sin);
            double y2 = entity.getY() + (coords[2] * sin + coords[3] * cos);
            gc.strokeLine(x1, y1, x2, y2);

        } else {

            int numPoints = coords.length / 2;
            double[] xPoints = new double[numPoints];
            double[] yPoints = new double[numPoints];

            for (int i = 0; i < numPoints; i++) {
                double localX = coords[i * 2];
                double localY = coords[i * 2 + 1];
                xPoints[i] = entity.getX() + (localX * cos - localY * sin);
                yPoints[i] = entity.getY() + (localX * sin + localY * cos);
            }
            if (entity instanceof Asteroid) {
                gc.setFill(Color.WHITE);
                gc.fillPolygon(xPoints, yPoints, numPoints);
                gc.setStroke(Color.WHITE);
            } else if (entity instanceof Enemy) {
                gc.setFill(Color.PURPLE);
                gc.fillPolygon(xPoints, yPoints, numPoints);
                gc.setStroke(Color.PURPLE.darker());
            } else {
                gc.setStroke(Color.WHITE);
            }
            gc.setLineWidth(1);

            gc.strokePolygon(xPoints, yPoints, numPoints);
        }
    }
}
