package com.wenzs.putaomovie;

import android.text.TextUtils;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/2.
 */
public class BaiduMapLocation {

    private static BaiduMapLocation instance=new BaiduMapLocation();
    private static final String TAG ="BaiduMapLocation" ;
    LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    List<MovieLocationListener> mListeners=new ArrayList<>();

    public static BaiduMapLocation getInstance() {
        return instance;
    }

    private BaiduMapLocation(){};

    GeoCoder mSearch = null; // 搜索模块，也可去掉地图模块独立使用

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            Log.d(TAG, "onReceiveLocation() called with: " + "location = [" + location + "]");
            // map view 销毁后不在处理新接收的位置
            if (location == null) {
                return;
            }

            if (TextUtils.isEmpty(location.getCity())) {
                LatLng ptCenter = new LatLng(location.getLatitude(), location.getLongitude());
                // 反Geo搜索
                mSearch.reverseGeoCode(new ReverseGeoCodeOption()
                        .location(ptCenter));
            } else {
                dispatchLocationInfo(location.getCity(), location.getAddrStr(),location.getLongitude(),location.getLatitude());
            }

        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }


    private OnGetGeoCoderResultListener mOnGetGeoCoderResultListener = new OnGetGeoCoderResultListener() {
        @Override
        public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
        }

        @Override
        public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {

//            for (MovieLocationListener listener : mListeners) {
                dispatchLocationInfo(reverseGeoCodeResult.getAddressDetail().city, reverseGeoCodeResult.getAddress(),reverseGeoCodeResult.getLocation().longitude,reverseGeoCodeResult.getLocation().latitude);
//            }

        }
    };

    public void addListener(MovieLocationListener listener){
        mListeners.add(listener);
    }

    public void removeListener(MovieLocationListener listener){
        mListeners.remove(listener);
    }

    private void dispatchLocationInfo(String city, String addrStr,Double longitude,Double latitude) {
        if (!TextUtils.isEmpty(city)) {
            if (city.endsWith("市")||city.endsWith("县")) {
                city = city.substring(0, city.length() - 1);
            }
        }
        for (MovieLocationListener listener:mListeners) {
            listener.locationSuccess(city,addrStr,longitude,latitude);
        }
    }

    /**
     * 执行定位
     */
    public void startLocation() {
        // 初始化搜索模块，注册事件监听
        mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(mOnGetGeoCoderResultListener);

        Log.d(TAG, "startLocation() called with: " + "");
        // 定位初始化
        mLocClient = new LocationClient( MyRequestQueue .getInstance());
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();//发起百度定位
    }

    public void destroy(){
        mLocClient.stop();
    }
}
