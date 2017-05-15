package com.breno.tenpinbowling.service;

import com.breno.tenpinbowling.exception.ValidationException;

import java.util.ArrayList;
import java.util.List;

import static com.breno.tenpinbowling.util.Constants.NUMBER_OF_FRAMES;
import static com.breno.tenpinbowling.util.Constants.SPARE;
import static com.breno.tenpinbowling.util.Constants.STRIKE;

/**
 * Created by breno.pinto on 13/5/17.
 */
class Frame {

    private int frameNumber;
    private List<Integer> attempts;
    private int score;
    private boolean isComplete;
    private boolean isStrike = false;
    private boolean isSpare = false;

    Frame(Integer number) {
        frameNumber = number;
        attempts = new ArrayList<>();
        score = 0;
    }

    boolean isStrike() {
        return isStrike;
    }

    boolean isSpare() {
        return isSpare;
    }

    int getFrameNumber() {
        return frameNumber;
    }

    int getScore() {
        return score;
    }

    void setScore(int score) {
        this.score = score;
    }

    void addRoll(int pins) throws ValidationException {
        attempts.add(pins);
        validateAttempts();

        if (attempts.size() == 1) {
            if (pins == STRIKE) {
                isStrike = true;
                if (frameNumber != NUMBER_OF_FRAMES) {
                    isComplete = true;
                }
            }
        } else if (attempts.size() == 2) {
            if (!isStrike && sumAttempts() == SPARE) {
                isSpare = true;
            }
            if (frameNumber != NUMBER_OF_FRAMES || (!isStrike && !isSpare)) {
                isComplete = true;
            }
        } else {
            isComplete = true;
        }

    }

    boolean isComplete() {
        return isComplete;
    }

    int sumAttempts() {
        int sum = 0;
        for (int attempt : attempts) {
            sum += attempt;
        }
        return sum;
    }

    List<Integer> getAttempts() {
        return attempts;
    }

    private void validateAttempts() throws ValidationException {
        if (frameNumber == NUMBER_OF_FRAMES) {
            if (attempts.size() == 2 && attempts.get(0) != STRIKE && sumAttempts() > SPARE) {
                throw new ValidationException("Input has the 2 first rolls in 10th frame with more than 10 pins in total.");
            }
        } else if (sumAttempts() > SPARE) {
            throw new ValidationException("Input has frame with more than 10 pins in total. Frame number: " + frameNumber);
        }
    }
}
