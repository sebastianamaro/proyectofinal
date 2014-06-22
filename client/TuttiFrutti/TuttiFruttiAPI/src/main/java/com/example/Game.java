package com.example;

/**
 * Created by Sebastian on 21/06/2014.
 */
public class Game {
    private String mode;
    private String categoriesType;
    private String opponentsType;

    public String getMode(){return mode;}
    public void setMode(String mode){this.mode=mode;}

    public String getCategoriesType(){return categoriesType;}
    public void setCategoriesType(String categoriesType){this.categoriesType=categoriesType;}


    public String getOpponentsType() {
        return opponentsType;
    }

    public void setOpponentsType(String opponentsType) {
        this.opponentsType = opponentsType;
    }
}
