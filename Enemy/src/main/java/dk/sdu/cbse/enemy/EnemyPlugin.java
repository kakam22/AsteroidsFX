package dk.sdu.cbse.enemy;

import dk.sdu.cbse.common.enemy.Enemy;
import dk.sdu.cbse.data.Entity;
import dk.sdu.cbse.data.GameData;
import dk.sdu.cbse.data.World;
import dk.sdu.cbse.services.IGamePluginService;

import java.util.ArrayList;
import java.util.List;

public class EnemyPlugin implements IGamePluginService {
    private final List<Entity> enemies = new ArrayList<>();

    @Override
    public void start(GameData gameData, World world) {
    // lav flere enemies, til demo
        for (int i = 0; i < 3; i++) {
            Entity enemy = createEnemyShip(gameData);
            enemies.add(enemy);
            world.addEntity(enemy);
        }
    }

    private Entity createEnemyShip(GameData gameData) {
        Entity enemyShip = new Enemy();
        enemyShip.setPolygonCoordinates(-5, -5, 10, 0, -5, 5);
        enemyShip.setX(Math.random() * gameData.getDisplayWidth());
        enemyShip.setY(Math.random() * gameData.getDisplayHeight());
        enemyShip.setRotation(Math.random() * 360);
        enemyShip.setRadius(8);
        return enemyShip;
    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity e : enemies) {
            world.removeEntity(e);
        }
        enemies.clear();
    }
}