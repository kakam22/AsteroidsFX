module Collision {
    requires Common;
    requires CommonBullet;
    requires CommonAsteroid;
    requires CommonPlayer;
    requires CommonEnemy;
    provides dk.sdu.cbse.services.IPostEntityProcessingService
            with dk.sdu.cbse.collision.CollisionDetector;
    exports dk.sdu.cbse.collision;
}