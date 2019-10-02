package com.mvgreen.opencode_test.game;

import java.io.Serializable;

public class Guess implements Serializable {

    private int[] guessedNumber;
    private int bulls;
    private int cows;
    private float score;

    public int[] getGuessedNumber() {
        return guessedNumber;
    }

    public void setGuessedNumber(int[] guessedNumber) {
        this.guessedNumber = guessedNumber;
    }

    public int getBulls() {
        return bulls;
    }

    public void setBulls(int bulls) {
        this.bulls = bulls;
    }

    public int getCows() {
        return cows;
    }

    public void setCows(int cows) {
        this.cows = cows;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
