package com.example.TuttiFruttiCore;

import java.util.ArrayList;

/**
 * Created by Sebastian on 04/08/2014.
 */
public class RoundScoreSummary {
    private Player player;
    private Integer score;
    private Boolean best;
    private ArrayList<SummarizedPlay> plays;


    public ArrayList<SummarizedPlay> getPlays() {
        return plays;
    }

    public void setPlays(ArrayList<SummarizedPlay> plays) {
        this.plays = plays;
    }

    public Boolean getBest() {
        return best;
    }

    public void setBest(Boolean best) {
        this.best = best;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
