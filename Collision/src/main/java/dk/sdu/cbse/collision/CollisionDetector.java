package dk.sdu.cbse.collision;

import dk.sdu.cbse.common.asteroid.Asteroid;
import dk.sdu.cbse.common.bullet.Bullet;
import dk.sdu.cbse.data.Entity;
import dk.sdu.cbse.data.GameData;
import dk.sdu.cbse.data.World;
import dk.sdu.cbse.common.enemy.Enemy;
import dk.sdu.cbse.common.player.Player;
import dk.sdu.cbse.services.IPostEntityProcessingService;


import java.util.ArrayList;
import java.util.List;

public class CollisionDetector implements IPostEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {

        List<Entity> toRemove = new ArrayList<>();
        List<Entity> toAdd = new ArrayList<>();

        for (Entity bullet : world.getEntities(Bullet.class)) {
            Bullet b = (Bullet) bullet;

            // Bullet vs Asteroid
            for (Entity entity : world.getEntities(Asteroid.class)) {
                Asteroid asteroid = (Asteroid) entity;
                if (collides(bullet, asteroid)) {
                    toRemove.add(bullet);
                    toRemove.add(asteroid);
                    // Split asteroid
                    splitAsteroid(asteroid, gameData, toAdd);
                }
            }

            // Player bullet vs Enemy
            if (b.getOwner() == Bullet.Owner.PLAYER) {
                for (Entity entity : world.getEntities(Enemy.class)) {
                    if (collides(bullet, entity)) {
                        toRemove.add(bullet);
                        toRemove.add(entity);
                    }
                }
            }

            // Enemy bullet vs Player
            if (b.getOwner() == Bullet.Owner.ENEMY) {
                for (Entity entity : world.getEntities(Player.class)) {
                    if (collides(bullet, entity)) {
                        toRemove.add(bullet);
                        handlePlayerHit((Player) entity, gameData, world);
                    }
                }
            }
        }

        // Player vs Asteroid
        for (Entity player : world.getEntities(Player.class)) {
            for (Entity asteroid : world.getEntities(Asteroid.class)) {
                if (collides(player, asteroid)) {
                    handlePlayerHit((Player) player, gameData, world);
                    toRemove.add(asteroid);
                    splitAsteroid((Asteroid) asteroid, gameData, toAdd);
                }
            }
        }

        // Enemy vs Asteroid
        for (Entity enemy : world.getEntities(Enemy.class)) {
            for (Entity asteroid : world.getEntities(Asteroid.class)) {
                if (collides(enemy, asteroid)) {
                    toRemove.add(enemy);
                    toRemove.add(asteroid);
                    splitAsteroid((Asteroid) asteroid, gameData, toAdd);
                }
            }
        }

        toRemove.forEach(world::removeEntity);
        toAdd.forEach(world::addEntity);
    }

    private void splitAsteroid(Asteroid asteroid, GameData gameData, List<Entity> toAdd) {
        if (asteroid.getSize() == Asteroid.Size.LARGE) {
            toAdd.add(spawnSplit(asteroid, gameData, Asteroid.Size.MEDIUM, 20));
            toAdd.add(spawnSplit(asteroid, gameData, Asteroid.Size.MEDIUM, -20));
        } else if (asteroid.getSize() == Asteroid.Size.MEDIUM) {
            toAdd.add(spawnSplit(asteroid, gameData, Asteroid.Size.SMALL, 20));
            toAdd.add(spawnSplit(asteroid, gameData, Asteroid.Size.SMALL, -20));
        }
        // SMALL asteroids just disappear
    }

    private Asteroid spawnSplit(Asteroid parent, GameData gameData, Asteroid.Size size, double rotationOffset) {
        Asteroid child = new Asteroid();
        child.setSize(size);
        child.setX(parent.getX());
        child.setY(parent.getY());
        child.setRotation(parent.getRotation() + rotationOffset);

        switch (size) {
            case MEDIUM -> {
                child.setPolygonCoordinates(-12, -8, 0, -15, 12, -8, 15, 0, 12, 8, 0, 15, -12, 8, -15, 0);
                child.setRadius(15);
            }
            case SMALL -> {
                child.setPolygonCoordinates(-6, -4, 0, -8, 6, -4, 8, 0, 6, 4, 0, 8, -6, 4, -8, 0);
                child.setRadius(8);
            }
            default -> {}
        }
        return child;
    }

    private void handlePlayerHit(Player player, GameData gameData, World world) {
        int lives = player.getLives() - 1;
        player.setLives(lives);
        if (lives <= 0) {
            world.removeEntity(player);
        } else {
            // Respawn at center
            player.setX(gameData.getDisplayWidth() / 2);
            player.setY(gameData.getDisplayHeight() / 2);
            player.setRotation(0);
        }
    }

    private boolean collides(Entity a, Entity b) {
        double dx = a.getX() - b.getX();
        double dy = a.getY() - b.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance < (a.getRadius() + b.getRadius());
    }
}