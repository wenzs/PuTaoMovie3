package com.wenzs.putaomovie;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.baidu.mapapi.SDKInitializer;


/**
 * Created by Administrator on 2016/6/29.
 */
public class MyRequestQueue extends Application {

    private RequestQueue requestQueue;
    private static  MyRequestQueue sIntance;

    /**
     * 获得当前运行时MyRequestQueue实例对象
     * @return
     */
    public static  MyRequestQueue getInstance() {
        return sIntance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        sIntance = this;
        requestQueue = Volley.newRequestQueue(this);
        SDKInitializer.initialize(this);
    }

    /**
     * 获得Volley的Http请求队列
     * @return
     */
    public RequestQueue getRequestQueue(){
        return requestQueue;
    }

}
