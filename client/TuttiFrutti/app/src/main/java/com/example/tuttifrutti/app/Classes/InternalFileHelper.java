package com.example.tuttifrutti.app.Classes;

import com.example.TuttiFruttiCore.PlayedRoundResult;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
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
    public PlayedRoundResult saveCategoryValue(FilePlay filePlay) {
        PlayedRoundResult currentPlayedRoundResult = null;

        File file = new File(filePlay.getFileName());
        Boolean writeFile=false;
        Gson gson = new Gson();

        try {
            if (file.exists()) {
                InputStream inputStream = new FileInputStream(file);
                Reader reader = new InputStreamReader(inputStream);
                currentPlayedRoundResult = gson.fromJson(reader, PlayedRoundResult.class);

                if (currentPlayedRoundResult == null) {
                    currentPlayedRoundResult = new PlayedRoundResult();
                    currentPlayedRoundResult.RoundId = filePlay.getRoundId();
                    currentPlayedRoundResult.CategoriesTimeStamp = new Date[filePlay.getCategoriesLength()];
                    currentPlayedRoundResult.CategoriesValues = new String[filePlay.getCategoriesLength()];
                }else if (currentPlayedRoundResult.CategoriesTimeStamp == null)
                {
                    currentPlayedRoundResult.CategoriesTimeStamp = new Date[filePlay.getCategoriesLength()];
                    currentPlayedRoundResult.CategoriesValues = new String[filePlay.getCategoriesLength()];
                }


                // pregunto si lo que ingreso es diferente de lo que yo ya tengo guardado (por si volvio a seleccionar el tab)
                if (currentPlayedRoundResult.CategoriesValues[filePlay.getCategoryPosition()] != filePlay.getCategoryValue()) {
                    currentPlayedRoundResult.CategoriesTimeStamp[filePlay.getCategoryPosition()] = new Date();
                    currentPlayedRoundResult.CategoriesValues[filePlay.getCategoryPosition()] = filePlay.getCategoryValue();
                    writeFile=true;
                }

            } else {
                currentPlayedRoundResult = new PlayedRoundResult();
                currentPlayedRoundResult.RoundId = filePlay.getRoundId();
                currentPlayedRoundResult.StartTime = new Date();
                writeFile=true;
            }

            if(writeFile)
                writeRoundResult(currentPlayedRoundResult,gson,file);



        } catch (Exception e) {
            e.printStackTrace();
        }

        return currentPlayedRoundResult;
    }

    private void writeRoundResult(PlayedRoundResult currentPlayedRoundResult, Gson gson, File file){

        String json = gson.toJson(currentPlayedRoundResult);
        try {
            FileWriter writer = new FileWriter(file);

            writer.write(json);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
