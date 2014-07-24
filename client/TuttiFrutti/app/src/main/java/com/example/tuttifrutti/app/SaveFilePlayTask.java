package com.example.tuttifrutti.app;

import android.os.AsyncTask;

import com.example.TuttiFruttiCore.RoundResult;
import com.example.tuttifrutti.app.Classes.FilePlay;
import com.example.tuttifrutti.app.Classes.InternalFileHelper;

public class SaveFilePlayTask extends AsyncTask<FilePlay,Void, RoundResult> {

    @Override
    protected RoundResult doInBackground(FilePlay... filePlays) {
        return new InternalFileHelper().saveCategoryValue(filePlays[0]);
    }
}
