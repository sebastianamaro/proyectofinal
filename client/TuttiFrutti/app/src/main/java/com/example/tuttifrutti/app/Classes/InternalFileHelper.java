package com.example.tuttifrutti.app.Classes;

import com.example.TuttiFruttiCore.FilePlayRoundResult;
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
    public FilePlayRoundResult saveCategoryValue(FilePlay filePlay) {
        FilePlayRoundResult currentFilePlayRoundResult = null;

        File file = new File(filePlay.getFileName());
        Boolean writeFile=false;
        Gson gson = new Gson();

        try {
            if (file.exists()) {
                InputStream inputStream = new FileInputStream(file);
                Reader reader = new InputStreamReader(inputStream);
                currentFilePlayRoundResult = gson.fromJson(reader, FilePlayRoundResult.class);

                if (currentFilePlayRoundResult == null) {
                    currentFilePlayRoundResult = new FilePlayRoundResult();
                    currentFilePlayRoundResult.RoundId = filePlay.getRoundId();
                    currentFilePlayRoundResult.CategoriesTimeStamp = new Date[filePlay.getCategoriesLength()];
                    currentFilePlayRoundResult.CategoriesValues = new String[filePlay.getCategoriesLength()];
                }else if (currentFilePlayRoundResult.CategoriesTimeStamp == null)
                {
                    currentFilePlayRoundResult.CategoriesTimeStamp = new Date[filePlay.getCategoriesLength()];
                    currentFilePlayRoundResult.CategoriesValues = new String[filePlay.getCategoriesLength()];
                }


                // pregunto si lo que ingreso es diferente de lo que yo ya tengo guardado (por si volvio a seleccionar el tab)
                if (currentFilePlayRoundResult.CategoriesValues[filePlay.getCategoryPosition()] != filePlay.getCategoryValue()) {
                    currentFilePlayRoundResult.CategoriesTimeStamp[filePlay.getCategoryPosition()] = new Date();
                    currentFilePlayRoundResult.CategoriesValues[filePlay.getCategoryPosition()] = filePlay.getCategoryValue();
                    writeFile=true;
                }

            } else {
                currentFilePlayRoundResult = new FilePlayRoundResult();
                currentFilePlayRoundResult.RoundId = filePlay.getRoundId();
                currentFilePlayRoundResult.StartTime = new Date();
                writeFile=true;
            }

            if(writeFile)
                writeRoundResult(currentFilePlayRoundResult,gson,file);



        } catch (Exception e) {
            e.printStackTrace();
        }

        return currentFilePlayRoundResult;
    }

    private void writeRoundResult(FilePlayRoundResult currentFilePlayRoundResult, Gson gson, File file){

        String json = gson.toJson(currentFilePlayRoundResult);
        try {
            FileWriter writer = new FileWriter(file);

            writer.write(json);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
