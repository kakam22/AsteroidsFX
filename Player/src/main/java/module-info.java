module Player {
    requires Common;
    uses dk.sdu.cbse.services.IGamePluginService;
    uses dk.sdu.cbse.services.IEntityProcessingService;
    provides dk.sdu.cbse.services.IGamePluginService with dk.sdu.cbse.player.PlayerPlugin;
    provides dk.sdu.cbse.services.IEntityProcessingService with dk.sdu.cbse.player.PlayerControlSystem;
}