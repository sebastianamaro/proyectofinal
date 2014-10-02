package com.example.tuttifrutti.app.Classes;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.TuttiFruttiCore.FilePlay;
import com.google.gson.Gson;

import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Date;
import java.util.logging.ConsoleHandler;

/**
 * Created by Nituguivi on 21/06/2014.
 */
public class InternalFileHelper {
    public FilePlay saveCategoryValue(com.example.tuttifrutti.app.Classes.FilePlay filePlay) {
        FilePlay currentFilePlay = null;

        File file = new File(filePlay.getFileName());
        Boolean writeFile=false;
        Gson gson = new Gson();

        try {
            if (file.exists()) {
                InputStream inputStream = new FileInputStream(file);
                Reader reader = new InputStreamReader(inputStream);
                currentFilePlay = gson.fromJson(reader, com.example.TuttiFruttiCore.FilePlay.class);

                if (currentFilePlay == null) {
                    currentFilePlay = new FilePlay();
                    currentFilePlay.RoundId = filePlay.getRoundId();
                    currentFilePlay.CategoriesTimeStamp = new Date[filePlay.getCategoriesLength()];
                    currentFilePlay.CategoriesValues = new String[filePlay.getCategoriesLength()];
                }else if (currentFilePlay.CategoriesTimeStamp == null)
                {
                    currentFilePlay.CategoriesTimeStamp = new Date[filePlay.getCategoriesLength()];
                    currentFilePlay.CategoriesValues = new String[filePlay.getCategoriesLength()];
                }


                // pregunto si lo que ingreso es diferente de lo que yo ya tengo guardado (por si volvio a seleccionar el tab)
                if (currentFilePlay.CategoriesValues[filePlay.getCategoryPosition()] != filePlay.getCategoryValue()) {
                    currentFilePlay.CategoriesTimeStamp[filePlay.getCategoryPosition()] = new Date();
                    currentFilePlay.CategoriesValues[filePlay.getCategoryPosition()] = filePlay.getCategoryValue();
                    writeFile=true;
                }

            } else {
                currentFilePlay = new FilePlay();
                currentFilePlay.RoundId = filePlay.getRoundId();
                currentFilePlay.StartTime = new Date();
                writeFile=true;
            }

            if(writeFile)
                writeRoundResult(currentFilePlay,gson,file);



        } catch (Exception e) {
            e.printStackTrace();
        }

        return currentFilePlay;
    }

    private void writeRoundResult(FilePlay currentFilePlay, Gson gson, File file){

        String json = gson.toJson(currentFilePlay);
        try {
            FileWriter writer = new FileWriter(file);

            writer.write(json);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveToInternalSorage(File mypath, Bitmap bitmapImage){
        FileOutputStream fos = null;
        try {
            if (!mypath.exists()) {
                fos = new FileOutputStream(mypath);

                // Use the compress method on the BitMap object to write image to the OutputStream
                bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //return directory.getAbsolutePath();
    }

    public Bitmap loadImageFromStorage(File f)
    {
        try {
            if (f.exists()) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                FileInputStream is2 = new FileInputStream(f);
                Bitmap b = BitmapFactory.decodeStream(is2);

                //Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
                return b;
            }else
                return null;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            String mess = e.getMessage();
            return null;
        }
    }
}
