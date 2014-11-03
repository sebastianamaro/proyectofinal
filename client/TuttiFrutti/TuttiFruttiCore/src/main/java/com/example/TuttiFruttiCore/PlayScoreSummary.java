package com.example.TuttiFruttiCore;

/**
 * Created by Sebastian on 04/08/2014.
 */

public class PlayScoreSummary {
    private String result;
    private ScoreInfo scoreInfo;
    private String word;
    private String category;
    private boolean validated;
    private boolean isValid;
    private boolean isFixed;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public ScoreInfo getScoreInfo() {
        return scoreInfo;
    }

    public void setScoreInfo(ScoreInfo score) {
        this.scoreInfo = score;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public boolean isValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }

    public boolean isFixed() {
        return isFixed;
    }

    public void setFixed(boolean isFixed) {
        this.isFixed = isFixed;
    }
}
