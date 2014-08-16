package com.example.TuttiFruttiCore;

import java.util.ArrayList;

/**
 * Created by Nituguivi on 03/08/2014.
 */
public class GameScoreSummary {
    private ArrayList<Player> players;
    private ArrayList<GameRoundScoreSummary> roundsResult;
    private ArrayList<PlayerResult> playerResult;


    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayersName(ArrayList<Player> players) {
        this.players = players;
    }

    public ArrayList<GameRoundScoreSummary> getRoundsResult() {
        return roundsResult;
    }

    public void setRoundsResult(ArrayList<GameRoundScoreSummary> roundsResult) {
        this.roundsResult = roundsResult;
    }
    public ArrayList<PlayerResult> getPlayerResult() {
        return playerResult;
    }

    public void setPlayerResult(ArrayList<PlayerResult> playerResult) {
        this.playerResult = playerResult;
    }
}
