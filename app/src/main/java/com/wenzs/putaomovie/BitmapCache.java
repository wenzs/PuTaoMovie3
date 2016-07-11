package com.wenzs.putaomovie;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by Administrator on 2016/6/29.
 */
public class BitmapCache implements ImageLoader.ImageCache {
    LruCache<String,Bitmap> lurcache;
    public BitmapCache(){
        lurcache=new LruCache<String,Bitmap>(10*1024*1024){
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getHeight()*bitmap.getRowBytes();
            }
        };
    }
    @Override
    public Bitmap getBitmap(String url) {
        return lurcache.get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        lurcache.put(url,bitmap);

    }
}
