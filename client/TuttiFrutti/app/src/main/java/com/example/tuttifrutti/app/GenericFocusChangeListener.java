package com.example.tuttifrutti.app;

import android.view.View;
import android.widget.EditText;

import com.example.tuttifrutti.app.Classes.FilePlay;

public class GenericFocusChangeListener implements View.OnFocusChangeListener {

    private final int Category;
    private final int TotalCategories;
    private final int RoundId;
    private final String fileName;

    public GenericFocusChangeListener(String fileName,int category, int totalCategories, int roundId) {
        Category = category;
        TotalCategories = totalCategories;
        RoundId = roundId;
        this.fileName=fileName;

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            String enteredString = ((EditText) v).getText().toString();
            
            new SaveFilePlayTask().execute(new FilePlay(fileName, this.Category, enteredString, TotalCategories, RoundId));
        }

    }


}
