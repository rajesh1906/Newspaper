package com.metrial.chrajeshkumar.newspaper.Utils;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.util.Log;

import com.metrial.chrajeshkumar.newspaper.Activities.Videos_list;
import com.metrial.chrajeshkumar.newspaper.Helper.Appcontants;
import com.metrial.chrajeshkumar.newspaper.R;

/**
 * Created by ChRajeshKumar on 9/16/2016.
 */
public class Show_dialog
{
    public static void show_dialog(int from, Context context)
    {
        String message="";
        Log.e("from","<><"+from);
        NetworkChangeReceiver receiver;
        switch (from)
        {

            case ConStants.NETWORK_CONNECTION_ERROR:
                message = context.getString(R.string.network_error);
                IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
                receiver = new NetworkChangeReceiver();
                context.registerReceiver(receiver, filter);

                break;
            case 2:
                message = "";
                break;
            case ConStants.ERROR_SEARCH:
                message = context.getString(R.string.search_error);
                break;
            case ConStants.EMAIL_ERROR:
                message = context.getString(R.string.error_email_auth);
                break;
            case ConStants.PASSWORD_ERROR:
                message = context.getString(R.string.error_password_auth);
                break;

        }
        Appcontants.alertDialog(context.getString(R.string.alert_title),message,context.getString(R.string.ok_btn),"",context);

    }

}
