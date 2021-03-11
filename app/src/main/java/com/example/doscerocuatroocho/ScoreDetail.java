package com.example.doscerocuatroocho;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doscerocuatroocho.score.ScoreAdapter;
import com.example.doscerocuatroocho.score.ScoresHelper;

public class ScoreDetail extends AppCompatActivity {
    public static final int NO_ID = -99;
    public static final String NO_DATE = "00/00/00";
    public static final String NO_USER = "-";
    public static final int NO_SCORE = 0;
    String mDate, mUserName;
    int mScore, mId;
    private ScoresHelper mDB;
    private EditText mDateText, mUserNameText, mScoreText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_detail);
        getSupportActionBar().hide();
        mDateText = findViewById(R.id.date_detail);
        mUserNameText = findViewById(R.id.user_detail);
        mScoreText = findViewById(R.id.score_detail);
        mDB = new ScoresHelper(this);
        this.getIntentExtras();

    }

    //------------------------ PRIVATE METHODS -----------------------------------------------------

    /**
     * Get ScoreItem information from Scores.class intent extras.
     */
    private void getIntentExtras() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mId = extras.getInt(ScoreAdapter.EXTRA_ID, NO_ID);
            mDate = extras.getString(ScoreAdapter.EXTRA_DATE, NO_DATE);
            mUserName = extras.getString(ScoreAdapter.EXTRA_USER_NAME, NO_USER);
            mScore = extras.getInt(ScoreAdapter.EXTRA_SCORE, NO_SCORE);
            mDateText.setText(mDate);
            mUserNameText.setText(mUserName);
            mScoreText.setText(String.valueOf(mScore));
        }
    }

    //------------------------ PUBLIC METHODS ------------------------------------------------------

    /**
     * Execute an update to the DataBase with new information of modified fields.
     *
     * @param view "Save Changes" Button.
     */
    public void updateChanges(View view) {
        String date = mDateText.getText().toString();
        String userName = mUserNameText.getText().toString();
        int score = Integer.valueOf(mScoreText.getText().toString());
        if (mDate != date) {
            mDB.updateRecord(String.valueOf(mId), ScoresHelper.DATE, date);
        }
        if (mUserName != userName) {
            mDB.updateRecord(String.valueOf(mId), ScoresHelper.USER_NAME, userName);
        }
        if (mScore != score) {
            mDB.updateRecord(String.valueOf(mId), ScoresHelper.SCORE, String.valueOf(score));
        }
        finish();
        Intent intent = new Intent(ScoreDetail.this, Scores.class);
        startActivity(intent);
    }
}