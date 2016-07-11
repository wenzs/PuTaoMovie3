package com.wenzs.putaomovie;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;

import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2016/7/5.
 */
public class MovieDetailActivity extends AppCompatActivity implements MyScrollView.OnScrollChangedListener, View.OnClickListener, Response.ErrorListener, Response.Listener<String>,StillAdapter.OnRecycleAdapterListener {

    public int threholdScroll = 200;
    private MovieData data;
    private ImageLoader imageloader;
    private ImageView mMovieLogo;
    private RatingBar mRatingbar;
    private TextView movieMark;
    private TextView movieType;
    private TextView movieLanguage;
    private TextView movieLength;
    private TextView movieReleaseDate;
    private RecyclerView mRvStills;
    private TextView movieDirector;
    private TextView movieActors;
    private TextView movieContent;
    private TextView movieComment;
    private String[] mStills;
    private View movieTopLayout;
    private HeadLayout mHeaderLayout;
    private MyScrollView myscrollview;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail_layout);

        data = (MovieData) getIntent().getSerializableExtra("data");
        imageloader=new ImageLoader(MyRequestQueue.getInstance().getRequestQueue(),new BitmapCache());
        initial();
       urlRequest();

    }

    private void urlRequest() {
        StringRequest stringrequest=null;
        String url="http://api.putao.so/sbiz/movie/detail?movieid=" + data.getMovieid();
        stringrequest=new StringRequest(url,this,this);
        MyRequestQueue.getInstance().getRequestQueue().add(stringrequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(String response) {
        MovieDetial moviedetail=JSONObject.parseObject(response,MovieDetial.class);
        if (moviedetail != null&&moviedetail.getRet_code().equals("0000")) {
        MovieData moviedata=moviedetail.getData();
            forRecycle(moviedata);
        }


    }

    private void forRecycle(MovieData movieData) {
        movieContent.setText(movieData.getContent());
        mStills=movieData.getStill().split(",");
        mRvStills.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        StillAdapter stilladapter=new StillAdapter(imageloader,mStills);
        stilladapter.setOnrecycleadapterlistener(this);
        mRvStills.setAdapter(stilladapter);
    }

    @Override
    public void onItemclick(View v, int p) {
        Intent intent=new Intent(this,StillShows.class);
        intent.putExtra("moviename",data.getMoviename());
        intent.putExtra("position",p);
        intent.putExtra("mSills",mStills);
        startActivity(intent);
        finish();
    }

    private void initial() {
        mHeaderLayout = (HeadLayout) findViewById(R.id.header);
        mMovieLogo = (ImageView) findViewById(R.id.movie_logo);//电影海报
        mRatingbar = (RatingBar) findViewById(R.id.movie_star);//影片得分ratingbar表现
        movieMark = (TextView) findViewById(R.id.movie_general_mark);//影片分数
        movieType = (TextView) findViewById(R.id.movie_type);//影片类型
        movieLanguage = (TextView) findViewById(R.id.movie_language);//电影语言
        movieReleaseDate = (TextView) findViewById(R.id.movie_release_date);//上映日期
        movieLength = (TextView) findViewById(R.id.movie_length_state);//时长
        movieComment = (TextView) findViewById(R.id.movie_highlight);//一句话影评
        mRvStills = (RecyclerView) findViewById(R.id.movie_dtl_still_list);//剧照
        movieDirector = (TextView) findViewById(R.id.movie_director);//导演
        movieActors = (TextView) findViewById(R.id.movie_actors);//演员
        movieContent = (TextView) findViewById(R.id.movie_content);//剧情
        movieTopLayout = findViewById(R.id.movie_detail_header);//高斯模糊背景布局
        myscrollview= (MyScrollView) findViewById(R.id.movie_scroll_view);

        myscrollview.setOnscrollchangedlistener(myscrollview,this);
        findViewById(R.id.btn_play_video).setOnClickListener(this);
        findViewById(R.id.movie_select_seat).setOnClickListener(this);

        mHeaderLayout.settitle(data.getMoviename());
        mHeaderLayout.setBackgroundvisible(0);
        mRatingbar.setRating(Float.parseFloat(data.getGeneralmark())/2);
        movieMark.setText(data.getGeneralmark()+"分");
        movieType.setText(data.getType());
        movieLanguage.setText(data.getLanguage());
        movieReleaseDate.setText(new SimpleDateFormat("yyyy年mm月dd日").format(data.getReleasedate())+"上映");
        movieLength.setText(data.getLength());
        movieComment.setText(data.getHighlight());
        movieDirector.setText(data.getDirector());
        movieActors.setText(data.getActors());

        imageloader.get(data.getLogo(), new ImageLoader.ImageListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                Bitmap bitmap = response.getBitmap();
                if (bitmap != null) {
                    mMovieLogo.setImageBitmap(bitmap);
                    Bitmap prebitmap=Bitmap.createScaledBitmap(bitmap,bitmap.getWidth()/4,bitmap.getHeight()/4,true);
                    Canvas canvas=new Canvas(prebitmap);
                    canvas.drawColor(Color.parseColor("#88000000"));
                    Bitmap gaosiBg = MovieBitmapUtils.fastblur(prebitmap, 2);
                    movieTopLayout.setBackgroundDrawable(new BitmapDrawable(gaosiBg));
                }


            }
        });

    }

    @Override
    public void onScrollChanged(int oldt, int t) {
        Log.e("透明度测试", "移动距离~~~~~~~~~~~~~~~~~~~~~" + t);
        if (t < threholdScroll) {
            mHeaderLayout.setBackgroundvisible(t);
        } else {
            mHeaderLayout.setBackgroundinvisible(255);
        }

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_play_video:
                Intent intent=new Intent(this,PrevuePlayer.class);
                intent.putExtra("Prevueurl",data.getVideoUrl());
                intent.putExtra("moviename",data.getMoviename());
                startActivity(intent);
                break;
            case R.id.movie_select_seat:
                intent = new Intent(this, CinemaSelectActivity.class);
                intent.putExtra("moviename",data.getMoviename());
                intent.putExtra("movieid",data.getMovieid());
                startActivity(intent);
                break;
        }
    }

}
