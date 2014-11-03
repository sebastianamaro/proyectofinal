package com.example.TuttiFruttiCore;

public class FullRound extends Round {
    private String letter;
    private String[] categories;
    private String gameStatus;
    private String gameMode;
    private String player;
    private int secondsAgoStarted;

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

    public String getGameMode() { return gameMode; }

    public void setGameMode(String gameMode) { this.gameMode = gameMode; }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public int getSecondsAgoStarted() {
        return secondsAgoStarted;
    }

    public void setSecondsAgoStarted(int secondsAgoStarted) {
        this.secondsAgoStarted = secondsAgoStarted;
    }
}
