package com.metrial.chrajeshkumar.newspaper.Callbacks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.metrial.chrajeshkumar.newspaper.Activities.Videos_list;
import com.metrial.chrajeshkumar.newspaper.Callbacks.JSON.CustomJSONObjectRequest;
import com.metrial.chrajeshkumar.newspaper.Callbacks.JSON.CustomVolleyRequestQueue;
import com.metrial.chrajeshkumar.newspaper.Helper.APIService;
import com.metrial.chrajeshkumar.newspaper.Helper.Update_youtube_timer;
import com.metrial.chrajeshkumar.newspaper.Pojos.MyPojo;
import com.metrial.chrajeshkumar.newspaper.Utils.Endpoints;

import org.json.JSONObject;

/**
 * Created by ChRajeshKumar on 30-Jan-17.
 */

public class Api_CallBack implements Response.Listener,
        Response.ErrorListener {
    String response,for_search;

    private RequestQueue mQueue;
    Context context;
    Activity activity;
    String coming_from;
    ProgressDialog progressDialog;
    MyPojo myPojo;
    Gson gson = new Gson();
    APIService apiService;
    String news_channel;
    int limit;
    Videos_list videos_list;
    Update_youtube_timer update_youtube_timer;
    String video_id;
    String url,getComing_from;


    public Api_CallBack(Context context,String news_channel,int limit)
    {
        this.context = context;
        this.news_channel = news_channel;
        this.apiService=(APIService)this.context;
        this.activity = (Activity)context;
        this.limit = limit;
        getComing_from = "raw_data";
        Volley_service();


    }

    public Api_CallBack(String video_id, Videos_list videos_list) {
        this.video_id = video_id;
        this.videos_list = videos_list;
        this.update_youtube_timer = videos_list;
        getComing_from = "for_timer";
        Volley_service();

    }
    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("error is ","<><>error"+error.toString());
        if (null != progressDialog) {
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }
        }
        Toast.makeText(context,"Internal Error Please search after some time",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(Object response) {

        try {
            if (null != progressDialog) {
                if(progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
            }
            Log.e("response is ","<>in fragment<>"+(response).toString());
//            resultentApi((response).toString());
            try{
                if(getComing_from.equals("raw_data")) {
                    myPojo = gson.fromJson(response.toString(), MyPojo.class);
                    Log.e("title ", "<><>" + myPojo.getItems().get(0).getSnippet().getTitle());
//            Log.e("next page token is","<><"+myPojo.getNextPageToken());
                    Endpoints.nextpagetoken = myPojo.getNextPageToken();
                    apiService.apiRespose(myPojo);
                }else{
                    update_youtube_timer.update_youtube_timer(response.toString());
                }
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Volley_service(){
        // Instantiate the RequestQueue.
       /* progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Fetching "+for_search+"....");
        progressDialog.show();
        progressDialog.setCancelable(false);*/
        mQueue = CustomVolleyRequestQueue.getInstance(activity)
                .getRequestQueue();

//        url = Endpoints.getSearching(news_channel.replaceAll(" ","%20"),limit);
        if(getComing_from.equals("raw_data")){
             url = Endpoints.getSearching(news_channel.replaceAll(" ","%20"),limit);
        }else{
            url= Endpoints.getVideo_duration_url(video_id);
        }

        final CustomJSONObjectRequest jsonRequest = new CustomJSONObjectRequest(Request.Method
                .GET, url,
                new JSONObject(), this, this);
        mQueue.add(jsonRequest);
    }

}
