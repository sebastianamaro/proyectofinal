package com.example.TuttiFruttiCore;

import java.util.Date;

/**
 * Created by Nituguivi on 07/06/2014.
 */
/*
Representa una jugada de un player en el contexto de una ronda en juego.
Se usa para persistir en el local storage lo que va escribiendo el jugador para enviarlo al servidor al finalizar la ronda
* */
public class FilePlay {
    public int RoundId;
    public Date[] CategoriesTimeStamp;
    public String[] CategoriesValues;
    public Date StartTime;

    public void initialize(int categoriesLength, int roundId) {

        this.CategoriesTimeStamp = new Date[categoriesLength];
        this.CategoriesValues = new String[categoriesLength];
        for (int i = 0; i < categoriesLength; i++) {
            this.CategoriesTimeStamp[i] = null;
            this.CategoriesValues[i] = "";
        }
        this.RoundId = roundId;
        this.StartTime = new Date();
    }
}
