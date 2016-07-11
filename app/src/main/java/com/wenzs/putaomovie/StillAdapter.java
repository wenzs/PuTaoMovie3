package com.wenzs.putaomovie;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

/**
 * Created by Administrator on 2016/7/5.
 */
public class StillAdapter extends RecyclerView.Adapter<ViewHolder>  {

    private ImageLoader imageloader;
    private String[] stills;
    public StillAdapter(ImageLoader imageloader,String[] stills){
        this.imageloader=imageloader;
        this.stills=stills;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        return new ViewHolder(View.inflate(context, R.layout.recycle_adapter_layout,null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        String still = stills[position];
        holder.img_movie_dtl_stills.setImageUrl(still,imageloader);
        holder.img_movie_dtl_stills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onrecycleadapterlistener != null) {
                    onrecycleadapterlistener.onItemclick(v,position);
                }

            }
        });
    }



    @Override
    public int getItemCount() {
        return stills.length;
    }


OnRecycleAdapterListener onrecycleadapterlistener;

    public OnRecycleAdapterListener getOnrecycleadapterlistener() {
        return onrecycleadapterlistener;
    }

    public void setOnrecycleadapterlistener(OnRecycleAdapterListener onrecycleadapterlistener) {
        this.onrecycleadapterlistener = onrecycleadapterlistener;
    }

    public static interface OnRecycleAdapterListener {
        void onItemclick(View v, int p);
    }


}

class ViewHolder extends RecyclerView.ViewHolder{
    NetworkImageView img_movie_dtl_stills;
    public ViewHolder(View itemView) {
        super(itemView);
        img_movie_dtl_stills= (NetworkImageView) itemView.findViewById(R.id.img_movie_dtl_stills);
    }
}

