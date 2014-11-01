package com.example.TuttiFruttiCore;

/**
 * Created by Sebastian on 21/06/2014.
 */
/*
*
* Clase basica de ronda
* */
public abstract class Round {
    private String status;
    private Integer roundId;
    private Integer gameId;

    public void setGameId(Integer gameId)
    {
        this.gameId=gameId;
    }

    public void setStatus(String status)
    {
        this.status=status;
    }

    public void setRoundId(Integer roundId) {
        this.roundId = roundId;
    }

    public String getStatus()
    {
        return this.status;
    }

    public Integer getRoundId()
    {
        return this.roundId;
    }

    public Integer getGameId()
    {
        return this.gameId;
    }
}
