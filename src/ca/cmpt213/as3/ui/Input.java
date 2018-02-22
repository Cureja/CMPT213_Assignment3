package ca.cmpt213.as3.ui;

import ca.cmpt213.as3.logic.Location;

import java.util.Scanner;

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

    //A normal ten used for string length of 3
    private final int LOC_TEN = 10;

    public Input() {           }

    //Has a while loop to check the validity of it.
    public Location readLocation(String userInput) {
        boolean ValidLocation = isValidLocation(userInput);
        while(!(ValidLocation)) {
            System.out.println("Commander! Those are civilians we are targeting! " +
                                "Please enter a new coordinate, such as A10");
            System.out.print("Enter your move: ");
            Scanner tryAgain = new Scanner(System.in);
            userInput = tryAgain.nextLine();
            ValidLocation = isValidLocation(userInput);
        }

        if(userInput.length() == 2) {
            return new Location(convertFirstCharToInt(userInput), convertSecondCharToInt(userInput));
        }
        return new Location(convertFirstCharToInt(userInput), LOC_TEN);

    }

    //Traps the user in a infinite for loop if they keep entering incorrect input.
    //CASE-Incentive
    private boolean isValidLocation(String userInput) {
        //Checks if the length of the string is > 3
        if(userInput.length() > 3) {
            return false;
        }

        //Case 1: The length of the input is 2, so we should have some
        if(userInput.length() == 2) {
            return isFirstCharValid(userInput) && isSecondCharValid(userInput);
        }

        //Case 2: The length of the input is 3, thus the number must be 10
        if(userInput.length() == 3) {
            if(!(isFirstCharValid(userInput))) {
                return false;
            }

            String shouldBeTen = userInput.substring(1,2);
            return shouldBeTen.equals(TEN);
        }
        return true;
    }

    //Checks the first char for it's validity
    private boolean isFirstCharValid(String userInput) {
        char firstLetter = userInput.charAt(0);
        if(firstLetter < ASCII_MIN_FOR_LOWERCASE || firstLetter > ASCII_MAX_FOR_LOWERCASE) {
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
        if(firstLetter >= ASCII_MIN_FOR_LOWERCASE && firstLetter <= ASCII_MAX_FOR_LOWERCASE) {
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
