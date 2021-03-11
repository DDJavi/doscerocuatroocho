package com.example.doscerocuatroocho;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.doscerocuatroocho.Game.GameView;
import com.example.doscerocuatroocho.score.ScoreItem;
import com.example.doscerocuatroocho.score.ScoresHelper;

public class GameActivity extends AppCompatActivity {

    public static GameActivity gameActivity = null;
    private TextView Score;
    public static int score = 0;
    private TextView maxScore;
    private Button restart;
    private Button back;
    private GameView gameView;
    private String username = "anon";
    private static ScoresHelper mDB;

    public GameActivity() {
        gameActivity = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getSupportActionBar().hide();

        mDB = new ScoresHelper(this);

        askUsername();
        Score = (TextView) findViewById(R.id.Score);
        maxScore = (TextView) findViewById(R.id.maxScore);
        maxScore.setText(getSharedPreferences("pMaxScore", MODE_PRIVATE).getInt("maxScore", 0) + "");

        gameView = (GameView)findViewById(R.id.gameView);
        restart = (Button) findViewById(R.id.restart);
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertScore();
            }
        });
        back = (Button)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gameView.hasTouched) {
                    score = gameView.score;
                    showScore();
                    for(int y=0;y<4;++y) {
                        for(int x=0;x<4;++x) {
                            gameView.cards[y][x].setNum(gameView.num[y][x]);
                        }
                    }
                }
            }
        });


    }

    public static GameActivity getGameActivity() {
        return gameActivity;
    }

    public void clearScore() {
        score = 0;
        showScore();
    }

    public void addScore(int i) {

        score += i;
        showScore();
        SharedPreferences pref = getSharedPreferences("pMaxScore", MODE_PRIVATE);

        if (score > pref.getInt("maxScore", 0)) {
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("maxScore", score);
            editor.commit();
            maxScore.setText(pref.getInt("maxScore", 0) + "");
        }

    }

    public void showScore() {
        Score.setText(score + "");
    }

    @Override
    public void onBackPressed() {
        createExitTipDialog();
    }

    private void createExitTipDialog() {
        new AlertDialog.Builder(GameActivity.this)
                .setMessage("¿Seguro que quiere salir？")
                .setTitle("Salida")
                .setIcon(R.drawable.alert_icon)
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(score>0)insertScore();
                        dialogInterface.dismiss();
                        finish();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }
    private void askUsername() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Introduzca su nombre");
        builder.setCancelable(false);

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                GameView.startGame();
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                username = input.getText().toString();
                if(username.isEmpty()) username="anon";
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }
    public static void insertScore() {
        ScoreItem item = new ScoreItem(score, gameActivity.username);
        mDB.insert(item);
    }
}
