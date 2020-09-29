package com.example.filip.mytirecenter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

/**
 * This class is the implementation of the splash activity.
 *
 * @author Marco Di Capua
 * @author Filippo Pelosi
 * @version 1.0
 */
public class SplashActivity extends AppCompatActivity {

    // The Tag for the Log
    private static final String TAG_LOG = SplashActivity.class.getName();

    // The key to use to save the isDone state variable
    private static final String IS_DONE_KEY = "com.example.filip.mytirecenter.key.IS_DONE_KEY";

    // The key to use to save the mStartTime state variable
    private static final String START_TIME_KEY = "com.example.filip.mytirecenter.key.START_TIME_KEY";

    // The minimum interval before going ahead
    private static final long MIN_WAIT_INTERVAL = 1500L;

    // The maximum interval before going ahead
    private static final long MAX_WAIT_INTERVAL = 3000L;

    // The What to use into the Handler to go to the next Activity
    private static final int GO_AHEAD_WHAT = 1;

    // The time considered as the start
    private long mStartTime = -1;

    // This variable is used to prevent the double launch of the next activity
    // or the launch when the current Activity has been closed.
    private boolean mIsDone;

    // This is the Handler used to manage time interval
    private UiHandler mHandler;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);
        // If present we get the information about the startTime
        if (savedInstanceState != null) {
            this.mStartTime = savedInstanceState.getLong(START_TIME_KEY);
        }
        // We initialize the Handler
        mHandler = new UiHandler(this);
        // We get the reference of the ImageView to manage the touch event
        final ImageView logoImageView = findViewById(R.id.splash_imageView);
        logoImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.d(TAG_LOG, "ImageView touched!!");
                long elapsedTime = SystemClock.uptimeMillis() - mStartTime;
                if (elapsedTime >= MIN_WAIT_INTERVAL && !mIsDone) {
                    mIsDone = true;
                    goAhead();
                } else {
                    Log.d(TAG_LOG, "Too early!");
                }
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mStartTime == -1L) {
            // We set the time to consider as the start only if not already done
            // in the onStart() method
            mStartTime = SystemClock.uptimeMillis();
        }
        // We set the time for going ahead automatically
        final Message goAheadMessage = mHandler.obtainMessage(GO_AHEAD_WHAT);
        mHandler.sendMessageAtTime(goAheadMessage, mStartTime + MAX_WAIT_INTERVAL);
        Log.d(TAG_LOG, "Handler message sent!");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(IS_DONE_KEY, mIsDone);
        outState.putLong(START_TIME_KEY, mStartTime);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.mIsDone = savedInstanceState.getBoolean(IS_DONE_KEY);
    }

    // Utility method that manages the transition to the FirstAccessActivity
    private void goAhead() {
        final Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    private static class UiHandler extends Handler {

        // The WeakReference to the Activity that uses it
        private WeakReference<SplashActivity> mActivityRef;

        /**
         * Constructor with the sourceActivity reference
         *
         * @param srcActivity The reference of the Activity.
         */
        private UiHandler(final SplashActivity srcActivity) {
            this.mActivityRef = new WeakReference<>(srcActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            final SplashActivity srcActivity = this.mActivityRef.get();
            if (srcActivity == null) {
                Log.d(TAG_LOG, "Reference to SplashActivity lost!");
                return;
            }
            switch (msg.what) {
                case GO_AHEAD_WHAT:
                    // We calculate le elapsed time to prevent the early launch of the next
                    // Activity
                    long elapsedTime = SystemClock.uptimeMillis() - srcActivity.mStartTime;
                    if (elapsedTime >= MIN_WAIT_INTERVAL && !srcActivity.mIsDone) {
                        srcActivity.mIsDone = true;
                        srcActivity.goAhead();
                    }
                    break;
            }
        }

    }
}