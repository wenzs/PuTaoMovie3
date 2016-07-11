package com.wenzs.putaomovie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class LandingPageActivity extends AppCompatActivity {

    final static int VERSIONSNUMBER=1;
    int pagercount=4;
    ViewPager landing_pager;
    MyPagerAdapter mypageradapter;
    List<View> v=new ArrayList<>(pagercount);
    int[] imagerid={R.drawable.start_i1,R.drawable.start_i2,R.drawable.start_i3,R.drawable.start_i4};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        int landingpager=PeferenceSave.getInt(PeferenceSave.VERSIONS_NUMBER,0);
        if (landingpager ==VERSIONSNUMBER) {
            jumpToMain();
            return;
        }
        landing_pager= (ViewPager) findViewById(R.id.landing_pager);
        initialize();
    }

    private void initialize() {
        for (int i = 0; i <pagercount ; i++) {
            ImageView iv=new ImageView(LandingPageActivity.this);
            iv.setImageResource(imagerid[i]);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
              v.add(iv);
//            TextView tv = new TextView(LandingPageActivity.this);
//            tv.setText("这是第"+i+"个面页");
//            v.add(tv);
        }
        v.get(pagercount-1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PeferenceSave.saveInt(PeferenceSave.VERSIONS_NUMBER,VERSIONSNUMBER);
                jumpToMain();
            }
        });
        mypageradapter=new MyPagerAdapter(v);
        landing_pager.setAdapter(mypageradapter);
    }

    private void jumpToMain(){
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }


}


 class MyPagerAdapter extends PagerAdapter {

     List<View> v;

     public MyPagerAdapter(List<View> v) {
         this.v = v;
     }

     @Override
     public int getCount() {
         return v == null ? 0 : v.size();
     }

     @Override
     public boolean isViewFromObject(View view, Object object) {
         return view == object;
     }

     @Override
     public Object instantiateItem(ViewGroup container, int position) {
         View view = v.get(position);
         container.addView(view);
         return view;
     }

     @Override
     public void destroyItem(ViewGroup container, int position, Object object) {
         View view = v.get(position);
         container.removeView(view);
     }

 }