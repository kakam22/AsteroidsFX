package dk.sdu.cbse.data;

public class GameKeys {
    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    public static final int SPACE = 4;

    private static final int NUM_KEYS = 5;
    private final boolean[] keys = new boolean[NUM_KEYS];
    private final boolean[] prevKeys = new boolean[NUM_KEYS];

    public void update() {
        System.arraycopy(keys, 0, prevKeys, 0, NUM_KEYS);
    }

    public void setKey(int key, boolean pressed) {
        keys[key] = pressed;
    }

    public boolean isDown(int key) {
        return keys[key];
    }

    public boolean isPressed(int key) {
        return keys[key] && !prevKeys[key];
    }
}