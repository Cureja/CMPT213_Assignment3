package ca.cmpt213.as3.logic;



public class Location {
    public int row;
    public int col;

    Location(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public boolean isEquals(Location other) {
        return (row == other.row && col == other.col);
    }
}
