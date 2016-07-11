package com.wenzs.putaomovie;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity{
private Fragment mainfragment;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mainfragment=(Fragment) getSupportFragmentManager().getFragment(savedInstanceState,"mainfragment");
        }else {
            mainfragment=new MainFragment();
        }

        setContentView(R.layout.activity_main_content);
        getSupportFragmentManager().
                beginTransaction()
                .replace(R.id.main_content,mainfragment)
                .commit();

        setBehindContentView(R.layout.activity_main_menu);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_menu,new MenuPager())
                        .commit();
        getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        getSlidingMenu().setBehindOffsetRes(R.dimen.Sliding_menu);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState,"mainfragment",mainfragment);
    }
}
