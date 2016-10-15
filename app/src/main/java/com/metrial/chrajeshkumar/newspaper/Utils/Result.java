package com.metrial.chrajeshkumar.newspaper.Utils;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ChRajeshKumar on 9/13/2016.
 */
public class Result {
    @SerializedName("result")
    private String result;

    public String getResult(){
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
