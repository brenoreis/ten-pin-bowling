package com.breno.tenpinbowling;

import com.breno.tenpinbowling.exception.ValidationException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by breno.pinto on 13/5/17.
 */
class Frame {

    private static final int TEN = 10;

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

    void addRoll(int roll) throws ValidationException {
        attempts.add(roll);
        if (frameNumber == TEN) {
            if (attempts.size() == 2 && attempts.get(0) != 10 && sumAttempts() > 10) {
                throw new ValidationException("Input has the 2 first rolls in 10th frame with more than 10 pins in total.");
            }
        } else if (sumAttempts() > TEN) {
            throw new ValidationException("Input has frame with more than 10 pins in total. Frame number: " + frameNumber);
        }

        if (frameNumber == TEN) {
            if (attempts.size() == 1) {
                if (roll == TEN) {
                    isStrike = true;
                }
            } else if (attempts.size() == 2) {
                if (!isStrike && sumAttempts() == TEN) {
                    isSpare = true;
                    isStrike = false;
                }
            } else {
                isComplete = true;
            }
        } else {
            if (attempts.size() == 1 && roll == TEN) {
                isComplete = true;
                isStrike = true;
            } else if (attempts.size() == 2) {
                if (sumAttempts() == TEN) {
                    isSpare = true;
                }
                isComplete = true;
            }
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
}
