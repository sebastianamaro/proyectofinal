package com.example.TuttiFruttiCore;

/**
 * Created by Sebastian on 18/08/2014.
 */
public class InvitationResponse {
    private String response;
    private Player player;

    public InvitationResponse(String response, Player player){
        this.response=response;
        this.player=player;
    }
    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
