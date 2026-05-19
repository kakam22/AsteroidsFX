package dk.sdu.cbse.enemy;

import dk.sdu.cbse.common.bullet.Bullet;
import dk.sdu.cbse.common.bullet.BulletSPI;
import dk.sdu.cbse.common.enemy.Enemy;
import dk.sdu.cbse.data.Entity;
import dk.sdu.cbse.data.GameData;
import dk.sdu.cbse.data.World;
import dk.sdu.cbse.services.IEntityProcessingService;

import java.util.Collection;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;
import static java.util.stream.Collectors.toList;

public class EnemyControlSystem implements IEntityProcessingService {

    private static final double SPEED = 1;
    private static final double TURN_CHANCE = 0.01; // 1% chance to change direction each frame
    private static final long SHOOT_INTERVAL_NS = 5_000_000_000L / 3 ; // 3 shots per 5 seconds, (change with divide for more sohts)

    private final Map<String, Long> lastShotTime = new ConcurrentHashMap<>();

    @Override
    public void process(GameData gameData, World world) {

        long now = System.nanoTime();

        for (Entity enemy : world.getEntities(Enemy.class)) {

            // Randomly change direction occasionally
            if (Math.random() < TURN_CHANCE) {
                enemy.setRotation(Math.random() * 180);
            }

            // Move forward in current direction
            double changeX = Math.cos(Math.toRadians(enemy.getRotation())) * SPEED;
            double changeY = Math.sin(Math.toRadians(enemy.getRotation())) * SPEED;
            enemy.setX(enemy.getX() + changeX);
            enemy.setY(enemy.getY() + changeY);

            // Shoot in movement direction every interval
            long lastShot = lastShotTime.getOrDefault(enemy.getID(), 0L);
            if (now - lastShot >= SHOOT_INTERVAL_NS) {
                getBulletSPIs().stream().findFirst().ifPresent(
                        spi -> world.addEntity(spi.createBullet(enemy, gameData, Bullet.Owner.ENEMY))
                );
                lastShotTime.put(enemy.getID(), now);
            }

            handleBoundaries(enemy, gameData);
        }
    }

    private void handleBoundaries(Entity enemy, GameData gameData) {
        // --- OPTION 1: Stop at edges (comment out to use wrapping instead) ---
        // if (enemy.getX() < 0) enemy.setX(1);//if (enemy.getX() > gameData.getDisplayWidth()) enemy.setX(gameData.getDisplayWidth() - 1);
        //if (enemy.getY() < 0) enemy.setY(1);
        //if (enemy.getY() > gameData.getDisplayHeight()) enemy.setY(gameData.getDisplayHeight() - 1);

        // --- OPTION 2: Wrap around edges (comment out option 1 and uncomment this) ---
        if (enemy.getX() < 0) enemy.setX(gameData.getDisplayWidth());
        if (enemy.getX() > gameData.getDisplayWidth()) enemy.setX(0);
        if (enemy.getY() < 0) enemy.setY(gameData.getDisplayHeight());
        if (enemy.getY() > gameData.getDisplayHeight()) enemy.setY(0);


    }

    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream()
                .map(ServiceLoader.Provider::get)
                .collect(toList());
    }
}