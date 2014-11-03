package com.example.TuttiFruttiCore;

import java.util.ArrayList;

/**
 * Created by Nituguivi on 03/08/2014.
 */
public class GameScoreSummary {
    private ArrayList<String> playersName;
    private ArrayList<GameRoundScoreSummary> roundsResult;
    private ArrayList<ScoreInfo> playerResult;

    public ArrayList<String> getPlayers() {
        return playersName;
    }

    public void setPlayersName(ArrayList<String> players) {
        this.playersName = players;
    }

    public ArrayList<GameRoundScoreSummary> getRoundsResult() {
        return roundsResult;
    }

    public void setRoundsResult(ArrayList<GameRoundScoreSummary> roundsResult) {
        this.roundsResult = roundsResult;
    }
    public ArrayList<ScoreInfo> getPlayerResult() {
        return playerResult;
    }

    public void setPlayerResult(ArrayList<ScoreInfo> playerResult) {
        this.playerResult = playerResult;
    }
}
