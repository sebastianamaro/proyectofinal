package com.example.TuttiFruttiCore;

import java.util.ArrayList;

/**
 * Created by Sebastian on 04/08/2014.
 */
public class RoundScoreSummary {
    private Player player;
    private ScoreInfo scoreInfo;
    private ArrayList<PlayScoreSummary> plays;

    public ArrayList<PlayScoreSummary> getPlays() {
        return plays;
    }

    public void setPlays(ArrayList<PlayScoreSummary> plays) {
        this.plays = plays;
    }

    public ScoreInfo getScoreInfo() {
        return scoreInfo;
    }

    public void setScoreInfo(ScoreInfo scoreInfo) {
        this.scoreInfo = scoreInfo;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
