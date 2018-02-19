package ca.cmpt213.as3.logic;

public enum Tile {
    HIDDEN_TANK (0), HIDDEN_MISS(1), TANK(2), MISS(3);

    private final int NUMBER_OF_STATES = 2;
    private int state;

    Tile(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public boolean isHidden() {
        return state <= NUMBER_OF_STATES;
    }

    public boolean isTank() {
        return (state % 2  == 0);
    }

    public void reveal() {
        if (isHidden()) {
            state += NUMBER_OF_STATES;
        }
    }
}
