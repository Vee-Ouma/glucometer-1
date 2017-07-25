package com.metavision.glucometer.activity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.metavision.glucometer.R;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class HomeActivity extends AppCompatActivity {

    private TextView mTextMessage;
   /* private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private View navHeader;
    private TextView txtName;
    private ImageView imgNavHeaderBg, imgProfile;*/
    private Toolbar toolbar;

    /*public static int navItemIndex = 0;

    private static final String TAG_SETTINGS = "settings";

    private String[] activityTitles;

    private boolean shouldLoadHomeActivityOnBackPress = true;
    private Handler mHandler;*/


    /*private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_user:
                    mTextMessage.setText(R.string.title_user);
                    return true;
                case R.id.navigation_glucometer:
                    mTextMessage.setText(R.string.title_glucometer);
                    return true;
                case R.id.navigation_statistics:
                    mTextMessage.setText(R.string.title_statistics);
                    return true;
                case R.id.navigation_about:
                    mTextMessage.setText(R.string.title_about);
                    return true;
            }
            return false;
        }

    };*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

       /* mTextMessage = (TextView) findViewById(R.id.message);*/
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

       /* mHandler = new Handler();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) findViewById(R.id.name);
        imgNavHeaderBg = (ImageView) findViewById(R.id.img_header_bg);
        imgProfile = (ImageView) findViewById(R.id.img_profile);

        activityTitles = getResources().getStringArray(R.array.next_app);

        loadNavHeader();*/



//        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        mTextMessage = (TextView) findViewById(R.id.textView);
        BottomBar bottomBar = (BottomBar) findViewById(R.id.navigation);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.navigation_user) {
                    mTextMessage.setText(R.string.title_user);
                } else if (tabId == R.id.navigation_glucometer) {
                    mTextMessage.setText(R.string.title_glucometer);
                } else if (tabId ==  R.id.navigation_statistics) {
                    mTextMessage.setText(R.string.title_statistics);
                } else if (tabId == R.id.navigation_about) {
                    mTextMessage.setText(R.string.title_about);
                }
            }
        });

        /*bottomBar.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if ( == R.id.navigation_user) {
                    mTextMessage.setText(R.string.title_user);
                } else if (tabId == R.id.navigation_glucometer) {
                    mTextMessage.setText(R.string.title_glucometer);
                } else if (tabId ==  R.id.navigation_statistics) {
                    mTextMessage.setText(R.string.title_statistics);
                } else if (tabId == R.id.navigation_about) {
                    mTextMessage.setText(R.string.title_about);
                }
            }
        });*/
        /*navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_user:
                        mTextMessage.setText(R.string.title_user);
                        return true;
                    case R.id.navigation_glucometer:
                        mTextMessage.setText(R.string.title_glucometer);
                        return true;
                    case R.id.navigation_statistics:
                        mTextMessage.setText(R.string.title_statistics);
                        return true;
                    case R.id.navigation_about:
                        mTextMessage.setText(R.string.title_about);
                        return true;
                }
                return false;
            }
        });*/
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    /*private void loadNavHeader() {
        txtName.setText("Gluco user");

    }*/
}
