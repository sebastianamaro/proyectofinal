package com.example.tuttifrutti.app.Classes;

/**
 * Created by Sebastian on 24/06/2014.
 */
public class GameSettings {
    private Boolean mode;
    private Boolean opponents;
    private Boolean categories;
    private int randomPlayersCount;

    public GameSettings(Boolean mode, Boolean opponents, Boolean categories) {
        this.mode = mode;
        this.opponents = opponents;
        this.categories = categories;
    }

    public Boolean getMode() {
        return mode;
    }

    public Boolean getCategories() {
        return categories;
    }

    public Boolean getOpponents() {
        return opponents;
    }

    public int getRandomPlayersCount() { return randomPlayersCount;}
}
