package com.example.TuttiFruttiCore;

/**
 *
 * var fullRoundSchema = new Schema({
 letter:   { type: String },
 status:   { type: String },
 roundId:   { type: Number },
 gameId:   { type: Number },
 categories: [ {type: String} ],
 gameStatus:   { type: String }
 });

 */


public class FullRound extends Round {


    private String letter;
    private String[] categories;
    private String gameStatus;

    public void setLetter(String letter)
    {
        this.letter=letter;
    }

    public void setCategories(String[] categories)
    {
        this.categories=categories;
    }

    public void setGameStatus(String gameStatus)
    {
        this.gameStatus=gameStatus;
    }

    public String getLetter()
    {
        return this.letter;
    }

    public String[] getCategories()
    {
        return this.categories;
    }

    public String getGameStatus()
    {
        return this.gameStatus;
    }

}
