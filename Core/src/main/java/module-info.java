module Core {
    requires Common;
    requires CommonBullet;
    requires CommonPlayer;

    requires javafx.graphics;
    requires javafx.controls;

    exports dk.sdu.cbse.core;

    uses dk.sdu.cbse.common.bullet.BulletSPI;
    uses dk.sdu.cbse.services.IGamePluginService;
    uses dk.sdu.cbse.services.IEntityProcessingService;
    uses dk.sdu.cbse.services.IPostEntityProcessingService;
}