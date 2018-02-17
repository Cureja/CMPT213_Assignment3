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
            throw new IllegalArgumentException("Cannot fit inputted tanks on board");
        }
        for(int i = 0; i < numberOfTanks; i++) {
            createTankOnBoard();
        }
    }

    private void createTankOnBoard() {
        ArrayList<Location> tank;
        ArrayList<Location> options;
        int row, col;
        do {
            row = (int) (BOARD_DIMENSION * Math.random());
            col = (int) (BOARD_DIMENSION * Math.random());
        } while(board[row][col] != Tile.HIDDEN_MISS);

    }

    private boolean addSectionsToTank(ArrayList<Location> tank, ArrayList<Location> options, int size) {
        if (size == Tank.getSize()) {
            return true;
        }
        int selection = (int)(options.size() * Math.random());

    }

    private ArrayList<Location> legalConnectingTiles(Location location) {
        ArrayList<Location> legal = new ArrayList<Location>();
        Location up    = new Location(location.row - 1, location.col);
        Location down  = new Location(location.row + 1, location.col);
        Location left  = new Location(location.row, location.col - 1);
        Location right = new Location(location.row, location.col + 1);
        if(isInBounds(up) && getTile(up) == Tile.HIDDEN_MISS) {
            legal.add(up);
        }
        if(isInBounds(down) && getTile(down) == Tile.HIDDEN_MISS) {
            legal.add(down);
        }
        if(isInBounds(left) && getTile(left) == Tile.HIDDEN_MISS) {
            legal.add(left);
        }
        if(isInBounds(right) && getTile(right) == Tile.HIDDEN_MISS) {
            legal.add(right);
        }
        return legal;
    }

    public boolean isInBounds(Location location) {
        if(0 <= location.row && location.row < BOARD_DIMENSION) {
            if(0 <= location.col && location.col < BOARD_DIMENSION) {
                return true;
            }
        }
        return false;
    }

    public Tile getTile(Location location) {
        if (isInBounds(location)) {
            return board[location.row][location.col];
        }
        return null;
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
