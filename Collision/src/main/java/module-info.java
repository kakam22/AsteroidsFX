module Collision {
    requires Common;
    requires CommonBullet;
    requires CommonAsteroid;
    requires CommonPlayer;
    requires CommonEnemy;
    requires spring.web;
    requires micrometer.observation;
    requires micrometer.commons;

    provides dk.sdu.cbse.services.IPostEntityProcessingService
            with dk.sdu.cbse.collision.CollisionDetector;
}
