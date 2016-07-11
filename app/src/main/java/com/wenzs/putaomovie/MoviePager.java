package com.wenzs.putaomovie;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

/**
 * Created by Administrator on 2016/6/29.
 */
public class MoviePager extends Fragment implements Response.Listener<String>, Response.ErrorListener, AdapterView.OnItemClickListener {

    GridView movie_gridview;
    MovieAdapter movieadapter;
    StringRequest stringrequest;
    MovieData[] movieData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.movie_pager_layout, null);
        movie_gridview = (GridView) inflate.findViewById(R.id.movie_gridview);
        movieadapter = new MovieAdapter(getActivity());
        movie_gridview.setAdapter(movieadapter);
        initate();
        movie_gridview.setOnItemClickListener(this);
        return inflate;
    }

    private void initate() {
        String url = null;
        try {
            url = "http://api.putao.so/sbiz/movie/list/?&citycode=" + URLEncoder.encode("深圳", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        stringrequest = new StringRequest(url, this, this);
        MyRequestQueue.getInstance().getRequestQueue().add(stringrequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getActivity(), "联网失败", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onResponse(String response) {
        Movie movie = JSONObject.parseObject(response, Movie.class);
        if (movie.getRet_code().equals("0000")) {
            movieData= movie.getData();
            if (movieData != null) {
                movieadapter.addAll(movieData);
            }

        } else {
            Toast.makeText(getActivity(), "movie解析失败", Toast.LENGTH_SHORT).show();
            Log.d("fail", "movie解析失败");
        }

    }


    @Override
    public void onDestroy() {
        if (stringrequest != null) {
            stringrequest.cancel();
        }
        super.onDestroy();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MovieData data=movieData[position];
        Intent intent=new Intent(getActivity(),MovieDetailActivity.class);
        intent.putExtra("data",data);
        startActivity(intent);
    }
}

class MovieAdapter extends ArrayAdapter<MovieData> {
    ImageLoader imageloder;

    public MovieAdapter(Context context) {
        super(context, 0);
        imageloder = new ImageLoader(MyRequestQueue.getInstance().getRequestQueue(), new BitmapCache());
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.movie_adapter_layout, null);
            vh = new ViewHolder(convertView);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        MovieData item = getItem(position);
        vh.logo_iv.setImageUrl(item.getLogo(), imageloder);
        vh.moviename_tv.setText(item.getMoviename());
        vh.moviemark_tv.setText(item.getGeneralmark());
        vh.star.setRating(Float.parseFloat(item.getGeneralmark()) / 2);
        Date date=new Date();
        if (date.getTime()<item.getReleasedate()) {
            vh.moviesell_tv.setText("预售");
        }else {
            vh.moviesell_tv.setText("购票");
        }

        vh.movie_gcedition.setText(item.getGcedition());

        return convertView;
    }

    class ViewHolder {
        NetworkImageView logo_iv;
        TextView moviename_tv;
        TextView moviemark_tv;
        TextView moviesell_tv;
        RatingBar star;
        TextView movie_gcedition;

        public ViewHolder(View v) {
            logo_iv = (NetworkImageView) v.findViewById(R.id.logo_iv);
            moviename_tv = (TextView) v.findViewById(R.id.moviename_tv);
            moviemark_tv = (TextView) v.findViewById(R.id.moviemark_tv);
            moviesell_tv = (TextView) v.findViewById(R.id.moviesell_tv);
            star = (RatingBar) v.findViewById(R.id.star);
            movie_gcedition = (TextView) v.findViewById(R.id.movie_gcedition);
            v.setTag(this);
        }


    }
}
