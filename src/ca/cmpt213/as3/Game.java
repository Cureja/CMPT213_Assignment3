package ca.cmpt213.as3;

import ca.cmpt213.as3.logic.Board;
import ca.cmpt213.as3.logic.Player;
import ca.cmpt213.as3.ui.Input;
import ca.cmpt213.as3.ui.View;

public class Game {

    private final static int FAILURE = -1;

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
    }

    //This prints out a line of dash
    private static void printDashBorder() {
        int lengthOfDash = 28;
        String dashLine = null;
        for(int i = 0; i < lengthOfDash; i++) {
            dashLine = dashLine + "-";
        }
        System.out.println(dashLine);
        System.out.println();
    }





    //Checks the arguments that the user passes to the program.
    private static void argsCheck(String[] args) {

        //Here is the asciiValues for digits 0-9
        int asciiMin = 48;
        int asciiMax = 57;

        try {
           if(args.length > 2) {
               throw new Exception("Unfortunately you have for than two arguments");
           }

           //Check to make sure the 1st argument is an integer.
           for(int i = 0; i < args[0].length(); i++) {
               if (args[0].charAt(i) < asciiMin || args[0].charAt(i) > asciiMax) {
                   throw new Exception("First Parameter is not an Int");
               }
           }

           if(args.length == 2) {
               //Check the 2nd parameter if it exists
               if(!(args[1].equals("--cheat"))) {
                   throw new Exception("2nd Parameter can only be --cheat");
               }
           }
        }
        catch (Exception e) {
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
        outputConsole.printBoard(gameBoard);
//        while(gameBoard.isGameWon() || theFortress.isGameLoss() ) {
//
//        }

    }


    private static void playCheatGame(int numberOfTanks) {

    }

}
