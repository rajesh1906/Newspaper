package com.metrial.chrajeshkumar.newspaper.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.metrial.chrajeshkumar.newspaper.Activities.Categories_details;
import com.metrial.chrajeshkumar.newspaper.Fragments.Fetch_categories;
import com.metrial.chrajeshkumar.newspaper.Helper.Activiy_control;
import com.metrial.chrajeshkumar.newspaper.R;
import com.metrial.chrajeshkumar.newspaper.Utils.CheckNetwork;
import com.metrial.chrajeshkumar.newspaper.Utils.ConStants;
import com.metrial.chrajeshkumar.newspaper.Utils.Control;

import java.util.HashMap;

/**
 * Created by ChRajeshKumar on 10/18/2016.
 */

public class News_deails_adaper extends BaseAdapter {
    private Context mContext;
    private final String[] string;
    private final Integer[] Imageid;
    Animation side_out, slide_in_right;
    Fetch_categories fetch_categories;
    Activiy_control activiy_control;
    String for_fetch;

    int error;

    public News_deails_adaper(Context c, String[] string, Integer[] Imageid,Fetch_categories fetch_categories,String for_fetch) {
        mContext = c;
        this.Imageid = Imageid;
        this.string = string;
        this.fetch_categories = fetch_categories;
        this.activiy_control = fetch_categories;
        this.for_fetch = for_fetch;
        side_out = AnimationUtils.loadAnimation(mContext,
                R.anim.anim_slide_out_left);

        slide_in_right = AnimationUtils.loadAnimation(mContext,
                R.anim.anim_slide_in_right);

    }


    @Override
    public int getCount() {
        return Imageid.length;
    }

    @Override
    public Object getItem(int p) {
        return null;
    }

    @Override
    public long getItemId(int p) {
        return 0;
    }

    @Override
    public View getView(final int p, View convertView, ViewGroup parent) {
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.category_item, null);
            TextView textView = (TextView) grid.findViewById(R.id.gridview_text);
            ImageView imageView = (ImageView) grid.findViewById(R.id.gridview_image);
//            textView.setText(string[p]);
//            Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), Imageid.getResourceId(p, -1));
//            imageItems.add(new ImageItem(bitmap, "Image#" + i));
            textView.setText(string[p]);
            imageView.setImageResource(Imageid[p]);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (CheckNetwork.isOnline(mContext)) {

                        HashMap<String, String> params = new HashMap<String, String>();
                        params.put("position", "" + p);
                        params.put("from category",for_fetch);
                        Log.e("position is ","<><>"+p);

                        activiy_control.activityCallback(params);
                        /*Intent intent = new Intent(mContext, Categories_details.class);
                        intent.putExtra("params", params);
                        mContext.startActivity(intent);
                        Activity activity = (Activity)mContext;
                        activity.overridePendingTransition(R.anim.left_slide_in, R.anim.left_slide_out);*/

                    } else {
                        error = ConStants.NETWORK_CONNECTION_ERROR;
                        Control.control_flow(ConStants.DIALOG_CONTROL, mContext);
                    }


                }
            });
        } else {
            grid = (View) convertView;
        }

        return grid;
    }


}
