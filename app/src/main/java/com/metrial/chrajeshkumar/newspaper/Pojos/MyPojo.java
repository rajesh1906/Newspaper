package com.metrial.chrajeshkumar.newspaper.Pojos;

import java.util.ArrayList;

/**
 * Created by ChRajeshKumar on 9/14/2016.
 */
public class MyPojo {

    public String nextPageToken;
    public String prevPageToken;

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public String getPrevPageToken() {
        return prevPageToken;
    }

    public void setPrevPageToken(String prevPageToken) {
        this.prevPageToken = prevPageToken;
    }


    public ArrayList<Items> items = new ArrayList<>();

    public ArrayList<Items> getItems ()
    {
        return items;
    }

    public void setItems (ArrayList<Items> items)
    {
        this.items = items;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [items = "+items+"]";
    }
}
