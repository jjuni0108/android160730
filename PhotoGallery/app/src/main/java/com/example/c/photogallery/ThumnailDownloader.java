package com.example.c.photogallery;

import android.os.HandlerThread;
import android.util.Log;

/**
 * Created by c on 2016-08-13.
 */
public class ThumnailDownloader extends HandlerThread{
    private static final  String TAG = "ThumbnailDownloader";

    public ThumnailDownloader() {
        super(TAG);
    }

    public void queueThumbnail(String url){
        Log.d(TAG,"thumbnail url  : "+url);
    }
}
