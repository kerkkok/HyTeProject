package com.example.hyteproject;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;



public class DailyReset extends BroadcastReceiver {

    private final String totalCaloriesKey = "totalCaloriesKey";
    private int totalCalories = 0;

    @Override
    public void onReceive(Context context, Intent intent){


        SharedPreferences sharedPref = context.getSharedPreferences("TotalCaloriesInformation", Context.MODE_PRIVATE);
        totalCalories = sharedPref.getInt(totalCaloriesKey, 0);

        SharedPreferences.Editor prefEditor = sharedPref.edit();
        sharedPref.edit().clear().commit();

        Log.d("DailyReset", "Reset happened");
    }
}
