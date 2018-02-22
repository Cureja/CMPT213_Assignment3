package ca.cmpt213.as3.logic;

public class Tile {
    private TileState state;
    private boolean isHidden;

    Tile() {
        this.state = TileState.MISS;
        isHidden = true;
    }

    public TileState getState() {
        return state;
    }

    public void setState(TileState state) {
        this.state = state;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setIsHidden(boolean set) {
        isHidden = set;
    }

    public boolean isTank() {
        return state == TileState.TANK;
    }

    public void reveal() {
        isHidden = false;
    }
}
