package dk.sdu.cbse.common.asteroid;

import dk.sdu.cbse.data.Entity;

/*
    provided interface: Asteroid extends Entity. Asteroid.Size {LARGE, MEDIUM, SMALL}
    required interface: from Common module, Entity.

    pre: Common module is available.
    post: a new asteroid entity can be created with inherited entity state.
 */

public class Asteroid extends Entity {

    public enum Size {
        LARGE, MEDIUM, SMALL
    }

    private Size size;

    /*
    pre: asteroid exists.
    post: returns current asteroid size without changing state.
     */
    public Size getSize() {
        return size;
    }

    /*
    pre: asteroid exists and size is LARGE, MEDIUM or SMALL.
    post: asteroid size is updated.
     */
    public void setSize(Size size) {
        this.size = size;
    }
}
