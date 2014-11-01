package com.example.tuttifrutti.app.Classes;

import java.io.Serializable;

/**
 * Created by root on 8/18/14.
 */
public class GameAndPlayerNotificationData extends GameNotificationData implements Serializable{
    private String player;

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }
}
