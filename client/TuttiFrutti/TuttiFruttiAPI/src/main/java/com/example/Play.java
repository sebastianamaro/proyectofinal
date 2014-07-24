package com.example;

import java.util.Date;

/**
 * Created by Sebastian on 21/06/2014.
 */
public class Play {
    private String category;
    private String word;
    private Date timeStamp;

    public int getScore() { return score; }

    public void setScore(int score) { this.score = score; }

    private int score;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }
}
