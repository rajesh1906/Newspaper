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
import com.metrial.chrajeshkumar.newspaper.R;

/**
 * Created by ChRajeshKumar on 9/1/2016.
 */
public class Navigation_items extends  RecyclerView.Adapter<Navigation_items.MyViewHolder> {

    private Context mContext;
    private final String[] string;
    private final Integer[] Imageid;
    private HandlingViews handlingViews;

    public Navigation_items(Context c,String[] string, Integer[] Imageid) {
        mContext = c;
        this.Imageid = Imageid;
        this.string = string;
        this.handlingViews = (HandlingViews) this.mContext;
    }

public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView title;
    ImageView icon;
    RelativeLayout rel_view;



    public MyViewHolder(View view) {
        super(view);
        title = (TextView) view.findViewById(R.id.title);
        icon = (ImageView) view.findViewById(R.id.icon);
        rel_view = (RelativeLayout) view.findViewById(R.id.rel_view);
    }
}




    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.navigation_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.title.setText(string[position]);
        holder.icon.setImageResource(Imageid[position]);
        holder.rel_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlingViews.implementation(string[0],position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Imageid.length;
    }
}
