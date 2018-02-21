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
            } while (board[row][col] != Tile.HIDDEN_MISS && getMaxPiecesCanAddToArea(new Location(row,col)) > 0);
            Location start = new Location(row, col);
            tank.add(start);
            options.addAll(legalConnectingTileLocations(start));
            addSectionsToTank(tank, options, tanksToAdd - tanksAdded);

            Location[] tankLocation = tank.toArray(new Location[0]);
            tanksOnBoard.add(new Tank(tankLocation));
        }
    }

    private boolean addSectionsToTank(List<Location> tank, List<Location> pastOptions, Integer tanksToBeAdded) {
        if (tanksToBeAdded < getMaxPiecesCanAdd()) {
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
            if(options.isEmpty()) {
                return false;
            } else if(trialSurround != null) {
                options.removeAll(trialSurround);
            }
            int selection = (int)(options.size() * Math.random());
            Location trialSection = options.remove(selection);
            tank.add(trialSection);
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
        int damage = 0;
        for(Tank tank : tanksOnBoard) {
            damage += tank.getAttack();
        }
        return damage;
    }

    public boolean fireAtLocation(Location location) {
        Tile tile = getTile(location);
        if(tile.isHidden()) {

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
                if(board[row][col] == Tile.HIDDEN_MISS) {
                    maxPieces += floodFillEmpty(new Location(row, col)) / Tank.getSize();
                }
            }
        }
        //clean up floodFillEmpty
        for(int row = 0; row < BOARD_DIMENSION; row++) {
            for(int col = 0; col < BOARD_DIMENSION; col++) {
                if(board[row][col].getState() == -1) {
                    board[row][col] = Tile.HIDDEN_MISS;
                }
            }
        }
        return maxPieces;
    }

    private int getMaxPiecesCanAddToArea(Location start) {
        if(getTile(start) == Tile.HIDDEN_MISS) {
            int maxPieces = floodFillEmpty(start) / Tank.getSize();
            //clean up floodFillEmpty
            for(int row = 0; row < BOARD_DIMENSION; row++) {
                for(int col = 0; col < BOARD_DIMENSION; col++) {
                    if(board[row][col].getState() == -1) {
                        board[row][col] = Tile.HIDDEN_MISS;
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
            getTile(tile).setState(-1);
        }
        for(Location tile : adjacent) {
            sum += floodFillEmpty(tile);
        }
        return sum;
    }
}
