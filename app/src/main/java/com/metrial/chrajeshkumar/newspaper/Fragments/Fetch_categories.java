package com.metrial.chrajeshkumar.newspaper.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.metrial.chrajeshkumar.newspaper.Activities.Categories_details;
import com.metrial.chrajeshkumar.newspaper.Activities.Splash_screen;
import com.metrial.chrajeshkumar.newspaper.Adapters.News_deails_adaper;
import com.metrial.chrajeshkumar.newspaper.Helper.Activiy_control;
import com.metrial.chrajeshkumar.newspaper.Helper.CustomeGridview;
import com.metrial.chrajeshkumar.newspaper.R;
import com.metrial.chrajeshkumar.newspaper.Utils.ConStants;
import com.metrial.chrajeshkumar.newspaper.Utils.Control;
import com.metrial.chrajeshkumar.newspaper.Utils.Papers_icon;

import java.util.HashMap;

import butterknife.Bind;

/**
 * Created by ChRajeshKumar on 10/18/2016.
 */

public class Fetch_categories extends Fragment implements Activiy_control{
    @Bind(R.id.grid_categories)
    CustomeGridview grid_categories;
    View view;
    Class fromActivity;
   public static String for_fetch;
    HashMap<String, String> params = new HashMap<>();

    public Fetch_categories(String for_fetch)
    {
        this.for_fetch = for_fetch;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.categories, container, false);
        InitilizeViews(view);
        grid_categories.setNumColumns(2);
        switch (for_fetch)
        {
            case ConStants.SPORTS:
                grid_categories.setAdapter(new News_deails_adaper(getActivity(), this.getResources().getStringArray(R.array.sports_news), Papers_icon.sports,Fetch_categories.this,for_fetch,true));
                break;
            case ConStants.TECHNOLOGY:
                grid_categories.setAdapter(new News_deails_adaper(getActivity(), this.getResources().getStringArray(R.array.tech_news), Papers_icon.technologies,Fetch_categories.this,for_fetch,false));
                break;
            case ConStants.ENTERTAINMENT:
                grid_categories.setAdapter(new News_deails_adaper(getActivity(), this.getResources().getStringArray(R.array.entertainment), Papers_icon.entertainment,Fetch_categories.this,for_fetch,true));
                break;
        }

        grid_categories.setExpanded(true);

        return view;
    }

    public void InitilizeViews(View view) {
        grid_categories = (CustomeGridview) view.findViewById(R.id.grid_categories);
    }

    @Override
    public Class fromActivity() {
        return fromActivity;
    }

    @Override
    public HashMap<String, String> params() {
        return params;
    }

    @Override
    public void activityCallback(HashMap<String, String> params1) {
        Log.e("activiy callback is ","<><>"+Fetch_categories.for_fetch);
        //pending for code optimization
        Intent intent = new Intent(getActivity(), Categories_details.class);
        intent.putExtra("params", params1);
        getActivity().startActivity(intent);
        getActivity().overridePendingTransition(R.anim.left_slide_in, R.anim.left_slide_out);
    }


    @Override
    public void onPause() {
        super.onPause();
        unbindDrawables(view.findViewById(R.id.gridview_image));
        System.gc();

    }


    private void unbindDrawables(View view)
    {
        if (view.getBackground() != null)
        {
            view.getBackground().setCallback(null);
        }
        if (view instanceof ViewGroup && !(view instanceof AdapterView))
        {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++)
            {
                unbindDrawables(((ViewGroup) view).getChildAt(i));
            }
            ((ViewGroup) view).removeAllViews();
        }
    }
}
