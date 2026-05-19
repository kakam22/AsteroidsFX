package dk.sdu.cbse.common.asteroid;

import dk.sdu.cbse.data.Entity;

public class Asteroid extends Entity {

    public enum Size {
        LARGE, MEDIUM, SMALL
    }

    private Size size;

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }
}