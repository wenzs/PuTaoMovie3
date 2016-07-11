package com.wenzs.putaomovie;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.baidu.mapapi.utils.DistanceUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Administrator on 2016/6/29.
 */
public class CinemaPager extends Fragment implements Response.Listener<String>, Response.ErrorListener, View.OnClickListener, AdapterView.OnItemClickListener {

    private static List<CinameData> currentlist = new ArrayList<>();
    private ListView cinema_listview;
    private TextView cinema_area;
    private TextView cinema_order;
    private TextView location_address;
    private TextView re_location;
    private TextView tv;
    private CinemaAdapter cinemaadapter;
    private StringRequest stringrequest;
    private CinameData[] cinemadata;
    private HashSet<String> cotuntyname = new HashSet<>();
    private List<String> pricerange = new ArrayList<>();
    private List<CinameData> newdata = new ArrayList<>();
    private PopupWindow areapow;
    private PopupWindow orderpup;
    private LatLng userposition;
    MovieLocationListener movielocationlistener = new MovieLocationListener() {
        @Override
        public void locationSuccess(String city, String address, Double longitude, Double latitude) {
            String la = (String) location_address.getText();
            String mylongitude = String.valueOf(longitude);
            String mylatitude = String.valueOf(latitude);
            if (!TextUtils.isEmpty(address) && !address.equals(PeferenceSave.getString(PeferenceSave.LOCATION_ADDRESS, null))) {
                Log.e("location", "locationSuccess() called with: " + "longitude = [" + longitude + "], latitude = [" + latitude + "]");
                PeferenceSave.saveString(PeferenceSave.LOCATION_ADDRESS, address);
                PeferenceSave.saveString(PeferenceSave.LOCATION_LONGITUDE, mylongitude);
                PeferenceSave.saveString(PeferenceSave.LOCATION_LATITUDE, mylatitude);
            }
            location_address.setText(PeferenceSave.getString(PeferenceSave.LOCATION_ADDRESS, null));
            powData(cinemadata, Double.parseDouble(PeferenceSave.getString(PeferenceSave.LOCATION_LONGITUDE, null)), Double.parseDouble(PeferenceSave.getString(PeferenceSave.LOCATION_LATITUDE, null)));
            BaiduMapLocation.getInstance().removeListener(movielocationlistener);
            BaiduMapLocation.getInstance().destroy();
        }

        @Override
        public void locationFaild() {

        }
    };
    private ArrayAdapter arrayadapter;
    private List<String> countylist = new ArrayList<>();
    private List<CinameData> countychoose = new ArrayList<>();
    private String movieid;
    private boolean loaddataquick = false;

    public void setLoaddataquick(boolean loaddataquick) {
        this.loaddataquick = loaddataquick;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.cinema_pager_layout, null);
        cinema_listview = (ListView) inflate.findViewById(R.id.cinema_listview);
        cinema_area = (TextView) inflate.findViewById(R.id.cinema_area);
        cinema_order = (TextView) inflate.findViewById(R.id.cinema_order);
        location_address = (TextView) inflate.findViewById(R.id.location_address);
        re_location = (TextView) inflate.findViewById(R.id.re_location);
        cinema_listview.setOnItemClickListener(this);
        re_location.setOnClickListener(this);
        cinema_area.setOnClickListener(this);
        cinema_order.setOnClickListener(this);
        cinemaadapter = new CinemaAdapter(getActivity());
        cinema_listview.setAdapter(cinemaadapter);
        if (loaddataquick) {
            refreshUI();
            inflate.findViewById(R.id.for_location).setAlpha(0);
            inflate.findViewById(R.id.for_location).getBackground().setAlpha(0);
        } else {
            initate();
        }
        BaiduMapLocation.getInstance().addListener(movielocationlistener);
        BaiduMapLocation.getInstance().startLocation();
        return inflate;
    }

    private void refreshUI() {
        movieid = getActivity().getIntent().getStringExtra("movieid");
        Log.e("movieid~~~~~~~~~~~~~", "refreshUI: " + movieid);
        initate();
    }

    private void initate() {
        String url = null;
        try {
            url = "http://api.putao.so/sbiz/movie/cinema/list/?&citycode=" + URLEncoder.encode("深圳", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (movieid != null) {
            url = url + "&movieid=" + movieid;
        }
        stringrequest = new StringRequest(url, this, this);
        MyRequestQueue.getInstance().getRequestQueue().add(stringrequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("error", "cinema链接失败");
    }

    @Override
    public void onResponse(String response) {
        Cinema cinema = JSONObject.parseObject(response, Cinema.class);
        if (cinema.getRet_code().equals("0000")) {
            cinemadata = cinema.getData();
        }

    }

    public void powData(CinameData[] cinemadata, Double longitude, Double latitude) {

        for (CinameData powneed : cinemadata) {
            String name = powneed.getCountyname();
            String price = powneed.getPricerange();
            cotuntyname.add(name);
            pricerange.add(price);
            userposition = new LatLng(longitude, latitude);
            LatLng cinemaposition = new LatLng(Double.parseDouble(powneed.getLongitude()), Double.parseDouble(powneed.getLatitude()));
            if (powneed.getCs().equals("1")) {
                CoordinateConverter coordinate = new CoordinateConverter();
                coordinate.coord(cinemaposition);
                coordinate.from(CoordinateConverter.CoordType.COMMON);
                cinemaposition = coordinate.convert();
            }
            double distance = DistanceUtil.getDistance(cinemaposition, userposition);
            double stepprice = (Double.parseDouble(powneed.getPricerange().split("-")[0]) / 100);
            powneed.setStepPrice(stepprice);
            powneed.setDistance(distance);
            newdata.add(powneed);

        }
        cinemaadapter.clear();
        currentlist = newdata;
        cinemaadapter.addAll(newdata);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.re_location:
                location_address.setText("正在定位······");
                BaiduMapLocation.getInstance().addListener(movielocationlistener);
                BaiduMapLocation.getInstance().startLocation();
                break;
            case R.id.cinema_area:
                cinema_area.setSelected(true);
                areaPress();
                break;
            case R.id.cinema_order:
                cinema_order.setSelected(true);
                orderPress();
                break;

            default:
                break;
        }
    }

    private void orderPress() {
        ListView orderlist = new ListView(getActivity());
        List<String> v = new ArrayList<>(2);
        v.add("离我最近");
        v.add("起价最低");
        arrayadapter = new ArrayAdapter(getActivity(), android.R.layout.simple_expandable_list_item_1, v);
        orderlist.setAdapter(arrayadapter);
        orderpup = new PopupWindow(orderlist, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        orderpup.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        orderpup.showAsDropDown(cinema_order);
        orderlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    sortByDistance(currentlist);
                    cinemaadapter.clear();
                    cinemaadapter.addAll(currentlist);
                    orderpup.dismiss();
                    cinema_order.setSelected(false);
                } else {
                    sortByStepprice(currentlist);
                    cinemaadapter.clear();
                    cinemaadapter.addAll(currentlist);
                    orderpup.dismiss();
                    cinema_order.setSelected(false);
                }
            }
        });
    }

    private void sortByStepprice(List<CinameData> data) {
        Collections.sort(data, new Comparator<CinameData>() {
            @Override
            public int compare(CinameData lhs, CinameData rhs) {
                int distancResult = Double.compare(lhs.getStepPrice(), rhs.getStepPrice());
                if (distancResult == 0) {//当距离相同时价格最低的排在前面
                    distancResult = Double.compare(lhs.getDistance(), rhs.getDistance());
                }
                return distancResult;
            }
        });

    }

    private void sortByDistance(List<CinameData> data) {
        Collections.sort(data, new Comparator<CinameData>() {
            @Override
            public int compare(CinameData lhs, CinameData rhs) {
                int distancResult = Double.compare(lhs.getDistance(), rhs.getDistance());
                if (distancResult == 0) {//当距离相同时价格最低的排在前面
                    distancResult = Double.compare(lhs.getStepPrice(), rhs.getStepPrice());
                }
                return distancResult;
            }
        });
    }


    private void areaPress() {
        ListView arealist = new ListView(getActivity());
        countylist.add("全部");
        for (String county : cotuntyname) {
            countylist.add(county);
        }
        arrayadapter = new ArrayAdapter(getActivity(), android.R.layout.simple_expandable_list_item_1, countylist);
        arealist.setAdapter(arrayadapter);
        areapow = new PopupWindow(arealist, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        areapow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        areapow.showAsDropDown(cinema_area);
        arealist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    cinemaadapter.clear();
                    currentlist = newdata;
                    cinemaadapter.addAll(newdata);
                    cinema_area.setSelected(false);
                    cinema_area.setText(countylist.get(position));
                    areapow.dismiss();
                } else {
                    cinema_area.setText(countylist.get(position));
                    chooseTheCounty(newdata, countylist.get(position));

                }
            }
        });
    }

    private void chooseTheCounty(List<CinameData> cinamedata, String county) {
        if (countychoose != null) {
            countychoose.clear();
        }
        for (CinameData foreach : cinamedata) {
            if (foreach.getCountyname().equals(county)) {
                countychoose.add(foreach);
            }
        }
        areapow.dismiss();
        cinemaadapter.clear();
        currentlist = countychoose;
        cinemaadapter.addAll(countychoose);
        cinema_area.setSelected(false);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Cinema cinema = (Cinema) adapterView.getAdapter().getItem(position);同样是提取被点击行的对象的方法
        CinameData cinemadetaildata = currentlist.get(position);
        if (cinemadetaildata == null) {
            return;
        }
        Bundle bundle=new Bundle();
        Intent intent = new Intent(getActivity(), CinemaDetailActivity.class);
        intent.putExtra("cinemaname", cinemadetaildata.getCinemaname());
        intent.putExtra("cinemaid", cinemadetaildata.getCinemaid());
        intent.putExtra("cinemaaddress", cinemadetaildata.getAddress());
        intent.putExtra("cinemalongitude", cinemadetaildata.getLongitude());
        intent.putExtra("cinemalatitude", cinemadetaildata.getLatitude());
        intent.putExtra("cinemalowmovieid", cinemadetaildata.getLowmovieid());
        bundle.putSerializable("cinemamovieprice",cinemadetaildata.getMovieprice());
        bundle.putSerializable("cinemadata",cinemadetaildata);

        startActivity(intent);
    }


    class CinemaAdapter extends ArrayAdapter<CinameData> {

        private List<CinameData> data;

        public CinemaAdapter(Context context) {
            super(context, 0);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder vh;
            if (convertView == null) {
                convertView = View.inflate(parent.getContext(), R.layout.cinema_adapter_layout, null);
                vh = new ViewHolder(convertView);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }
            CinameData item = getItem(position);
            vh.cinema_name.setText(item.getCinemaname());
            vh.cinema_address.setText(item.getAddress());
            if (movieid != null) {
                vh.countdes.setText("最近场次" + item.getTodayOpis());
            } else {
                vh.countdes.setText(item.getCountdes());
            }
            vh.movie_price.setText("￥" + item.getStepPrice() + "起");
            vh.distance.setText(String.valueOf(item.getDistance()));
            if (item.getCpcount() > 1) {
                vh.cpcount.setText(item.getCpcount() + "家比价");
            }
            return convertView;
        }

        class ViewHolder {
            TextView cinema_name;
            TextView cinema_address;
            TextView countdes;
            TextView movie_price;
            TextView distance;
            TextView cpcount;

            public ViewHolder(View v) {
                cinema_name = (TextView) v.findViewById(R.id.cinema_name);
                cinema_address = (TextView) v.findViewById(R.id.cinema_address);
                countdes = (TextView) v.findViewById(R.id.countdes);
                movie_price = (TextView) v.findViewById(R.id.movie_price);
                distance = (TextView) v.findViewById(R.id.distance);
                cpcount = (TextView) v.findViewById(R.id.cpcount);
                v.setTag(this);
            }


        }
    }
}

