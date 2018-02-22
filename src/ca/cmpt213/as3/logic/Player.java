package ca.cmpt213.as3.logic;

/**
 * This is the player class, It's responsibility is to keep track of the
 * fortress health and receive damage
 */

public class Player {
    private int health;
    private static final int MAX_HEALTH = 1500;

    //Player constuctor
    public Player() {
        this.health = MAX_HEALTH;
    }


    //Returns the player's health
    public int getHealth() {
        return health;
    }

    //Player takes damage based off a passed damage value
    public void takeDamage(int damage) {
        this.health -= damage;

        //Ensure that this health does not go below zero.
        if(this.health < 0) {
            this.health = 0;
        }
    }

    //Check if the player has no health left
    public boolean isGameLoss() {
        return health <= 0;
    }
}
