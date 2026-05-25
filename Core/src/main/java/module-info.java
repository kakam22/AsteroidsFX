module Core {
    requires Common;
    requires CommonBullet;
    requires CommonPlayer;
    requires CommonAsteroid;
    requires CommonEnemy;

    requires javafx.graphics;
    requires javafx.controls;
    requires spring.context;
    requires spring.web;
    requires micrometer.observation;
    requires micrometer.commons;

    exports dk.sdu.cbse.core;
    opens dk.sdu.cbse.core to spring.core, spring.beans, spring.context;

    uses dk.sdu.cbse.common.bullet.BulletSPI;
    uses dk.sdu.cbse.services.IGamePluginService;
    uses dk.sdu.cbse.services.IEntityProcessingService;
    uses dk.sdu.cbse.services.IPostEntityProcessingService;
}
