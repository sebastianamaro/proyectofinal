package com.example.TuttiFruttiCore;

/**
 * Created by Sebastian on 23/07/2014.
 */
public class UserGame {
    private String status;
    private int gameId;

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public int getGameId() {
        return gameId;
    }
}