package com.cuongptnk.flashlight;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener{

    ImageButton btnLight;
    ImageButton btnNeon;
    ImageButton btnWarning;
    ImageButton btnPolic;
    ImageButton btnTraffic;
    ImageButton btnTorch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_menu);

        btnLight = (ImageButton) findViewById(R.id.btnLight);
        btnLight.setOnClickListener(this);

        btnNeon = (ImageButton) findViewById(R.id.btnNeon);
        btnNeon.setOnClickListener(this);

        btnWarning = (ImageButton) findViewById(R.id.btnWarning);
        btnWarning.setOnClickListener(this);

        btnPolic = (ImageButton) findViewById(R.id.btnPolic);
        btnPolic.setOnClickListener(this);

        btnTraffic = (ImageButton) findViewById(R.id.btnTraffic);
        btnTraffic.setOnClickListener(this);

        btnTorch = (ImageButton) findViewById(R.id.btnTorch);
        btnTorch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;

        switch (v.getId()) {
            case R.id.btnLight:
                intent = new Intent(MenuActivity.this,LightActivity.class);
                break;
            case R.id.btnNeon:
                intent = new Intent(MenuActivity.this,NeonActivity.class);
                break;
            case R.id.btnWarning:
                intent = new Intent(MenuActivity.this,WarningActivity.class);
                break;
            case R.id.btnPolic:
                intent = new Intent(MenuActivity.this,PolicActivity.class);
                break;
            case R.id.btnTraffic:
                intent = new Intent(MenuActivity.this,TrafficActivity.class);
                break;
            case R.id.btnTorch:
                intent = new Intent(MenuActivity.this,TorchActivity.class);
                break;

            default:
        }

        startActivity(intent);
    }
}
