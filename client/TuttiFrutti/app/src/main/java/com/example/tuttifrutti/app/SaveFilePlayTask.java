package com.example.tuttifrutti.app;

import android.os.AsyncTask;

import com.example.TuttiFruttiCore.PlayedRoundResult;
import com.example.tuttifrutti.app.Classes.FilePlay;
import com.example.tuttifrutti.app.Classes.InternalFileHelper;

public class SaveFilePlayTask extends AsyncTask<FilePlay,Void, PlayedRoundResult> {

    @Override
    protected PlayedRoundResult doInBackground(FilePlay... filePlays) {
        return new InternalFileHelper().saveCategoryValue(filePlays[0]);
    }
}
