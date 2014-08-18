package com.example.TuttiFruttiCore;


public class Player {
    private String registrationId;
    private String name;

    public Player(){}
    public Player(String registrationId) {
        this.registrationId=registrationId;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
