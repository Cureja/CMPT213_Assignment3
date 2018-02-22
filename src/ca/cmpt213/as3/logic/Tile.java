package ca.cmpt213.as3.logic;

/**
 * This class is in the underlying structure of board and it's responsibility
 * is to store and keep track of tile states.
 */


public class Tile {
    private TileState state;
    private boolean isHidden;

    //Default constructor
    Tile() {
        this.state = TileState.MISS;
        isHidden = true;
    }

    //Get the state of the tile
    public TileState getState() {
        return state;
    }

    //Set the tile state
    public void setState(TileState state) {
        this.state = state;
    }

    //Checks if the tile is a hidden tile.
    public boolean isHidden() {
        return isHidden;
    }

    //Sets the tile to be hidden
    public void setIsHidden(boolean set) {
        isHidden = set;
    }

    //Check for if the tile is a tank
    public boolean isTank() {
        return state == TileState.TANK;
    }

    //Once the tank is revealed it is no longer hidden
    public void reveal() {
        isHidden = false;
    }
}
