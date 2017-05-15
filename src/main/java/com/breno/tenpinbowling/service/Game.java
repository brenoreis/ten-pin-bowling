package com.breno.tenpinbowling.service;

import com.breno.tenpinbowling.exception.ValidationException;
import com.breno.tenpinbowling.util.GameUtil;

import java.util.ArrayList;
import java.util.List;

import static com.breno.tenpinbowling.util.Constants.NUMBER_OF_FRAMES;
import static com.breno.tenpinbowling.util.Constants.SPARE;
import static com.breno.tenpinbowling.util.Constants.STRIKE;

/**
 * Created by breno.pinto on 13/5/17.
 */
public class Game {

    private int currentFrameIndex = 0;
    private List<Frame> frames = new ArrayList<>();
    private int currentScore = 0;

    private void createGame() {
        for (int i = 1; i <= NUMBER_OF_FRAMES; i++) {
            Frame frame = new Frame(i);
            frames.add(frame);
        }
    }

    private boolean isGameComplete() {
        return (frames.get(NUMBER_OF_FRAMES - 1).isComplete());
    }

    private String getTotalScore() {
        return "Final Result: " + currentScore;
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

    private void completeGame() throws ValidationException {
        while (!isGameComplete()) {
            roll(0);
        }
    }

    public String calculateScore(String[] rolls) {
        String result;
        try {
            GameUtil.validateRolls(rolls);
            createGame();
            for (String rollString : rolls) {
                roll(Integer.parseInt(rollString));
            }
            completeGame();
            result = getTotalScore();
        } catch (ValidationException ve) {
            //would log in a real app
            result = ve.getMessage();
        } catch (Exception e) {
            //would log in a real app
            result = "UNEXPECTED ERROR OCCURRED. " + e.getMessage();
        }
        return result;
    }

    private void roll(int pins) throws ValidationException {

        validateCurrentFrame();
        Frame frame = frames.get(currentFrameIndex);
        frame.addRoll(pins);

        switch (frame.getAttempts().size()) {
            case 1:
                //if last frame was spare
                if (isFrameSpare(currentFrameIndex - 1)) {
                    currentScore = getFrameScore(currentFrameIndex - 2) + SPARE + pins;
                    frames.get(currentFrameIndex - 1).setScore(currentScore);
                } else if (isFrameStrike(currentFrameIndex - 1) && isFrameStrike(currentFrameIndex - 2)) { //if last and second last frames were strike
                    currentScore = getFrameScore(currentFrameIndex - 3) + (STRIKE * 2) + pins;
                    frames.get(currentFrameIndex - 2).setScore(currentScore);
                }
                break;
            case 2:
                //if frame complete
                if (frame.isComplete()) {
                    //if last frame was strike
                    if (isFrameStrike(currentFrameIndex - 1)) {
                        currentScore = getFrameScore(currentFrameIndex - 2) + STRIKE + frame.sumAttempts();
                        frames.get(currentFrameIndex - 1).setScore(currentScore);
                        if (!frame.isSpare()) {
                            currentScore = getFrameScore(currentFrameIndex - 1) + frame.sumAttempts();
                            frame.setScore(currentScore);
                        }
                    } else if (!frame.isSpare()) {  // if not spare, sum pins
                        currentScore = getFrameScore(currentFrameIndex - 1) + frame.sumAttempts();
                        frame.setScore(currentScore);
                    }
                } else if (isFrameStrike(currentFrameIndex - 1)) {
                        currentScore = getFrameScore(currentFrameIndex - 2) + STRIKE + frame.sumAttempts();
                        frames.get(currentFrameIndex - 1).setScore(currentScore);
                }
                break;
            default: //if 10th frame has bonus rolls
                currentScore = getFrameScore(currentFrameIndex - 1) + frame.sumAttempts();
                frame.setScore(currentScore);
        }

        currentFrameIndex = getNextFrameIndex(frame);
    }

    private void validateCurrentFrame() throws ValidationException {
        if (currentFrameIndex >= NUMBER_OF_FRAMES) {
            throw new ValidationException("Maximum number of frames has been exceeded.");
        }
    }
}
