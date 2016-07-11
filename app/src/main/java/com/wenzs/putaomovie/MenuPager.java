package com.wenzs.putaomovie;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/7/3.
 */
public class MenuPager extends Fragment implements View.OnClickListener {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.menu_pager_layout, null);
        v.findViewById(R.id.layout_login).setOnClickListener(this);
        v.findViewById(R.id.tv_order_weixiaofei).setOnClickListener(this);
        v.findViewById(R.id.tv_order_daifukuan).setOnClickListener(this);
        v.findViewById(R.id.tv_order_daipingjia).setOnClickListener(this);
        v.findViewById(R.id.tv_order_tuikuan).setOnClickListener(this);
        v.findViewById(R.id.btn_my_msg).setOnClickListener(this);
        v.findViewById(R.id.btn_setting).setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_login:
                //去登录
                Toast.makeText(getActivity(),"登录",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_order_weixiaofei:
                //未消费
                Toast.makeText(getActivity(),"未消费",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_order_daifukuan:
                //待付款
                Toast.makeText(getActivity(),"待付款",Toast.LENGTH_SHORT).show();
                break;

            case R.id.tv_order_daipingjia:
                //待评价
                Toast.makeText(getActivity(),"待评价",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_order_tuikuan:
                //退款
                Toast.makeText(getActivity(),"退款",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_my_msg:
                //我的消息
                Toast.makeText(getActivity(),"我的消息",Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_setting:
                //设置
                Toast.makeText(getActivity(),"设置",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
