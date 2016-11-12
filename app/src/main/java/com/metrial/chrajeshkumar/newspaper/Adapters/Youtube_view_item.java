package com.metrial.chrajeshkumar.newspaper.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.metrial.chrajeshkumar.newspaper.Helper.HandlingViews;
import com.metrial.chrajeshkumar.newspaper.Helper.Update_youtube_timer;
import com.metrial.chrajeshkumar.newspaper.Pojos.MyPojo;
import com.metrial.chrajeshkumar.newspaper.R;

/**
 * Created by ChRajeshKumar on 9/14/2016.
 */
public class Youtube_view_item extends RecyclerView.Adapter<Youtube_view_item.MyViewHolder>
{
    private Context mContext;
    MyPojo pojo= new MyPojo();
    private HandlingViews handlingViews;

    public Youtube_view_item(Context c, MyPojo pojo) {
        mContext = c;
        this.pojo = pojo;
Log.e("coming to adapter is ","<><"+pojo);
        this.handlingViews = (HandlingViews) this.mContext;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title,description;
        ImageView icon;
        RelativeLayout rel_view;



        public MyViewHolder(View view) {
            super(view);
            icon = (ImageView) view.findViewById(R.id.img_thumb);
            title = (TextView)view.findViewById(R.id.gridview_text);
            description = (TextView) view.findViewById(R.id.description);
            rel_view = (RelativeLayout)view.findViewById(R.id.rel_view);
        }
    }




    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.youtube_view_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

//        holder.title.setText(string[position]);
//        holder.icon.setImageResource(pojo.getItems()[position].getSnippet().getThumbnails().getaDefault().getUrl());

        Log.e("coming to on bind view holder","<><"+pojo.getItems().get(0).getSnippet().getThumbnails().gethigh());
        holder.title.setText(pojo.getItems().get(position).getSnippet().getTitle());
        holder.description.setText(pojo.getItems().get(position).getSnippet().getDescription());
        Glide.with(mContext).load(pojo.getItems().get(position).getSnippet().getThumbnails().gethigh().getUrl())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.icon);
        holder.rel_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlingViews.conncetion(pojo.getItems().get(position).getId().getVideoId(),pojo.getItems().get(position).getSnippet().getTitle(),pojo.getItems().get(position).getSnippet().getDescription());
            }
        });
    }

    @Override
    public int getItemCount() {
        return pojo.getItems().size();
    }

    public void SetTimer(String response){


    }
}

