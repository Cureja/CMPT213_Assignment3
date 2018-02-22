package ca.cmpt213.as3.ui;

import ca.cmpt213.as3.logic.*;

import java.util.List;

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
        for(int i = 0; i < BOARD_SIZE; i++) {
            System.out.print("    " + (char)(boardLetter + i));
            for(int j = 0; j < BOARD_SIZE; j++) {
                Location placeHolderLocation = new Location(i, j);
                Tile gameTile = gameBoard.getTile(placeHolderLocation);
                if(gameTile.isHidden()) {
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
        System.out.println("Fortress Structure Left: " + thePlayer.getHealth() + ".");
        System.out.print("Enter you move: ");
    }

    public void printGameOverBoard(Board gameBoard, Player thePlayer) {
        //Initial print of the board
        System.out.println("Game Board:");
        System.out.println("       1  2  3  4  5  6  7  8  9 10");
        char boardLetter = 65;


        //Get the tile states of each tile and print out a specific tile depending on it's current state.
        for(int i = 0; i < BOARD_SIZE; i++) {
            System.out.print("    " + (char)(boardLetter + i));
            for(int j = 0; j < BOARD_SIZE; j++) {
                Location placeHolderLocation = new Location(i, j);
                Tile gameTile = gameBoard.getTile(placeHolderLocation);
                if(gameTile.isTank()) {
                    System.out.print("  X");
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

    private void printLoop(Board gameBoard, char boardLetter, int castAdd) {
        for(int i = 0; i < BOARD_SIZE; i++) {
            System.out.print("    " + (char)(boardLetter + i));
            for(int j = 0; j < BOARD_SIZE; j++) {
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
