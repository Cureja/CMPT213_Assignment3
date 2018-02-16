package ca.cmpt213.as3.logic;

public class Tank {
    private static final int size = 4;
    private int tankHealth;
    private Location[] tankLocation;

    Tank(int health, Location[] tankLocation) {
        tankHealth = health;
    }

    public static int getSize(){
        return size;
    }

    public int getAttack() {

    }

    public int decreaseHealth() {

    }

    public boolean isInBounds() {

    }
}
