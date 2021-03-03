package com.example.hyteproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.logging.Handler;
import java.util.logging.LogRecord;


/**
 * When alarm triggers this class will run to wipe out the SharedPreferences for food diary information, saves last days total calories to its own preference.
 * @author Mortti Malin
 */
public class DailyReset extends BroadcastReceiver {

    private int yesterdaysCalories = 0;
    private int yesterdaysStepCount = 0;
    private static String yesterdaysCaloriesKey = "yesterdaysCaloriesKey";
    private static String yesterdaysStepCountKey = "yesterdaysStepCountKey";

    @Override
    public void onReceive(Context context, Intent intent){

        SharedPreferences yesterdaysCaloriesPref = context.getSharedPreferences("YesterdaysCalorieInformation", Context.MODE_PRIVATE); /* Gets yesterdays calories SharedPreferences and empties it */
        yesterdaysCaloriesPref.edit().clear().commit();

        SharedPreferences yesterdaysStepCountsPref = context.getSharedPreferences("YesterdaysStepCounts", Context.MODE_PRIVATE);    /* Gets yesterdays step count SharedPreferences and empties it */
        yesterdaysStepCountsPref.edit().clear().commit();

        SharedPreferences totalCaloriesInformationSharedPref = context.getSharedPreferences("TotalCaloriesInformation", Context.MODE_PRIVATE); /* Gets today's calories and puts it in yesterdaysCalories variable before resetting SharedPreferences  */
        yesterdaysCalories = DataManager.readCaloriesInPref(context);
        totalCaloriesInformationSharedPref.edit().clear().commit();

        SharedPreferences stepCountSharedPref = context.getSharedPreferences("StepCount", Context.MODE_PRIVATE); /* Gets today's step count and puts it in yesterdaysStepCount variable before resetting SharedPreferences  */
        yesterdaysStepCount = DataManager.readStepCountInPref(context);
        stepCountSharedPref.edit().clear().commit();

        SharedPreferences.Editor YesterdayCaloriesPrefEditor = yesterdaysCaloriesPref.edit(); /* Puts the yesterdaysCalories variable in yesterdays calories SharedPreferences  */
        YesterdayCaloriesPrefEditor.putInt(yesterdaysCaloriesKey, yesterdaysCalories);
        YesterdayCaloriesPrefEditor.commit();

        SharedPreferences.Editor YesterdayStepCountPrefEditor = yesterdaysStepCountsPref.edit(); /* Puts the yesterdaysStepCount variable in yesterdays StepCount SharedPreferences  */
        YesterdayStepCountPrefEditor.putInt(yesterdaysStepCountKey, yesterdaysStepCount);
        YesterdayStepCountPrefEditor.commit();

        Log.d("DailyReset", "Reset happened");
        //System.exit(1);
    }
}
