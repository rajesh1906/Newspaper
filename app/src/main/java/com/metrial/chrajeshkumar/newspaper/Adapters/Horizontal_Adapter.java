package com.metrial.chrajeshkumar.newspaper.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.metrial.chrajeshkumar.newspaper.Helper.HandlingViews;
import com.metrial.chrajeshkumar.newspaper.R;
import com.metrial.chrajeshkumar.newspaper.Utils.Papers_icon;

/**
 * Created by ChRajeshKumar on 9/14/2016.
 */
public class Horizontal_Adapter extends  RecyclerView.Adapter<Horizontal_Adapter.MyViewHolder> {

    private Context mContext;
    private final String[] string;
    private final Integer[] Imageid,english_channel,telugu_channel,hindi_channel,tamil_channel,malayalam_channel;

    private HandlingViews handlingViews;

    public Horizontal_Adapter(Context c,String[] string, Integer[] Imageid,Integer[] english_channel,Integer[] telugu_channel,Integer[] hindi_channel
                              ,Integer[] tamil_channel,Integer[] malayalam_channel) {
        mContext = c;
        this.Imageid = Imageid;
        this.string = string;
        this.english_channel = english_channel;
        this.telugu_channel = telugu_channel;
        this.hindi_channel = hindi_channel;
        this.tamil_channel = tamil_channel;
        this.malayalam_channel = malayalam_channel;
        this.handlingViews = (HandlingViews) this.mContext;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        ImageView icon,img_play_icon;
        RelativeLayout rel_view;
        GridView gridview;


        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.gridview_text);
            icon = (ImageView) view.findViewById(R.id.gridview_image);
            rel_view = (RelativeLayout) view.findViewById(R.id.linearLayout);
            gridview = (GridView)view.findViewById(R.id.gridview);
            img_play_icon = (ImageView)view.findViewById(R.id.img_play_icon);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.horizontal_items, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        if(position == 0)
        {
            Log.e("position is ","<>0<>");
            holder.gridview.setAdapter(new Channel_adapter(mContext, mContext.getResources().getStringArray(R.array.english_paper), Papers_icon.english_news_channel_icons));

        }else if(position ==1)
        {
            Log.e("position is ","<>1<>");
            holder.gridview.setAdapter(new Channel_adapter(mContext, mContext.getResources().getStringArray(R.array.telugu_paper), Papers_icon.telugu_news_channel_icons));
        }else if(position ==2)
        {
            Log.e("position is ","<>2<>");
            holder.gridview.setAdapter(new Channel_adapter(mContext, mContext.getResources().getStringArray(R.array.hindi_paper), Papers_icon.hindi_news_channel_icons));
        }else if(position == 3)
        {
            Log.e("position is ","<>3<>");
            holder.gridview.setAdapter(new Channel_adapter(mContext, mContext.getResources().getStringArray(R.array.tamil_paper), Papers_icon.tamil_news_channel_icons));
        }else if(position == 4)
        {
            Log.e("position is ","<>4<>");
            holder.gridview.setAdapter(new Channel_adapter(mContext, mContext.getResources().getStringArray(R.array.malayalam_paper), Papers_icon.malayalam_news_channel_icons));

        }



        holder.title.setText(string[position]);
        holder.icon.setImageResource(Imageid[position]);
        holder.img_play_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("coming to on click is ","><<><");
                handlingViews.implementation(string[0],position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Imageid.length;
    }
}
