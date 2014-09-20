package com.example.TuttiFruttiCore;

import java.util.ArrayList;

/**
 * Created by Nituguivi on 20/09/2014.
 */
public class PlayerRoundScoreSummary {
    private boolean canPlayerPlay;
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
}
