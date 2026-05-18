package dk.sdu.cbse.enemy;

import dk.sdu.cbse.data.Entity;
import dk.sdu.cbse.data.GameData;
import dk.sdu.cbse.data.World;
import dk.sdu.cbse.services.IEntityProcessingService;

public class EnemyControlSystem implements IEntityProcessingService {

    private static final double SPEED = 1;
    private static final double TURN_CHANCE = 0.01; // 2% chance to change direction each frame

    @Override
    public void process(GameData gameData, World world) {
        for (Entity enemy : world.getEntities(Enemy.class)) {

            // Randomly change direction occasionally
            if (Math.random() < TURN_CHANCE) {
                enemy.setRotation(Math.random() * 360);
            }

            // Move forward in current direction
            double changeX = Math.cos(Math.toRadians(enemy.getRotation())) * SPEED;
            double changeY = Math.sin(Math.toRadians(enemy.getRotation())) * SPEED;
            enemy.setX(enemy.getX() + changeX);
            enemy.setY(enemy.getY() + changeY);

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
}