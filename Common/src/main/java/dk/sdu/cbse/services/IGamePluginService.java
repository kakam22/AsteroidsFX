package dk.sdu.cbse.services;

import dk.sdu.cbse.data.GameData;
import dk.sdu.cbse.data.World;

/**
 * Service interface for game plugins.
 * this is used to get onto the game's lifecycle and add/remove entities when the game starts/stops.
 */
public interface IGamePluginService {

    /**
     * Called once when the plugin is loaded and the game starts.
     *
     * pre: gameData and world are not null.
     * post: Plugin entities have been added to world.
     *
     * @param gameData shared game state
     * @param world    the game world to add entities to
     */
    void start(GameData gameData, World world);

    /**
     * Called once when the plugin is unloaded and the game stops.
     *
     * Pre: start() has been called. gameData and world are not null.
     * Post: All plugin entities have been removed from world.
     * @param gameData same as before
     * @param world    the same but to remove instead
     */
    void stop(GameData gameData, World world);
}