package com.example.c.photogallery;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;

import java.util.List;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class PollService extends IntentService {

    public PollService() {
        super("PollService");
    }

    public static Intent newIntent(Context context){
        return new Intent(context,PollService.class);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if(isNetworkAvailable()==false){
            return;
        }

        String query = QueryPreperence.getStoredQuery(this);
        String lastResultId= QueryPreperence.getLastResultId(this);
        List<GalleryItem> items;
        if(query == null){

            items = new FlickrFetchr().fetchRecentPhotos();
        }else{
            items = new FlickrFetchr().searchPhotos(query);
        }

        if(items.size()==0){
            return;
        }

        String resultId = items.get(0).getId();
        if(resultId.equals(lastResultId)){
            Log.d("PollService","Got old data");
        }else{
            Log.d("PollService" ," Got new data");
        }

        QueryPreperence.setLastResultId(this,resultId);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager cm= (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        //네트워크 가능한지
        boolean isNetworkAvailable = cm.getActiveNetworkInfo() != null;
        //wifi에 ap가 접속하지 못했을경우
        boolean isNetworkConnected = isNetworkAvailable && cm.getActiveNetworkInfo().isConnected();
        return isNetworkConnected;

    }

}
