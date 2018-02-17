package ca.cmpt213.as3.logic;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final int BOARD_DIMENSION = 10;
    private List<Tank> tanksOnBoard = new ArrayList<Tank>();
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
        List<Location> tank = new ArrayList<Location>();
        List<Location> options = new ArrayList<Location>();
        int row, col;
        do {
            row = (int) (BOARD_DIMENSION * Math.random());
            col = (int) (BOARD_DIMENSION * Math.random());
        } while(board[row][col] != Tile.HIDDEN_MISS);

        Location start = new Location(row, col);
        tank.add(start);
        options.addAll(legalConnectingTiles(start));

        addSectionsToTank(tank,options);
    }

    private boolean addSectionsToTank(List<Location> tank, List<Location> options) {
        if (tank.size() == Tank.getSize()) {
            getMaxPiecesCanAdd();
            return true;
        }
        int selection = (int)(options.size() * Math.random());

    }

    private List<Location> legalConnectingTiles(Location location) {
        List<Location> legal = new ArrayList<Location>();
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

    private int getMaxPiecesCanAddToArea(Location) {

    }

    // Helper method for getMaxPiecesCanAdd
    private int floodFill(Location location, Tile state) {

    }
}
