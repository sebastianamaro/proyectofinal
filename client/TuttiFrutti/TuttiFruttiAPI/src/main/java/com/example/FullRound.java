package com.example;

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


public class FullRound {
    private String letter;
    private String status;
    private Integer roundId;
    private Integer gameId;
    private String[] categories;
    private String gameStatus;

    public void setLetter(String letter)
    {
        this.letter=letter;
    }

    public void setStatus(String status)
    {
        this.status=status;
    }

    public void setRoundId(Integer roundId)
    {
        this.roundId=roundId;
    }

    public void setGameId(Integer gameId)
    {
        this.gameId=gameId;
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

    public String[] getCategories()
    {
        return this.categories;
    }

    public String getGameStatus()
    {
        return this.gameStatus;
    }

}
