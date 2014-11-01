package com.example.TuttiFruttiCore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Game implements Serializable {
    private String mode;
    private String categoriesType;
    private String opponentsType;
    private Player owner;
    private int randomPlayersCount;
    private ArrayList<Category> selectedCategories;
    private ArrayList<Player> selectedFriends;
    private ArrayList<Player> players;
    private int roundsCount;

    public Game(){}
    public Game(String mode, String categoriesType, String opponentsType, int randomPlayersCount, int roundsCount){
        this.mode=mode;
        this.categoriesType=categoriesType;
        this.opponentsType=opponentsType;
        this.randomPlayersCount=randomPlayersCount;
        this.roundsCount=roundsCount;
    }

    public void setSettings(boolean gameMode, boolean categories, boolean opponents, int roundsCount)
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

        this.roundsCount=roundsCount;

    }
    public String getMode(){return mode;}
    public void setMode(String mode){
        this.mode=mode;
    }

    public String getCategoriesType(){return categoriesType;}
    public void setCategoriesType(String categoriesType){
        this.categoriesType=categoriesType;
    }

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

    public ArrayList<Player> getSelectedFriends() {
        return selectedFriends;
    }

    public void setSelectedFriends(ArrayList<Player> selectedFriends) {
        this.selectedFriends = selectedFriends;
    }

    public void addSelectedFriend(String friendFbId, String friendName)
    {
        if (this.selectedFriends == null)
            setSelectedFriends(new ArrayList<Player>());

        Player playerToAdd = new Player();
        playerToAdd.setFbId(friendFbId);
        playerToAdd.setName(friendName);
        this.selectedFriends.add(playerToAdd);
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public String getSpanishCategoriesType() {
        if (categoriesType.equals("FIXED"))
            return "Controladas";
        else
            return "Libres";
    }

    public String getSpanishOpponentsType() {
        if (opponentsType.equals("FRIENDS"))
            return "Amigos";
        else
            return "Aleatorios";
    }

    public void clearSelectedFriends() {
        if (this.selectedFriends != null)
            this.selectedFriends.clear();
    }

    public int getRoundsCount() {
        return roundsCount;
    }

    public void setRoundsCount(int roundsCount) {
        this.roundsCount = roundsCount;
    }
}
