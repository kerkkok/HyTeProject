package com.example.hyteproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;


/**
 * When alarm triggers this class will run to wipe out the SharedPreferences for food diary information, saves last days total calories to its own preference.
 * @author Mortti Malin
 */
public class DailyReset extends BroadcastReceiver {

    private int yesterdaysCalories = 0;
    private static String yesterdaysCaloriesKey = "yesterdaysCaloriesKey";

    @Override
    public void onReceive(Context context, Intent intent){
        // Gets yesterdays calories SharedPreferences and empties it
        SharedPreferences yesterdaysCaloriesPref = context.getSharedPreferences("YesterdaysCalorieInformation", Context.MODE_PRIVATE);
        yesterdaysCaloriesPref.edit().clear().commit();

        // Gets today's calories and puts it in yesterdaysCalories variable before resetting SharedPreferences
        SharedPreferences totalCaloriesInformationSharedPref = context.getSharedPreferences("TotalCaloriesInformation", Context.MODE_PRIVATE);
        yesterdaysCalories = DataManager.readCaloriesInPref(context);
        totalCaloriesInformationSharedPref.edit().clear().commit();

        // Puts the yesterdaysCalories variable in yesterdays calories SharedPreferences
        SharedPreferences.Editor YesterdayCaloriesPrefEditor = yesterdaysCaloriesPref.edit();
        YesterdayCaloriesPrefEditor.putInt(yesterdaysCaloriesKey, yesterdaysCalories);
        YesterdayCaloriesPrefEditor.commit();
    }
}
