package com.example.TuttiFruttiCore;

import java.util.ArrayList;

/**
 * Created by Sebastian on 24/08/2014.
 */
public class FullGame extends Game {
    int gameId;

    public FullGame(int game_id) {
        this.gameId = game_id;
    }
    public FullGame() {}

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getGameId() {
        return gameId;
    }
}
