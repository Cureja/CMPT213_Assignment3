package ca.cmpt213.as3;

import ca.cmpt213.as3.logic.Board;
import ca.cmpt213.as3.logic.Location;
import ca.cmpt213.as3.logic.Player;
import ca.cmpt213.as3.logic.Tank;
import ca.cmpt213.as3.ui.Input;
import ca.cmpt213.as3.ui.View;

import java.util.List;
import java.util.Scanner;

public class Game {

    private final static int FAILURE = -1;
    //Here is the asciiValues for digits 0-9
    static private final int ASCII_MIN = 48;
    static private final int ASCII_MAX = 57;


    public static void main(String[] args) {
        int numberOfTanks = Integer.parseInt(args[0]);
        argsCheck(args);
        if(args.length == 1) {
            playGame(numberOfTanks);
        }
        else {

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
        String dashLine = null;
        for(int i = 0; i < lengthOfDash; i++) {
            dashLine = dashLine + "-";
        }
        System.out.println(dashLine);
    }





    //Checks the arguments that the user passes to the program.
    private static void argsCheck(String[] args) {
        //This is the main block of our argument checking. We have to make sure the parameters are valid.
        try {
            //If there are no parameters passed.
            if(args.length == 0) {
                throw new Exception("No Parameters were passed.");
            }


            //Checks if the user put in more than two arguments
            if(args.length > 2) {
               throw new Exception("Unfortunately you have for than two arguments");
            }

            //Check to make sure the 1st argument is an integer.
            for(int i = 0; i < args[0].length(); i++) {
                if (args[0].charAt(i) < ASCII_MIN || args[0].charAt(i) > ASCII_MAX) {
                    throw new Exception("First Parameter is not an Int");
                }
            }

            //This is the check when the user decides not add a number for tank and takes in a solo --cheat parameter
            Integer.parseInt(args[0]);


            //Checks if the user entered a negative amount of tanks
            if(Integer.parseInt(args[0]) < 0) {
               throw new Exception("Unfortunately you have a negative numbers of tanks.");
            }

            //Checks if the user entered a number that is greater than the max number of tanks
            if(Integer.parseInt(args[0]) > 25) {
                throw new Exception("Unfortunately you have asked for a game " +
                                    "with more than 25 tanks. Please pick less.");
            }


            if(args.length == 2) {
               //Check if the 2nd parameter is valid/
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
        Board gameBoard = new Board(numberOfTanks);
        Player theFortress = new Player();
        View outputConsole = new View();
        Input inputHandler = new Input();
        printWelcome();
        outputConsole.printBoard(gameBoard, theFortress);
        while(!(gameBoard.isGameWon() || theFortress.isGameLoss())) {
           Scanner userInput = new Scanner(System.in);
           String input = userInput.nextLine();
           Location convertedInput = inputHandler.readLocation(input);
           boolean isShotATank = gameBoard.fireAtLocation(convertedInput);
           if(isShotATank) {
               System.out.println("Nice Shot Commander, that was a hit!");
           }
           else {
               System.out.println("Nice try Commander, that was a miss.");
           }
           List<Tank> gameTanks = gameBoard.getTanksOnBoard();
           printTankAttacks(gameTanks);
           int totalGDamage = gameBoard.getTotalDamageDealt();
           theFortress.takeDamage(totalGDamage);
           outputConsole.printBoard(gameBoard, theFortress);
        }
        if(gameBoard.isGameWon()) {
            System.out.println("Congratulations! You won! The enemies are on the run");
        }
        else {
            System.out.println("I'm sorry commander, the fortress has been smashed.");
        }
    }


    private static void playCheatGame(int numberOfTanks) {

    }

    private static void printTankAttacks(List<Tank> gameTanks) {
        int numberOfAliveTanks = gameTanks.size();
        for(Tank tank:gameTanks) {
            if(tank.getAttack() == 0) {
                numberOfAliveTanks--;
            }
        }

        int tankNumber = 1;
        for(Tank tank:gameTanks) {
            if(tank.getAttack() != 0) {
                System.out.println("Alive Tank #" + tankNumber + " of " + numberOfAliveTanks
                        + " shot you for " + tank.getAttack() + "!");
                tankNumber++;
            }
        }
    }
}




