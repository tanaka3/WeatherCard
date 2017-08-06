package net.masaya3.weather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity {

    Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        Intent intent  = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

        }
    };


    /**
     *
     */
    @Override
    public void onResume(){
        super.onResume();
        mHandler.postDelayed(mRunnable, getResources().getInteger(R.integer.splash_time));
    }

    /**
     *
     */
    @Override
    public void onPause(){
        super.onPause();
        mHandler.removeCallbacks(mRunnable);
    }
}
