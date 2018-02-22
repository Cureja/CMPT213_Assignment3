package ca.cmpt213.as3.logic;

public class Tank {
    private static final int SIZE = 4;
    private static final int[] DAMAGE = {0,1,2,5,20};
    private int health;
    private Location[] tankLocations;

    Tank(Location[] tankLocations) {
        this.health = SIZE;
        this.tankLocations = tankLocations;
    }

    public static int getSize(){
        return SIZE;
    }

    public boolean isDamaged() {
        return health < SIZE;
    }

    public int getAttack() {
        return DAMAGE[health];
    }

    public void decreaseHealth() {
        health--;
    }

    public boolean isInBounds(Location location) {
        for (Location tank : tankLocations) {
            if(tank.isEquals(location)) {
                return true;
            }
        }
        return false;
    }
}
