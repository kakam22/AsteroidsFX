package dk.sdu.cbse.common.player;

import dk.sdu.cbse.data.Entity;

public class Player extends Entity {
    private int lives = 3;

    public int getLives() { return lives; }
    public void setLives(int lives) { this.lives = lives; }
}