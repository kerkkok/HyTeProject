package com.example.hyteproject;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FoodDataManager {


    private static String totalCaloriesKey = "totalCaloriesKey";
    private static String dailyFoodsKey = "dailyFoodsKey";

    /**
     * Writes the ArrayList information into the SharedPreferences using the help of gson.
     * @param context
     * @param list food ArrayList from FoodDiary
     */
    public static void writeArrayInPref(Context context, List<String> list){

        Gson gson = new Gson();
        String json = gson.toJson(list);
        SharedPreferences sharedPref = context.getSharedPreferences("TotalCaloriesInformation", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPref.edit();
        prefEditor.putString(dailyFoodsKey, json);
        prefEditor.commit();
    }

    /**
     * Gets gson from SharedPreferences and turns it back into ArrayList
     * @param context
     * @return ArrayList with food information
     */
    public static ArrayList<String> readArrayFromPref(Context context){

        SharedPreferences sharedPref = context.getSharedPreferences("TotalCaloriesInformation", Context.MODE_PRIVATE);
        String json = sharedPref.getString("dailyFoodsKey", "");
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        ArrayList<String> list = gson.fromJson(json, type);
        return list;
    }

    /**
     * Writes totalCalories in SharedPreferences
     * @param context
     * @param calories totalCalories from FoodDiary
     */
    public static void writeCaloriesInPref(Context context, int calories){

        SharedPreferences sharedPref = context.getSharedPreferences("TotalCaloriesInformation", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPref.edit();
        prefEditor.putInt(totalCaloriesKey, calories);
        prefEditor.commit();
    }

    /**
     * Gets totalCalories from SharedPreferences
     * @param context
     * @return totalCalories
     */
    public static int readCaloriesInPref(Context context){

        SharedPreferences sharedPref = context.getSharedPreferences("TotalCaloriesInformation", Context.MODE_PRIVATE);
        int calories = sharedPref.getInt(totalCaloriesKey, 0);
        return calories;
    }




}
