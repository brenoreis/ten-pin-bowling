package com.breno.tenpinbowling.util;

import com.breno.tenpinbowling.exception.ValidationException;

/**
 * Created by breno.pinto on 13/5/17.
 */
public class GameUtil {

    public static void validateRolls(String[] rolls) throws ValidationException {

        for (String rollString : rolls) {
            int roll;
            try {
                roll = Integer.parseInt(rollString);
            } catch (NumberFormatException nfe) {
                //not int
                String message = String.format("Input string contains a non integer character. Value: %s", rollString);
                throw new ValidationException(message);
            }
            //number of pins
            if (roll < 0 || roll > 10) {
                String message = String.format("Input contains an invalid roll. Value: %s", roll);
                throw new ValidationException(message);
            }
        }

        //max number of rolls in a game
        if (rolls.length > 21) {
            String message = "Input contains more rolls than maximum possible: " + rolls.length;
            throw new ValidationException(message);
        }
    }
}
