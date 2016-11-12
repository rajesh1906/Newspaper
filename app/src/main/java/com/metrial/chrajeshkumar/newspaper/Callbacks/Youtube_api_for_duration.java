package com.metrial.chrajeshkumar.newspaper.Callbacks;

import android.os.AsyncTask;
import android.util.Log;

import com.metrial.chrajeshkumar.newspaper.Activities.Videos_list;
import com.metrial.chrajeshkumar.newspaper.Adapters.Youtube_view_item;
import com.metrial.chrajeshkumar.newspaper.Helper.Update_youtube_timer;
import com.metrial.chrajeshkumar.newspaper.Pojos.MyPojo;
import com.metrial.chrajeshkumar.newspaper.Utils.Endpoints;
import com.metrial.chrajeshkumar.newspaper.Utils.UrltoValue;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by ChRajeshKumar on 04-Nov-16.
 */

public class Youtube_api_for_duration extends AsyncTask<URL, Integer, Long> {

    String response;
    Videos_list videos_list;
    Update_youtube_timer update_youtube_timer;
    String video_id;

    public Youtube_api_for_duration(String video_id, Videos_list videos_list) {
        this.video_id = video_id;
        this.videos_list = videos_list;
        this.update_youtube_timer = videos_list;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Long doInBackground(URL... params) {

        try {

                response = UrltoValue.getValuefromUrl(Endpoints.getVideo_duration_url(video_id));
                Log.e("request string is ", "<><" + Endpoints.getVideo_duration_url(video_id));
                Log.e("response is ", "<><" + response);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Long aLong) {
        super.onPostExecute(aLong);
        update_youtube_timer.update_youtube_timer(response);
    }

}
