package ca.cmpt213.as3.logic;

public enum Tile {
    HIDDEN_MISS, HIDDEN_TANK, MISS, TANK;
    int tileState;

    @Override
    String toString(){

    }

    void reveal() {
        if(tileState < 2) {
            tileState += 2;
        }
    }


}
