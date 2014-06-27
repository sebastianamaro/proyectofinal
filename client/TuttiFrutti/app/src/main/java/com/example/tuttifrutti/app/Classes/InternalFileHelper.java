package com.example.tuttifrutti.app.Classes;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Date;

/**
 * Created by Nituguivi on 21/06/2014.
 */
public class InternalFileHelper {
    public void startRound(FilePlay filePlay)
    {
        File file = new File(filePlay.getFileName());
        Gson gson = new Gson();

        RoundResult currentRoundResult = new RoundResult();
        currentRoundResult.RoundId = filePlay.getRoundId();
        currentRoundResult.StartTime = new Date();

        String json = gson.toJson(currentRoundResult);
        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public RoundResult saveCategoryValue(FilePlay filePlay) {
        RoundResult currentRoundResult = null;

        File file = new File(filePlay.getFileName());

        Gson gson = new Gson();

        try {
            //deberia existir siempre, pero por las dudas...
            if (file.exists()) {
                InputStream inputStream = new FileInputStream(file);
                Reader reader = new InputStreamReader(inputStream);
                currentRoundResult = gson.fromJson(reader, RoundResult.class);
                //deberia existir siempre, pero por las dudas...
                if (currentRoundResult == null) {
                    currentRoundResult = new RoundResult();
                    currentRoundResult.RoundId = filePlay.getRoundId();
                    currentRoundResult.CategoriesTimeStamp = new Date[filePlay.getCategoriesLength()];
                    currentRoundResult.CategoriesValues = new String[filePlay.getCategoriesLength()];
                }else if (currentRoundResult.CategoriesTimeStamp == null)
                {
                    currentRoundResult.CategoriesTimeStamp = new Date[filePlay.getCategoriesLength()];
                    currentRoundResult.CategoriesValues = new String[filePlay.getCategoriesLength()];
                }

            } else {
                currentRoundResult = new RoundResult();
                currentRoundResult.RoundId = filePlay.getRoundId();
                currentRoundResult.CategoriesTimeStamp = new Date[filePlay.getCategoriesLength()];
                currentRoundResult.CategoriesValues = new String[filePlay.getCategoriesLength()];
            }

            // pregunto si lo que ingreso es diferente de lo que yo ya tengo guardado (por si volvio a seleccionar el tab)
            if (currentRoundResult.CategoriesValues[filePlay.getCategoriesLength()] != filePlay.getCategoryValue()) {
                currentRoundResult.CategoriesTimeStamp[filePlay.getCategoriesLength()] = new Date();
                currentRoundResult.CategoriesValues[filePlay.getCategoriesLength()] = filePlay.getCategoryValue();

                String json = gson.toJson(currentRoundResult);
                FileWriter writer = new FileWriter(file);
                writer.write(json);
                writer.close();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return currentRoundResult;
    }


}
