package ca.cmpt213.as3.logic;

/**
 * This class is responsible for keeping track of it's own Location and life
 * It tells the board how much damage it can deal to the player
 * and always has a size of 4.
 */


public class Tank {
    private static final int SIZE = 4;
    private static final int[] DAMAGE = {0,1,2,5,20};
    private int health;
    private Location[] tankLocations;

    
    //Default constructor
    Tank(Location[] tankLocations) {
        this.health = SIZE;
        this.tankLocations = tankLocations;
    }


    //Returns the size of the tank
    public static int getSize(){
        return SIZE;
    }


    //If it's health is less than it's size then it is damaged
    public boolean isDamaged() {
        return health < SIZE;
    }


    //Return the attack damage of the tank based off it's health
    public int getAttack() {
        return DAMAGE[health];
    }


    //Decreases the health counter by on if it is shot
    public void decreaseHealth() {
        health--;
    }

    //It checks if a location is contained in tank
    public boolean isInBounds(Location location) {
        for (Location tank : tankLocations) {
            if (tank.isEquals(location)) {
                return true;
            }
        }
        return false;
    }
}
