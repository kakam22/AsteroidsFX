package dk.sdu.cbse.player;

import dk.sdu.cbse.common.player.Player;
import dk.sdu.cbse.data.Entity;
import dk.sdu.cbse.data.GameData;
import dk.sdu.cbse.data.GameKeys;
import dk.sdu.cbse.data.World;
import dk.sdu.cbse.services.IEntityProcessingService;

public class PlayerControlSystem implements IEntityProcessingService {

    private static final double SPEED = 3;

    @Override
    public void process(GameData gameData, World world) {
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

            handleBoundaries(player, gameData);
        }
    }

    private void handleBoundaries(Entity player, GameData gameData) {
        // --- OPTION 1: Stop at edges (comment out to use wrapping instead) ---
        if (player.getX() < 0) player.setX(1);
        if (player.getX() > gameData.getDisplayWidth()) player.setX(gameData.getDisplayWidth() - 1);
        if (player.getY() < 0) player.setY(1);
        if (player.getY() > gameData.getDisplayHeight()) player.setY(gameData.getDisplayHeight() - 1);

        // --- OPTION 2: Wrap around edges (comment out option 1 and uncomment this) ---
        // if (player.getX() < 0) player.setX(gameData.getDisplayWidth());
        // if (player.getX() > gameData.getDisplayWidth()) player.setX(0);
        // if (player.getY() < 0) player.setY(gameData.getDisplayHeight());
        // if (player.getY() > gameData.getDisplayHeight()) player.setY(0);
    }
}