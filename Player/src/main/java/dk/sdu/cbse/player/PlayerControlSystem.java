package dk.sdu.cbse.player;

import dk.sdu.cbse.common.player.Player;
import dk.sdu.cbse.data.Entity;
import dk.sdu.cbse.data.GameData;
import dk.sdu.cbse.data.GameKeys;
import dk.sdu.cbse.data.World;
import dk.sdu.cbse.services.IEntityProcessingService;

import dk.sdu.cbse.common.bullet.Bullet;
import dk.sdu.cbse.common.bullet.BulletSPI;

import java.util.Collection;
import java.util.ServiceLoader;
import static java.util.stream.Collectors.toList;

public class PlayerControlSystem implements IEntityProcessingService {

    private static final double SPEED = 3;
    private static final long SHOOT_COOLDOWN_NS = 300_000_000L; // 0.3 seconds between shots
    private long lastShotTime = 0;
    @Override
    public void process(GameData gameData, World world) {

        long now = System.nanoTime();

        for (Entity player : world.getEntities(Player.class)) {

            if (gameData.getKeys().isDown(GameKeys.LEFT)) {
                player.setRotation(player.getRotation() - 5);
            }
            if (gameData.getKeys().isDown(GameKeys.RIGHT)) {
                player.setRotation(player.getRotation() + 5);
            }
            if (gameData.getKeys().isDown(GameKeys.UP)) {
                double changeX = Math.cos(Math.toRadians(player.getRotation())) * SPEED;
                double changeY = Math.sin(Math.toRadians(player.getRotation())) * SPEED;
                player.setX(player.getX() + changeX);
                player.setY(player.getY() + changeY);
            }
            if (gameData.getKeys().isDown(GameKeys.SPACE) && now - lastShotTime >= SHOOT_COOLDOWN_NS) {
                getBulletSPIs().stream().findFirst().ifPresent(
                        spi -> world.addEntity(spi.createBullet(player, gameData, Bullet.Owner.PLAYER))
                );
                lastShotTime = now;
            }


            handleBoundaries(player, gameData);
        }


    }

    private void handleBoundaries(Entity player, GameData gameData) {


        if (player.getX() < 0) player.setX(gameData.getDisplayWidth());
        if (player.getX() > gameData.getDisplayWidth()) player.setX(0);
        if (player.getY() < 0) player.setY(gameData.getDisplayHeight());
        if (player.getY() > gameData.getDisplayHeight()) player.setY(0);
    }

    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(PlayerControlSystem.class.getModule().getLayer(), BulletSPI.class).stream()
                .map(ServiceLoader.Provider::get)
                .collect(toList());
    }
}
