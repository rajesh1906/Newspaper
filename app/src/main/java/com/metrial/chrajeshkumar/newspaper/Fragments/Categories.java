package com.metrial.chrajeshkumar.newspaper.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.metrial.chrajeshkumar.newspaper.Adapters.Categories_adapter;
import com.metrial.chrajeshkumar.newspaper.Helper.CustomeGridview;
import com.metrial.chrajeshkumar.newspaper.R;
import com.metrial.chrajeshkumar.newspaper.Utils.Papers_icon;

import butterknife.Bind;

/**
 * Created by ChRajeshKumar on 10/18/2016.
 */

public class Categories extends Fragment {

    @Bind(R.id.grid_categories)
    CustomeGridview grid_categories;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.categories, container, false);
        InitilizeViews(view);
        grid_categories.setNumColumns(2);
        grid_categories.setAdapter(new Categories_adapter(getActivity(), this.getResources().getStringArray(R.array.categories), Papers_icon.categories));
        grid_categories.setExpanded(true);

        return view;
    }

    public void InitilizeViews(View view) {
        grid_categories = (CustomeGridview) view.findViewById(R.id.grid_categories);
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
