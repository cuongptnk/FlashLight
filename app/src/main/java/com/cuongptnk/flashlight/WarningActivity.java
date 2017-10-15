package com.cuongptnk.flashlight;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ToggleButton;

import java.util.Timer;
import java.util.TimerTask;

public class WarningActivity extends AppCompatActivity {
    ToggleButton tgWarning;
    Handler handler;
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_warning);

        tgWarning = (ToggleButton) findViewById(R.id.tgWarning);

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                tgWarning.setChecked(!tgWarning.isChecked());
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
