package com.metrial.chrajeshkumar.newspaper.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.metrial.chrajeshkumar.newspaper.Helper.Appcontants;
import com.metrial.chrajeshkumar.newspaper.R;

/**
 * Created by ChRajeshKumar on 9/20/2016.
 */
public class NetworkChangeReceiver extends BroadcastReceiver {

    private static final String LOG_TAG = "CheckNetworkStatus";
    private NetworkChangeReceiver receiver;
    private boolean isConnected = false;

    @Override
    public void onReceive(final Context context, final Intent intent) {

        Log.v(LOG_TAG, "Receieved notification about network status");
        isNetworkAvailable(context);

    }


    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        if (!isConnected) {
                            Log.v(LOG_TAG, "Now you are connected to Internet!");
//                            Toast.makeText(context,"Now you are connected to Internet!",Toast.LENGTH_LONG).show();
//                            Control.control_flow(ConStants.DIALOG_CONTROL, context);
//                            Appcontants.alertDialog(context.getString(R.string.alert_title), "Now you are connected to Internet!", context.getString(R.string.ok_btn), "", context);
                            isConnected = true;
                            //do your processing here ---
                            //if you need to post any data to the server or get status
                            //update from the server
                        }
                        return true;
                    }
                }
            }
        }
//        Toast.makeText(context,"You are not connected to Internet!",Toast.LENGTH_LONG).show();
        Log.v(LOG_TAG, "You are not connected to Internet!");
        isConnected = false;
        return false;
    }

}
