package com.example.hyteproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.TextView;

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


    protected void onPause(){
        super.onPause();
        SharedPreferences.Editor prefEditor = sharedPreferencesFoodInformation.edit();
        prefEditor.putInt(totalCaloriesKey, totalCalories);
        prefEditor.commit();
    }
}