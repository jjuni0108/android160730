package com.example.c.photogallery;

import android.net.Uri;

/**
 * Created by c on 2016-08-07.
 */
public class GalleryItem {
    private String mCaption;
    private String mId;
    private String mUrl;
    private String owner;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Uri getPhotoPageUri(){
      return Uri.parse("http://www.flikr.com/photos/")
              .buildUpon()
              .appendPath(owner)
              .appendPath(mId)
              .build();
    }

    @Override
    public String toString() {
        return mCaption;
    }

    public String getCaption() {
        return mCaption;
    }

    public void setCaption(String caption) {
        mCaption = caption;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }
}
