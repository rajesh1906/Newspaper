package com.metrial.chrajeshkumar.newspaper.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.metrial.chrajeshkumar.newspaper.Helper.Activiy_control;
import com.metrial.chrajeshkumar.newspaper.Helper.HandlingViews;
import com.metrial.chrajeshkumar.newspaper.Helper.Webview_implementation;
import com.metrial.chrajeshkumar.newspaper.R;
import com.metrial.chrajeshkumar.newspaper.Utils.ConStants;
import com.metrial.chrajeshkumar.newspaper.Utils.Control;
import com.metrial.chrajeshkumar.newspaper.Utils.Endpoints;
import com.metrial.chrajeshkumar.newspaper.smoothprogress.SmoothProgressDrawable;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ChRajeshKumar on 10/18/2016.
 */

public class Categories_details extends AppCompatActivity implements HandlingViews, View.OnClickListener, Activiy_control {

    @Bind(R.id.webview)
    WebView webview;
    int position;
    String endpoint;
    @Bind(R.id.progress_download_google)
    ProgressBar progress_download_google;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.fab_home)
    FloatingActionButton fab_home;
    @Bind(R.id.fab_back)
    FloatingActionButton fab_back;
    private Animation fab_open, fab_close, rotate_forward, rotate_backward;
    private Boolean isFabOpen = false;
    Class fromclass;
    String from_category;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dash_board_detail);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        HashMap<String, String> hashMap = (HashMap<String, String>) intent.getSerializableExtra("params");
        from_category = hashMap.get("from category");
        position = Integer.parseInt(hashMap.get("position"));
        Log.e("from_category is ", "<><>" + from_category);
        progress_download_google.setVisibility(View.VISIBLE);
        progress_download_google.setIndeterminateDrawable(new SmoothProgressDrawable.Builder(this).interpolator(new AccelerateInterpolator()).build());
        progress_download_google.getIndeterminateDrawable().setColorFilter(
                getResources().getColor(R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_backward);
        fab.setOnClickListener(this);
        fab_home.setOnClickListener(this);
        fab_back.setOnClickListener(this);

        new Webview_implementation().startWebView(get_Endpoint(position, from_category), webview, Categories_details.this);
    }

    public String get_Endpoint(int position, String from_category) {
        String endpoint = "";
        if (from_category.equals("sports")) {
            switch (position) {
                case 0:
                    endpoint = Endpoints.nbc_sports;


                    break;
                case 1:
                    endpoint = Endpoints.fox_sports;
                    break;
                case 2:

                    endpoint = Endpoints.espn;
                    break;
                case 3:
                    endpoint = Endpoints.bleachar_report;
                    break;
                case 4:
                    endpoint = Endpoints.cbs_sports;
                    break;
                case 5:
                    endpoint = Endpoints.si;
                    break;
            }
        } else {
            switch (position) {
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
        }


        return endpoint;
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

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fab:

                animateFAB();
                break;
            case R.id.fab_home:

                fromclass = Splash_screen.class;
                Control.control_flow(ConStants.ACTIVITY_CONTROL, Categories_details.this);
                finish();
                Log.d("Raj", "Fab 1");
                break;
            case R.id.fab_back:

                Log.d("Raj", "Fab 2");
                finish();
                break;
        }
    }

    public void animateFAB() {

        if (isFabOpen) {

            fab.startAnimation(rotate_backward);
            fab_home.startAnimation(fab_close);
            fab_back.startAnimation(fab_close);
            fab_home.setClickable(false);
            fab_back.setClickable(false);
            isFabOpen = false;
            Log.d("Raj", "close");

        } else {

            fab.startAnimation(rotate_forward);
            fab_home.startAnimation(fab_open);
            fab_back.startAnimation(fab_open);
            fab_home.setClickable(true);
            fab_back.setClickable(true);
            isFabOpen = true;
            Log.d("Raj", "open");

        }
    }

    @Override
    public Class fromActivity() {
        return fromclass;
    }

    @Override
    public HashMap<String, String> params() {
        return null;
    }

    @Override
    public void activityCallback(HashMap<String, String> params) {


    }
}
