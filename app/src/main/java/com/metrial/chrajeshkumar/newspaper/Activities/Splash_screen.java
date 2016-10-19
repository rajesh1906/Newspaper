package com.metrial.chrajeshkumar.newspaper.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.Toast;

import com.metrial.chrajeshkumar.newspaper.DashBoardActivity;
import com.metrial.chrajeshkumar.newspaper.Fragments.Categories;
import com.metrial.chrajeshkumar.newspaper.Fragments.Technology;
import com.metrial.chrajeshkumar.newspaper.Helper.Activiy_control;
import com.metrial.chrajeshkumar.newspaper.Helper.HandlingViews;
import com.metrial.chrajeshkumar.newspaper.R;
import com.metrial.chrajeshkumar.newspaper.Utils.CheckNetwork;
import com.metrial.chrajeshkumar.newspaper.Utils.ConStants;
import com.metrial.chrajeshkumar.newspaper.Utils.Control;

import java.util.HashMap;

/**
 * Created by ChRajeshKumar on 9/30/2016.
 */
public class Splash_screen extends AppCompatActivity implements Activiy_control, HandlingViews {
    Handler handler;
    AlertDialog alertDialog;
    HashMap<String, String> params = new HashMap<>();
    Class fromclass;
    int error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (!isTaskRoot()) {
            final Intent intent = getIntent();
            final String intentAction = intent.getAction();
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && intentAction != null && intentAction.equals(Intent.ACTION_MAIN)) {
                Log.e("Main Activity is not the root.  Finishing Main Activity instead of launching.", "<><>");
                finish();
                return;
            }
        }

        new Thread() {
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    Message msg = handler.obtainMessage();
                    handler.sendMessage(msg);
                } catch (NullPointerException ex) {
                    Log.e("Handler Exception :", ex.toString());

                }
            }

            ;
        }.start();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                super.handleMessage(msg);

                if (CheckNetwork.isOnline(Splash_screen.this)) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                    transaction.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_in_right);
                    transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                    transaction.replace(R.id.container, new Categories(), "Categories");
                    transaction.commit();
                    overridePendingTransition(R.anim.left_slide_in, R.anim.left_slide_out);
                } else {
                    error = ConStants.NETWORK_CONNECTION_ERROR;
                    Control.control_flow(ConStants.DIALOG_CONTROL, Splash_screen.this);
                }
            }
        };


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
        Log.e("position is", "<><><" + params1.get("position") + " category name is " + params1.get("category"));
        params = params1;
        switch (Integer.parseInt(params1.get("position"))) {
            case 0:
                fromclass = DashBoardActivity.class;
                Control.control_flow(ConStants.ACTIVITY_CONTROL, Splash_screen.this);
                break;
            case 1:
                Toast.makeText(this, "Under construction", Toast.LENGTH_LONG).show();
                break;
            case 2:
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                transaction.add(R.id.container, new Technology(), "Technology");
                transaction.commit();
//                Toast.makeText(this, "Under construction", Toast.LENGTH_LONG).show();
                break;
            case 3:
                Toast.makeText(this, "Under construction", Toast.LENGTH_LONG).show();
                break;
        }


    }

    @Override
    public void implementation(String message, int position) {

    }

    @Override
    public void conncetion(String value, String title, String description) {

    }

    @Override
    public int getContantField() {
        return error;
    }

    @Override
    public void dialog_control() {

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.container);
        Log.e("onback presses is  ", "<><>" + currentFragment.getTag());
        if (currentFragment.getTag().equals("Technology")) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, new Categories(), "Categories");
            transaction.commit();
        } else if (currentFragment.getTag().equals("Categories")) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
    }
}
