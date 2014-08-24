package com.example.tuttifrutti.app.Classes;

import java.io.Serializable;

/**
 * Created by root on 8/18/14.
 */
public class StopNotificationData implements Serializable{
    private String player;
    private String game_id;
    private String round_id;

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getGame_id() {
        return game_id;
    }

    public void setGame_id(String game_id) {
        this.game_id = game_id;
    }

    public String getRound_id() {
        return round_id;
    }

    public void setRound_id(String round_id) {
        this.round_id = round_id;
    }
}
