package com.wenzs.putaomovie;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Administrator on 2016/7/5.
 */
public class PrevuePlayer extends AppCompatActivity{
    final static String TAG="PrevuePlayer";
    private HeadLayout header_video;
    private WebView webview_video;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prevue_player_layout);

        String prevueurl = getIntent().getStringExtra("Prevueurl");
        String moviename = getIntent().getStringExtra("moviename");
        header_video= (HeadLayout) findViewById(R.id.header_video);
        header_video.settitle(moviename);
        webview_video= (WebView) findViewById(R.id.webview_video);
        webview_video.loadUrl(prevueurl);
        webview_video.getSettings().setJavaScriptEnabled(true);
        webview_video.setWebViewClient(new WebViewClient() {

            @Override
            public void onLoadResource(WebView view, String url) {
                if (url.contains(".mp4?")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);//隐式intent启动系统自带视频播放器
                    intent.setDataAndType(Uri.parse(url), "video/mp4");
                    startActivity(intent);
                    finish();
                    return;
                }
                Log.d(TAG, "onLoadResource() called with: " + "view = [" + view + "], url = [" + url + "]");
                super.onLoadResource(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webview_video.destroy();

    }
}
