package com.example.TuttiFruttiCore;

import java.util.ArrayList;
import java.io.Serializable;

/**
 * Created by Sebastian on 23/07/2014.
 */
public class UserGame implements Serializable {
    private String status;
    private int gameId;
    private String mode;
    private String categoriesType;
    private ArrayList<String> playersName;
    private boolean playerHasPlayedCurrentRound;
    private int statusCode;
    private boolean isFirstRound;

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

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getCategoriesType() {
        return categoriesType;
    }

    public void setCategoriesType(String categoryType) {
        this.categoriesType = categoryType;
    }

    public ArrayList<String> getPlayersName() {
        return playersName;
    }

    public void setPlayersName(ArrayList<String> playersName) {
        this.playersName = playersName;
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