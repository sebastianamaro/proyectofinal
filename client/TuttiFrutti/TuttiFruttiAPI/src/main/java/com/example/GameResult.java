package com.example;

import com.example.TuttiFruttiCore.Player;

import java.util.ArrayList;

/**
 * Created by Nituguivi on 03/08/2014.
 */
public class GameResult {
    private ArrayList<Player> players;
    private ArrayList<RoundResult> roundsResult;
    private ArrayList<PlayerResult> playerResult;

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public ArrayList<RoundResult> getRoundsResult() {
        return roundsResult;
    }

    public void setRoundsResult(ArrayList<RoundResult> roundsResult) {
        this.roundsResult = roundsResult;
    }
    public ArrayList<PlayerResult> getPlayerResult() {
        return playerResult;
    }

    public void setPlayerResult(ArrayList<PlayerResult> playerResult) {
        this.playerResult = playerResult;
    }

}
