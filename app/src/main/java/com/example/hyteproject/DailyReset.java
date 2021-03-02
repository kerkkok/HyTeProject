package com.example.hyteproject;

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

    private int yesterdaysCalories = 0;

    @Override
    public void onReceive(Context context, Intent intent){

        SharedPreferences yesterdaysCaloriesPref = context.getSharedPreferences("YesterdaysCalorieInformation", Context.MODE_PRIVATE);
        yesterdaysCaloriesPref.edit().clear().commit();

        SharedPreferences sharedPref = context.getSharedPreferences("TotalCaloriesInformation", Context.MODE_PRIVATE);
        yesterdaysCalories = FoodDataManager.readCaloriesInPref(context);
        sharedPref.edit().clear().commit();

        SharedPreferences.Editor prefEditor = yesterdaysCaloriesPref.edit();
        prefEditor.putInt("yesterdaysCaloriesKey", yesterdaysCalories);
        prefEditor.commit();

        Log.d("DailyReset", "Reset happened");
        //System.exit(1);
    }
}
