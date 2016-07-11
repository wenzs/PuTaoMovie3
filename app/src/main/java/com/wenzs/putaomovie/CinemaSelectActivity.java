package com.wenzs.putaomovie;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2016/7/10.
 */
public class CinemaSelectActivity extends AppCompatActivity {

    private HeadLayout header;
    private LinearLayout toreplace;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cinema_select_layout);
        header = (HeadLayout) findViewById(R.id.header);
        toreplace = (LinearLayout) findViewById(R.id.toreplace);
        String moviename = getIntent().getStringExtra("moviename");
        header.settitle(moviename);
        CinemaPager cinemapager = new CinemaPager();
        cinemapager.setLoaddataquick(true);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.toreplace, cinemapager);
        ft.commit();
    }
}
