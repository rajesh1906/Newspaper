package com.metrial.chrajeshkumar.newspaper.Callbacks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.metrial.chrajeshkumar.newspaper.Helper.APIService;
import com.metrial.chrajeshkumar.newspaper.Pojos.MyPojo;
import com.metrial.chrajeshkumar.newspaper.Utils.Endpoints;
import com.metrial.chrajeshkumar.newspaper.Utils.UrltoValue;

import java.net.URL;

/**
 * Created by ChRajeshKumar on 9/13/2016.
 */
public class Youtube_api_callback extends AsyncTask<URL, Integer, Long> {

    String response;
    Gson gson = new Gson();
    Context context;
    APIService apiService;
    String news_channel;
    int limit;
    MyPojo myPojo;

    public Youtube_api_callback(Context context,String news_channel,int limit)
    {
        this.context = context;
        this.news_channel = news_channel;
        this.apiService=(APIService)this.context;
        this.limit = limit;

    }
    @Override
    public void onPreExecute() {

    }
    @Override
    protected Long doInBackground(URL... params) {
        // TODO Auto-generated method stub
        try {
            response = UrltoValue.getValuefromUrl(Endpoints.getSearching(news_channel.replaceAll(" ","%20"),limit));
            Log.e("request string is ","<><"+Endpoints.getSearching(news_channel,limit));
            Log.e("response is ","<><"+response);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Long result1) {

        try{
             myPojo = gson.fromJson(response, MyPojo.class);
            Log.e("title ","<><>"+myPojo.getItems().get(0).getSnippet().getTitle());
//            Log.e("next page token is","<><"+myPojo.getNextPageToken());
            Endpoints.nextpagetoken = myPojo.getNextPageToken();
            apiService.apiRespose(myPojo);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    }
