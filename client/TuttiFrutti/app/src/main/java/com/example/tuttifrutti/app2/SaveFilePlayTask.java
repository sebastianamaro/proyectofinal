package com.example.tuttifrutti.app2;

import android.os.AsyncTask;

import com.example.TuttiFruttiCore.FilePlay;
import com.example.tuttifrutti.app2.Classes.InternalFileHelper;

public class SaveFilePlayTask extends AsyncTask<com.example.tuttifrutti.app2.Classes.FilePlay,Void, FilePlay> {

    @Override
    protected FilePlay doInBackground(com.example.tuttifrutti.app2.Classes.FilePlay... filePlays) {
        return new InternalFileHelper().saveCategoryValue(filePlays[0]);
    }
}
