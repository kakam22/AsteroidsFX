package dk.sdu.cbse.common.bullet;

import dk.sdu.cbse.data.Entity;
import dk.sdu.cbse.data.GameData;

public interface BulletSPI {
    Bullet createBullet(Entity shooter, GameData gameData, Bullet.Owner owner);
}