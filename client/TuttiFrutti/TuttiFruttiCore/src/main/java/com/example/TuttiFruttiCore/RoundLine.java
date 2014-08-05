package com.example.TuttiFruttiCore;

import java.util.Date;

/**
 * Created by Sebastian on 21/06/2014.
 */
public class RoundLine {
    private Date startTimestamp;
    private Play[] plays;
    private Player player;
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

    public void setPlayer(String playerRegId) {
        this.player = new Player();
        this.player.setRegistrationId(playerRegId);
    }

    public Player getPlayer() { return this.player; }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

}
