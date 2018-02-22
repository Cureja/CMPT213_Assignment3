package ca.cmpt213.as3.logic;

import java.util.ArrayList;
import java.util.List;

/**
 * Board class models the information about the
 * game board.
 * Data includes list of tanks on the board and
 * the state of each tile.
 * Responsibilities includes board generation, board
 * manipulation, and knowing if the game is won.
 */

public class Board {
    private final int BOARD_DIMENSION = 10;
    private List<Tank> tanksOnBoard = new ArrayList<>();
    private Tile[][] board;

    public Board(int numberOfTanks) {
        int boardSize = BOARD_DIMENSION * BOARD_DIMENSION;
        int maxTanksOnBoard = boardSize / Tank.getSize();
        if(numberOfTanks > maxTanksOnBoard) {
            throw new IllegalArgumentException("Cannot fit inputted tanks on board");
        }
        board = new Tile[BOARD_DIMENSION][BOARD_DIMENSION];
        for (int row = 0; row < BOARD_DIMENSION; row++) {
            for (int col = 0; col < BOARD_DIMENSION; col++) {
                board[row][col] = new Tile();
            }
        }
        createTanksOnBoard(numberOfTanks);

    }

    // Helper function to board constructor.
    private void createTanksOnBoard(int tanksToAdd) {
        for (int tanksAdded = 1; tanksAdded <= tanksToAdd; tanksAdded++) {
            List<Location> tank = new ArrayList<>();
            List<Location> options = new ArrayList<>();

            // Loop gets a random location that has no tank and has enough space for a tank.
            int row, col;
            do {
                row = (int) (BOARD_DIMENSION * Math.random());
                col = (int) (BOARD_DIMENSION * Math.random());
            } while (board[row][col].getState() != TileState.MISS || getMaxTanksCanAddToArea(new Location(row,col)) == 0);

            Location start = new Location(row, col);
            tank.add(start);
            board[row][col].setState(TileState.TANK);
            options.addAll(legalConnectingTileLocations(start));
            addSectionsToTank(tank, options, tanksToAdd - tanksAdded);
            Location[] tankLocation = tank.toArray(new Location[0]);
            tanksOnBoard.add(new Tank(tankLocation));
        }
    }

    // Helper function for createTanksOnBoard.
    private boolean addSectionsToTank(List<Location> tank, List<Location> pastOptions, Integer tanksToBeAdded) {
        // If this limits the board into not having enough space, return false and go back a step.
        if (tanksToBeAdded > getMaxTanksCanAdd()) {
            return false;
        }
        else if (tank.size() == Tank.getSize()) {
            return true;
        }

        // Deep clone on past options to not mess with the previous list
        List<Location> options  = new ArrayList<>();
        for (Location past : pastOptions) {
            options.add(new Location(past));
        }

        // Loop keeps trying different spots and branches out from that choice to see
        // if it does not produce an impossible board for future added tanks.
        boolean isSelectionCorrect = false;
        List<Location> trialSurround = null;
        while (!isSelectionCorrect) {
            // If the selection is not the first one, remove past options and tank section.
            if (trialSurround != null) {
                Location remove = tank.get(tank.size()-1);
                board[remove.row][remove.col].setState(TileState.MISS);
                tank.remove(tank.size()-1);
                options.removeAll(trialSurround);
            }

            if (!options.isEmpty()) {
                // Random selection of options and updating options and tank to that choice.
                int selection = (int) (options.size() * Math.random());
                Location trialSection = options.remove(selection);
                tank.add(trialSection);
                board[trialSection.row][trialSection.col].setState(TileState.TANK);
                trialSurround = legalConnectingTileLocations(trialSection);
                options.addAll(trialSurround);

                // Recursive call and check
                isSelectionCorrect = addSectionsToTank(tank, options, tanksToBeAdded);
            } else {
                return false;
            }
        }
        return true;
    }

    // Helper function for addSectionsToTank and floodFillEmpty.
    private List<Location> legalConnectingTileLocations(Location location) {
        List<Location> legal = new ArrayList<>();
        Location up    = new Location(location.row - 1, location.col);
        Location down  = new Location(location.row + 1, location.col);
        Location left  = new Location(location.row, location.col - 1);
        Location right = new Location(location.row, location.col + 1);

        if (isInBounds(up) && !getTile(up).isTank() && getTile(up).isHidden()) {
            legal.add(up);
        }
        if (isInBounds(down) && !getTile(down).isTank() && getTile(down).isHidden()) {
            legal.add(down);
        }
        if (isInBounds(left) && !getTile(left).isTank() && getTile(left).isHidden()) {
            legal.add(left);
        }
        if (isInBounds(right) && !getTile(right).isTank() && getTile(right).isHidden()) {
            legal.add(right);
        }
        return legal;
    }

    // Helper function to check if in bounds.
    private boolean isInBounds(Location location) {
        if (0 <= location.row && location.row < BOARD_DIMENSION) {
            if (0 <= location.col && location.col < BOARD_DIMENSION) {
                return true;
            }
        }
        return false;
    }

    // Helper functions for addingSectionsToTank.
    // It gets the max amount of tanks that can be added to the board.
    private int getMaxTanksCanAdd() {
        int maxPieces = 0;
        for (int row = 0; row < BOARD_DIMENSION; row++) {
            for (int col = 0; col < BOARD_DIMENSION; col++) {
                Tile tile = board[row][col];
                // If the tile is hidden and does not has a tank do a flood fill on the area
                // and get the floor of its size divided by tank size and add it to the amount
                // that can be added.
                if (tile.isHidden() && !tile.isTank()) {
                    tile.setIsHidden(false);
                    maxPieces += floodFillEmpty(new Location(row, col)) / Tank.getSize();
                }
            }
        }

        //clean up floodFillEmpty's changes
        cleanUpFloodFillEmpty();

        return maxPieces;
    }

    // Helper functions for createTanksOnBoard
    // It gets the max amount of tanks that can be added to the given area.
    private int getMaxTanksCanAddToArea(Location start) {
        if (getTile(start).isHidden()) {
            board[start.row][start.col].setIsHidden(false);
            // Floor of the area divided by tank area.
            int maxPieces = floodFillEmpty(start) / Tank.getSize();

            //clean up floodFillEmpty
            cleanUpFloodFillEmpty();

            return maxPieces;
        }
        return 0;
    }

    // Helper functions for getMaxTanksCanAdd and getMaxTanksCanAddToArea
    // It gets the amount of connected empty and hidden squares and can only
    // be used at board creation time. Relies on its implementers to reset the tiles.
    private int floodFillEmpty(Location start) {
        int sum = 1;
        List<Location> adjacent = legalConnectingTileLocations(start);
        for (Location tile : adjacent) {
            board[tile.row][tile.col].setIsHidden(false);
        }
        for (Location tile : adjacent) {
            sum += floodFillEmpty(tile);
        }
        return sum;
    }

    // Cleans up the changes flood fill makes to do its calculation.
    private void cleanUpFloodFillEmpty() {
        for (int row = 0; row < BOARD_DIMENSION; row++) {
            for (int col = 0; col < BOARD_DIMENSION; col++) {
                if (!board[row][col].isHidden()) {
                    board[row][col].setIsHidden(true);
                }
            }
        }
    }

    public Tile getTile(Location location) {
        if (isInBounds(location)) {
            return board[location.row][location.col];
        }
        return null;
    }

    public int getTotalDamageDealt() {
        int damage = 0;
        for (Tank tank : tanksOnBoard) {
            damage += tank.getAttack();
        }
        return damage;
    }

    public List<Tank> getTanksOnBoard() {
        return tanksOnBoard;
    }

    public int getTankIndex(Location location) {
        for (int i = 0; i < tanksOnBoard.size(); i++) {
            Tank tank = tanksOnBoard.get(i);
            if (tank.isInBounds(location)) {
                return i;
            }
        }
        return -1;
    }

    public boolean fireAtLocation(Location location) {
        Tile tile = getTile(location);
        if (tile.isHidden()) {
            tile.reveal();
            if (tile.isTank()) {
                for (Tank tank : tanksOnBoard) {
                    if (tank.isInBounds(location)) {
                        tank.decreaseHealth();
                    }
                }
                return true;
            }
        }
        return false;
    }

    // Checks if the game's win condition is met, which is if all tanks
    // are hit at least once.
    public boolean isGameWon() {
        for (Tank tank : tanksOnBoard) {
            if (!tank.isDamaged()) {
                return false;
            }
        }
        return true;
    }
}
