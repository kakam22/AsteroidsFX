package dk.sdu.cbse.bullet;

import dk.sdu.cbse.common.bullet.Bullet;
import dk.sdu.cbse.data.Entity;
import dk.sdu.cbse.data.GameData;
import dk.sdu.cbse.data.World;
import dk.sdu.cbse.services.IEntityProcessingService;

import java.util.ArrayList;
import java.util.List;

public class BulletControlSystem implements IEntityProcessingService {

    private static final double SPEED = 5;
    private static final long LIFETIME_NS = 2_000_000_000L; // 2 seconds in nanoseconds, ændre med /3 for flere skud. måske til demo
    private final java.util.Map<String, Long> spawnTimes = new java.util.concurrent.ConcurrentHashMap<>();

    @Override
    public void process(GameData gameData, World world) {
        long now = System.nanoTime();
        List<Entity> toRemove = new ArrayList<>();

        for (Entity entity : world.getEntities(Bullet.class)) {
            Bullet bullet = (Bullet) entity;

            if (now - bullet.getSpawnTime() > LIFETIME_NS) {
                toRemove.add(bullet);
                continue;
            }

            double changeX = Math.cos(Math.toRadians(bullet.getRotation())) * SPEED;
            double changeY = Math.sin(Math.toRadians(bullet.getRotation())) * SPEED;
            bullet.setX(bullet.getX() + changeX);
            bullet.setY(bullet.getY() + changeY);

            if (bullet.getX() < 0) bullet.setX(gameData.getDisplayWidth());
            if (bullet.getX() > gameData.getDisplayWidth()) bullet.setX(0);
            if (bullet.getY() < 0) bullet.setY(gameData.getDisplayHeight());
            if (bullet.getY() > gameData.getDisplayHeight()) bullet.setY(0);
        }

        toRemove.forEach(world::removeEntity);
    }
}