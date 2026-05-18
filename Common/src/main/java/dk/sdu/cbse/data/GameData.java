package dk.sdu.cbse.data;

public class GameData {
    private int displayWidth = 800;
    private int displayHeight = 800;
    private final GameKeys keys = new GameKeys();

    public GameKeys getKeys() {
        return keys;
    }

    public int getDisplayWidth() {
        return displayWidth;
    }

    public void setDisplayWidth(int displayWidth) {
        this.displayWidth = displayWidth;
    }

    public int getDisplayHeight() {
        return displayHeight;
    }

    public void setDisplayHeight(int displayHeight) {
        this.displayHeight = displayHeight;
    }
}