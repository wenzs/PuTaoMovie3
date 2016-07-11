package com.wenzs.putaomovie;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/7/2.
 */
public class PeferenceSave {

    public final static String VERSIONS_NUMBER = "VERSIONS_NUMBER";
    public final static String CITY_LOCATION = "CITY_LOCATION";
    public final static String LOCATION_ADDRESS="LOCATION_ADDRESS";
    public final static String LOCATION_LONGITUDE="LOCATION_LONGITUDE";
    public final static String LOCATION_LATITUDE="LOCATION_LATITUDE";
    public final static String LOCATION_CITY = "LOCATION_CITY";
    private final static String PS_NAME = "movie_ps";

    public static void saveString(String key, String values) {
        Context context = MyRequestQueue.getInstance();
        SharedPreferences sp = context.getSharedPreferences(PS_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(key, values).commit();
    }

    public static void saveInt(String key, int values) {
        Context context = MyRequestQueue.getInstance();
        SharedPreferences sp = context.getSharedPreferences(PS_NAME, Context.MODE_PRIVATE);
        sp.edit().putInt(key, values).commit();
    }


    public static String getString(String key, String defvalues) {
        Context context = MyRequestQueue.getInstance();
        SharedPreferences sp = context.getSharedPreferences(PS_NAME, Context.MODE_PRIVATE);
        return sp.getString(key, defvalues);
    }

    public static int getInt(String key, int defvalues) {
        Context context = MyRequestQueue.getInstance();
        SharedPreferences sp = context.getSharedPreferences(PS_NAME, Context.MODE_PRIVATE);
        return sp.getInt(key, defvalues);
    }
}
