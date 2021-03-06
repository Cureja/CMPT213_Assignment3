package ca.cmpt213.as3.ui;

import ca.cmpt213.as3.logic.Location;

import java.util.Scanner;

/**
 * Input has the responsibility of ensuring correct user input
 * and the parsing for the inputs in the case of 2 and 3 char inputs
 */


public class Input {
    //Just a comparision used for a later case
    private final String TEN = "10";

    //THe range of acceptable input for the first letter is a-j
    private final int ASCII_MIN_FOR_LOWERCASE = 97;
    private final int ASCII_MAX_FOR_LOWERCASE = 106;

    //The range of acceptable input for the first letter is A-J
    private final int ASCII_MIN_FOR_UPPERCASE = 65;
    private final int ASCII_MAX_FOR_UPPERCASE = 74;

    //The range of acceptable input for the last integer is 1-9 (10) is a separate case.
    private final int ASCII_MIN_INT = 49;
    private final int ASCII_MAX_INT = 57;

    //A normal nine used for string length of 3
    private final int LOC_NINE = 9;

    public Input() {           }

    //This class return a location based off what the user entered
    public Location readLocation(String userInput) {
        boolean ValidLocation = isValidLocation(userInput);

        //Has a while loop to check the validity of the input.
        while (!(ValidLocation)) {
            System.out.println("Commander! Those are civilians we are targeting! " +
                                "Please enter a new coordinate, such as A10");
            System.out.print("Enter your move: ");
            Scanner tryAgain = new Scanner(System.in);
            userInput = tryAgain.nextLine();
            ValidLocation = isValidLocation(userInput);
        }

        //Accomodate the two cases.
        if (userInput.length() == 2) {
            return new Location(convertFirstCharToInt(userInput), convertSecondCharToInt(userInput));
        }
        return new Location(convertFirstCharToInt(userInput), LOC_NINE);

    }

    //Checks the validity of input
    private boolean isValidLocation(String userInput) {
        int maxCharThreshold = 3;
        int charSizeTwo = 2;
        //Checks if the length of the string is > 3
        if (userInput.length() > maxCharThreshold) {
            return false;
        }

        //Checks for only 1 char.
        if (userInput.length() < charSizeTwo) {
            return false;
        }
        //Case 1: The length of the input is 2, so we should have some
        if (userInput.length() == charSizeTwo) {
            return isFirstCharValid(userInput) && isSecondCharValid(userInput);
        }

        //Case 2: The length of the input is 3, thus the number must be 10
        if(userInput.length() == maxCharThreshold) {
            if (!(isFirstCharValid(userInput))) {
                return false;
            }

            String shouldBeTen = userInput.substring(1,3);
            return shouldBeTen.equals(TEN);
        }
        return true;
    }

    //Checks the first char for it's validity
    private boolean isFirstCharValid(String userInput) {
        char firstLetter = userInput.charAt(0);
        if (firstLetter < ASCII_MIN_FOR_LOWERCASE || firstLetter > ASCII_MAX_FOR_LOWERCASE) {
            return firstLetter >= ASCII_MIN_FOR_UPPERCASE && firstLetter <= ASCII_MAX_FOR_UPPERCASE;
        }
        return true;
    }

    //Checks the second char for it's validity.
    private boolean isSecondCharValid(String userInput) {
        char number = userInput.charAt(1);
        return number >= ASCII_MIN_INT && number <= ASCII_MAX_INT;
    }

    //A helper method used to convert the first char assuming it is a valid one
    private int convertFirstCharToInt(String userInput) {
        char firstLetter = userInput.charAt(0);
        if (firstLetter >= ASCII_MIN_FOR_LOWERCASE && firstLetter <= ASCII_MAX_FOR_LOWERCASE) {
            return (int)firstLetter - ASCII_MIN_FOR_LOWERCASE;
        }
        else {
            return (int)firstLetter - ASCII_MIN_FOR_UPPERCASE;
        }
    }

    //A helper method used to convert the second char assuming it is a valid one
    private int convertSecondCharToInt(String userInput) {
        char secondLetter = userInput.charAt(1);
        return (int)secondLetter - ASCII_MIN_INT;
    }
}
