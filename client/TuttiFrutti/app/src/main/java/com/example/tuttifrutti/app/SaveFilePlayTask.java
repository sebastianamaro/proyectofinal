package com.example.tuttifrutti.app;

import android.os.AsyncTask;

import com.example.TuttiFruttiCore.FilePlay;
import com.example.tuttifrutti.app.Classes.InternalFileHelper;

public class SaveFilePlayTask extends AsyncTask<com.example.tuttifrutti.app.Classes.FilePlay,Void, FilePlay> {

    @Override
    protected FilePlay doInBackground(com.example.tuttifrutti.app.Classes.FilePlay... filePlays) {
        return new InternalFileHelper().saveCategoryValue(filePlays[0]);
    }
}
