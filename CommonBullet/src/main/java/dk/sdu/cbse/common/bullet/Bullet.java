package dk.sdu.cbse.common.bullet;

import dk.sdu.cbse.data.Entity;

public class Bullet extends Entity {
    public enum Owner { PLAYER, ENEMY }
    private Owner owner;
    private long spawnTime = System.nanoTime();


    public Owner getOwner() { return owner; }
    public void setOwner(Owner owner) { this.owner = owner; }
    public long getSpawnTime() {return spawnTime; }
    public void setSpawnTime(long spawnTime){this.spawnTime = spawnTime;}
}