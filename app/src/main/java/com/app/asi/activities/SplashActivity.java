package com.app.asi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.app.asi.R;

import java.util.Timer;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.txt)
    ImageView txt;
    @BindView(R.id.mainFrameLayout)
    RelativeLayout mainFrameLayout;

    final int MIN_TIME_INTERVAL_FOR_SPLASH = 2500; // in millis
    boolean workComplete = false;
    Timer checkWorkTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mainFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mainFrameLayout.setBackground(getResources().getDrawable(R.drawable.ic_launcher));
              txt.setImageResource(R.drawable.logo1);
                launchTimerAndTask();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        changeBackground();

    }


    private void changeBackground() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               // mainFrameLayout.setBackground(getResources().getDrawable(R.drawable.image2));
                txt.setImageResource(R.drawable.logo1);
                launchTimerAndTask();
            }
        }, MIN_TIME_INTERVAL_FOR_SPLASH);
    }

    private void launchTimerAndTask() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showMainActivity();
            }
        }, MIN_TIME_INTERVAL_FOR_SPLASH);
    }

    private void initNextActivity() {
        checkWorkTimer.cancel();
        showMainActivity();

    }

    private void showMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }
}