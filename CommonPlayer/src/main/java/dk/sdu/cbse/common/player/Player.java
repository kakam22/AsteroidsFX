package dk.sdu.cbse.common.player;

import dk.sdu.cbse.data.Entity;

/*
    provided interface: Player extends Entity.
    required interface: from Common module, Entity.

    pre: Common module is available.
    post: a new player entity can be created with inherited entity state.
 */
public class Player extends Entity {
    private int lives = 10;

    /*
    pre: player exists.
    post: returns player lives without changing state.
     */
    public int getLives() { return lives; }

    /*
    pre: player exists and lives is 0 or higher.
    post: player lives is updated.
     */
    public void setLives(int lives) { this.lives = lives; }
}
