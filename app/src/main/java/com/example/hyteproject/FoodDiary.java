package com.example.hyteproject;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;
import java.util.ArrayList;


/**
 * FoodDiary for the application. User can keep track of his daily total calories by inputting them to the application.
 * @author Malin Mortti, Kyyrö Kerkko
 * @version 1.0
 */
public class FoodDiary extends AppCompatActivity {

    private ListView foodListView;
    private EditText calories;
    private EditText foodName;
    private Food food;
    private ArrayList<String> foodList;
    private TextView textViewYesterdaysCalories;
    private TextView textViewCalorieCounter;
    private int submittedCalories = 0;
    private int yesterdaysCalories = 0;
    private int totalCalories = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_diary);
        foodListView = findViewById(R.id.listViewFoodList);
        foodName = findViewById(R.id.editTextFoodName);
        calories = findViewById(R.id.editTextNumberCaloriesAmount);
        textViewYesterdaysCalories = findViewById(R.id.textViewYesterdaysCalories);
        textViewCalorieCounter = findViewById(R.id.textViewTotalCalories);

        foodList = DataManager.readArrayFromPref(this);
                if (foodList == null) {
                foodList = new ArrayList<>(); }
        final ArrayAdapter adapter = (new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, foodList));
        foodListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        yesterdaysCalories = DataManager.readYesterdaysCaloriesInPref(this);
        textViewYesterdaysCalories.setText(Integer.toString(yesterdaysCalories));

        totalCalories = DataManager.readCaloriesInPref(this);
        textViewCalorieCounter.setText(Integer.toString(totalCalories));
        setTime();


        foodListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(foodList.size() >= 0) {
                    String splitString = foodList.get(i);

                    String[] split = splitString.split(", |\\ C");
                    String splitResult = split[1];
                    Log.d("Pepe", splitResult);
                    totalCalories -= Integer.parseInt(splitResult);

                    foodList.remove(i);

                    SharedPreferences sharedPref = getSharedPreferences("TotalCaloriesInformation", Context.MODE_PRIVATE);
                    sharedPref.edit().clear().commit();
                    DataManager.writeArrayInPref(getApplicationContext(), foodList);

                    textViewCalorieCounter.setText(Integer.toString(totalCalories));
                    foodListView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }
        });

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
            submittedName = submittedName.replaceAll("[^a-zA-Z0-9]", "");
            if (!submittedName.isEmpty()) {
                food = new Food(submittedName, submittedCalories);
                foodList.add(food.getFood());
                totalCalories += Integer.valueOf(submittedCalories);
                calories.getText().clear();
                foodName.getText().clear();
            } else {
                Toast.makeText(this, "Please insert both food name and calories amount", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "Please insert both food name and calories amount", Toast.LENGTH_SHORT).show();
        }
        textViewCalorieCounter.setText(Integer.toString(totalCalories));

        final ArrayAdapter adapter = (new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, foodList));
        foodListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    /**
    * Sets a specific clock time for a reset.
    */
    public void setTime(){
        Calendar c = Calendar.getInstance();
        c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH),
                12, 56, 0);
        setReset(c.getTimeInMillis());
    }

    /**
     * Sets an alarm activity using the time from setTime
     * @param time time for a reset
     */
    private void setReset(long time){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, DailyReset.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,intent, 0);
        alarmManager.setRepeating(AlarmManager.RTC, time, AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    protected void onPause() {
        super.onPause();
        DataManager.writeCaloriesInPref(getApplicationContext(), totalCalories);
        DataManager.writeArrayInPref(getApplicationContext(), foodList);
    }
}