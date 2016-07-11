package com.wenzs.putaomovie;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/7/5.
 */
public class HeadLayout extends LinearLayout implements View.OnClickListener {

    private ImageView finish_bt;
    private TextView title_tv;
    private View inflate;
    private TextView subtitle_tv;
    private ImageView location_icon;
    private RelativeLayout movie_detail_header;
    private int backgroundvisible;
    private int backgroundinvisible;

    public HeadLayout(Context context) {
        super(context);
    }

    public HeadLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        inflate = View.inflate(getContext(), R.layout.head_layout, this);
        finish_bt = (ImageView) inflate.findViewById(R.id.finish_bt);
        title_tv = (TextView) inflate.findViewById(R.id.title_tv);
        subtitle_tv= (TextView) inflate.findViewById(R.id.subtitle_tv);
        movie_detail_header = (RelativeLayout) inflate.findViewById(R.id.movie_header);
        location_icon= (ImageView) inflate.findViewById(R.id.location_icon);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.moviedetailhead);
        String string = typedArray.getString(R.styleable.moviedetailhead_title);
        Drawable imager = typedArray.getDrawable(R.styleable.moviedetailhead_imager);
        backgroundvisible = typedArray.getInt(R.styleable.moviedetailhead_backgroundvisible, 0);
        backgroundinvisible = typedArray.getInt(R.styleable.moviedetailhead_backgroundinvisible, 0);
        typedArray.recycle();

        finish_bt.setOnClickListener(this);
        inflate.setBackgroundColor(Color.WHITE);

    }

    @Override
    public void onClick(View v) {
        ((Activity) getContext()).finish();
    }

    public void settitle(String string) {
        title_tv.setText(string);
    }

    public void setSubtitle(String string){
        subtitle_tv.setVisibility(VISIBLE);
        subtitle_tv.setText(string);
    }

    public void setLocationIcon(){
        location_icon.setVisibility(VISIBLE);
    }

    public int getBackgroundvisible() {
        return backgroundvisible;
    }

    public void setBackgroundvisible(int backgroundvisible) {
        title_tv.setTextColor(Color.WHITE);
        finish_bt.setImageResource(R.drawable.putao_icon_back);
        location_icon.setImageResource(R.drawable.putao_icon_taxi_position);
        setBackgroundAlpha(backgroundvisible);
        this.backgroundvisible = backgroundvisible;

    }

    public int getBackgroundinvisible() {
        return backgroundinvisible;
    }

    public void setBackgroundinvisible(int backgroundinvisible) {
        title_tv.setTextColor(Color.BLACK);
        finish_bt.setImageResource(R.drawable.putao_arrow_left_press);
        location_icon.setImageResource(R.drawable.putao_icon_quick_zbss_s);
        setBackgroundAlpha(backgroundinvisible);
        this.backgroundinvisible = backgroundinvisible;
    }

    public void setBackgroundAlpha(int t) {
        inflate.getBackground().setAlpha(t);
    }
}
