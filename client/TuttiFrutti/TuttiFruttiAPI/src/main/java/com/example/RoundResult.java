package com.example;

import java.util.ArrayList;

/**
 * Created by Nituguivi on 03/08/2014.
 */
public class RoundResult {
    private ArrayList<RoundScore> scores;
    private int roundId;


    public int getRoundId() {
        return roundId;
    }

    public void setRoundId(int roundId) {
        this.roundId = roundId;
    }

    public ArrayList<RoundScore> getScores() {
        return scores;
    }

    public void setScores(ArrayList<RoundScore> scores) {
        this.scores = scores;
    }
}
