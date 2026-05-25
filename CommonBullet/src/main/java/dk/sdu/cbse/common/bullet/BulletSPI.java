package dk.sdu.cbse.common.bullet;

import dk.sdu.cbse.data.Entity;
import dk.sdu.cbse.data.GameData;

/*
    provided interface: BulletSPI.
    required interface: from Common module, Entity and GameData.

    pre: Bullet component is available.
    post: bullet service can create bullets for other components.
 */
public interface BulletSPI {

    /*
    pre: shooter and gameData are not null, and owner is PLAYER or ENEMY.
    post: returns a new bullet with position, rotation, owner and spawn time initialized.
     */
    Bullet createBullet(Entity shooter, GameData gameData, Bullet.Owner owner);
}
