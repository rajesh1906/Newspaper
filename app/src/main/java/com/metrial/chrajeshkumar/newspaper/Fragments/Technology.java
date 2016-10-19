package com.metrial.chrajeshkumar.newspaper.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.metrial.chrajeshkumar.newspaper.Adapters.News_deails_adaper;
import com.metrial.chrajeshkumar.newspaper.Helper.CustomeGridview;
import com.metrial.chrajeshkumar.newspaper.R;
import com.metrial.chrajeshkumar.newspaper.Utils.Papers_icon;

import butterknife.Bind;

/**
 * Created by ChRajeshKumar on 10/18/2016.
 */

public class Technology extends Fragment {
    @Bind(R.id.grid_categories)
    CustomeGridview grid_categories;


    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.categories, container, false);
        InitilizeViews(view);
        grid_categories.setNumColumns(2);
        grid_categories.setAdapter(new News_deails_adaper(getActivity(), this.getResources().getStringArray(R.array.tech_news), Papers_icon.technologies));
        grid_categories.setExpanded(true);

        return view;
    }

    public void InitilizeViews(View view) {
        grid_categories = (CustomeGridview) view.findViewById(R.id.grid_categories);
    }
}
