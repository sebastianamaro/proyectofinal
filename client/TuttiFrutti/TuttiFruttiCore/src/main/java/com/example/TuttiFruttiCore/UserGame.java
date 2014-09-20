package com.example.TuttiFruttiCore;

import java.util.ArrayList;

/**
 * Created by Sebastian on 23/07/2014.
 */
public class UserGame {
    private String status;
    private int gameId;
    private String mode;
    private String categoriesType;
    private ArrayList<String> playersName;

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
}