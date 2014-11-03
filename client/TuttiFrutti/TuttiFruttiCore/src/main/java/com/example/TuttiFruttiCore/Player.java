package com.example.TuttiFruttiCore;


import java.io.Serializable;

public class Player implements Serializable{
    private String registrationId;
    private String name;
    private String fbId;
    private String email;

    public Player(){}
    public Player(String userId) {
        this.setFbId(userId);
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public String getName() {
       if(name == null)
        return "NO TIENE NOMBRE, FIJATE";

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

    @Override
    public boolean equals(Object object)
    {
        boolean sameSame = false;

        if (object != null && object instanceof Player)
        {
            sameSame = this.fbId.equals(((Player) object).fbId);
        }

        return sameSame;
    }

    @Override
    public int hashCode() {
        return Integer.valueOf(this.fbId);
    }
}
