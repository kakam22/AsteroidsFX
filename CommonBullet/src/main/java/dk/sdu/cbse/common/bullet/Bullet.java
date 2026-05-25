package dk.sdu.cbse.common.bullet;

import dk.sdu.cbse.data.Entity;


/*
    provided interface: Bullet extends Entity. Bullet.Owner {PLAYER, ENEMY}
    required interface: from Common module, Entity.

    pre: Common module is available.
    post: a new bullet entity can be created with inherited entity state.
 */
public class Bullet extends Entity {
    public enum Owner { PLAYER, ENEMY }
    private Owner owner;
    private long spawnTime = System.nanoTime();


    /*
    pre: bullet exists.
    post: returns bullet owner without changing state.
     */
    public Owner getOwner() { return owner; }

    /*
    pre: bullet exists and owner is PLAYER or ENEMY.
    post: bullet owner is updated.
     */
    public void setOwner(Owner owner) { this.owner = owner; }

    /*
    pre: bullet exists.
    post: returns bullet spawn time without changing state.
     */
    public long getSpawnTime() {return spawnTime; }

    /*
    pre: bullet exists and spawnTime uses System.nanoTime().
    post: bullet spawn time is updated.
     */
    public void setSpawnTime(long spawnTime){this.spawnTime = spawnTime;}
}
