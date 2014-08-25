package com.example.TuttiFruttiCore;


import java.io.Serializable;

public class Player implements Serializable{
    private String registrationId;
    private String name;
    private String fbId;
    private String email;

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
       if(name == null)
        return registrationId.substring(0,3);

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFbId() {
        return fbId;
    }

    public void setFbId(String fbId) {
        this.fbId = fbId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
