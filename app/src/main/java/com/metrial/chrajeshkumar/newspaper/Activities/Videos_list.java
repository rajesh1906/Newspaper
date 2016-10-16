package com.metrial.chrajeshkumar.newspaper.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.metrial.chrajeshkumar.newspaper.Adapters.Youtube_view_item;
import com.metrial.chrajeshkumar.newspaper.Callbacks.Youtube_api_callback;
import com.metrial.chrajeshkumar.newspaper.Helper.APIService;
import com.metrial.chrajeshkumar.newspaper.Helper.Edittext_focus;
import com.metrial.chrajeshkumar.newspaper.Helper.HandlingViews;
import com.metrial.chrajeshkumar.newspaper.Pojos.Items;
import com.metrial.chrajeshkumar.newspaper.Pojos.MyPojo;
import com.metrial.chrajeshkumar.newspaper.R;
import com.metrial.chrajeshkumar.newspaper.Utils.CheckNetwork;
import com.metrial.chrajeshkumar.newspaper.Utils.ConStants;
import com.metrial.chrajeshkumar.newspaper.Utils.Config;
import com.metrial.chrajeshkumar.newspaper.Utils.Control;
import com.metrial.chrajeshkumar.newspaper.Utils.Endpoints;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ChRajeshKumar on 9/14/2016.
 */
public class Videos_list extends YouTubeBaseActivity implements APIService, HandlingViews, YouTubePlayer.OnInitializedListener, Edittext_focus {

    private static final int RECOVERY_REQUEST = 1;
    @Bind(R.id.recycler_view)
    RecyclerView recycler_view;
    @Bind(R.id.recycler_view_search)
    RecyclerView recycler_view_search;
    Activity activity;
    String video_id;
    @Bind(R.id.youtube_view)
    YouTubePlayerView youTubeView;
    @Bind(R.id.progress_view)
    ProgressBar progress_view;
    @Bind(R.id.search_icon)
    ImageView search_icon;
    @Bind(R.id.ll_search_expansation)
    LinearLayout ll_search_expansation;
    @Bind(R.id.edittext_search)
    EditText edittext_search;
    @Bind(R.id.news_dialog)
    ImageView news_dialog;
    @Bind(R.id.txt_title_val)
    TextView txt_title_val;
    @Bind(R.id.txt_description_val)
    TextView txt_description_val;


    YouTubePlayer youTubePlayer_outer;
    MyPojo mypojo, temp_pojo;
    ArrayList<Items> dataList = new ArrayList<>();
    private boolean loading, flag = true;
    Youtube_view_item youtube_view_item;
    String channel;
    int start = 5, error;
    boolean isFlag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videos_list);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ButterKnife.bind(this);
        activity = this;
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(Videos_list.this, LinearLayoutManager.VERTICAL, false);
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(horizontalLayoutManagaer);

        recycler_view_search.setHasFixedSize(true);
        LinearLayoutManager horizontalLayoutManagaer_search
                = new LinearLayoutManager(Videos_list.this, LinearLayoutManager.VERTICAL, false);
        recycler_view_search.setLayoutManager(horizontalLayoutManagaer_search);
        Intent intent = getIntent();
        HashMap<String, String> hashMap = (HashMap<String, String>) intent.getSerializableExtra("params");
        Log.v("HashMapTest", hashMap.get("channel"));
//        channel = getIntent().getStringExtra("channel");
        channel = hashMap.get("channel");
        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(news_dialog);
        Glide.with(this).load(R.raw.paper_dialog).into(imageViewTarget);
        news_dialog.setVisibility(View.VISIBLE);
        recycler_view.addOnScrollListener(new EndlessScrollListener(recycler_view));
        recycler_view_search.addOnScrollListener(new EndlessScrollListener(recycler_view_search));
        if (CheckNetwork.isOnline(this)) {
            new Youtube_api_callback(this, channel, start).execute();
        } else {
            error = ConStants.NETWORK_CONNECTION_ERROR;
            Control.control_flow(ConStants.DIALOG_CONTROL, this);
        }

        edittext_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String value = edittext_search.getText().toString();
                if (isFlag) {
                    isFlag = false;
                    if (actionId == EditorInfo.IME_ACTION_SEARCH && channel.contains(value)) {
                        Log.e("perfect search ", "<><");
                        recycler_view.setVisibility(View.GONE);
                        recycler_view_search.setVisibility(View.VISIBLE);
                        news_dialog.setVisibility(View.VISIBLE);
                        start = 5;
                        new Youtube_api_callback(Videos_list.this, value, start).execute();
                    } else {
                        Log.e("error in search", "<><><" + " ediit text ");
                        error = ConStants.ERROR_SEARCH;

                        Control.control_flow(ConStants.DIALOG_CONTROL, Videos_list.this);
                    }
                    Control.control_flow(ConStants.KEYBOARD_CONTROL, Videos_list.this);

                }

                return false;
            }
        });

        edittext_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                isFlag = true;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void backbutton_implementation() {
        if (recycler_view_search.isShown()) {
            recycler_view_search.setVisibility(View.GONE);
            recycler_view.setVisibility(View.VISIBLE);
            search_icon.setVisibility(View.VISIBLE);
            edittext_search.setText("");
            ll_search_expansation.setVisibility(View.GONE);

        } else {
            Endpoints.nextpagetoken = "";
            finish();
        }
    }

    @OnClick(R.id.ll_back)
    public void backButton() {
        backbutton_implementation();
    }

    @OnClick(R.id.search_icon)
    public void searchIcon_Implementation() {
        search_icon.setVisibility(View.GONE);
        ll_search_expansation.setVisibility(View.VISIBLE);
        edittext_search.requestFocus();
        edittext_search.setFocusable(true);
        edittext_search.setCursorVisible(true);
        Control.control_flow(ConStants.KEYBOARD_CONTROL, this);
    }

    @OnClick(R.id.clear)
    public void clear_button() {

        search_icon.setVisibility(View.VISIBLE);
        edittext_search.setText("");
        ll_search_expansation.setVisibility(View.GONE);
        Control.control_flow(ConStants.KEYBOARD_CONTROL, this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        backbutton_implementation();
    }

    @Override
    public void apiRespose(MyPojo myPojo) {

        mypojo = myPojo;
        loading = false;
        if (flag) {
            Log.e("video id is ", "<><" + myPojo.getItems().get(0).getId().getVideoId());
            video_id = myPojo.getItems().get(0).getId().getVideoId();
            youTubeView.initialize(Config.YOUTUBE_API_KEY, this);
            txt_title_val.setText(mypojo.getItems().get(0).getSnippet().getTitle());
            txt_description_val.setText(mypojo.getItems().get(0).getSnippet().getDescription());
        }

        if (start == 5) {
            temp_pojo = mypojo;
        } else {
            dataList = temp_pojo.getItems();
            dataList.addAll(mypojo.getItems());
            temp_pojo.setItems(dataList);
        }


        if (start == 5) {
            youtube_view_item = new Youtube_view_item(this, temp_pojo);
            if (recycler_view.isShown()) {
                recycler_view.setAdapter(youtube_view_item);
            } else {
                recycler_view_search.setAdapter(youtube_view_item);
            }


        } else {
            youtube_view_item.notifyDataSetChanged();
        }
        news_dialog.setVisibility(View.GONE);
        youtube_view_item.notifyDataSetChanged();

    }

    @Override
    public void implementation(String message, int position) {

    }

    @Override
    public void conncetion(String videoId,String title,String description) {
        if (CheckNetwork.isOnline(this)) {

            Bundle bundle = new Bundle();
            bundle.putString("video_id", videoId);
            video_id = videoId;
            Log.e("coming to connection is ", "<><>");
            txt_title_val.setText(title);
            txt_description_val.setText(description);
            youTubePlayer_outer.loadVideo(video_id);
        } else {
            error = ConStants.NETWORK_CONNECTION_ERROR;
            Control.control_flow(ConStants.DIALOG_CONTROL, this);
        }


    }

    @Override
    public int getContantField() {
        return error;
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if (!b) {
            youTubePlayer_outer = youTubePlayer;
            youTubePlayer.loadVideo(video_id); // Plays https://www.youtube.com/watch?v=fhWaJi1Hsfo
        }

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            String error_log = String.format(getString(R.string.player_error), youTubeInitializationResult.toString());
            Toast.makeText(this, error_log, Toast.LENGTH_LONG).show();
            error = ConStants.YOUTUBE_ERROR;
//            Control.control_flow(ConStants.DIALOG_CONTROL, this);
        }
    }

    @Override
    public boolean edittext_focus() {

        if (edittext_search.getText().length() != 0) {
            return false;
        } else {
            return search_icon.isShown() ? false : true;
        }

    }


    public class EndlessScrollListener extends RecyclerView.OnScrollListener {
        private RecyclerView listView;

        public EndlessScrollListener(RecyclerView listView) {
            this.listView = listView;
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            Log.e("dx is ", "<><" + dx);
        }

        @Override
        public void onScrollStateChanged(RecyclerView view, int scrollState) {
            Log.e("onScrollStateChanged", "<><><>");
            LinearLayoutManager layoutManager = ((LinearLayoutManager) view.getLayoutManager());
            int lastVisiblePosition = layoutManager.findLastVisibleItemPosition();
            int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
            Log.e("first visible position is ", "<><>" + firstVisiblePosition);
            if (null != temp_pojo) {
                if (scrollState == 0
                        && lastVisiblePosition == listView.getAdapter().getItemCount() - 1) {
                    if (!loading) {
                        loading = true;
                        flag = false;
                        Log.e("coming to end less", "<><><>");
                        if (CheckNetwork.isOnline(Videos_list.this)) {
                            start += 5;
                            news_dialog.setVisibility(View.VISIBLE);
                            new Youtube_api_callback(Videos_list.this, channel, start).execute();
                        } else {
                            error = ConStants.NETWORK_CONNECTION_ERROR;
                            Control.control_flow(ConStants.DIALOG_CONTROL, Videos_list.this);
                        }
                    }

                }
            }
        }
    }


}
