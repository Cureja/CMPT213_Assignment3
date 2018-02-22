package ca.cmpt213.as3.ui;

import ca.cmpt213.as3.logic.*;

/**
 * This is class has the responsibility to print out the current status of the board
 * It has functionality to print out a cheat board and game over boards for the
 * two game modes. It relies on the tile class in order to assigned a string to print out
 * on the screen
 */


public class View {
    //some final variable
    private final int BOARD_SIZE = 10;

    //Default constructor that doesn't do anything
    public View() {            }

    public void printBoard(Board gameBoard, Player thePlayer) {
        //Initial print of the board
        System.out.println("Game Board:");
        System.out.println("       1  2  3  4  5  6  7  8  9 10");
        char boardLetter = 65;


        //Get the tile states of each tile and print out a specific tile depending on it's current state.
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.print("    " + (char)(boardLetter + i));

            //Inner loop that checks every tile
            for (int j = 0; j < BOARD_SIZE; j++) {
                Location placeHolderLocation = new Location(i, j);
                Tile gameTile = gameBoard.getTile(placeHolderLocation);

                //Checks the tile states and assigns a tile
                if (gameTile.isHidden()) {
                    System.out.print("  ~");
                }
                else if(gameTile.getState() == TileState.TANK) {
                    System.out.print("  X");
                }
                else {
                    System.out.print("   ");
                }
            }
            System.out.println();
        }

        //Prints out leftover health and asks the player for a new move
        System.out.println("Fortress Structure Left: " + thePlayer.getHealth() + ".");
        System.out.print("Enter you move: ");
    }

    public void printGameOverBoard(Board gameBoard, Player thePlayer) {

        //Initial print of the board
        System.out.println("Game Board:");
        System.out.println("       1  2  3  4  5  6  7  8  9 10");
        char boardLetter = 65;


        //Get the tile states of each tile and print out a specific tile depending on it's current state.
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.print("    " + (char)(boardLetter + i));
            for (int j = 0; j < BOARD_SIZE; j++) {
                Location placeHolderLocation = new Location(i, j);
                Tile gameTile = gameBoard.getTile(placeHolderLocation);

                //Checks the tile states and assigns a tile
                if (gameTile.isTank()) {
                    System.out.print("  X");
                }
                else if (gameTile.isHidden()) {
                    System.out.print("  ~");
                }
                else {
                    System.out.print("   ");
                }
            }
            System.out.println();
        }

        //Since it is game over no need to ask for another coordinate
        System.out.println("Fortress Structure Left: " + thePlayer.getHealth() + ".");
    }


    public void printCheatBoard(Board gameBoard, Player thePlayer) {
        //Initial print of the board
        System.out.println("Game Board:");
        System.out.println("       1  2  3  4  5  6  7  8  9 10");
        char boardLetter = 65;
        int castAdd = 65;

        //Get the tile states of each tile and print out a specific tile depending on it's current state.
        printLoop(gameBoard, boardLetter, castAdd);
        System.out.println("Fortress Structure Left: " + thePlayer.getHealth() + ".");
        System.out.print("Enter you move: ");
    }

    //This is a helper function to reduce the number of duplicate code. This is for the cheat methods.
    private void printLoop(Board gameBoard, char boardLetter, int castAdd) {
        for (int i = 0; i < BOARD_SIZE; i++) {

            System.out.print("    " + (char)(boardLetter + i));

            //Checks the tile states and assigns a tile. Uses char and casting.
            for (int j = 0; j < BOARD_SIZE; j++) {
                Location placeHolderLocation = new Location(i, j);
                Tile gameTile = gameBoard.getTile(placeHolderLocation);
                if(gameTile.isTank()) {
                    int tankIndex = gameBoard.getTankIndex(placeHolderLocation);
                    char uniqueTankLabel = (char)(tankIndex + castAdd);
                    System.out.print("  " + uniqueTankLabel);
                }

                else if(gameTile.isHidden()) {
                    System.out.print("  ~");
                }

                else {
                    System.out.print("   ");
                }
            }
            System.out.println();
        }
    }

    //Prints the game over for the cheat version of the game
    public void printCheatGameOverBoard(Board gameBoard, Player thePlayer) {
        //Initial print of the board
        System.out.println("Game Board:");
        System.out.println("       1  2  3  4  5  6  7  8  9 10");
        char boardLetter = 65;
        int castAdd = 97;

        //Get the tile states of each tile and print out a specific tile depending on it's current state.
        printLoop(gameBoard, boardLetter, castAdd);
        System.out.println("Fortress Structure Left: " + thePlayer.getHealth() + ".");
    }

}
