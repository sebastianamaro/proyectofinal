package com.example.TuttiFruttiCore;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Sebastian on 21/06/2014.
 */
public class Game implements Serializable {
    private String mode;
    private String categoriesType;
    private String opponentsType;
    private Player owner;
    private int randomPlayersCount;
    private ArrayList<Category> selectedCategories;
    private ArrayList<String> selectedFriends;

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

    public void setOwner(Player owner){this.owner = owner;}

    public Player getOwner(){return this.owner;}

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
    public ArrayList<Category> getSelectedCategories() {
        return selectedCategories;
    }

    public void setSelectedCategories(ArrayList<Category> selectedCategories) {
        this.selectedCategories = selectedCategories;
    }

    public ArrayList<String> getSelectedFriends() {
        return selectedFriends;
    }

    public void setSelectedFriends(ArrayList<String> selectedFriends) {
        this.selectedFriends = selectedFriends;
    }

    public void addSelectedFriend(String friendFbId)
    {
        if (this.selectedFriends == null)
            setSelectedFriends(new ArrayList<String>());

        this.selectedFriends.add(friendFbId);
    }
}
