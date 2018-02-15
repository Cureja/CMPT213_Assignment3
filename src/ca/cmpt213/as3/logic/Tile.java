package ca.cmpt213.as3.logic;

public enum Tile {
    HIDDEN_TANK (0), HIDDEN_MISS(1), TANK(2), MISS(3);
    private final int HIDDEN_THRESHOLD = 2;
    private int tileState;


    Tile(int tileState) {
        this.tileState = tileState;
    }

    public int getTileState() {
        return tileState;
    }

    public void reveal() {
        if(isHidden()) {
            tileState += HIDDEN_THRESHOLD;
        }
    }

    public boolean isHidden() {
        return tileState <= HIDDEN_THRESHOLD;
    }
}
