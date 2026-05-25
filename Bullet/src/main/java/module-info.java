module Bullet {
    requires Common;
    requires CommonBullet;
    provides dk.sdu.cbse.services.IGamePluginService
            with dk.sdu.cbse.bullet.BulletPlugin;
    provides dk.sdu.cbse.services.IEntityProcessingService
            with dk.sdu.cbse.bullet.BulletControlSystem;
    provides dk.sdu.cbse.common.bullet.BulletSPI
            with dk.sdu.cbse.bullet.BulletPlugin;
    exports dk.sdu.cbse.bullet;
}