package ca.cmpt213.as3;

public class Game {

    private final static int FAILURE = -1;

    private static void main(String[] args) {
        argsCheck(args);
    }

    //Checks the arguments that the user passes to the program.
    private static void argsCheck(String[] args) {

        //Here is the asciiValues for digits 0-9
        int asciiMin = 48;
        int asciiMax = 57;

        try {
           if(args.length <= 2) {
               throw new Exception("Unfortunately you have for than two arguments");
           }

           //Check to make sure the 1st argument is an integer.
           for(int i = 0; i < args[0].length(); i++) {
                if(args[0].charAt(i) < asciiMin || args[0].charAt(i) > asciiMax) {
                    throw new Exception("First Parameter is not an Int");
                }
           }

           //Check the 2nd parameter
           if(!(args[1].equals("--cheat"))) {
               throw new Exception("2nd Parameter can only be --cheat");
           }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(FAILURE);
        }
    }

    private static void playGame(int numberOfTanks) {


    }


    private static void playCheatGame(int numberOfTanks) {

    }

}
