package com.metrial.chrajeshkumar.newspaper.Helper;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.metrial.chrajeshkumar.newspaper.R;

/**
 * Created by ChRajeshKumar on 9/16/2016.
 */
public class Appcontants extends AppCompatActivity
{
    public static void alertDialog(String title,String message, String positive_text, String negitaive_text, Context context)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MyDialogTheme);
        builder.setCancelable(false);
        builder.setTitle(title).setMessage(message).
                setPositiveButton(positive_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();

    }

    public static void keyboard_show(boolean value, Context context)
    {
        try {
            Activity activity = (Activity)context;
            View view = activity.getCurrentFocus();
            InputMethodManager imm = (InputMethodManager)context.getSystemService(Service.INPUT_METHOD_SERVICE);
            Log.e("keyboard status","<><"+value);
            if (view != null) {

                final boolean b = value ? imm.showSoftInput(view, 0) : imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                           }

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    public static void setpadding(EditText field, Context context) {
        int paddtop = dpToPx(3, context);
        int paddbottom = dpToPx(6, context);
        field.setPadding(0, paddtop, 0, paddbottom);
    }
    public static int dpToPx(int dp, Context context) {
        final float scale = context.getResources().getDisplayMetrics().density;
        int pixels = (int) (dp * scale + 0.5f);
        return pixels;
    }

}
