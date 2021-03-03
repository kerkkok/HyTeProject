package com.example.hyteproject;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * This class have methods that will write and read information from SharedPreferences
 * @author Mortti Malin, Kerkko Kyyrö
 */
public class DataManager {


    private static String totalCaloriesKey = "totalCaloriesKey";
    private static String dailyFoodsKey = "dailyFoodsKey";
    private static String yesterdaysCaloriesKey = "yesterdaysCaloriesKey";
    private static String stepCountKey = "stepCountKey";
    private static String yesterdaysStepCountKey = "yesterdaysStepCountKey";
    private static String targetStepsKey = "targetStepsKey";
    private static String targetCaloriesKey = "targetCaloriesKey";
    private static String bmiSettingsKey = "bmiSettingsKey";

    /**
     * Writes the ArrayList information into the SharedPreferences using the help of json.
     * @param list food ArrayList from FoodDiary.
     */
    public static void writeArrayInPref(Context context, List<Food> list){

        Gson gson = new Gson();
        String json = gson.toJson(list);
        SharedPreferences sharedPref = context.getSharedPreferences("TotalCaloriesInformation", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPref.edit();
        prefEditor.putString(dailyFoodsKey, json);
        prefEditor.commit();
    }

    /**
     * Gets json string from SharedPreferences and turns it back into ArrayList<Food>.
     * @return ArrayList<Food>.
     */
    public static ArrayList<Food> readArrayFromPref(Context context){

        SharedPreferences sharedPref = context.getSharedPreferences("TotalCaloriesInformation", Context.MODE_PRIVATE);
        String json = sharedPref.getString("dailyFoodsKey", "");
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Food>>() {}.getType();
        ArrayList<Food> list = gson.fromJson(json, type);
        return list;
    }

    /**
     * Writes totalCalories in SharedPreferences.
     * @param calories the daily int totalCalories from FoodDiary.
     */
    public static void writeCaloriesInPref(Context context, int calories){

        SharedPreferences sharedPref = context.getSharedPreferences("TotalCaloriesInformation", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPref.edit();
        prefEditor.putInt(totalCaloriesKey, calories);
        prefEditor.commit();
    }

    /**
     * Gets totalCalories from SharedPreferences.
     * @return int totalCalories from SharedPreferences.
     */
    public static int readCaloriesInPref(Context context){

        SharedPreferences sharedPref = context.getSharedPreferences("TotalCaloriesInformation", Context.MODE_PRIVATE);
        int calories = sharedPref.getInt(totalCaloriesKey, 0);
        return calories;
    }

    /**
     * Gets totalCalories from SharedPreferences.
     * @return yesterdaysCalories from SharedPreferences.
     */
    public static int readYesterdaysCaloriesInPref(Context context){

        SharedPreferences sharedPref = context.getSharedPreferences("YesterdaysCalorieInformation", Context.MODE_PRIVATE);
        int calories = sharedPref.getInt(yesterdaysCaloriesKey, 0);
        return calories;
    }

    /**
     * Writes today's stepCounts in SharedPreferences
     * @param stepCount will be written in StepCount SharedPreferences.
     */
    public static void writeStepCountInPref(Context context, int stepCount){

        SharedPreferences sharedPref = context.getSharedPreferences("StepCount", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPref.edit();
        prefEditor.putInt(stepCountKey, stepCount);
        prefEditor.commit();
    }

    /**
     * Gets stepCount from SharedPreferences.
     * @return int stepCount from SharedPreferences.
     */
    public static int readStepCountInPref(Context context){

        SharedPreferences sharedPref = context.getSharedPreferences("StepCount", Context.MODE_PRIVATE);
        int stepCount = sharedPref.getInt(stepCountKey, 0);
        return stepCount;
    }

    /**
     * Gets yesterdays stepCount from SharedPreferences.
     * @return yesterdays int stepCount
     */
    public static int readYesterdaysStepCountInPref(Context context){

        SharedPreferences sharedPref = context.getSharedPreferences("YesterdaysStepCounts", Context.MODE_PRIVATE);
        int yesterdayStepCount = sharedPref.getInt(yesterdaysStepCountKey, 0);
        return yesterdayStepCount;
    }

    /**
     * Writes targetSteps String in SharedPreferences
     * @param targetSteps will be written in TargetSteps SharedPreferences.
     */
    public static void writeTargetStepsInPref(Context context, String targetSteps) {
        SharedPreferences sharedPref = context.getSharedPreferences("TargetSteps", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPref.edit();
        prefEditor.putString(targetStepsKey, targetSteps);
        prefEditor.commit();
    }

    /**
     * Gets users targetSteps from SharedPreferences
     * @return String targetSteps
     */
    public static String readTargetStepsInPref(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences("TargetSteps", Context.MODE_PRIVATE);
        String targetSteps = sharedPref.getString(targetStepsKey, "");
        return targetSteps;
    }

    /**
     * Writes targetCalories String in SharedPreferences
     * @param targetCalories will be written in targetCalories SharedPreferences.
     */
    public static void writeTargetCaloriesInPref(Context context, String targetCalories) {
        SharedPreferences sharedPref = context.getSharedPreferences("TargetCalories", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPref.edit();
        prefEditor.putString(targetCaloriesKey, targetCalories);
        prefEditor.commit();
    }

    /**
     * Gets users targetCalories from SharedPreferences
     * @return String targetCalories
     */
    public static String readTargetCaloriesInPref(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences("TargetCalories", Context.MODE_PRIVATE);
        String targetCalories = sharedPref.getString(targetCaloriesKey, "");
        return targetCalories;
    }

    /**
     * Gets users selectedUnits from SharedPreferences
     * @return int selectedUnits selection reference number
     */
    public static int readBMISettingInPref(Context context){

        SharedPreferences sharedPref = context.getSharedPreferences("BMISettings", Context.MODE_PRIVATE);
        int selectedUnits = sharedPref.getInt(bmiSettingsKey, 0);
        return selectedUnits;
    }

    /**
     * Writes selectedUnits reference number in SharedPreferences
     * @param selectedUnits will be written in BMISettings SharedPreferences.
     */
    public static void writeBMISettingInPref(Context context, int selectedUnits){

        SharedPreferences sharedPref = context.getSharedPreferences("BMISettings", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPref.edit();
        prefEditor.putInt(bmiSettingsKey, selectedUnits);
        prefEditor.commit();
    }





}
