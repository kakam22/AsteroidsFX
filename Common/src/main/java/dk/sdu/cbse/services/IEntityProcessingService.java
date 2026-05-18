package dk.sdu.cbse.services;

import dk.sdu.cbse.data.GameData;
import dk.sdu.cbse.data.World;

/**
 * Service interface for entity processing in every frame.
 * This to updates entity state each game tick
 * (movement, input handling, animation).
 */
public interface IEntityProcessingService {

    /**
     * Called once per game tick to process and update relevant entities
     * Pre: gameData and world are not null.
     * Post: Relevant entities have been updated according to game logic.
     *  @param gameData shared game state
     * @param world    the game world containing all active entities
     */
    void process(GameData gameData, World world);
}