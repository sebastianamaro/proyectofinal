package com.example.TuttiFruttiCore;

import java.util.ArrayList;
import java.io.Serializable;

/**
 * Created by Sebastian on 23/07/2014.
 */
public class UserGame extends FullGame implements Serializable {

    private String status;
    private boolean playerHasPlayedCurrentRound;
    private int statusCode;
    private boolean isFirstRound;


    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public boolean getPlayerHasPlayedCurrentRound() {
        return playerHasPlayedCurrentRound;
    }

    public void setPlayerHasPlayedCurrentRound(boolean playerHasPlayedCurrentRound) {
        this.playerHasPlayedCurrentRound = playerHasPlayedCurrentRound;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int currentRoundId) {
        this.statusCode = currentRoundId;
    }

    public boolean getIsFirstRound() {
        return isFirstRound;
    }

    public void setIsFirstRound(boolean isFirstRound) {
        this.isFirstRound = isFirstRound;
    }

}