package dk.sdu.cbse.services;

import dk.sdu.cbse.data.GameData;
import dk.sdu.cbse.data.World;

/**
 * Service interface for post-frame entity processing.
 * this for logic that must run after all individual
 * entity processors are done
 */
public interface IPostEntityProcessingService {

    /**
     * Called once per game tick after all entity
     * processing is done to perform any necessary
     * post-processing logic
     *
     * Pre: All IEntityProcessingService processors have already run this tick.
     * Post: Cross-entity interactions (e.g. collisions) have been resolved.
     */
    void process(GameData gameData, World world);
}