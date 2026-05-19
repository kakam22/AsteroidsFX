package dk.sdu.cbse.asteroid;

import dk.sdu.cbse.common.asteroid.Asteroid;
import dk.sdu.cbse.data.Entity;
import dk.sdu.cbse.data.GameData;
import dk.sdu.cbse.data.World;
import dk.sdu.cbse.services.IEntityProcessingService;
import dk.sdu.cbse.services.IGamePluginService;

import java.util.ArrayList;
import java.util.List;

public class AsteroidPlugin implements IGamePluginService, IEntityProcessingService {

    private final List<String> asteroidIDs = new ArrayList<>();
    private static final int INITIAL_COUNT = 5;

    @Override
    public void start(GameData gameData, World world) {
        for (int i = 0; i < INITIAL_COUNT; i++) {
            Asteroid asteroid = createAsteroid(gameData, Asteroid.Size.LARGE);
            asteroidIDs.add(world.addEntity(asteroid));
        }
    }

    public static Asteroid createAsteroid(GameData gameData, Asteroid.Size size) {
        Asteroid asteroid = new Asteroid();
        asteroid.setSize(size);

        // Size determines shape and radius
        switch (size) {
            case LARGE -> {
                asteroid.setPolygonCoordinates(
                        -20, -15, 0, -25, 20, -15, 25, 0, 20, 15, 0, 25, -20, 15, -25, 0
                );
                asteroid.setRadius(25);
            }
            case MEDIUM -> {
                asteroid.setPolygonCoordinates(
                        -12, -8, 0, -15, 12, -8, 15, 0, 12, 8, 0, 15, -12, 8, -15, 0
                );
                asteroid.setRadius(15);
            }
            case SMALL -> {
                asteroid.setPolygonCoordinates(
                        -6, -4, 0, -8, 6, -4, 8, 0, 6, 4, 0, 8, -6, 4, -8, 0
                );
                asteroid.setRadius(8);
            }
        }

        // Spawn at a random edge of the screen
        spawnAtEdge(asteroid, gameData);

        // Random initial rotation direction
        asteroid.setRotation(Math.random() * 360);

        return asteroid;
    }

    private static void spawnAtEdge(Asteroid asteroid, GameData gameData) {
        int edge = (int) (Math.random() * 4);
        switch (edge) {
            case 0 -> { // top
                asteroid.setX(Math.random() * gameData.getDisplayWidth());
                asteroid.setY(0);
            }
            case 1 -> { // bottom
                asteroid.setX(Math.random() * gameData.getDisplayWidth());
                asteroid.setY(gameData.getDisplayHeight());
            }
            case 2 -> { // left
                asteroid.setX(0);
                asteroid.setY(Math.random() * gameData.getDisplayHeight());
            }
            case 3 -> { // right
                asteroid.setX(gameData.getDisplayWidth());
                asteroid.setY(Math.random() * gameData.getDisplayHeight());
            }
        }
    }


    //asteroid respawner
    //TODO: this is still not working, need to checkout why.
    @Override
    public void process(GameData gameData, World world) {

        boolean hasAsteroids = false;

        for (Entity entity : world.getEntities()) {

            if (entity instanceof Asteroid) {
                hasAsteroids = true;
                break;
            }
        }

        // Spawn new wave if all asteroids are gone
        if (!hasAsteroids) {

            for (int i = 0; i < INITIAL_COUNT; i++) {

                Asteroid asteroid =
                        createAsteroid(gameData, Asteroid.Size.LARGE);

                asteroidIDs.add(world.addEntity(asteroid));
            }
        }
    }

    @Override
    public void stop(GameData gameData, World world) {
        for (String id : asteroidIDs) {
            world.removeEntity(id);
        }
        asteroidIDs.clear();
    }
}