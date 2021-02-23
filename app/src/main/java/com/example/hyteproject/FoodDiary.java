package com.example.hyteproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.ArrayList;

/**
 * FoodDiary for the application. User can keep track of his daily total calories by inputting them to the application.
 * @author Malin Mortti, Kyyrö Kerkko
 * @version 1.0
 */
public class FoodDiary extends AppCompatActivity {

    private ListView foodListview;
    private EditText calories;
    private EditText foodName;
    private Food food;
    private ArrayList<String> foodList;
    private TextView textViewCalorieCounter;
    private SharedPreferences sharedPreferencesFoodInformation;
    private final String totalCaloriesKey = "totalCaloriesKey";
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private Calendar calendar;
    private int day;

    private int submittedCalories = 0;
    private int totalCalories = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_diary);
        foodListview = findViewById(R.id.listViewFoodList);
        foodName = findViewById(R.id.editTextFoodName);
        calories = findViewById(R.id.editTextNumberCaloriesAmount);
        textViewCalorieCounter = findViewById(R.id.textViewTotalCalories);
        foodList = new ArrayList<>();

        sharedPreferencesFoodInformation = getSharedPreferences("TotalCaloriesInformation", Activity.MODE_PRIVATE);
        totalCalories = sharedPreferencesFoodInformation.getInt(totalCaloriesKey, 0);
        textViewCalorieCounter.setText(Integer.toString(totalCalories));
        setTime();

        /*
        pendingIntent = PendingIntent.getService(context 0, new Intent(context, MyService.class), PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        */
    }


    /**
     * Submits the user input for food name and calorie amount to a list and shows it to the user
     * adds calories to a total calorie count
     *
     */
    public void submitFood(View view){

        String submittedName = foodName.getText().toString();
        if(calories.getText().toString().isEmpty() == false) {
            submittedCalories = Integer.valueOf(calories.getText().toString());
            if (submittedName != null) {
                food = new Food(submittedName, submittedCalories);
            }
            foodList.add(food.getFood());
            totalCalories += Integer.valueOf(submittedCalories);
        }

        textViewCalorieCounter.setText(Integer.toString(totalCalories));

        final ArrayAdapter adapter = (new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, foodList));
        foodListview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void setTime(){
        Calendar c = Calendar.getInstance();
        c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH),
                11, 14, 0);

        setReset(c.getTimeInMillis());
    }

    private void setReset(long time){

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, DailyReset.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,intent, 0);

        alarmManager.setRepeating(AlarmManager.RTC, time, AlarmManager.INTERVAL_DAY, pendingIntent);
    }


    protected void onPause(){
        super.onPause();
        SharedPreferences.Editor prefEditor = sharedPreferencesFoodInformation.edit();
        prefEditor.putInt(totalCaloriesKey, totalCalories);
        prefEditor.commit();
    }
}