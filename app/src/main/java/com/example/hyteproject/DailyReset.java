package com.example.hyteproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;


/**
 * When alarm triggers this class will run to wipe out the SharedPreferences for food diary information, saves last days total calories to its own preference.
 * @author Mortti Malin
 */
public class DailyReset extends BroadcastReceiver {

    private int yesterdaysCalories = 0;
    private static String yesterdaysCaloriesKey = "yesterdaysCaloriesKey";

    @Override
    public void onReceive(Context context, Intent intent){

        SharedPreferences yesterdaysCaloriesPref = context.getSharedPreferences("YesterdaysCalorieInformation", Context.MODE_PRIVATE); /* Gets yesterdays calories SharedPreferences and empties it */
        yesterdaysCaloriesPref.edit().clear().commit();

        SharedPreferences totalCaloriesInformationSharedPref = context.getSharedPreferences("TotalCaloriesInformation", Context.MODE_PRIVATE); /* Gets today's calories and puts it in yesterdaysCalories variable before resetting SharedPreferences  */
        yesterdaysCalories = DataManager.readCaloriesInPref(context);
        totalCaloriesInformationSharedPref.edit().clear().commit();

        SharedPreferences.Editor YesterdayCaloriesPrefEditor = yesterdaysCaloriesPref.edit(); /* Puts the yesterdaysCalories variable in yesterdays calories SharedPreferences  */
        YesterdayCaloriesPrefEditor.putInt(yesterdaysCaloriesKey, yesterdaysCalories);
        YesterdayCaloriesPrefEditor.commit();
    }
}
