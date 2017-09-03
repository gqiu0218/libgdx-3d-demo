package com.mygdx.game.android;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;

public class MainActivity extends AndroidApplication implements View.OnClickListener, View.OnTouchListener {

    private MyGdxGame gdxGame;
    private float offsetX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FrameLayout container = (FrameLayout) findViewById(R.id.container);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        gdxGame = new MyGdxGame();
        View libgdxView = initializeForView(gdxGame);
        container.addView(libgdxView);

        libgdxView.setOnTouchListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                break;
            case R.id.btn2:
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                offsetX = event.getRawX();
                break;
            case MotionEvent.ACTION_MOVE:
                float currentX = event.getRawX();
                if(currentX<offsetX){
                    gdxGame.rotateToRight();
                }else{
                    gdxGame.rotateToLeft();
                }
                offsetX = currentX;
                break;
            case MotionEvent.ACTION_UP:


                break;
        }
        return true;
    }
}
