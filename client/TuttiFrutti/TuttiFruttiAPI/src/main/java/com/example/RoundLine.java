package com.example;

import java.util.Date;

/**
 * Created by Sebastian on 21/06/2014.
 */
public class RoundLine {
    private Date startTimestamp;
    private Play[] plays;
    private String player;
    private Integer score;

    public Date getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(Date startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public Play[] getPlays() {
        return plays;
    }

    public void setPlays(Play[] plays) {
        this.plays = plays;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

}
