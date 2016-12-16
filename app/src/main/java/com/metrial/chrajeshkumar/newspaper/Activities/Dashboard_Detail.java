package com.metrial.chrajeshkumar.newspaper.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.metrial.chrajeshkumar.newspaper.Adapters.Navigation_items;
import com.metrial.chrajeshkumar.newspaper.DashBoardActivity;
import com.metrial.chrajeshkumar.newspaper.Helper.Activiy_control;
import com.metrial.chrajeshkumar.newspaper.Helper.HandlingViews;
import com.metrial.chrajeshkumar.newspaper.Helper.Webview_implementation;
import com.metrial.chrajeshkumar.newspaper.R;
import com.metrial.chrajeshkumar.newspaper.Utils.ConStants;
import com.metrial.chrajeshkumar.newspaper.Utils.Control;
import com.metrial.chrajeshkumar.newspaper.Utils.Endpoints;
import com.metrial.chrajeshkumar.newspaper.Utils.Papers_icon;
import com.metrial.chrajeshkumar.newspaper.smoothprogress.SmoothProgressDrawable;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ChRajeshKumar on 8/31/2016.
 */
public class Dashboard_Detail extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, HandlingViews, View.OnClickListener, Activiy_control {

    int position;
    String endpoint;
    RecyclerView grid;
    RecyclerView grid_telugu;
    RecyclerView grid_hindi;
    RecyclerView grid_tamil;
    RecyclerView grid_malayalam;
    private Animation fab_open, fab_close, rotate_forward, rotate_backward;
    private Boolean isFabOpen = false;
    Class fromclass;

    @Bind(R.id.webview)
    WebView webview;
    @Bind(R.id.progress_download_google)
    ProgressBar progress_download_google;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.fab_home)
    FloatingActionButton fab_home;
    @Bind(R.id.fab_back)
    FloatingActionButton fab_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_news);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ButterKnife.bind(this);
//        Bundle bundle = getIntent().getExtras();
//        String message = bundle.getString("category");
//        position = bundle.getInt("position");

        Intent intent = getIntent();
        HashMap<String, String> hashMap = (HashMap<String, String>) intent.getSerializableExtra("params");

        String message = hashMap.get("category");
        position = Integer.parseInt(hashMap.get("position"));
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        progress_download_google.setVisibility(View.VISIBLE);
        View view = navigationView.getHeaderView(0);
        grid = (RecyclerView) navigationView.getHeaderView(0).findViewById(R.id.grid);
        grid_telugu = (RecyclerView) navigationView.getHeaderView(0).findViewById(R.id.grid_telugu);
        grid_hindi = (RecyclerView) navigationView.getHeaderView(0).findViewById(R.id.grid_hindi);
        grid_tamil = (RecyclerView) navigationView.getHeaderView(0).findViewById(R.id.grid_tamil);
        grid_malayalam = (RecyclerView) navigationView.getHeaderView(0).findViewById(R.id.grid_malayalam);
        progress_download_google.setIndeterminateDrawable(new SmoothProgressDrawable.Builder(this).interpolator(new AccelerateInterpolator()).build());
        progress_download_google.getIndeterminateDrawable().setColorFilter(
                getResources().getColor(R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
        businessLogic(message);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        grid.setLayoutManager(mLayoutManager);
        grid.setItemAnimator(new DefaultItemAnimator());
        grid.setAdapter(new Navigation_items(this, this.getResources().getStringArray(R.array.english_paper), Papers_icon.english_papers));

        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getApplicationContext());
        grid_telugu.setLayoutManager(mLayoutManager1);
        grid_telugu.setAdapter(new Navigation_items(this, this.getResources().getStringArray(R.array.telugu_paper), Papers_icon.telugu_papers));

        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(getApplicationContext());
        grid_hindi.setLayoutManager(mLayoutManager2);
        grid_hindi.setAdapter(new Navigation_items(this, this.getResources().getStringArray(R.array.hindi_paper), Papers_icon.hindi_papers));

        RecyclerView.LayoutManager mLayoutManager3 = new LinearLayoutManager(getApplicationContext());
        grid_tamil.setLayoutManager(mLayoutManager3);
        grid_tamil.setAdapter(new Navigation_items(this, this.getResources().getStringArray(R.array.tamil_paper), Papers_icon.tamil_papers));

        RecyclerView.LayoutManager mLayoutManager4 = new LinearLayoutManager(getApplicationContext());
        grid_malayalam.setLayoutManager(mLayoutManager4);
        grid_malayalam.setAdapter(new Navigation_items(this, this.getResources().getStringArray(R.array.malayalam_paper), Papers_icon.malayalam_papers));


        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_backward);
        fab.setOnClickListener(this);
        fab_home.setOnClickListener(this);
        fab_back.setOnClickListener(this);
    }


    @OnClick(R.id.showevents)
    public void ReturnDashboard() {
        Log.e("coming to ", " logo click");
        backAnimation();
    }

    public void businessLogic(String message) {
        if (message.equals("The Times of India")) {
            English_paper_endpoints();
        } else if (message.equals("Eenadu")) {
            Telugu_paper_endpoints();
        } else if (message.equals("Amar Ujala")) {
            Hindi_paper_endpoints();
        } else if (message.equals("Dina Thanthi")) {
            Tamil_paper_endpoints();
        } else if (message.equals("Malayala Manorama")) {
            Malayalam_paper_endpoints();
        }

    }


    public void English_paper_endpoints() {


        switch (position) {
            case 0:
                endpoint = Endpoints.time_of_india;
                break;
            case 1:
                endpoint = Endpoints.hindustan_times;
                break;
            case 2:
                endpoint = Endpoints.indian_express;
                break;
            case 3:
                endpoint = Endpoints.the_hindu;
                break;
            case 4:
                endpoint = Endpoints.dc;
                break;
            case 5:
                endpoint = Endpoints.businessstandard;
                break;
        }

        startWebView(endpoint);

    }

    public void Telugu_paper_endpoints() {
        switch (position) {
            case 0:
                endpoint = Endpoints.eenadu;
                break;
            case 1:
                endpoint = Endpoints.andhra_bhoomi;
                break;
            case 2:
                endpoint = Endpoints.andhra_joythi;
                break;
            case 3:
                endpoint = Endpoints.sakshi;
                break;
            case 4:
                endpoint = Endpoints.namaste_el;
                break;
            case 5:
                endpoint = Endpoints.vartha;
                break;
        }

        startWebView(endpoint);
    }

    public void Hindi_paper_endpoints() {
        switch (position) {
            case 0:
                endpoint = Endpoints.amar_ujala;
                break;
            case 1:
                endpoint = Endpoints.dainik_jagran;
                break;
            case 2:
                endpoint = Endpoints.hindi_milap;
                break;
            case 3:
                endpoint = Endpoints.nava_bharat;
                break;
            case 4:
                endpoint = Endpoints.prabhat_khabar;
                break;
            case 5:
                endpoint = Endpoints.national_duniya;
                break;


        }
        startWebView(endpoint);
    }

    public void Tamil_paper_endpoints() {
        switch (position) {
            case 0:
                endpoint = Endpoints.dina_thanthi;
                break;
            case 1:
                endpoint = Endpoints.dinakaran;
                break;
            case 2:
                endpoint = Endpoints.dinamalar;
                break;
            case 3:
                endpoint = Endpoints.dina_mani;
                break;
            case 4:
                endpoint = Endpoints.maalai_malar;
                break;
            case 5:
                endpoint = Endpoints.thinaboomi;
                break;


        }
        startWebView(endpoint);
    }

    public void Malayalam_paper_endpoints() {
        switch (position) {
            case 0:
                endpoint = Endpoints.malayala_manorama;
                break;
            case 1:
                endpoint = Endpoints.mathrubhumi;
                break;
            case 2:
                endpoint = Endpoints.deshabhimani;
                break;
            case 3:
                endpoint = Endpoints.madhyamam;
                break;
            case 4:
                endpoint = Endpoints.mangalam;
                break;
            case 5:
                endpoint = Endpoints.kerala_kaumudi;
                break;


        }
        startWebView(endpoint);
    }

    private void startWebView(String url) {
        new Webview_implementation().startWebView(url, webview, Dashboard_Detail.this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (webview.canGoBack()) {
                        webview.goBack();
                    } else {
                        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                        if (drawer.isDrawerOpen(GravityCompat.START)) {
                            drawer.closeDrawer(GravityCompat.START);
                        } else {
                            backAnimation();
                        }

                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    public void backAnimation()

    {
        Intent intent_Home = new Intent(Dashboard_Detail.this,
                DashBoardActivity.class);
        intent_Home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent_Home);
        overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.left_slide_out);
        finish();
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Log.e("item id is ", "<><" + id);
        MenuNews_implementation(id);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void MenuNews_implementation(int id) {
        String url = "";
        switch (id) {
            //english
            case R.id.tio:
                url = Endpoints.time_of_india;
                break;
            case R.id.hindu:
                url = Endpoints.the_hindu;
                break;
            case R.id.hindustan_times:
                url = Endpoints.hindustan_times;
                break;
            case R.id.indian_express:
                url = Endpoints.indian_express;
                break;
            case R.id.dc:
                url = Endpoints.dc;
                break;
            case R.id.businessstandard:
                url = Endpoints.businessstandard;
                break;

//            telugu
            case R.id.eenadu:
                url = Endpoints.eenadu;
                break;
            case R.id.andhra_bhoomi:
                url = Endpoints.andhra_bhoomi;
                break;
            case R.id.andhra_joythi:
                url = Endpoints.andhra_joythi;
                break;
            case R.id.sakshi:
                url = Endpoints.sakshi;
                break;
            case R.id.namaste_telangana:
                url = Endpoints.namaste_el;
                break;
            case R.id.vartha:
                url = Endpoints.vartha;
                break;

            //hindi
            case R.id.au:
                url = Endpoints.amar_ujala;
                break;
            case R.id.dj:
                url = Endpoints.dainik_jagran;
                break;
            case R.id.hm:
                url = Endpoints.hindi_milap;
                break;
            case R.id.nav_barth:
                url = Endpoints.nava_bharat;
                break;
            case R.id.pb:
                url = Endpoints.prabhat_khabar;
                break;
            case R.id.nd:
                url = Endpoints.national_duniya;
                break;
        }
        startWebView(url);


    }

    @Override
    public void implementation(String message, int position) {
        this.position = position;
        progress_download_google.setVisibility(View.VISIBLE);
        businessLogic(message);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public void conncetion(String value, String titiel, String description) {

    }

    @Override
    public int getContantField() {
        return 0;
    }

    @Override
    public void dialog_control() {
        progress_download_google.setVisibility(View.GONE);
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
                Control.control_flow(ConStants.ACTIVITY_CONTROL, Dashboard_Detail.this);
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
