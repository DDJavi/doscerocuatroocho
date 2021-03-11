package com.example.doscerocuatroocho.score;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ScoresHelper extends SQLiteOpenHelper {
    private static final String TAG = ScoresHelper.class.getSimpleName();
    private SQLiteDatabase mWritable;
    private SQLiteDatabase mReadable;
    public static final String DATE = "date";
    public static final String USER_NAME = "user";
    public static final String SCORE = "score";
    public static final String GREATER_THAN = ">";
    public static final String LESS_THAN = "<";
    public static final String EQUAL_TO = "LIKE";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "scores_entries";
    private static final String SCORE_TABLE = "scores";
    private static final String KEY_ID = "id";
    private static final String SCORE_TABLE_CREATE = "CREATE TABLE " + SCORE_TABLE + " (" +
            KEY_ID + " INTEGER PRIMARY KEY, " +
            DATE + " TEXT, " +
            USER_NAME + " TEXT, " +
            SCORE + " INTEGER);";

    public ScoresHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //------------------------ PRIVATE METHODS -----------------------------------------------------

    /**
     * Get current date and time to be stored with score.
     * @return String with a particular date-time pattern.
     */
    private String getCurrentDateTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        return simpleDateFormat.format(new Date());
    }

    //------------------------ PUBLIC METHODS ------------------------------------------------------

    /**
     * Executes a delete statement by Id.
     * @param item Current ScoreItem.
     * @return number of deleted rows.
     */
    public int deleteById(ScoreItem item) {
        int deleted = 0;
        try {
            if (mWritable == null) {
                mWritable = getWritableDatabase();
            }
            deleted = mWritable.delete(SCORE_TABLE, KEY_ID + " = ? ",
                    new String[]{String.valueOf(item.getId())});
        } catch (Exception e) {
            Log.d(TAG, "EXCEPTION IN deleteById METHOD! " + e.getMessage());
        }
        return deleted;
    }

    /**
     * Retrieves all records in DataBase.
     * @return ScoreItem ArrayList.
     */
    public ArrayList<ScoreItem> getAllEntries() {
        ArrayList<ScoreItem> scoreList = new ArrayList<>();
        String query = "SELECT * FROM " + SCORE_TABLE + ";";
        Cursor cursor = null;
        try {
            if (mReadable == null) {
                mReadable = getReadableDatabase();
            }
            cursor = mReadable.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    ScoreItem item = new ScoreItem();
                    item.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                    item.setUserName(cursor.getString(cursor.getColumnIndex(USER_NAME)));
                    item.setDate(cursor.getString(cursor.getColumnIndex(DATE)));
                    item.setScore(cursor.getInt(cursor.getColumnIndex(SCORE)));
                    scoreList.add(item);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "EXCEPTION IN getAllEntries METHOD!" + e.getMessage());
        } finally {
            cursor.close();
            return scoreList;
        }
    }

    /**
     * Retrieves records ordered by user name or score.
     * @param column Database's column name.
     * @param operator It could be ascendant or descendant.
     * @return ScoreItem ArrayList.
     */
    public ArrayList<ScoreItem> getOrderByEntries(String column, String operator) {
        ArrayList<ScoreItem> scoreList = new ArrayList<>();
        String query = "SELECT * FROM " + SCORE_TABLE + " ORDER BY " + column + " " + operator
                + ";";
        Cursor cursor = null;
        try {
            if (mReadable == null) {
                mReadable = getReadableDatabase();
            }
            cursor = mReadable.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    ScoreItem item = new ScoreItem();
                    item.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                    item.setUserName(cursor.getString(cursor.getColumnIndex(USER_NAME)));
                    item.setDate(cursor.getString(cursor.getColumnIndex(DATE)));
                    item.setScore(cursor.getInt(cursor.getColumnIndex(SCORE)));
                    scoreList.add(item);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "EXCEPTION IN getAllEntries METHOD!" + e.getMessage());
        } finally {
            cursor.close();
            return scoreList;
        }
    }

    /**
     * Retrieves specific records that match with given pattern.
     * @param column DataBase's name column.
     * @param value Filter parameter.
     * @param operator It could be >,< and =.
     * @return ScoreItem ArrayList.
     */
    public ArrayList<ScoreItem> getSpecificEntries(String column, String value, String operator) {
        ArrayList<ScoreItem> scoreList = new ArrayList<>();
        String query = "SELECT * FROM " + SCORE_TABLE + " WHERE " + column + " " + operator
                + " " + value + ";";
        Cursor cursor = null;
        try {
            if (mReadable == null) {
                mReadable = getReadableDatabase();
            }
            cursor = mReadable.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    ScoreItem item = new ScoreItem();
                    item.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                    item.setUserName(cursor.getString(cursor.getColumnIndex(USER_NAME)));
                    item.setDate(cursor.getString(cursor.getColumnIndex(DATE)));
                    item.setScore(cursor.getInt(cursor.getColumnIndex(SCORE)));
                    scoreList.add(item);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "EXCEPTION IN getAllEntries METHOD!" + e.getMessage());
        } finally {
            cursor.close();
            return scoreList;
        }
    }

    /**
     * Executes an insert statement.
     * @param item Current ScoreItem.
     * @return the row ID of the newly inserted row, or -1 if an error occurred.
     */
    public long insert(ScoreItem item) {
        long newId = 0;
        ContentValues values = new ContentValues();
        values.put(DATE, this.getCurrentDateTime());
        values.put(USER_NAME, item.getUserName());
        values.put(SCORE, String.valueOf(item.getScore()));
        try {
            if (mWritable == null) {
                mWritable = getWritableDatabase();
            }
            newId = mWritable.insert(SCORE_TABLE, null, values);
        } catch (Exception e) {
            Log.d(TAG, "INSERT EXCEPTION!" + e.getMessage());
        }
        return newId;
    }

    /**
     * Retrieves max score stored in Database.
     * @return maximum points.
     */
    public int selectMaxScore() {
        String query = "SELECT MAX(" + SCORE + ") FROM " + SCORE_TABLE + ";";
        Cursor cursor = null;
        int maxScore = 0;
        try {
            if (mReadable == null) {
                mReadable = getReadableDatabase();
            }
            cursor = mReadable.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    maxScore = cursor.getInt(0);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "EXCEPTION IN selectMaxScore METHOD! " + e.getMessage());
        } finally {
            cursor.close();
            return maxScore;
        }
    }

    /**
     * Executes an update statement by Id.
     * @param id Record Id.
     * @param column DataBase's column name.
     * @param value New value to be updated in a particular record.
     */
    public void updateRecord(String id, String column, String value) {
        String keyWord = "";
        if (mWritable == null) {
            mWritable = getWritableDatabase();
        }
        ContentValues values = new ContentValues();
        switch (column) {
            case DATE:
                keyWord = DATE;
                break;
            case USER_NAME:
                keyWord = USER_NAME;
                break;
            case SCORE:
                keyWord = SCORE;
                break;
        }
        values.put(keyWord, value);
        try {
            mWritable.update(SCORE_TABLE, values, KEY_ID + " = ?", new String[]{id});
        } catch (Exception e) {
            Log.d(TAG, "EXCEPTION IN updateRecord METHOD! " + e.getMessage());
        }
    }

    //------------------------ OVERWRITTEN METHODS -------------------------------------------------

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SCORE_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
