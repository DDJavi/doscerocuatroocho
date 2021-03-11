package com.example.doscerocuatroocho;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doscerocuatroocho.R;
import com.example.doscerocuatroocho.score.ScoreAdapter;
import com.example.doscerocuatroocho.score.ScoreItem;
import com.example.doscerocuatroocho.score.ScoresHelper;

import java.util.ArrayList;

public class Scores extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private RecyclerView mRecyclerView;
    private ScoreAdapter mAdapter;
    private ScoresHelper mDB;
    private Spinner mFilterSpinner, mOperatorSpinner;
    private int mFilterOption, mOperatorOption;
    private EditText mFilterText;
    private ArrayList<ScoreItem> mScoreList;
    private ImageButton mSearch, mUserButton, mScoreButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        getSupportActionBar().hide();
        this.findViewsById();
        this.setSpinnerAdapters();
        mDB = new ScoresHelper(this);
        mScoreList = new ArrayList<>();
        mScoreList = mDB.getAllEntries();
        this.setRecyclerViewSettings();
    }

    private void findViewsById() {
        mRecyclerView = findViewById(R.id.score_recycler);
        mFilterSpinner = findViewById(R.id.filter_spinner);
        mFilterSpinner.setOnItemSelectedListener(this);
        mOperatorSpinner = findViewById(R.id.temporary_spinner);
        mOperatorSpinner.setOnItemSelectedListener(this);
        mFilterText = findViewById(R.id.filter);
        mSearch = findViewById(R.id.search_button);
        mUserButton = findViewById(R.id.order_by_username);
        mScoreButton = findViewById(R.id.order_by_score);
    }


    private void setRecyclerViewSettings() {
        mAdapter = new ScoreAdapter(this, mScoreList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int deleted = mDB.deleteById(mScoreList.get(viewHolder.getAdapterPosition()));
                if (deleted > 0) {
                    mScoreList.remove(viewHolder.getAdapterPosition());
                    mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                }
            }
        });
        helper.attachToRecyclerView(mRecyclerView);
    }

    private void setSpinnerAdapters() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.options, R.layout.spinner_text);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mFilterSpinner.setAdapter(adapter);
        adapter = ArrayAdapter.createFromResource(this,
                R.array.filter, R.layout.spinner_text);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mOperatorSpinner.setAdapter(adapter);
    }

    public void applyFilter(View view) {
        String attribute = "";
        String value = "";
        String operator = "";
        switch (mFilterOption) {
            case 0:
                attribute = ScoresHelper.DATE;
                break;
            case 1:
                attribute = ScoresHelper.USER_NAME;
                break;
            case 2:
                attribute = ScoresHelper.SCORE;
                break;
        }
        switch (mOperatorOption) {
            case 0:
                operator = ScoresHelper.GREATER_THAN;
                break;
            case 1:
                operator = ScoresHelper.LESS_THAN;
                break;
            case 2:
                operator = ScoresHelper.EQUAL_TO;
                break;
        }
        if (attribute.equals(ScoresHelper.USER_NAME) || attribute.equals(ScoresHelper.DATE)) {
            value = "'%" + mFilterText.getText().toString() + "%'";
        } else {
            value = mFilterText.getText().toString();
        }
        mAdapter.setScoreList(mDB.getSpecificEntries(attribute, value, operator));
        mAdapter.notifyDataSetChanged();
    }


    public void orderByScore(View view) {
        if (mScoreButton.getDrawable().getConstantState().equals(
                getResources().getDrawable(R.drawable.arrow_down).getConstantState())) {
            mScoreButton.setImageDrawable(getResources().getDrawable(R.drawable.arrow_up));
            mAdapter.setScoreList(mDB.getOrderByEntries(ScoresHelper.SCORE, "ASC"));
        } else if (mScoreButton.getDrawable().getConstantState().equals(
                getResources().getDrawable(R.drawable.arrow_up).getConstantState())) {
            mScoreButton.setImageDrawable(getResources().getDrawable(R.drawable.arrow_down));
            mAdapter.setScoreList(mDB.getOrderByEntries(ScoresHelper.SCORE, "DESC"));
        }
        mAdapter.notifyDataSetChanged();
    }

    public void orderByUserName(View view) {
        if (mUserButton.getDrawable().getConstantState().equals(
                getResources().getDrawable(R.drawable.arrow_down).getConstantState())) {
            mUserButton.setImageDrawable(getResources().getDrawable(R.drawable.arrow_up));
            mAdapter.setScoreList(mDB.getOrderByEntries(ScoresHelper.USER_NAME, "ASC"));
        } else if (mUserButton.getDrawable().getConstantState().equals(
                getResources().getDrawable(R.drawable.arrow_up).getConstantState())) {
            mUserButton.setImageDrawable(getResources().getDrawable(R.drawable.arrow_down));
            mAdapter.setScoreList(mDB.getOrderByEntries(ScoresHelper.USER_NAME, "DESC"));
        }
        mAdapter.notifyDataSetChanged();
    }

    //------------------------ OVERWRITTEN METHODS -------------------------------------------------

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.filter_spinner:
                mFilterOption = position;
                break;
            case R.id.temporary_spinner:
                mOperatorOption = position;
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}