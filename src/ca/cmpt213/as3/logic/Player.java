package ca.cmpt213.as3.logic;

/**
 * This is the player class, It's responsibility is to keep track of the
 * fortress health and receive damage
 */

public class Player {
    private int health;
    private final int MAX_HEALTH = 1500;

    //Player constuctor
    Player() {
        this.health = MAX_HEALTH;
    }


    //Returns the player's health
    public int getHealth() {
        return health;
    }

    //Player
    void takeDamage(int damage) {
        this.health = this.health - damage;
    }
}
