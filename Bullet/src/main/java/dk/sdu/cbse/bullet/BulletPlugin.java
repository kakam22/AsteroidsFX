package dk.sdu.cbse.bullet;

import dk.sdu.cbse.common.bullet.Bullet;
import dk.sdu.cbse.common.bullet.BulletSPI;
import dk.sdu.cbse.data.Entity;
import dk.sdu.cbse.data.GameData;
import dk.sdu.cbse.data.World;
import dk.sdu.cbse.services.IGamePluginService;

public class BulletPlugin implements IGamePluginService, BulletSPI {

    @Override
    public void start(GameData gameData, World world) {}

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity e : world.getEntities(Bullet.class)) {
            world.removeEntity(e);
        }
    }

    @Override
    public Bullet createBullet(Entity shooter, GameData gameData, Bullet.Owner owner) {
        Bullet bullet = new Bullet();
        bullet.setOwner(owner);
        bullet.setRotation(shooter.getRotation());
        bullet.setX(shooter.getX());
        bullet.setY(shooter.getY());
        bullet.setRadius(3);
        bullet.setSpawnTime(System.nanoTime());

        bullet.setPolygonCoordinates(0, 0, 8, 0);
        return bullet;
    }
}