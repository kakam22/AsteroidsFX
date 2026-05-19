module Player {
    requires Common;
    requires CommonPlayer;
    requires CommonBullet;
    uses dk.sdu.cbse.common.bullet.BulletSPI;
    provides dk.sdu.cbse.services.IGamePluginService
            with dk.sdu.cbse.player.PlayerPlugin;
    provides dk.sdu.cbse.services.IEntityProcessingService
            with dk.sdu.cbse.player.PlayerControlSystem;
}