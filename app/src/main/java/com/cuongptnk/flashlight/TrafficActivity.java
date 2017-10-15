package com.cuongptnk.flashlight;

import android.graphics.drawable.LevelListDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class TrafficActivity extends AppCompatActivity {
    ImageView ivTraffic;
    LevelListDrawable levelListDrawable;
    Handler handler;
    Timer timer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_traffic);

        ivTraffic = (ImageView) findViewById(R.id.ivTraffic);
        levelListDrawable = (LevelListDrawable) ivTraffic.getDrawable();

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                ivTraffic.setImageLevel((levelListDrawable.getLevel()+1)%3);
            }
        };

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        },1000,1000);
    }
}
