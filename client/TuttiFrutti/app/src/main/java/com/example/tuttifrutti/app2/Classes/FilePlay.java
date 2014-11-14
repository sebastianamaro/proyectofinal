package com.example.tuttifrutti.app2.Classes;

/**
 * Created by Sebastian on 26/06/2014.
 * String fileName, int categoryPosition, String categoryValue, int categoriesLength, int roundId
 */
public class FilePlay {
    private String fileName;
    private int categoryPosition;
    private String categoryValue;
    private int categoriesLength;
    private int roundId;

    public FilePlay(String fileName, int categoryPosition, String categoryValue, int categoriesLength, int roundId){
        this.fileName=fileName;
        this.categoryPosition=categoryPosition;
        this.categoryValue=categoryValue;
        this.categoriesLength=categoriesLength;
        this.roundId=roundId;
    }

    public String getFileName() {
        return fileName;
    }

    public int getCategoryPosition() {
        return categoryPosition;
    }

    public String getCategoryValue() {
        return categoryValue;
    }

    public int getCategoriesLength() {
        return categoriesLength;
    }

    public int getRoundId() {
        return roundId;
    }
}
