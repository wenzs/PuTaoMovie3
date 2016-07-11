package com.wenzs.putaomovie;

import java.util.List;

/**
 * Created by Administrator on 2016/7/9.
 */
public class SendTheCinemaData {
    private static SendTheCinemaData instance = new SendTheCinemaData();
    public List<CinameData> data;

    public static SendTheCinemaData getInstance() {
        return instance;
    }

    public void setmyData(CinameData cinemadata) {
        data.add(cinemadata);
    }
}
