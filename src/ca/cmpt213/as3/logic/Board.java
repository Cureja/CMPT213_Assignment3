package ca.cmpt213.as3.logic;

import java.util.ArrayList;

public class Board {
    private final int BOARD_DIMENSION = 10;
    private ArrayList<Tank> tanksOnBoard = new ArrayList<Tank>();
    private Tile[][] board;

    public Board(int numberOfTanks) {
        int boardSize = BOARD_DIMENSION * BOARD_DIMENSION;
        int maxTanksOnBoard = boardSize/Tank.getSize();
        if(numberOfTanks < boardSize/Tank.getSize()) {
            throw new IllegalArgumentException("Cannot fit tanks to board");
        }
        for(int i = 0; i < numberOfTanks; i++) {
            createTankOnBoard();
        }
    }

    private void createTankOnBoard() {
        ArrayList<Location> tank;
        ArrayList<Location> tried;
        int row, col;
        do {
            row = (int) (BOARD_DIMENSION * Math.random());
            col = (int) (BOARD_DIMENSION * Math.random());
        } while(board[row][col] != Tile.HIDDEN_MISS);

    }

    public Tile getTile(int row, int col) {

    }

    public int attackPlayer() {

    }

    public Tile fireAtLocation(Location location) {

    }

    public boolean isGameWon() {

    }

    private int getMaxPiecesCanAdd() {

    }

    // Helper method for getMaxPiecesCanAdd
    private int floodFill(Location location) {

    }
}
