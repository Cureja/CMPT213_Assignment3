package ca.cmpt213.as3.ui;

import ca.cmpt213.as3.logic.Board;
import ca.cmpt213.as3.logic.Location;
import ca.cmpt213.as3.logic.Tile;

public class View {
    //some final variable
    private final int BOARD_SIZE = 9;
    private final int HIDDEN_TILE = 0;
    private final int HIDDEN_TANK = 1;
    private final int TANK = 2;

    //Default constructor that doesn't do anything
    public View() {            }

    public void printBoard(Board gameBoard) {
        //Inital print
        System.out.println("Game Board:");
        System.out.println("       1  2  3  4  5  6  7  8  9 10");
        char letter = 65;


        //Get the tile states of each tile and print out a specific tile depending on it's current state.
        for(int i = 0; i < BOARD_SIZE; i++) {
            System.out.print("    " + (char)(letter + i));
            for(int j = 0; j < BOARD_SIZE; j++) {
                Location placeHolderLocation = new Location(i, j);
                Tile gameTile = gameBoard.getTile(placeHolderLocation);
                int tileState = gameTile.getState();
                if(tileState == HIDDEN_TILE || tileState == HIDDEN_TANK) {
                    System.out.print("  ~");
                }
                else if(tileState == TANK) {
                    System.out.print("  X");
                }
                else {
                    System.out.print("   ");
                }
            }
            System.out.println();
        }
    }


    public void printCheatBoard(Board gameBoard) {

    }

}
