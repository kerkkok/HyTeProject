package com.example.hyteproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView;

import java.util.ArrayList;

public class FoodDiary extends AppCompatActivity {

    private ListView foodListview;
    private EditText calories;
    private EditText foodName;
    private Food food;
    private ArrayList<String> foodList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_diary);

        foodListview = findViewById(R.id.listViewFoodList);
        foodName = findViewById(R.id.editTextFoodName);
        calories = findViewById(R.id.editTextNumberCaloriesAmount);
        foodList = new ArrayList<>();

        foodListview.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, foodList));

    }

    public void submitFood(View view){

        String submittedName = foodName.getText().toString();
        int submittedCalories = Integer.valueOf(calories.getText().toString());
        food = new Food(submittedName, submittedCalories);
        foodList.add(food.getFood());
        foodListview.invalidateViews();

        Log.d("Submit", "Tieto lähetetty");
    }



}