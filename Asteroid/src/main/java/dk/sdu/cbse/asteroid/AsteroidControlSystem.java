package dk.sdu.cbse.asteroid;

import dk.sdu.cbse.common.asteroid.Asteroid;
import dk.sdu.cbse.data.Entity;
import dk.sdu.cbse.data.GameData;
import dk.sdu.cbse.data.World;
import dk.sdu.cbse.services.IEntityProcessingService;

public class AsteroidControlSystem implements IEntityProcessingService {

    // Speed per size
    private static final double LARGE_SPEED  = 0.8;
    private static final double MEDIUM_SPEED = 1.4;
    private static final double SMALL_SPEED  = 2.0;

    // How gradually the asteroid curves (lower = smoother curve)
    private static final double TURN_AMOUNT = 0.4;

    @Override
    public void process(GameData gameData, World world) {
        for (Entity entity : world.getEntities(Asteroid.class)) {
            Asteroid asteroid = (Asteroid) entity;

            // Gently drift rotation for smooth curved movement
            asteroid.setRotation(asteroid.getRotation() + (Math.random() * TURN_AMOUNT * 2 - TURN_AMOUNT));

            double speed = switch (asteroid.getSize()) {
                case LARGE  -> LARGE_SPEED;
                case MEDIUM -> MEDIUM_SPEED;
                case SMALL  -> SMALL_SPEED;
            };

            double changeX = Math.cos(Math.toRadians(asteroid.getRotation())) * speed;
            double changeY = Math.sin(Math.toRadians(asteroid.getRotation())) * speed;
            asteroid.setX(asteroid.getX() + changeX);
            asteroid.setY(asteroid.getY() + changeY);

            // Wrap around screen edges
            if (asteroid.getX() < 0) asteroid.setX(gameData.getDisplayWidth());
            if (asteroid.getX() > gameData.getDisplayWidth()) asteroid.setX(0);
            if (asteroid.getY() < 0) asteroid.setY(gameData.getDisplayHeight());
            if (asteroid.getY() > gameData.getDisplayHeight()) asteroid.setY(0);
        }
    }
}