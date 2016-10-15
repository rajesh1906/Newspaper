package com.metrial.chrajeshkumar.newspaper.Pojos;

/**
 * Created by ChRajeshKumar on 9/14/2016.
 */
public class Thumbnails {


    private High high;


    private String channelTitle;

    private String liveBroadcastContent;

    public High gethigh() {
        return high;
    }

    public void sethigh(High aDefault) {
        this.high = aDefault;
    }

    public String getChannelTitle ()
    {
        return channelTitle;
    }

    public void setChannelTitle (String channelTitle)
    {
        this.channelTitle = channelTitle;
    }

    public String getLiveBroadcastContent ()
    {
        return liveBroadcastContent;
    }

    public void setLiveBroadcastContent (String liveBroadcastContent)
    {
        this.liveBroadcastContent = liveBroadcastContent;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [default = "+""+", channelTitle = "+channelTitle+", liveBroadcastContent = "+liveBroadcastContent+"]";
    }
}
