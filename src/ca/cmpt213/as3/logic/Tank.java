package ca.cmpt213.as3.logic;

public class Tank {
    private static final int size = 4;
    private static final int[] damage = {0,1,2,5,20};
    private int health;
    private Location[] tankLocations;

    Tank(Location start) {
        this.health = size;
    }

    public static int getSize(){
        return size;
    }

    public int getAttack() {
        return damage[health];
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
