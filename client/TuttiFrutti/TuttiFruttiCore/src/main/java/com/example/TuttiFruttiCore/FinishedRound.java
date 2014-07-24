package com.example.TuttiFruttiCore;


import java.util.Date;

/**
 * Created by Sebastian on 29/06/2014.
 */
public class FinishedRound {
    private int gameId;
    private int roundId;
    private Date startTime;
    private Play[] plays;

    public FinishedRound(int gameId, int roundId, Date startTime, Play[] plays){
        this.gameId=gameId;
        this.roundId=roundId;
        this.startTime=startTime;
        this.plays=plays;
    }

    public int getGameId() {
        return gameId;
    }

    public int getRoundId() {
        return roundId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Play[] getPlays() {
        return plays;
    }
}
