package ca.cmpt213.as3;

import ca.cmpt213.as3.logic.Board;
import ca.cmpt213.as3.logic.Location;
import ca.cmpt213.as3.logic.Player;
import ca.cmpt213.as3.logic.Tank;
import ca.cmpt213.as3.ui.Input;
import ca.cmpt213.as3.ui.View;

import java.util.List;
import java.util.Scanner;

/**
 * This is the game class, It's responsibility is to maintain the game loop and run main.
 * It does argument to ensure that the user does not enter too many parameters or none at all
 * It also ensure that the parameters are correct via a series of exceptions.
 * It has several helper functions
 */

public class Game {
    //In case of a exception being thrown exit with an failure
    private final static int FAILURE = -1;


    //Here is the asciiValue range for digits 0-9
    static private final int ASCII_MIN = 48;
    static private final int ASCII_MAX = 57;


    //Main. Checks for the game for its cheat.
    public static void main(String[] args) {
        argsCheck(args);
        int numberOfTanks = Integer.parseInt(args[0]);

        if (args.length == 1) {
            playGame(numberOfTanks);
        }
        else {
            playCheatGame(numberOfTanks);
        }
    }


    //Prints welcome screen
    private static void printWelcome() {
        printDashBorder();
        System.out.println("Welcome to Fortress Defense");
        System.out.println("by Alpha Alvin, and Beta Andy");
        printDashBorder();
        System.out.println();
    }



    //This prints out a line of dash
    private static void printDashBorder() {
        int lengthOfDash = 28;
        String dashLine = "";
        for (int i = 0; i < lengthOfDash; i++) {
            dashLine = dashLine + "-";
        }
        System.out.println(dashLine);
    }



    //Checks the arguments that the user passes to the program.
    private static void argsCheck(String[] args) {
        //This is the main block of our argument checking. We have to make sure the parameters are valid.
        try {
            //If there are no parameters passed.
            if (args.length == 0) {
                throw new Exception("No Parameters were passed.");
            }


            //Checks if the user put in more than two arguments
            if (args.length > 2) {
               throw new Exception("Unfortunately you have for than two arguments");
            }

            //Check to make sure the 1st argument is an integer.
            for (int i = 0; i < args[0].length(); i++) {
                if (args[0].charAt(i) < ASCII_MIN || args[0].charAt(i) > ASCII_MAX) {
                    throw new Exception("First Parameter is not an Int");
                }
            }


            //Checks if the user entered a negative amount of tanks
            if (Integer.parseInt(args[0]) < 0) {
               throw new Exception("Unfortunately you have a negative numbers of tanks.");
            }

            //Checks if the user entered a number that is greater than the max number of tanks
            if (Integer.parseInt(args[0]) > 25) {
                throw new Exception("Unfortunately you have asked for a game " +
                                    "with more than 25 tanks. Please pick less.");
            }


            if (args.length == 2) {
               //Check if the 2nd parameter is valid
               if(!(args[1].equals("--cheat"))) {
                   throw new Exception("2nd Parameter can only be --cheat");
               }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(FAILURE);
        }
    }


    private static void playGame(int numberOfTanks) {
        //Initializing the game objects
        Board gameBoard = new Board(numberOfTanks);
        Player theFortress = new Player();
        View outputConsole = new View();
        Input inputHandler = new Input();


        //Prints a welcome screen, game board and prints out the gameLoop
        printWelcome();
        outputConsole.printBoard(gameBoard, theFortress);
        gameLoop(gameBoard, theFortress, outputConsole, inputHandler);


        //When the game loop breaks check to see if the user won or lost the game
        if (gameBoard.isGameWon()) {
            System.out.println("Congratulations! You won! The enemies are on the run");
        }
        else {
            System.out.println("I'm sorry commander, the fortress has been smashed.");
        }

        //Print out the answer key
        outputConsole.printGameOverBoard(gameBoard, theFortress);
    }

    //This version of the game prints out the answer key.
    private static void playCheatGame(int numberOfTanks) {
        //Initializes gameObjects
        Board gameBoard = new Board(numberOfTanks);
        Player theFortress = new Player();
        View outputConsole = new View();
        Input inputHandler = new Input();

        //Prints the welcome message and the answer key
        printWelcome();
        outputConsole.printCheatBoard(gameBoard, theFortress);

        //The main game loop. Won't break until player wins or loses
        gameLoop(gameBoard, theFortress, outputConsole, inputHandler);

        //When the game loop breaks check to see if the user won or lost the game
        if (gameBoard.isGameWon()) {
            System.out.println("Congratulations! You won! The enemies are on the run");
        }
        else {
            System.out.println("I'm sorry commander, the fortress has been smashed.");
        }

        //Prints the cheat board in lower case form
        outputConsole.printCheatGameOverBoard(gameBoard, theFortress);
    }

    //Here is the game loop that both the cheatBoard and normal board use
    private static void gameLoop(Board gameBoard, Player theFortress, View outputConsole, Input inputHandler) {
        while(!(gameBoard.isGameWon() || theFortress.isGameLoss())) {

            //A scanner object to take in userInput
            Scanner userInput = new Scanner(System.in);
            String input = userInput.nextLine();

            //This single line of does all input checking through the input object
            Location convertedInput = inputHandler.readLocation(input);

            //Checks if the tile is a tank and fire at the location
            boolean isShotATank = gameBoard.fireAtLocation(convertedInput);
            if (isShotATank) {
                System.out.println("Nice Shot Commander, that was a hit!");
            }
            else {
                System.out.println("Nice try Commander, that was a miss.");
            }

            //Get a list of tanks and make the board attack the player
            List<Tank> gameTanks = gameBoard.getTanksOnBoard();
            printTankAttacks(gameTanks);

            //Damage is then applied to player
            int totalDamage = gameBoard.getTotalDamageDealt();
            theFortress.takeDamage(totalDamage);

            //Prints out the new state of the board.
            outputConsole.printBoard(gameBoard, theFortress);
        }

    }

    //This function prints out the sequence of tank attacks
    private static void printTankAttacks(List<Tank> gameTanks) {

        //We must get the number of functional tanks on the board
        int numberOfAliveTanks = gameTanks.size();
        for(Tank tank:gameTanks) {
            if(tank.getAttack() == 0) {
                numberOfAliveTanks--;
            }
        }

        //We then list the functional tanks
        int tankNumber = 1;
        for(Tank tank:gameTanks) {
            if (tank.getAttack() != 0) {
                System.out.println("Alive Tank #" + tankNumber + " of " + numberOfAliveTanks
                        + " shot you for " + tank.getAttack() + "!");
                tankNumber++;
            }
        }
    }
}




