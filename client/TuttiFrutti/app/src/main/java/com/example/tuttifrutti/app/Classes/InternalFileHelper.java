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
    public void startRound(String fileName, int roundId)
    {
        File file = new File(fileName);
        Gson gson = new Gson();

        RoundResult currentRoundResult = new RoundResult();
        currentRoundResult.RoundId = roundId;
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
    public RoundResult saveCategoryValue(String fileName, int categoryPosition, String categoryValue, int categoriesLength, int roundId) {
        RoundResult currentRoundResult = null;

        File file = new File(fileName);

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
                    currentRoundResult.RoundId = roundId;
                    currentRoundResult.CategoriesTimeStamp = new Date[categoriesLength];
                    currentRoundResult.CategoriesValues = new String[categoriesLength];
                }else if (currentRoundResult.CategoriesTimeStamp == null)
                {
                    currentRoundResult.CategoriesTimeStamp = new Date[categoriesLength];
                    currentRoundResult.CategoriesValues = new String[categoriesLength];
                }

            } else {
                currentRoundResult = new RoundResult();
                currentRoundResult.RoundId = roundId;
                currentRoundResult.CategoriesTimeStamp = new Date[categoriesLength];
                currentRoundResult.CategoriesValues = new String[categoriesLength];
            }

            // pregunto si lo que ingreso es diferente de lo que yo ya tengo guardado (por si volvio a seleccionar el tab)
            if (currentRoundResult.CategoriesValues[categoryPosition] != categoryValue) {
                currentRoundResult.CategoriesTimeStamp[categoryPosition] = new Date();
                currentRoundResult.CategoriesValues[categoryPosition] = categoryValue;

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
