package ca.cmpt213.as3.logic;

/**
 * This class has the responsibility of storing an x and y coordinate for the board
 * It is essentially a struct.
 */

public class Location {
    public int row;
    public int col;

    //A parameterized constructor to set the location.
    public Location(int row, int col) {
        this.row = row;
        this.col = col;
    }

    //If a location is passed it will then assign it's location to the new one passsed
    Location(Location other) {
        this.row = other.row;
        this.col = other.col;
    }

    //Checks if the two locations are equal to each other
    public boolean isEquals(Location other) {
        return (row == other.row && col == other.col);
    }
}
