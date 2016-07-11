package com.wenzs.putaomovie;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by Administrator on 2016/7/5.
 */
public class MyScrollView extends ScrollView{
    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (onscrollchangedlistener!= null) {
            onscrollchangedlistener.onScrollChanged(t-oldt,t);
        }
    }


    public OnScrollChangedListener getOnscrollchangedlistener() {
        return onscrollchangedlistener;
    }

    public void setOnscrollchangedlistener(ScrollView scrollview,MyScrollView.OnScrollChangedListener onscrollchangedlistener) {
        this.onscrollchangedlistener = onscrollchangedlistener;
    }

    private OnScrollChangedListener onscrollchangedlistener;

    public interface OnScrollChangedListener {
        void onScrollChanged(int Distance,int t);
    }
}
