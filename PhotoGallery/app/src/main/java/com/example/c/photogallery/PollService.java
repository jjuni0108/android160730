package com.example.c.photogallery;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
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
    private static final int POLL_INTERNAL = 1000 *10;



    public PollService() {
        super("PollService");
    }



    public static Intent newIntent(Context context){
        return new Intent(context,PollService.class);
    }

    public static void setSeviceAlarm(Context context, boolean isOn){
        Intent intent = PollService.newIntent(context);
        PendingIntent pIntent =PendingIntent.getService(context,0,intent,0);
        AlarmManager am = (AlarmManager)context.getSystemService(ALARM_SERVICE);
        if(isOn){
            am.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(),POLL_INTERNAL,pIntent );
        }else{
            am.cancel(pIntent);
            pIntent.cancel();
        }


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
            Intent photointent = new Intent(this,PhotoGalleryActivity.class);
            PendingIntent pendingIntent=PendingIntent.getActivity(this,0,photointent,0);
            Notification notification= new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("PhotoGallery")
                    .setContentText("Got new Data")
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .build();

            NotificationManagerCompat managerCompat=  NotificationManagerCompat. from(this);
            managerCompat.notify(0,notification);
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

    public static boolean isServiceAlarmOn(Context context) {
        Intent intent= PollService.newIntent(context);
        PendingIntent pendingIntent = PendingIntent.getService(context,0,intent,PendingIntent.FLAG_NO_CREATE);
        return pendingIntent!=null;
    }
}
