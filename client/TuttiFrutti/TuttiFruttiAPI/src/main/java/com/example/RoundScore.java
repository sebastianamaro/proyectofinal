package com.example;

/**
 * Created by Nituguivi on 03/08/2014.
 */
public class RoundScore {
    private int score;
    private boolean best;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean getBest() {
        return best;
    }

    public void setBest(boolean isTheBiggest) {
        this.best = isTheBiggest;
    }
}
