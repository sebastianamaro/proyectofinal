package com.example.TuttiFruttiCore;

import java.util.ArrayList;
import java.io.Serializable;

/**
 * Created by Sebastian on 23/07/2014.
 */
public class UserGame extends FullGame implements Serializable {

    private String status;
    private boolean playerHasPlayedCurrentRound;
    private int statusCode;
    private boolean isFirstRound;


    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public boolean getPlayerHasPlayedCurrentRound() {
        return playerHasPlayedCurrentRound;
    }

    public void setPlayerHasPlayedCurrentRound(boolean playerHasPlayedCurrentRound) {
        this.playerHasPlayedCurrentRound = playerHasPlayedCurrentRound;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int currentRoundId) {
        this.statusCode = currentRoundId;
    }

    public boolean getIsFirstRound() {
        return isFirstRound;
    }

    public void setIsFirstRound(boolean isFirstRound) {
        this.isFirstRound = isFirstRound;
    }

    public boolean isFinishedOrRejected() {
        return this.isFinished() || this.allPlayersRejected();
    }

    public boolean isFinished()
    {
        return this.getStatus().equals("FINISHED");
    }

    public boolean allPlayersRejected()
    {
        return this.getStatus().equals("ALLPLAYERSREJECTED");
    }

    public boolean SelectedFriendIsPlayer(String name) {
        for (Player p : this.getPlayers())
        {
            if (p.getName().equals(name))
                return true;
        }

        return false;
    }
}