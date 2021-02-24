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
import android.widget.Toast;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

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
    private final String dailyFoodsKey = "dailyFoodsKey";
    private Gson gson;

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

        foodList = FoodDataManager.readArrayFromPref(this);
                if (foodList == null) {
                foodList = new ArrayList<>(); }
        final ArrayAdapter adapter = (new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, foodList));
        foodListview.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        totalCalories = FoodDataManager.readCaloriesInPref(this);
        textViewCalorieCounter.setText(Integer.toString(totalCalories));
        setTime();


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
        } else {
            Toast.makeText(this, "Please insert both food name and calories amount", Toast.LENGTH_SHORT).show();
        }

        FoodDataManager.writeCaloriesInPref(getApplicationContext(), totalCalories);
        FoodDataManager.writeArrayInPref(getApplicationContext(), foodList);
        textViewCalorieCounter.setText(Integer.toString(totalCalories));

        final ArrayAdapter adapter = (new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, foodList));
        foodListview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void setTime(){
        Calendar c = Calendar.getInstance();
        c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH),
                23, 59, 0);

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
    }
}