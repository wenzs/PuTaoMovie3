package com.wenzs.putaomovie;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment implements View.OnClickListener {

    List<Fragment> fragment = new ArrayList<>();
    MyFragmentAdapter fragmentadapter;
    int[] radiobutton = {R.id.movie_RB, R.id.cinema_RB, R.id.discover_RB};
    private ViewPager puta_movie;
    private RadioGroup button_radiogroup;
    private TextView head_name;
    private TextView city_tv;
    private String[] headname;
    private MovieLocationListener locationlilstener = new MovieLocationListener() {
        @Override
        public void locationSuccess(final String city, String address, Double longitude, Double latitude) {
            Log.d("citylocation", "locationSuccess() called with: " + "city = [" + city + "], address = [" + address + "]");
            String currentcity = CityManager.getInstance().getCity();
            if (PeferenceSave.getString(PeferenceSave.LOCATION_CITY, null) == null || !PeferenceSave.getString(PeferenceSave.LOCATION_CITY, null).equals(city)) {
                PeferenceSave.saveString(PeferenceSave.LOCATION_CITY, city);
                if (!TextUtils.isEmpty(city) && !currentcity.equals(PeferenceSave.getString(PeferenceSave.LOCATION_CITY, null))) {
                    new AlertDialog.Builder(getActivity()).setTitle("定位提示").setMessage("当前城市是：" + currentcity + "\n定位到的城市是：" + city + "\n是否切换?")
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    CityManager.getInstance().saveCity(city);
                                    city_tv.setText(CityManager.getInstance().getCity());
                                }
                            }).setNegativeButton("否", null).create().show();
                }
            }
        }

        @Override
        public void locationFaild() {

        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_fragment_main, null);
        puta_movie = (ViewPager) v.findViewById(R.id.puta_movie);
        button_radiogroup = (RadioGroup) v.findViewById(R.id.button_radiogroup);
        head_name = (TextView) v.findViewById(R.id.head_name);
        city_tv = (TextView) v.findViewById(R.id.city_tv);
        city_tv.setText(CityManager.getInstance().getCity());
        v.findViewById(R.id.menu_iv).setOnClickListener(this);
        headname = this.getResources().getStringArray(R.array.head_string_array);
        initial();
        button_radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < radiobutton.length; i++) {
                    if (radiobutton[i] == checkedId) {
                        puta_movie.setCurrentItem(i);
                    }
                }
            }
        });
        if (BaiduMapLocation.getInstance().mListeners != null) {
            BaiduMapLocation.getInstance().mListeners.clear();
        }
        BaiduMapLocation.getInstance().addListener(locationlilstener);
        BaiduMapLocation.getInstance().startLocation();
        return v;
    }

    @Override
    public void onStop() {
        super.onStop();
        BaiduMapLocation.getInstance().removeListener(locationlilstener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BaiduMapLocation.getInstance().destroy();
    }


    private void initial() {
        fragment.add(new MoviePager());
        fragment.add(new CinemaPager());
        fragment.add(new DiscoverPager());
        fragmentadapter = new MyFragmentAdapter(getChildFragmentManager(), fragment);
        puta_movie.setAdapter(fragmentadapter);
        puta_movie.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                button_radiogroup.check(radiobutton[position]);
                head_name.setText(headname[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu_iv:
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).getSlidingMenu().toggle(true);
                }
                break;
        }
    }

    class MyFragmentAdapter extends FragmentPagerAdapter {
        List<Fragment> fragment;

        public MyFragmentAdapter(FragmentManager fm, List<Fragment> fragment) {
            super(fm);
            this.fragment = fragment;

        }

        @Override
        public Fragment getItem(int position) {
            return fragment.get(position);
        }

        @Override
        public int getCount() {
            return fragment.size();
        }
    }

}