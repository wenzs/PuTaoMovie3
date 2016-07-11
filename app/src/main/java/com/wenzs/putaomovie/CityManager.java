package com.wenzs.putaomovie;

/**
 * Created by Administrator on 2016/7/2.
 */
public class CityManager {
    private static CityManager instance=new CityManager();

    public static CityManager getInstance() {
        return instance;
    }

    private CityManager (){};

    public void saveCity(String city){
       PeferenceSave.saveString(PeferenceSave.CITY_LOCATION,city);
    }

    public String getCity(){
        return PeferenceSave.getString(PeferenceSave.CITY_LOCATION,"北京");
    }
}
