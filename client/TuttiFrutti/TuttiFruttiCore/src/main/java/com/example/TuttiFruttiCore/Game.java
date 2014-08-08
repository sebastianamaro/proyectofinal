package com.example.TuttiFruttiCore;

/**
 * Created by Sebastian on 21/06/2014.
 */
public class Game {
    private String mode;
    private String categoriesType;
    private String opponentsType;
    private String owner;
    private int randomPlayersCount;

    public Game(){}
    public Game(String mode, String categoriesType, String opponentsType, int randomPlayersCount){
        this.mode=mode;
        this.categoriesType=categoriesType;
        this.opponentsType=opponentsType;
        this.randomPlayersCount=randomPlayersCount;
    }

    public void setSettings(boolean gameMode, boolean categories, boolean opponents)
    {
        if(gameMode)
            this.setMode("ONLINE");
        else
            this.setMode("OFFLINE");

        if(opponents)
            this.setOpponentsType("RANDOM");
        else
            this.setOpponentsType("FRIENDS");

        if(categories)
            this.setCategoriesType("FIXED");
        else
            this.setCategoriesType("FREE");

    }
    public String getMode(){return mode;}
    public void setMode(String mode){this.mode=mode;}

    public String getCategoriesType(){return categoriesType;}
    public void setCategoriesType(String categoriesType){this.categoriesType=categoriesType;}

    public void setOwner(String owner){this.owner = owner;}

    public String getOwner(){return this.owner;}

    public String getOpponentsType() {
        return opponentsType;
    }

    public void setOpponentsType(String opponentsType) {
        this.opponentsType = opponentsType;
    }

    public void setRandomPlayersCount(int randomPlayersCount) {
        this.randomPlayersCount = randomPlayersCount;
    }

    public int getRandomPlayersCount() { return randomPlayersCount;}
}
