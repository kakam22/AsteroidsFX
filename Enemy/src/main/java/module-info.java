module Enemy {
    requires Common;
    requires CommonEnemy;
    uses dk.sdu.cbse.services.IGamePluginService;
    uses dk.sdu.cbse.services.IEntityProcessingService;
    provides dk.sdu.cbse.services.IGamePluginService with dk.sdu.cbse.enemy.EnemyPlugin;
    provides dk.sdu.cbse.services.IEntityProcessingService with dk.sdu.cbse.enemy.EnemyControlSystem;
}