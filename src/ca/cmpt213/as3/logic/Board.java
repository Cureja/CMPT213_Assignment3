package ca.cmpt213.as3.logic;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final int BOARD_DIMENSION = 10;
    private List<Tank> tanksOnBoard = new ArrayList<>();
    private Tile[][] board;

    public Board(int numberOfTanks) {
        int boardSize = BOARD_DIMENSION * BOARD_DIMENSION;
        int maxTanksOnBoard = boardSize/Tank.getSize();
        if(numberOfTanks > maxTanksOnBoard) {
            throw new IllegalArgumentException("Cannot fit inputted tanks on board");
        }
        board = new Tile[BOARD_DIMENSION][BOARD_DIMENSION];
        for(int row = 0; row < BOARD_DIMENSION; row++) {
            for(int col = 0; col < BOARD_DIMENSION; col++) {
                board[row][col] = new Tile();
            }
        }
        createTanksOnBoard(numberOfTanks);

    }

    private void createTanksOnBoard(int tanksToAdd) {
        for(int tanksAdded = 0; tanksAdded < tanksToAdd; tanksAdded++) {
            List<Location> tank = new ArrayList<>();
            List<Location> options = new ArrayList<>();
            int row, col;
            do {
                row = (int) (BOARD_DIMENSION * Math.random());
                col = (int) (BOARD_DIMENSION * Math.random());
            } while (board[row][col].getState() != TileState.MISS || getMaxPiecesCanAddToArea(new Location(row,col)) == 0);
            Location start = new Location(row, col);
            tank.add(start);
            board[row][col].setState(TileState.TANK);
            options.addAll(legalConnectingTileLocations(start));
            addSectionsToTank(tank, options, tanksToAdd - tanksAdded);
            Location[] tankLocation = tank.toArray(new Location[0]);
            tanksOnBoard.add(new Tank(tankLocation));
        }
    }

    private boolean addSectionsToTank(List<Location> tank, List<Location> pastOptions, Integer tanksToBeAdded) {
        if (tanksToBeAdded-1 > getMaxPiecesCanAdd()) {
            return false;
        } else if (tank.size() == Tank.getSize()) {
            return true;
        }
        List<Location> options  = new ArrayList<>();
        for(Location past : pastOptions) {
            options.add(new Location(past));
        }

        boolean isSelectionCorrect = false;
        List<Location> trialSurround = null;
        while(!isSelectionCorrect) {
            if(trialSurround != null) {
                Location remove = tank.get(tank.size()-1);
                board[remove.row][remove.col].setState(TileState.MISS);
                tank.remove(tank.size()-1);
                options.removeAll(trialSurround);
            }
            if(options.isEmpty()) {
                return false;
            }
            int selection = (int)(options.size() * Math.random());
            Location trialSection = options.remove(selection);
            tank.add(trialSection);
            board[trialSection.row][trialSection.col].setState(TileState.TANK);
            trialSurround = legalConnectingTileLocations(trialSection);
            options.addAll(trialSurround);
            isSelectionCorrect = addSectionsToTank(tank, options, tanksToBeAdded);
        }
        return true;
    }

    private List<Location> legalConnectingTileLocations(Location location) {
        List<Location> legal = new ArrayList<>();
        Location up    = new Location(location.row - 1, location.col);
        Location down  = new Location(location.row + 1, location.col);
        Location left  = new Location(location.row, location.col - 1);
        Location right = new Location(location.row, location.col + 1);
        if(isInBounds(up) && !getTile(up).isTank() && getTile(up).isHidden()) {
            legal.add(up);
        }
        if(isInBounds(down) && !getTile(down).isTank() && getTile(down).isHidden()) {
            legal.add(down);
        }
        if(isInBounds(left) && !getTile(left).isTank() && getTile(left).isHidden()) {
            legal.add(left);
        }
        if(isInBounds(right) && !getTile(right).isTank() && getTile(right).isHidden()) {
            legal.add(right);
        }
        return legal;
    }

    private boolean isInBounds(Location location) {
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

    public int getTotalDamageDealt() {
        int damage = 0;
        for(Tank tank : tanksOnBoard) {
            damage += tank.getAttack();
        }
        return damage;
    }

    public boolean fireAtLocation(Location location) {
        Tile tile = getTile(location);
        if(tile.isHidden()) {
            tile.reveal();
            if (tile.isTank()) {
                for(Tank tank : tanksOnBoard) {
                    if(tank.isInBounds(location)) {
                        tank.decreaseHealth();
                    }
                }
                return true;
            }
        }
        return false;
    }

    public boolean isGameWon() {
        for(Tank tank : tanksOnBoard) {
            if(!tank.isDamaged()) {
                return false;
            }
        }
        return true;
    }

    private int getMaxPiecesCanAdd() {
        int maxPieces = 0;
        for(int row = 0; row < BOARD_DIMENSION; row++) {
            for(int col = 0; col < BOARD_DIMENSION; col++) {
                if(board[row][col].isHidden()) {
                    board[row][col].setIsHidden(false);
                    maxPieces += floodFillEmpty(new Location(row, col)) / Tank.getSize();
                }
            }
        }
        //clean up floodFillEmpty
        for(int row = 0; row < BOARD_DIMENSION; row++) {
            for(int col = 0; col < BOARD_DIMENSION; col++) {
                if(!board[row][col].isHidden()) {
                    board[row][col].setIsHidden(true);
                }
            }
        }
        return maxPieces;
    }

    private int getMaxPiecesCanAddToArea(Location start) {
        if(getTile(start).isHidden()) {
            board[start.row][start.col].setIsHidden(false);
            int maxPieces = floodFillEmpty(start) / Tank.getSize();
            //clean up floodFillEmpty
            for(int row = 0; row < BOARD_DIMENSION; row++) {
                for(int col = 0; col < BOARD_DIMENSION; col++) {
                    if(!board[row][col].isHidden()) {
                        board[row][col].setIsHidden(true);
                    }
                }
            }

            return maxPieces;
        }
        return 0;
    }

    private int floodFillEmpty(Location start) {
        int sum = 1;
        List<Location> adjacent = legalConnectingTileLocations(start);
        for(Location tile : adjacent) {
            board[tile.row][tile.col].setIsHidden(false);
        }
        for(Location tile : adjacent) {
            sum += floodFillEmpty(tile);
        }
        return sum;
    }

    public List<Tank> getTanksOnBoard() {
        return tanksOnBoard;
    }

    public int getTankIndex(Location location) {
        for(int i = 0; i < tanksOnBoard.size(); i++) {
            Tank tank = tanksOnBoard.get(i);
            if(tank.isInBounds(location)) {
                return i;
            }
        }
        return -1;
    }
}
