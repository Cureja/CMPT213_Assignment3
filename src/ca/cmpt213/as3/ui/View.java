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
                if(gameTile == Tile.HIDDEN_MISS || gameTile == Tile.HIDDEN_TANK) {
                    System.out.print("  ~");
                }
                else if(gameTile == Tile.TANK) {
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


    public void printCheatBoard(Board gameBoard) {


    }

}
