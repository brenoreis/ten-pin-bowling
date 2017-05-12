package com.breno.tenpinbowling;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

/**
 * Created by breno.pinto on 12/5/17.
 */
public class TenPinBowlingScoreTest {

    private TenPinBowlingScore tenPinBowlingScore;

    @Before
    public void setUp() throws Exception {
        tenPinBowlingScore = new TenPinBowlingScore();
    }

    @Test
    public void shouldReturnPerfectResultWhenPerfectInput() {
        String[] input = {"10 10 10 10 10 10 10 10 10 10 10 10"};
        String expectedResult = "300";
        String result = tenPinBowlingScore.calculateScore(input);
        Assert.assertThat(result, is(expectedResult));
    }

    @Test
    public void shouldReturnCurrentResultWhenLessThanTenFrames() {
        String[] input = {"1 2 3 4"};
        String expectedResult = "10";
        String result = tenPinBowlingScore.calculateScore(input);
        Assert.assertThat(result, is(expectedResult));

        input = new String[]{"9 1 9 1"};
        expectedResult = "29";
        result = tenPinBowlingScore.calculateScore(input);
        Assert.assertThat(result, is(expectedResult));

        input = new String[]{"1 1 1 1 10 1 1"};
        expectedResult = "18";
        result = tenPinBowlingScore.calculateScore(input);
        Assert.assertThat(result, is(expectedResult));
    }

    @Test
    public void shouldReturnErrorMessageWhenOneFrameHasMoreThanTenPins() {
        String[] input = {"1 2 3 8 1 3"};
        String expectedResult = "Input error.";
        String result = tenPinBowlingScore.calculateScore(input);
        Assert.assertThat(result, is(expectedResult));
    }

    @Test
    public void shouldReturnErrorMessageWhenMaxResultExceeded() {
        String[] input = {"10 10 10 10 10 10 10 10 10 10 10 10 10"};
        String expectedResult = "Input error.";
        String result = tenPinBowlingScore.calculateScore(input);
        Assert.assertThat(result, is(expectedResult));
    }

    @Test
    public void shouldReturnErrorMessageWhenMaxNumberOfFramesExceeded() {
        String[] input = {"1 2 3 4 1 2 3 4 1 2 3 4 1 2 3 4 1 2 3 4 1"};
        String expectedResult = "Input error.";
        String result = tenPinBowlingScore.calculateScore(input);
        Assert.assertThat(result, is(expectedResult));
    }

    @Test
    public void shouldReturnErrorMessageWhenInputHasNotNumberCharacter() {
        String[] input = {"1 2 3 4 1 A"};
        String expectedResult = "Input error.";
        String result = tenPinBowlingScore.calculateScore(input);
        Assert.assertThat(result, is(expectedResult));
    }


}