package com.example.TuttiFruttiCore;

/**
 * Created by Sebastian on 04/08/2014.
 */
public class SummarizedPlay {
    private String result;
    private Integer score;
    private String word;
    private String category;
    private Boolean best;


    public Boolean getBest() {
        return best;
    }

    public void setBest(Boolean best) {
        this.best = best;
    }

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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
