package com.wenzs.putaomovie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class CinemaDetailActivity extends AppCompatActivity implements MyListview.OnListViewChangedListener {

    public int threholdScroll = 200;
    private ImageLoader imageloader;
    private HeadLayout header;
    private MyListview the_main_lv;
    private String cinemaname;
    private String cinemaid;
    private String cinemaaddress;
    private String cinemalongitude;
    private String cinemalatitude;
    private String cinemalowmovieid;
    private CinameData cinemadata;
    private CinemaMoviePrice[] cinemamovieprice;
    private List<MovieData> moviedataforcinema = new ArrayList<>();
    private GallyAdapter gallyadapter;
    private ListViewAdapter listviewadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema_detail);
        header = (HeadLayout) findViewById(R.id.header);
        the_main_lv = (MyListview) findViewById(R.id.the_main_lv);

        Bundle bundle = new Bundle();
        cinemaname = getIntent().getStringExtra("cinemaname");
        cinemaid = getIntent().getStringExtra("cinemaid");
        cinemaaddress = getIntent().getStringExtra("cinemaaddress");
        cinemalongitude = getIntent().getStringExtra("cinemalongitude");
        cinemalatitude = getIntent().getStringExtra("cinemalatitude");
        cinemamovieprice = (CinemaMoviePrice[]) bundle.getSerializable("cinemamovieprice");
        cinemalowmovieid = getIntent().getStringExtra("cinemalowmovieid");
        cinemadata= (CinameData) bundle.getSerializable("cinemadata");

        header.settitle(cinemaname);
        header.setSubtitle(cinemaaddress);
        header.setLocationIcon();
        header.setBackgroundvisible(0);
        the_main_lv.setOnListViewChangedListener(this);
        cinemaData();
        addHeadViewForlv();

    }

    private void addHeadViewForlv() {

        imageloader=new ImageLoader(MyRequestQueue.getInstance().getRequestQueue(),new BitmapCache());
        gallyadapter=new GallyAdapter(moviedataforcinema,imageloader);

        View inflate = View.inflate(this, R.layout.headview_for_listview, null);
        Gallery the_movies_logo = (Gallery) inflate.findViewById(R.id.the_movies_logo);
        TextView the_movie_name = (TextView) inflate.findViewById(R.id.the_movie_name);
        TextView the_movie_mark = (TextView) inflate.findViewById(R.id.the_movie_mark);
        TextView the_movie_type = (TextView) inflate.findViewById(R.id.the_movie_type);
        ScrollView the_date = (ScrollView) inflate.findViewById(R.id.the_date);

        int position=gallyadapter.getSelectedid();
        the_movie_name.setText(moviedataforcinema.get(position).getMoviename());
        the_movie_mark.setText(moviedataforcinema.get(position).getGeneralmark()+"分");
        the_movie_type.setText(moviedataforcinema.get(position).getType());

        the_movies_logo.setAdapter(gallyadapter);
        the_movies_logo.setSpacing(50);
        the_movies_logo.setGravity(Gravity.CENTER_HORIZONTAL);
        the_movies_logo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                gallyadapter.setSelectedid(position);
            }
        });
        the_main_lv.addHeaderView(inflate, null, false);

        listviewadapter=new ListViewAdapter(this,moviedataforcinema,cinemadata);
        the_main_lv.setAdapter(listviewadapter);

    }

    public void cinemaData() {
        for (int i = 0; i < cinemamovieprice.length; i++) {
            MoviePager moviepager = new MoviePager();

            for (MovieData moviedata : moviepager.movieData) {
                if (moviedata.getId() == cinemamovieprice[i].getPtMovieId()) {
                    moviedataforcinema.add(moviedata);
                }
            }
        }
    }

    //监听机制可能有问题，
    @Override
    public void getScrolly(View view, int FirstVisiblePosition) {
        if (view == null) {
            return;
        }
        int top = view.getTop();
        Log.e("listview第一个子view的顶部高度", "getScrolly:" + view.getTop());
        Log.e("第一个可见子view的位置", "getScrolly: " + FirstVisiblePosition);
        int t = FirstVisiblePosition * view.getHeight() - top;
        if (t < threholdScroll) {
            header.setBackgroundvisible(t);
        } else {
            header.setBackgroundinvisible(255);
        }
    }




}
