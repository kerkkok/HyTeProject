package com.example.hyteproject;

import androidx.appcompat.app.AppCompatActivity;

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
    private int totalCalories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_diary);
        foodListview = findViewById(R.id.listViewFoodList);
        foodName = findViewById(R.id.editTextFoodName);
        calories = findViewById(R.id.editTextNumberCaloriesAmount);
        foodList = new ArrayList<>();
    }


    /**
     * Submits the user input for food name and calorie amount to a list and shows it to the user
     * adds calories to a total calorie count
     *
     */
    public void submitFood(View view){

        String submittedName = foodName.getText().toString();
        int submittedCalories = Integer.valueOf(calories.getText().toString());
        food = new Food(submittedName, submittedCalories);
        foodList.add(food.getFood());
        totalCalories += Integer.valueOf(submittedCalories);

        textViewCalorieCounter = findViewById(R.id.textViewTotalCalories);
        textViewCalorieCounter.setText(Integer.toString(totalCalories));

        final ArrayAdapter adapter = (new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, foodList));
        foodListview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}