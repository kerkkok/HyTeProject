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
    private int yesterdaysStepCount = 0;
    private static String yesterdaysCaloriesKey = "yesterdaysCaloriesKey";
    private static String yesterdaysStepCountKey = "yesterdaysStepCountKey";

    @Override
    public void onReceive(Context context, Intent intent){

        SharedPreferences yesterdaysCaloriesPref = context.getSharedPreferences("YesterdaysCalorieInformation", Context.MODE_PRIVATE);
        yesterdaysCaloriesPref.edit().clear().commit();

        SharedPreferences yesterdaysStepCountsPref = context.getSharedPreferences("YesterdaysStepCounts", Context.MODE_PRIVATE);
        yesterdaysStepCountsPref.edit().clear().commit();

        SharedPreferences totalCaloriesInformationSharedPref = context.getSharedPreferences("TotalCaloriesInformation", Context.MODE_PRIVATE);
        yesterdaysCalories = DataManager.readCaloriesInPref(context);
        totalCaloriesInformationSharedPref.edit().clear().commit();

        SharedPreferences stepCountSharedPref = context.getSharedPreferences("StepCount", Context.MODE_PRIVATE);
        yesterdaysStepCount = DataManager.readStepCountInPref(context);
        stepCountSharedPref.edit().clear().commit();

        SharedPreferences.Editor YesterdayCaloriesPrefEditor = yesterdaysCaloriesPref.edit();
        YesterdayCaloriesPrefEditor.putInt(yesterdaysCaloriesKey, yesterdaysCalories);
        YesterdayCaloriesPrefEditor.commit();

        SharedPreferences.Editor YesterdayStepCountPrefEditor = yesterdaysStepCountsPref.edit();
        YesterdayStepCountPrefEditor.putInt(yesterdaysStepCountKey, yesterdaysStepCount);
        YesterdayStepCountPrefEditor.commit();

        Log.d("DailyReset", "Reset happened");
        //System.exit(1);
    }
}
