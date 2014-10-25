package com.example.tuttifrutti.app.Classes;

import java.io.Serializable;

/**
 * Created by root on 8/18/14.
 */
public class GameNotificationData implements Serializable{

    private int game_id;

    public int getGame_id() {
        return game_id;
    }

    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }
}

