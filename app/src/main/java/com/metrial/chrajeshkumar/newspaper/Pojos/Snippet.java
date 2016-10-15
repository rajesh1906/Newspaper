package com.metrial.chrajeshkumar.newspaper.Pojos;

/**
 * Created by ChRajeshKumar on 9/14/2016.
 */
public class Snippet {
    private String title;

    private String description;

    private Thumbnails thumbnails;

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public Thumbnails getThumbnails ()
    {
        return thumbnails;
    }

    public void setThumbnails (Thumbnails thumbnails)
    {
        this.thumbnails = thumbnails;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [title = "+title+", description = "+description+", thumbnails = "+thumbnails+"]";
    }
}
