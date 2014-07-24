package com.example.TuttiFruttiCore;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Nituguivi on 21/07/2014.
 */
public class Line {
    private Player player;
    private ArrayList<Play> plays;
    private int score;
    private Date timestamp;

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public ArrayList<Play> getPlays() {
        return plays;
    }

    public void setPlays(ArrayList<Play> plays) {
        this.plays = plays;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
