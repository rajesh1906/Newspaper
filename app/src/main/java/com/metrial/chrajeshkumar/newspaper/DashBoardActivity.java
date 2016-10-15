package com.metrial.chrajeshkumar.newspaper;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.metrial.chrajeshkumar.newspaper.Activities.Dashboard_Detail;
import com.metrial.chrajeshkumar.newspaper.Activities.Videos_list;
import com.metrial.chrajeshkumar.newspaper.Adapters.Channel_list_adapter;
import com.metrial.chrajeshkumar.newspaper.Adapters.Dashboard;
import com.metrial.chrajeshkumar.newspaper.Adapters.Horizontal_Adapter;
import com.metrial.chrajeshkumar.newspaper.Authentication.Twitter;
import com.metrial.chrajeshkumar.newspaper.Helper.Activiy_control;
import com.metrial.chrajeshkumar.newspaper.Helper.CustomeGridview;
import com.metrial.chrajeshkumar.newspaper.Helper.HandlingViews;
import com.metrial.chrajeshkumar.newspaper.Helper.Recycle_item_view;
import com.metrial.chrajeshkumar.newspaper.Sharing.WebViewActivity;
import com.metrial.chrajeshkumar.newspaper.Utils.CheckNetwork;
import com.metrial.chrajeshkumar.newspaper.Utils.ConStants;
import com.metrial.chrajeshkumar.newspaper.Utils.Control;
import com.metrial.chrajeshkumar.newspaper.Utils.Papers_icon;
import com.sa90.materialarcmenu.ArcMenu;
import com.sa90.materialarcmenu.StateChangeListener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

public class DashBoardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, HandlingViews, Activiy_control, Recycle_item_view, View.OnClickListener {


    @Bind(R.id.grid)
    CustomeGridview grid;
    @Bind(R.id.grid_telugu)
    CustomeGridview grid_telugu;
    @Bind(R.id.grid_hindi)
    CustomeGridview grid_hindi;

    @Bind(R.id.grid_tamil)
    CustomeGridview grid_tamil;
    @Bind(R.id.grid_malayalam)
    CustomeGridview grid_malayalam;

    @Bind(R.id.collapsing_toolbar_android_layout)
    CollapsingToolbarLayout collapsing_toolbar;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.horizontal_recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.nested_scrollview)
    NestedScrollView nested_scrollview;

    @Bind(R.id.arcmenu)
    ArcMenu arcmenu;
    @Bind(R.id.fab_arc_fb)
    FloatingActionButton fab_arc_fb;
    @Bind(R.id.fab_arc_twitter)
    FloatingActionButton fab_arc_twitter;
    @Bind(R.id.fab_arc_instagram)
    FloatingActionButton fab_arc_instagram;
    @Bind(R.id.fab_arc_linkedin)
    FloatingActionButton fab_arc_linkedin;
    @Bind(R.id.appbar_layout)
    AppBarLayout appbar_layout;
    @Bind(R.id.img_header_paper)
    ImageView img_header_paper;
    @Bind(R.id.txt_title)
    TextView txt_title;
    @Bind(R.id.txt_sticky)
    TextView txt_sticky;


    TextView txt_share;
    RecyclerView recycler_share;
    CardView card_view;

    int error;
    Activity activity;
    HashMap<String, String> params = new HashMap<>();
    Class fromclass;
    String[] dialog_channels;
    Integer[] dialog_channels_icons;
    public static Dialog dialog;
    String from_whare;
    CallbackManager callbackManager;
    SharedPreferences mSharedPreferences;
    public static final int WEBVIEW_REQUEST_CODE = 100;


    /* Shared preference keys */
    private static final String PREF_NAME = "sample_twitter_pref";
    private static final String PREF_KEY_OAUTH_TOKEN = "oauth_token";
    private static final String PREF_KEY_OAUTH_SECRET = "oauth_token_secret";
    private static final String PREF_KEY_TWITTER_LOGIN = "is_twitter_loggedin";
    private static final String PREF_USER_NAME = "twitter_user_name";
    private static RequestToken requestToken;
    private static twitter4j.Twitter twitter;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ButterKnife.bind(this);

        //Initilize facebook
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);


        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.metrial.chrajeshkumar.newspaper",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        activity = this;
        grid.setNumColumns(2);
        grid.setAdapter(new Dashboard(this, this.getResources().getStringArray(R.array.english_paper), Papers_icon.english_papers));
        grid.setExpanded(true);
        grid_telugu.setNumColumns(2);
        grid_telugu.setAdapter(new Dashboard(this, this.getResources().getStringArray(R.array.telugu_paper), Papers_icon.telugu_papers));
        grid_telugu.setExpanded(true);
        grid_hindi.setNumColumns(2);
        grid_hindi.setAdapter(new Dashboard(this, this.getResources().getStringArray(R.array.hindi_paper), Papers_icon.hindi_papers));
        grid_hindi.setExpanded(true);

        //tamil
        grid_tamil.setNumColumns(2);
        grid_tamil.setAdapter(new Dashboard(this, this.getResources().getStringArray(R.array.tamil_paper), Papers_icon.tamil_papers));
        grid_tamil.setExpanded(true);

        //malayalam
        grid_malayalam.setNumColumns(2);
        grid_malayalam.setAdapter(new Dashboard(this, this.getResources().getStringArray(R.array.malayalam_paper), Papers_icon.malayalam_papers));
        grid_malayalam.setExpanded(true);

        appbar_layout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

//
                if (Math.abs(verticalOffset) == appBarLayout.getTotalScrollRange()) {
                    // Collapsed
                    Log.e("collapsed is ", "<><>");
                    img_header_paper.setVisibility(View.VISIBLE);
                    txt_title.setVisibility(View.VISIBLE);
                    txt_sticky.setVisibility(View.VISIBLE);
                    txt_title.setText("News Paper");
                } else if (verticalOffset == 0) {
                    // Expanded
                    img_header_paper.setVisibility(View.GONE);
                    txt_sticky.setVisibility(View.GONE);
                    txt_title.setVisibility(View.GONE);
                    Log.e("Expanded is ", "<><>");
                } else {
                    img_header_paper.setVisibility(View.GONE);
                    txt_sticky.setVisibility(View.GONE);
                    txt_title.setVisibility(View.GONE);
                    Log.e("some where is there", "<><<");
                    // Somewhere in between
                }
            }
        });
        dynamicToolbarColor();
        toolbarTextAppernce();
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(DashBoardActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManagaer);
        recyclerView.setAdapter(new Horizontal_Adapter(this, this.getResources().getStringArray(R.array.news_channels), Papers_icon.news_channel_icons
                , Papers_icon.english_news_channel_icons, Papers_icon.telugu_news_channel_icons, Papers_icon.hindi_news_channel_icons, Papers_icon.tamil_news_channel_icons,
                Papers_icon.malayalam_news_channel_icons));

        arcmenu.setStateChangeListener(new StateChangeListener() {
            @Override
            public void onMenuOpened() {
                //TODO something when menu is opened
            }

            @Override
            public void onMenuClosed() {
                //TODO something when menu is closed
            }
        });

        //fab items
        fab_arc_fb.setOnClickListener(this);
        fab_arc_twitter.setOnClickListener(this);
        fab_arc_instagram.setOnClickListener(this);
        fab_arc_linkedin.setOnClickListener(this);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        nested_scrollview.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Log.e("scrollX is shown", "<><" + scrollY);
                txt_title.setVisibility(View.GONE);
                txt_sticky.setVisibility(View.VISIBLE);
                if (scrollY < 1400) {
                    txt_sticky.setText("English");
                } else if (scrollY > 1400 && scrollY < 2740) {
                    txt_sticky.setText("Telugu");
                } else if (scrollY > 2740 && scrollY < 4070) {
                    txt_sticky.setText("Hindi");
                } else if (scrollY > 4070 && scrollY < 5400) {
                    txt_sticky.setText("Tamil");
                }else
                {
                    txt_sticky.setText("Malayalam");
                }
            }
        });
    }


    public void share_items() {

        if (recycler_share.isShown()) {
            recycler_share.setVisibility(View.GONE);
        } else {
            from_whare = "shareing";
            recycler_share.setVisibility(View.VISIBLE);
            dialog_channels = getResources().getStringArray(R.array.sharing_items);
            dialog_channels_icons = Papers_icon.share_icons;
//        Show_channel_dialog(dialog_channels, dialog_channels_icons);
            LinearLayoutManager horizontalLayoutManagaer_share
                    = new LinearLayoutManager(DashBoardActivity.this, LinearLayoutManager.VERTICAL, false);
            recycler_share.setLayoutManager(horizontalLayoutManagaer_share);
            recycler_share.setAdapter(new Channel_list_adapter(this, dialog_channels, dialog_channels_icons, from_whare));
        }


    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Log.e("item id is ", "<><" + id);
        share_items();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void dynamicToolbarColor() {

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_launcher);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
//                collapsing_toolbar.setContentScrimColor(palette.getMutedColor(R.color.colorPrimary));
//                collapsing_toolbar.setStatusBarScrimColor(palette.getMutedColor(R.attr.colorPrimaryDark));
            }
        });
    }


    private void toolbarTextAppernce() {
        collapsing_toolbar.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        collapsing_toolbar.setExpandedTitleTextAppearance(R.style.expandedappbar);
    }

    @Override
    public void implementation(String message, int position) {
        Log.e("coming to implementation", "<><>" + this.getResources().getStringArray(R.array.news_channels)[position]);

        Log.e("enmuration value is ", "<><>" + ConStants.IDNTIFIACTION.values()[position]);
        String tem = "";

        if (CheckNetwork.isOnline(this)) {

            switch (this.getResources().getStringArray(R.array.news_channels)[position]) {
                case ConStants.ENGLISH:
                    dialog_channels = getResources().getStringArray(R.array.english_news_channels);
                    dialog_channels_icons = Papers_icon.english_news_channel_icons;
                    break;
                case ConStants.TELUGU:
                    dialog_channels = getResources().getStringArray(R.array.telugu_news_channels);
                    dialog_channels_icons = Papers_icon.telugu_news_channel_icons;
                    break;
                case ConStants.HINDI:
                    dialog_channels = getResources().getStringArray(R.array.hindi_news_channels);
                    dialog_channels_icons = Papers_icon.hindi_news_channel_icons;
                    break;
                case ConStants.TAMIL:
                    dialog_channels = getResources().getStringArray(R.array.tamil_news_channels);
                    dialog_channels_icons = Papers_icon.tamil_news_channel_icons;
                    break;
                case ConStants.MALAYALAM:
                    dialog_channels = getResources().getStringArray(R.array.malayalam_news_channels);
                    dialog_channels_icons = Papers_icon.malayalam_news_channel_icons;
                    break;


            }

//            fromclass = Videos_list.class;
//            params.put("channel", this.getResources().getStringArray(R.array.news_channels)[position]);
//            Control.control_flow(ConStants.ACTIVITY_CONTROL, DashBoardActivity.this);
//            overridePendingTransition(R.anim.left_slide_in, R.anim.left_slide_out);

            from_whare = "channel";
            Show_channel_dialog(dialog_channels, dialog_channels_icons);
        } else {
            error = ConStants.NETWORK_CONNECTION_ERROR;
            Control.control_flow(ConStants.DIALOG_CONTROL, this);
        }
    }

    @Override
    public void conncetion(String value, String title, String desc) {

    }

    @Override
    public int getContantField() {
        return error;
    }


    @Override
    public Class fromActivity() {
        return fromclass;
    }

    @Override
    public HashMap<String, String> params() {
        return params;
    }

    @Override
    public void activityCallback(HashMap<String, String> params1) {
        params = params1;
        fromclass = Dashboard_Detail.class;
        Control.control_flow(ConStants.ACTIVITY_CONTROL, DashBoardActivity.this);
    }

    public void Show_channel_dialog(String[] dialog_channels, Integer[] dialog_channels_icons) {
        // Create custom dialog object
        dialog = new Dialog(this, R.style.ThemeDialogCustom);
        // Include dialog.xml file
        dialog.setContentView(R.layout.channel_dialog);
        RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.recycler_view_channel);

        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(DashBoardActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManagaer);
        if (from_whare.equals("channel")) {
            recyclerView.setAdapter(new Channel_list_adapter(this, dialog_channels, dialog_channels_icons));
        } else {
            recyclerView.setAdapter(new Channel_list_adapter(this, dialog_channels, dialog_channels_icons, from_whare));
        }

        dialog.show();


    }

    @Override
    public void return_control(String channel) {

        if (from_whare.equals("channel")) {
            dialog.dismiss();
            fromclass = Videos_list.class;
            params.put("channel", channel);
            Control.control_flow(ConStants.ACTIVITY_CONTROL, DashBoardActivity.this);
            overridePendingTransition(R.anim.left_slide_in, R.anim.left_slide_out);
        } else {
//            Toast.makeText(this,channel,Toast.LENGTH_LONG).show();

            switch (channel) {
                case ConStants.FACEBOOK:
                    facebook_implementation();
                    break;
                case ConStants.TWITTER:
                    twitter_implementation();
                    break;
                case ConStants.IMGULER:
                    break;
                case ConStants.INSTAGRAM:
                    break;
            }

        }


    }


    public void facebook_implementation() {
        
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.e("sucess full login", "<><><");
                ShareLinkContent content = new ShareLinkContent.Builder()
                        .setContentUrl(Uri.parse("https://developers.facebook.com"))
                        .build();
                ShareDialog shareDialog = new ShareDialog(DashBoardActivity.this);
                shareDialog.show(content, ShareDialog.Mode.AUTOMATIC);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }

        });


    }

    public void twitter_implementation() {
         /*Intent intent= new Intent(DashBoardActivity.this, Sharing.class);
        startActivity(intent);*/

        final ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.setOAuthConsumerKey(Twitter.consumer_key);
        builder.setOAuthConsumerSecret(Twitter.secret_key);

        final twitter4j.conf.Configuration configuration = builder.build();
        final TwitterFactory factory = new TwitterFactory(configuration);
//			twitter = factory.getInstance();
        twitter = factory.getInstance();

        try {
            requestToken = twitter.getOAuthRequestToken(getString(R.string.twitter_callback));

            /**
             *  Loading twitter login page on webview for authorization
             *  Once authorized, results are received at onActivityResult
             *  */
            final Intent intent = new Intent(this, WebViewActivity.class);
            intent.putExtra(WebViewActivity.EXTRA_URL, requestToken.getAuthenticationURL());
            startActivityForResult(intent, WEBVIEW_REQUEST_CODE);

        } catch (TwitterException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "DashBoard Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.metrial.chrajeshkumar.newspaper/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "DashBoard Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.metrial.chrajeshkumar.newspaper/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }


    private void saveTwitterInfo(AccessToken accessToken) {

        long userID = accessToken.getUserId();

        User user;
        try {
            user = twitter.showUser(userID);

            String username = user.getName();

			/* Storing oAuth tokens to shared preferences */
            //Log.e("user name is ","<><><>"+username);
            mSharedPreferences = PreferenceManager
                    .getDefaultSharedPreferences(this);
            SharedPreferences.Editor e = mSharedPreferences.edit();
            e.putString(Twitter.consumer_key, accessToken.getToken());
            e.putString(Twitter.secret_key, accessToken.getTokenSecret());
            e.putBoolean(PREF_KEY_TWITTER_LOGIN, true);
            e.putString(PREF_USER_NAME, username);
            e.commit();

        } catch (TwitterException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        arcmenu.setClipChildren(false);
        if (v.equals(fab_arc_fb)) {
            Log.e("fab facebook", "<><>");
            facebook_implementation();
        } else if (v.equals(fab_arc_twitter)) {
            Log.e("fab twitter", "<><>");
//            twitter_implementation();
            Toast.makeText(this, "Under construction", Toast.LENGTH_LONG).show();

        } else if (v.equals(fab_arc_instagram)) {
            Log.e("fab instagram", "<><>");


        } else if (v.equals(fab_arc_linkedin)) {
            Log.e("fab linked in", "<><>");
            Toast.makeText(this, "Under construction", Toast.LENGTH_LONG).show();

        }
    }
}
