package com.cuongptnk.flashlight;

import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ToggleButton;

import java.util.Timer;
import java.util.TimerTask;

public class PolicActivity extends AppCompatActivity {
    ToggleButton tgPolic;
    Handler handler;
    Timer timer;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_polic);

        tgPolic = (ToggleButton) findViewById(R.id.tgPolic);
        mediaPlayer = MediaPlayer.create(PolicActivity.this,R.raw.music_polic);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                tgPolic.setChecked(!tgPolic.isChecked());
            }
        };

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        },300,300);

    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.stop();
    }
}
