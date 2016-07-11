package com.wenzs.putaomovie;

/**
 * Created by Administrator on 2016/7/2.
 */
public interface MovieLocationListener {

    public abstract void locationSuccess(String city,String address,Double longitude,Double latitude);
    public abstract void locationFaild();
}
