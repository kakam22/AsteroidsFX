package dk.sdu.cbse.player;

import dk.sdu.cbse.common.player.Player;
import dk.sdu.cbse.data.GameData;
import dk.sdu.cbse.data.GameKeys;
import dk.sdu.cbse.data.World;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerControlSystemTest {

    @Test
    void processMovesPlayerForwardWhenUpKeyIsDown() {
        GameData gameData = new GameData();
        World world = new World();
        Player player = new Player();
        player.setX(100);
        player.setY(100);
        player.setRotation(0);
        world.addEntity(player);

        gameData.getKeys().setKey(GameKeys.UP, true);

        new PlayerControlSystem().process(gameData, world);

        assertEquals(103, player.getX(), 0.001);
        assertEquals(100, player.getY(), 0.001);
    }
}
