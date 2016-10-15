package com.metrial.chrajeshkumar.newspaper.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.metrial.chrajeshkumar.newspaper.Helper.Activiy_control;
import com.metrial.chrajeshkumar.newspaper.Helper.Appcontants;
import com.metrial.chrajeshkumar.newspaper.Helper.Edittext_focus;
import com.metrial.chrajeshkumar.newspaper.Helper.HandlingViews;
import com.metrial.chrajeshkumar.newspaper.R;

/**
 * Created by ChRajeshKumar on 9/17/2016.
 */
public class Control {
    public static void control_flow(int from, Context context) {
        switch (from) {
            case ConStants.DIALOG_CONTROL:
                HandlingViews handlingViews = (HandlingViews) context;
                Show_dialog.show_dialog(handlingViews.getContantField(), context);
                break;
            case ConStants.KEYBOARD_CONTROL:
                Edittext_focus edittext_focus = (Edittext_focus) context;
                Appcontants.keyboard_show((edittext_focus.edittext_focus()), context);
                break;
            case ConStants.ACTIVITY_CONTROL:
                Activiy_control activiy_control = (Activiy_control) context;
                Activity activity = (Activity) context;
//                Log.e("activiy_control.fromActivity()", "<<>>>>" + activiy_control.fromActivity().toString());
                Intent intent = new Intent(activity, activiy_control.fromActivity());
                intent.putExtra("params", activiy_control.params());
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.left_slide_in, R.anim.left_slide_out);
                break;

        }

    }


}
