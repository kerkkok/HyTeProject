package com.example.hyteproject;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;



public class DailyReset extends BroadcastReceiver {

    /**
     * Alarm triggers this method at a fixed time to wipe out the SharedPreferences for total calories
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent){

        SharedPreferences sharedPref = context.getSharedPreferences("TotalCaloriesInformation", Context.MODE_PRIVATE);
        sharedPref.edit().clear().commit();

        Log.d("DailyReset", "Reset happened");
    }
}
