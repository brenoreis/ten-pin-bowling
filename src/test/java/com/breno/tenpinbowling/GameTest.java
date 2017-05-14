package com.breno.tenpinbowling;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

/**
 * Created by breno.pinto on 14/5/17.
 */
public class GameTest {

    private static final String FINAL_RESULT = "Final Result: ";

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void shouldReturnPerfectResultWhenPerfectInput() {
        String[] input = "10 10 10 10 10 10 10 10 10 10 10 10".split(" ");
        String expectedResult = FINAL_RESULT + "300";
        String result = getGameResult(input);
        Assert.assertThat(result, is(expectedResult));
    }

    private String getGameResult(String[] input) {
        Game game = new Game();
        return game.calculateScore(input);
    }

    @Test
    public void shouldCompleteGameAndReturnResult() {
        String[] input = "1 2 3 4".split(" ");
        String expectedResult = FINAL_RESULT + "10";
        String result = getGameResult(input);
        Assert.assertThat(result, is(expectedResult));

        input = "9 1 9 1".split(" ");
        expectedResult = FINAL_RESULT + "29";
        result = getGameResult(input);
        Assert.assertThat(result, is(expectedResult));

        input = "1 1 1 1 10 1 1".split(" ");
        expectedResult = FINAL_RESULT + "18";
        result = getGameResult(input);
        Assert.assertThat(result, is(expectedResult));

        input = "1 9 10 10 10 7 2 5 5 5 5 7 2 3 5 4 6 10".split(" ");
        expectedResult = FINAL_RESULT + "174";
        result = getGameResult(input);
        Assert.assertThat(result, is(expectedResult));

        input = "4 5 7 3 2 1 2 4 5 3 8 2 10 8 2 4 5 10 8 2".split(" ");
        expectedResult = FINAL_RESULT + "121";
        result = getGameResult(input);
        Assert.assertThat(result, is(expectedResult));

        input = "3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 10 10 10 10".split(" ");
        expectedResult = FINAL_RESULT + "108";
        result = getGameResult(input);
        Assert.assertThat(result, is(expectedResult));

        input = "3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 10 10".split(" ");
        expectedResult = FINAL_RESULT + "78";
        result = getGameResult(input);
        Assert.assertThat(result, is(expectedResult));
    }

    @Test
    public void shouldReturnErrorMessageWhenOneFrameHasMoreThanTenPins() {
        String[] input = "1 2 3 8 1 3".split(" ");
        String expectedResult = "Input has frame with more than 10 pins in total. Frame number: 2";
        String result = getGameResult(input);
        Assert.assertThat(result, is(expectedResult));
    }

    @Test
    public void shouldReturnErrorMessageWhenMaxNumberOfFramesExceeded() {
        String[] input = "10 10 10 10 10 10 10 10 10 10 10 10 10".split(" ");
        String expectedResult = "Maximum number of frames has been exceeded.";
        String result = getGameResult(input);
        Assert.assertThat(result, is(expectedResult));
    }

    @Test
    public void shouldReturnErrorMessageWhenMaxNumberOfRollsExceeded() {
        String[] input = "1 2 3 4 1 2 3 4 1 2 3 4 1 2 3 4 1 2 3 4 1 2".split(" ");
        String expectedResult = "Input contains more rolls than maximum possible: " + input.length;
        String result = getGameResult(input);
        Assert.assertThat(result, is(expectedResult));
    }

    @Test
    public void shouldReturnErrorMessageWhenInputHasNonNumberCharacter() {
        String[] input = "1 2 3 4 1 A".split(" ");
        String expectedResult = "Input string contains a non integer character. Value: A";
        String result = getGameResult(input);
        Assert.assertThat(result, is(expectedResult));
    }

    @Test
    public void shouldReturnErrorMessageWhenInputRollWithMoreThanTenPins() {
        String[] input = "1 20 3 4 1 ".split(" ");
        String expectedResult = "Input contains an invalid roll. Value: 20";
        String result = getGameResult(input);
        Assert.assertThat(result, is(expectedResult));
    }

    @Test
    public void shouldReturnErrorMessageWhenInputRollWithNegativeNumberOfPins() {
        String[] input = "1 8 3 -4 1 ".split(" ");
        String expectedResult = "Input contains an invalid roll. Value: -4";
        String result = getGameResult(input);
        Assert.assertThat(result, is(expectedResult));
    }

    @Test
    public void shouldReturnErrorMessageWhenTenthFrameHasMoreThanTenPinsInTheFirstTwoRolls() {
        String[] input = "10 10 10 10 10 3 4 1 2 3 4 1 2 3 9 1".split(" ");
        String expectedResult = "Input has the 2 first rolls in 10th frame with more than 10 pins in total.";
        String result = getGameResult(input);
        Assert.assertThat(result, is(expectedResult));
    }

}