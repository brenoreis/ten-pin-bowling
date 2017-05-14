package com.breno.tenpinbowling;

import com.breno.tenpinbowling.exception.ValidationException;
import com.breno.tenpinbowling.util.GameUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by breno.pinto on 13/5/17.
 */
class Game {

    private static final int TEN = 10;

    private int currentFrameIndex = 0;
    private List<Frame> frames = new ArrayList<>();
    private int currentScore;

    private void createGame() {
        for (int i = 0; i< TEN; i++) {
            Frame frame = new Frame(i+1);
            frames.add(frame);
        }
    }

    private boolean isGameComplete() {
        return (frames.get(9).isComplete());
    }

    private String getTotalScore() {
        return "Final Result: " + currentScore;
    }

    private void roll(int roll) throws ValidationException {

        if (currentFrameIndex > 9) {
            throw new ValidationException("Maximum number of frames has been exceeded.");
        }

        Frame frame = frames.get(currentFrameIndex);
        frame.addRoll(roll);
        //last frame is strike
        if (isFrameStrike(currentFrameIndex - 1)) {
            //second last frame is strike
            if (isFrameStrike(currentFrameIndex - 2)) {
                if (frame.isStrike()) {
                    if (frame.getFrameNumber() == TEN && frame.getAttempts().size() == 2) {
                        currentScore = getFrameScore(currentFrameIndex - 2) + TEN + (roll * 2);
                        frames.get(currentFrameIndex - 1).setScore(currentScore);
                    } else if (frame.getFrameNumber() == TEN && frame.getAttempts().size() == 3) {
                        currentScore = getFrameScore(currentFrameIndex - 1) + TEN + (roll * 2);
                        frame.setScore(currentScore);
                    } else {
                        currentScore = getFrameScore(currentFrameIndex - 3) + TEN + (roll * 2);
                        frames.get(currentFrameIndex - 2).setScore(currentScore);
                    }
                } else if (frame.isComplete()){
                    frames.get(currentFrameIndex - 1).setScore(getFrameScore(currentFrameIndex - 2) + TEN + frame.sumAttempts());
                    currentScore = getFrameScore(currentFrameIndex - 1) + frame.sumAttempts();
                    frame.setScore(currentScore);
                } else {
                    currentScore = getFrameScore(currentFrameIndex - 3) + (TEN * 2) + roll;
                    frames.get(currentFrameIndex - 2).setScore(currentScore);
                }
            } else {
                if (frame.isComplete() && !frame.isStrike()) {
                    currentScore = getFrameScore(currentFrameIndex - 2) + TEN + frame.sumAttempts();
                    frames.get(currentFrameIndex - 1).setScore(currentScore);
                    if (!frame.isSpare()) {
                        currentScore = getFrameScore(currentFrameIndex - 1) + frame.sumAttempts();
                        frame.setScore(currentScore);
                    }
                } else if (frame.isStrike() && frame.getFrameNumber() == TEN) {
                    if (frame.getAttempts().size() == 2) {
                        currentScore = getFrameScore(currentFrameIndex - 2) + TEN + frame.sumAttempts();
                        frames.get(currentFrameIndex - 1).setScore(currentScore);
                    } else if (frame.getAttempts().size() == 3) {
                        currentScore = getFrameScore(currentFrameIndex - 1) + frame.sumAttempts();
                        frame.setScore(currentScore);
                    }
                }
            }
        } else if (isFrameSpare(currentFrameIndex - 1)){
            if (frame.isStrike()) {
                currentScore = getFrameScore(currentFrameIndex - 2) + (roll * 2);
                frames.get(currentFrameIndex - 1).setScore(currentScore);
            } else if (!frame.isSpare()) {
                if (frame.isComplete()) {
                    currentScore = getFrameScore(currentFrameIndex - 1) + frame.sumAttempts();
                    frame.setScore(currentScore);
                } else{
                    currentScore = getFrameScore(currentFrameIndex - 2) + TEN + roll;
                    frames.get(currentFrameIndex - 1).setScore(currentScore);
                }
            }
        } else if (frame.isComplete()) {
            if (!frame.isSpare() && !frame.isStrike()) {
                currentScore = getFrameScore(currentFrameIndex - 1) + frame.sumAttempts();
                frame.setScore(currentScore);
            } else if (frame.getFrameNumber() == TEN) {
                if (!frame.isStrike()) {
                    currentScore = getFrameScore(currentFrameIndex - 1) + TEN + roll;
                    frame.setScore(currentScore);
                } else {
                    currentScore = getFrameScore(currentFrameIndex - 1) + frame.sumAttempts();
                }
            }
        }
        currentFrameIndex = getNextFrameIndex(frame);
    }

    private int getFrameScore(int frameNumber) {
        return frameNumber >= 0 ? frames.get(frameNumber).getScore() : 0;
    }

    private boolean isFrameStrike(int frameNumber) {
        return frameNumber >= 0 && frames.get(frameNumber).isStrike();
    }

    private boolean isFrameSpare(int frameNumber) {
        return frameNumber >= 0 && frames.get(frameNumber).isSpare();
    }

    private int getNextFrameIndex(Frame frame) {
        if (frame.isComplete()) {
            return frame.getFrameNumber();
        } else {
            return frame.getFrameNumber() - 1;
        }
    }

    String calculateScore(String[] rolls) {
        try {
            GameUtil.validateRolls(rolls);
            createGame();
            for (String rollString : rolls) {
                roll(Integer.parseInt(rollString));
            }
            completeGame();
            return getTotalScore();
        } catch (ValidationException ve) {
            return ve.getMessage();
        }
    }

    private void completeGame() throws ValidationException {
        while (!isGameComplete()) {
            roll(0);
        }
    }
}
