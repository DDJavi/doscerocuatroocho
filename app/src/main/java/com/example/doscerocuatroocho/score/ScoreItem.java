package com.example.doscerocuatroocho.score;

public class ScoreItem {
    private int mId;
    private int mScore;
    private String mDate;
    private String mUserName;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getScore() {
        return mScore;
    }

    public void setScore(int score) {
        mScore = score;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public ScoreItem() {
    }

    public ScoreItem(int score, String userName) {
        mScore = score;
        mUserName = userName;
    }
}