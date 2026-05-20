module Core {
    requires Common;
    requires CommonBullet;
    requires CommonPlayer;

    requires javafx.graphics;
    requires javafx.controls;
    requires spring.context;

    exports dk.sdu.cbse.core;
    opens dk.sdu.cbse.core to spring.core, spring.beans, spring.context;

    uses dk.sdu.cbse.common.bullet.BulletSPI;
    uses dk.sdu.cbse.services.IGamePluginService;
    uses dk.sdu.cbse.services.IEntityProcessingService;
    uses dk.sdu.cbse.services.IPostEntityProcessingService;
}
