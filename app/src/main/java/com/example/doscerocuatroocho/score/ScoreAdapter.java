package com.example.doscerocuatroocho.score;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doscerocuatroocho.R;
import com.example.doscerocuatroocho.ScoreDetail;

import java.util.ArrayList;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ScoreHolder> {
    private final LayoutInflater INFLATER;
    public static final String EXTRA_ID = "id";
    public static final String EXTRA_DATE = "date";
    public static final String EXTRA_USER_NAME = "username";
    public static final String EXTRA_SCORE = "score";
    private Context mContext;
    private ArrayList<ScoreItem> mScoreList;

    public void setScoreList(ArrayList<ScoreItem> scoreList) {
        mScoreList = scoreList;
    }

    public ScoreAdapter(Context context, ArrayList<ScoreItem> scoreList) {
        INFLATER = LayoutInflater.from(context);
        mContext = context;
        mScoreList = scoreList;
    }

    @NonNull
    @Override
    public ScoreHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = INFLATER.inflate(R.layout.score_item, parent, false);
        return new ScoreHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreHolder holder, int position) {
        ScoreItem current = mScoreList.get(position);
        holder.mScoreEntry.setText(String.format("%s | %s | %s", current.getDate(),
                current.getUserName(), current.getScore()));
    }

    @Override
    public int getItemCount() {
        return mScoreList.size();
    }


    public class ScoreHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        LinearLayout mLinearLayout;
        TextView mScoreEntry;

        public ScoreHolder(@NonNull View itemView) {
            super(itemView);
            this.mLinearLayout = itemView.findViewById(R.id.score_layout);
            this.mScoreEntry = itemView.findViewById(R.id.score_entry);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            ScoreItem currentScore = mScoreList.get(getAdapterPosition());
            Intent detailIntent = new Intent(mContext, ScoreDetail.class);
            detailIntent.putExtra(EXTRA_ID, currentScore.getId());
            detailIntent.putExtra(EXTRA_DATE, currentScore.getDate());
            detailIntent.putExtra(EXTRA_USER_NAME, currentScore.getUserName());
            detailIntent.putExtra(EXTRA_SCORE, currentScore.getScore());
            mContext.startActivity(detailIntent);
        }
    }
}
