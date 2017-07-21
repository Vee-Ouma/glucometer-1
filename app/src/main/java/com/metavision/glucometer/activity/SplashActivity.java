package com.metavision.glucometer.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.jlmd.animatedcircleloadingview.AnimatedCircleLoadingView;
import com.metavision.glucometer.R;

public class SplashActivity extends AppCompatActivity {
    private AnimatedCircleLoadingView animatedCircleLoadingView;
    private Intent load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ImageView img_logo = (ImageView) findViewById(R.id.img_logo);
        fadeOutandHide(img_logo);
        animatedCircleLoadingView = (AnimatedCircleLoadingView) findViewById(R.id.circle_loading_view);
        activateBluetooth();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        this.registerReceiver(mReceiver, filter);
//        load = new Intent(SplashActivity.this, IntroActivity.class);
//        startActivity(load);
    }

    public void activateBluetooth() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null){
            Toast.makeText(getApplicationContext(), "Sorry, you don\'t have bluetooth.", Toast.LENGTH_SHORT).show();
        } else if (!mBluetoothAdapter.isEnabled()){
            mBluetoothAdapter.enable();
            finish();
        }
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);


            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                Toast.makeText(getApplicationContext(), "BT Found", Toast.LENGTH_SHORT).show();
//                if Device found
            } else if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {
                Toast.makeText(getApplicationContext(), "BT Connected", Toast.LENGTH_SHORT).show();
//                if Device is now connected
            } else if (BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED.equals(action)) {
                Toast.makeText(getApplicationContext(), "BT will be disconnected", Toast.LENGTH_SHORT).show();
//                if Device is just around to disconnect
            } else if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {
                Toast.makeText(getApplicationContext(), "BT has disconnected", Toast.LENGTH_SHORT).show();
//                if Device has disconnected
            }

        }
    };

    private void fadeOutandHide(final ImageView img) {
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setDuration(1500);

        fadeOut.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                img.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                img.setVisibility(View.GONE);
                startLoading();
                startPercentMockThread();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        img.setAnimation(fadeOut);
    }

    private void startLoading() {
        animatedCircleLoadingView.startDeterminate();
    }

    private void startPercentMockThread() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1500);
                    for (int i = 0; i <= 100; i++){
                        Thread.sleep(50);
                        changePercent(i);
                    }
                    finish();
                    startActivity(new Intent(SplashActivity.this, IntroActivity.class));
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        new Thread(runnable).start();
    }

    private void changePercent(final int percent) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                animatedCircleLoadingView.setPercent(percent);
            }
        });
    }

    public void resetLoading() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                animatedCircleLoadingView.resetLoading();
            }
        });
    }
}
