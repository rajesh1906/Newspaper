package com.metrial.chrajeshkumar.newspaper.Helper;

import java.util.HashMap;

/**
 * Created by ChRajeshKumar on 9/20/2016.
 */
public interface Activiy_control {
    public Class fromActivity();

    public HashMap<String, String> params();

    public void activityCallback(HashMap<String, String> params);
}
