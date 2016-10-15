package com.metrial.chrajeshkumar.newspaper.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.metrial.chrajeshkumar.newspaper.Helper.HandlingViews;
import com.metrial.chrajeshkumar.newspaper.Helper.Recycle_item_view;
import com.metrial.chrajeshkumar.newspaper.R;

/**
 * Created by ChRajeshKumar on 9/28/2016.
 */
public class Channel_list_adapter extends  RecyclerView.Adapter<Channel_list_adapter.MyViewHolder>  {
    private Context mContext;
    private final String[] string;
    private final Integer[] Imageid;
    private HandlingViews handlingViews;
    private Recycle_item_view recycle_item_view;
    String from_whare = "channel";
    View itemView;

    public Channel_list_adapter(Context c,String[] string, Integer[] Imageid) {
        mContext = c;
        this.Imageid = Imageid;
        this.string = string;
        this.handlingViews = (HandlingViews) this.mContext;
        this.recycle_item_view = (Recycle_item_view)this.mContext;
    }
    public Channel_list_adapter(Context c,String[] string, Integer[] Imageid,String from_whare) {
        mContext = c;
        this.Imageid = Imageid;
        this.string = string;
        this.handlingViews = (HandlingViews) this.mContext;
        this.recycle_item_view = (Recycle_item_view)this.mContext;
        this.from_whare = from_whare;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title,txt_title,txt_description;
        ImageView icon;
        RelativeLayout rel_view;



        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.gridview_text);
            icon = (ImageView) view.findViewById(R.id.gridview_image);
            rel_view = (RelativeLayout) view.findViewById(R.id.linearLayout);
            txt_title = (TextView)view.findViewById(R.id.txt_title);
            txt_description = (TextView)view.findViewById(R.id.txt_description);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(from_whare.equals("channel")) {
             itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.channel_item, parent, false);
        }else{
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.share_icons_list, parent, false);
        }

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.txt_title.setText(string[position]);
        holder.icon.setImageResource(Imageid[position]);
        holder.rel_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recycle_item_view.return_control(string[position]);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Imageid.length;
    }
}
