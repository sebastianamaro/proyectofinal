package com.example.TuttiFruttiCore;

import java.util.ArrayList;

/**
 * Created by Nituguivi on 20/09/2014.
 */
public class PlayerRoundScoreSummary {
    private boolean isComplete;
    private boolean canPlayerPlay;
    private int roundNumber;
    private String roundLetter;
    private ArrayList<RoundScoreSummary> roundScoreSummaries;

    public boolean getCanPlayerPlay() {
        return canPlayerPlay;
    }

    public void setCanPlayerPlay(boolean canPlayerPlay) {
        this.canPlayerPlay = canPlayerPlay;
    }

    public ArrayList<RoundScoreSummary> getRoundScoreSummaries() {
        return roundScoreSummaries;
    }

    public void setRoundScoreSummaries(ArrayList<RoundScoreSummary> roundScoreSummaries) {
        this.roundScoreSummaries = roundScoreSummaries;
    }

    public boolean getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public String getRoundLetter() {
        return roundLetter;
    }

    public void setRoundLetter(String roundLetter) {
        this.roundLetter = roundLetter;
    }
}
