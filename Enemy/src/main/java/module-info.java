module Enemy {
    requires Common;
    requires CommonEnemy;
    requires CommonBullet;
    uses dk.sdu.cbse.common.bullet.BulletSPI;
    uses dk.sdu.cbse.services.IGamePluginService;
    uses dk.sdu.cbse.services.IEntityProcessingService;
    provides dk.sdu.cbse.services.IGamePluginService with dk.sdu.cbse.enemy.EnemyPlugin;
    provides dk.sdu.cbse.services.IEntityProcessingService with dk.sdu.cbse.enemy.EnemyControlSystem;
}