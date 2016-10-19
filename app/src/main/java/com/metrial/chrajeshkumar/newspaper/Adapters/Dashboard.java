package com.metrial.chrajeshkumar.newspaper.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.metrial.chrajeshkumar.newspaper.Helper.Activiy_control;
import com.metrial.chrajeshkumar.newspaper.Helper.Appcontants;
import com.metrial.chrajeshkumar.newspaper.Helper.HandlingViews;
import com.metrial.chrajeshkumar.newspaper.R;
import com.metrial.chrajeshkumar.newspaper.Utils.CheckNetwork;
import com.metrial.chrajeshkumar.newspaper.Utils.ConStants;
import com.metrial.chrajeshkumar.newspaper.Utils.Control;

import java.util.HashMap;

/**
 * Created by ChRajeshKumar on 8/31/2016.
 */
public class Dashboard extends BaseAdapter implements HandlingViews {
    private Context mContext;
    private final String[] string;
    private final Integer[] Imageid;
    Animation side_out, slide_in_right;
    Activiy_control activiy_control;
    int error;
    public Dashboard(Context c, String[] string, Integer[] Imageid) {
        mContext = c;
        this.Imageid = Imageid;
        this.string = string;
        side_out = AnimationUtils.loadAnimation(mContext,
                R.anim.anim_slide_out_left);

        slide_in_right = AnimationUtils.loadAnimation(mContext,
                R.anim.anim_slide_in_right);
        activiy_control = (Activiy_control) c;
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
            grid = inflater.inflate(R.layout.dash_board_item, null);
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
                        params.put("category", string[0]);
                        params.put("position", "" + p);
                        activiy_control.activityCallback(params);
//                        Intent intent = new Intent(mContext, Dashboard_Detail.class);
//                        intent.putExtra("category", string[0]);
//                        intent.putExtra("position", p);
//                        mContext.startActivity(intent);
//                        Activity activity = (Activity) mContext;
//                        activity.overridePendingTransition(R.anim.left_slide_in, R.anim.left_slide_out);
                    } else {
                        error = ConStants.NETWORK_CONNECTION_ERROR;
                        Control.control_flow(ConStants.DIALOG_CONTROL,mContext);
                    }


                }
            });
        } else {
            grid = (View) convertView;
        }

        return grid;
    }

    @Override
    public void implementation(String message, int position) {

    }

    @Override
    public void conncetion(String value,String title,String desc) {

    }

    @Override
    public int getContantField() {
        return error;
    }

    @Override
    public void dialog_control() {

    }
}
