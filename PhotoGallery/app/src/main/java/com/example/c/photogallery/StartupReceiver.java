package com.example.c.photogallery;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class StartupReceiver extends BroadcastReceiver {
    public StartupReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("StartupReceiver","재부팅 했을 경우 리시버 받는 부분");
        PollService.setSeviceAlarm(context,true);
    }
}
