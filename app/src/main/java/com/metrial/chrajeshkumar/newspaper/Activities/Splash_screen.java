package com.metrial.chrajeshkumar.newspaper.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;

import com.metrial.chrajeshkumar.newspaper.Authentication.LaunchingScreen;
import com.metrial.chrajeshkumar.newspaper.DashBoardActivity;
import com.metrial.chrajeshkumar.newspaper.R;
import com.metrial.chrajeshkumar.newspaper.Utils.CheckNetwork;

/**
 * Created by ChRajeshKumar on 9/30/2016.
 */
public class Splash_screen extends AppCompatActivity
{
    Handler handler;
    AlertDialog alertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (!isTaskRoot()) {
            final Intent intent = getIntent();
            final String intentAction = intent.getAction();
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && intentAction != null && intentAction.equals(Intent.ACTION_MAIN)) {
                Log.e( "Main Activity is not the root.  Finishing Main Activity instead of launching.","<><>");
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
            };
        }.start();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                super.handleMessage(msg);

                if (CheckNetwork.isOnline(Splash_screen.this)) {

                    Intent intent = new Intent(Splash_screen.this,
                            DashBoardActivity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.left_slide_in, R.anim.left_slide_out);
                } else {
                    Log.e("coming to else condition", "<><>");
                }
            }
        };



    }



}
