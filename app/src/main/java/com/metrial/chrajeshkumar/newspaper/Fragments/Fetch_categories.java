package com.metrial.chrajeshkumar.newspaper.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.metrial.chrajeshkumar.newspaper.Activities.Categories_details;
import com.metrial.chrajeshkumar.newspaper.Adapters.News_deails_adaper;
import com.metrial.chrajeshkumar.newspaper.Helper.Activiy_control;
import com.metrial.chrajeshkumar.newspaper.Helper.CustomeGridview;
import com.metrial.chrajeshkumar.newspaper.R;
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
    String for_fetch;

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
            case "sports":
                grid_categories.setAdapter(new News_deails_adaper(getActivity(), this.getResources().getStringArray(R.array.sports_news), Papers_icon.sports,Fetch_categories.this));
                break;
            case "technology":
                grid_categories.setAdapter(new News_deails_adaper(getActivity(), this.getResources().getStringArray(R.array.tech_news), Papers_icon.technologies,Fetch_categories.this));
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
        return null;
    }

    @Override
    public HashMap<String, String> params() {
        return null;
    }

    @Override
    public void activityCallback(HashMap<String, String> params) {
        Log.e("activiy callback is ","<><>");
        Intent intent = new Intent(getActivity(), Categories_details.class);
        intent.putExtra("params", params);
        intent.putExtra("from category",for_fetch);
        getActivity().startActivity(intent);
        getActivity().overridePendingTransition(R.anim.left_slide_in, R.anim.left_slide_out);
    }
}
