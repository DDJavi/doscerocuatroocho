package com.example.doscerocuatroocho.Game;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import com.example.doscerocuatroocho.GameActivity;

import java.util.ArrayList;
import java.util.List;

public class GameView extends GridLayout {
    public static Card[][] cards = new Card[4][4];
    private static List<Point> emptyPoints = new ArrayList<Point>();
    public int num[][] = new int[4][4];
    public int score;
    public boolean hasTouched = false;

    public GameView(Context context) {
        super(context);
        initGameView();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGameView();
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initGameView();
    }

    private void initGameView() {
        setRowCount(4);
        setColumnCount(4);
        setOnTouchListener(new Listener());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        super.onSizeChanged(w, h, oldw, oldh);
        int cardWidth = (Math.min(w, h)-10)/4;
        addCards(cardWidth, cardWidth);
        startGame();

    }


    private void addCards(int cardWidth, int cardHeight) {
        this.removeAllViews();
        Card c;
        for(int y=0;y<4;++y) {
            for(int x = 0;x<4;++x) {
                c = new Card(getContext());
                c.setNum(0);
                addView(c, cardWidth, cardHeight);
                cards[x][y] = c;
            }
        }
    }


    private static void addRandomNum() {
        emptyPoints.clear();
        for (int y = 0; y < 4; ++y) {
            for (int x = 0; x < 4; ++x) {
                if (cards[x][y].getNum() == 0) {
                    emptyPoints.add(new Point(x, y));
                }
            }
        }

        Point p = emptyPoints.remove((int)(Math.random()*emptyPoints.size()));
        cards[p.x][p.y].setNum(Math.random()>0.1?2:4);
    }


    public static void startGame() {
        GameActivity.getGameActivity().clearScore();
        for(int y=0;y<4;++y) {
            for(int x=0;x<4;++x) {
                cards[x][y].setNum(0);
            }
        }
        addRandomNum();
        addRandomNum();
    }

    private void swipeLeft() {
        boolean b = false;

        for(int y=0;y<4;++y) {

            for(int x=0;x<3;++x) {

                for(int x1=x+1;x1<4;++x1) {

                    if (cards[x1][y].getNum()>0) {

                        if (cards[x][y].getNum()==0) {
                            cards[x][y].setNum(cards[x1][y].getNum());
                            cards[x1][y].setNum(0);
                            --x;
                            b = true;
                        } else if (cards[x][y].equals(cards[x1][y])) {

                            cards[x][y].setNum(cards[x][y].getNum()*2);
                            cards[x1][y].setNum(0);
                            GameActivity.getGameActivity().addScore(cards[x][y].getNum());
                            b = true;
                        }

                        break;
                    }
                }
            }
        }
        if (b) {
            addRandomNum();
            checkGameOver();
        }
    }

    private void swipeRight() {
        boolean b = false;
        for(int y=0;y<4;++y) {
            for(int x=3;x>0;--x) {
                for(int x1=x-1;x1>=0;--x1) {
                    if (cards[x1][y].getNum()>0) {
                        if (cards[x][y].getNum()==0) {
                            cards[x][y].setNum(cards[x1][y].getNum());
                            cards[x1][y].setNum(0);
                            ++x;
                            b = true;
                        } else if (cards[x][y].equals(cards[x1][y])) {
                            cards[x][y].setNum(cards[x][y].getNum()*2);
                            cards[x1][y].setNum(0);
                            GameActivity.getGameActivity().addScore(cards[x][y].getNum());
                            b = true;
                        }
                        break;
                    }
                }
            }
        }
        if (b) {
            addRandomNum();
            checkGameOver();
        }
    }

    private void swipeUp() {
        boolean b = false;
        for(int x=0;x<4;++x) {
            for(int y=0;y<3;++y) {
                for(int y1=y+1;y1<4;++y1) {
                    if (cards[x][y1].getNum()>0) {
                        if (cards[x][y].getNum()==0) {
                            cards[x][y].setNum(cards[x][y1].getNum());
                            cards[x][y1].setNum(0);
                            --y;
                            b = true;
                        } else if (cards[x][y].equals(cards[x][y1])) {
                            cards[x][y].setNum(cards[x][y].getNum()*2);
                            cards[x][y1].setNum(0);
                            GameActivity.getGameActivity().addScore(cards[x][y].getNum());
                            b = true;
                        }
                        break;
                    }
                }
            }
        }
        if (b) {
            addRandomNum();
            checkGameOver();
        }
    }

    private void swipeDown() {
        boolean b = false;
        for(int x=0;x<4;++x) {
            for(int y=3;y>0;--y) {
                for(int y1=y-1;y1>=0;--y1) {
                    if (cards[x][y1].getNum()>0) {
                        if (cards[x][y].getNum()==0) {
                            cards[x][y].setNum(cards[x][y1].getNum());
                            cards[x][y1].setNum(0);
                            ++y;
                            b = true;
                        } else if (cards[x][y].equals(cards[x][y1])) {
                            cards[x][y].setNum(cards[x][y].getNum()*2);
                            cards[x][y1].setNum(0);
                            GameActivity.getGameActivity().addScore(cards[x][y].getNum());
                            b = true;
                        }
                        break;
                    }
                }
            }
        }
        if (b) {
            addRandomNum();
            checkGameOver();
        }
    }

    private void checkGameOver() {
        boolean isOver = true;
        ALL:
        for(int y=0;y<4;++y) {
            for(int x=0;x<4;++x) {

                if (cards[x][y].getNum()==0||
                        (x<3&&cards[x][y].getNum()==cards[x+1][y].getNum())||
                        (y<3&&cards[x][y].getNum()==cards[x][y+1].getNum())) {

                    isOver = false;
                    break ALL;
                }
            }
        }
        if (isOver) {
            GameActivity.insertScore();
            new AlertDialog.Builder(getContext()).setTitle("Se acabó").setMessage("La puntuación final es: "+ GameActivity.score).setPositiveButton("Prueba otra vez", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    startGame();
                }
            }).show();
        }
    }

    class Listener implements View.OnTouchListener {

        private float startX, startY, offsetX, offsetY;

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {

            if (!hasTouched) {
                hasTouched = true;
            }

            score = GameActivity.score;

            for(int y=0;y<4;++y) {
                for(int x=0;x<4;++x) {
                    num[y][x] = cards[y][x].getNum();
                }
            }

            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    startX = motionEvent.getX();
                    startY = motionEvent.getY();
                    break;
                case MotionEvent.ACTION_UP:
                    offsetX = motionEvent.getX()-startX;
                    offsetY = motionEvent.getY()-startY;

                    if (Math.abs(offsetX)>Math.abs(offsetY)) {
                        if (offsetX<-5) {
                            swipeLeft();
                        } else if (offsetX>5) {
                            swipeRight();
                        }
                    } else {
                        if (offsetY<-5) {
                            swipeUp();
                        } else if (offsetY>5) {
                            swipeDown();
                        }
                    }

            }

            return true;

        }

    }

}