package com.wenzs.putaomovie;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/5.
 */
public class StillShows extends AppCompatActivity{

    private HeadLayout stills_header;
    private ViewPager stills_vp;
    private int p;
    private String[] stills;
    private String moviename;
    private List<View> v;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stills_shows_layout);

        moviename=getIntent().getStringExtra("moviename");
        p=getIntent().getIntExtra("position",0);
        stills=getIntent().getStringArrayExtra("mSills");

        stills_header= (HeadLayout) findViewById(R.id.stills_header);
        stills_header.settitle(moviename);

        stills_vp= (ViewPager) findViewById(R.id.stills_vp);
        v=new ArrayList<>(stills.length);
        for (int i = 0; i <stills.length ; i++) {
            View view=View.inflate(this,R.layout.stillshowsadapter_layout,null);
            v.add(view);
        }
        StillShowsAdapter stillshowadapter=new StillShowsAdapter(stills,v);
        stills_vp.setAdapter(stillshowadapter);
        stills_vp.setCurrentItem(p);
    }



}
class StillShowsAdapter extends PagerAdapter {
    private ImageLoader imageloader;
    String[] stills;
    List<View> v;


    public StillShowsAdapter(String[] stills,List<View> v){
        this.stills=stills;
        this.v=v;
        imageloader=new ImageLoader(MyRequestQueue.getInstance().getRequestQueue(),new BitmapCache());
    }
    @Override
    public int getCount() {
        return stills==null?0:stills.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = v.get(position);
        TouchImageView still_iv= (TouchImageView) view.findViewById(R.id.still_iv);
        imageloader.get(stills[position],ImageLoader.getImageListener(still_iv,R.mipmap.ic_launcher,R.mipmap.ic_launcher));
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = v.get(position);
        container.removeView(view);
    }
}