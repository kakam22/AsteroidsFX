module Asteroid {
    requires Common;
    provides dk.sdu.cbse.services.IGamePluginService
            with dk.sdu.cbse.asteroid.AsteroidPlugin;
    provides dk.sdu.cbse.services.IEntityProcessingService
            with dk.sdu.cbse.asteroid.AsteroidControlSystem;
}