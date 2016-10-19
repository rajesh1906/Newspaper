package com.metrial.chrajeshkumar.newspaper.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.metrial.chrajeshkumar.newspaper.Helper.HandlingViews;
import com.metrial.chrajeshkumar.newspaper.Helper.Webview_implementation;
import com.metrial.chrajeshkumar.newspaper.R;
import com.metrial.chrajeshkumar.newspaper.Utils.Endpoints;
import com.metrial.chrajeshkumar.newspaper.smoothprogress.SmoothProgressDrawable;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ChRajeshKumar on 10/18/2016.
 */

public class Categories_details extends AppCompatActivity implements HandlingViews {

    @Bind(R.id.webview)
    WebView webview;
    int position;
    String endpoint;
    @Bind(R.id.progress_download_google)
    ProgressBar progress_download_google;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dash_board_detail);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        HashMap<String, String> hashMap = (HashMap<String, String>) intent.getSerializableExtra("params");


        position = Integer.parseInt(hashMap.get("position"));
        progress_download_google.setVisibility(View.VISIBLE);
        progress_download_google.setIndeterminateDrawable(new SmoothProgressDrawable.Builder(this).interpolator(new AccelerateInterpolator()).build());
        progress_download_google.getIndeterminateDrawable().setColorFilter(
                getResources().getColor(R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);

        switch (position){
            case 0:
                endpoint = Endpoints.bbc_technology;
                break;
            case 1:
                endpoint = Endpoints.gadget_360;
                break;
            case 2:
                endpoint = Endpoints.info_world;
                break;
            case 3:
                endpoint = Endpoints.mobile_tech_today;
                break;
            case 4:
                endpoint = Endpoints.tech_news_world;
                break;
            case 5:
                endpoint = Endpoints.techgig;
                break;
        }

        new Webview_implementation().startWebView(endpoint,webview,Categories_details.this);
    }

    @Override
    public void implementation(String message, int position) {

    }

    @Override
    public void conncetion(String value, String title, String description) {

    }

    @Override
    public int getContantField() {
        return 0;
    }

    @Override
    public void dialog_control() {
//    Categories_details.progress_download_google.setVisibility(View.GONE);
    }
}
