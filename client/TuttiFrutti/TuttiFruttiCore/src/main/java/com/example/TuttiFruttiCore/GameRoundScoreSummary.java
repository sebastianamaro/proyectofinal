package com.example.TuttiFruttiCore;

import java.util.ArrayList;

/**
 * Created by Nituguivi on 03/08/2014.
 */
public class GameRoundScoreSummary{
    private ArrayList<ScoreInfo> scores;
    private int roundId;


    public int getRoundId() {
        return roundId;
    }

    public void setRoundId(int roundId) {
        this.roundId = roundId;
    }

    public ArrayList<ScoreInfo> getScores() {
        return scores;
    }

    public void setScores(ArrayList<ScoreInfo> scores) {
        this.scores = scores;
    }
}
