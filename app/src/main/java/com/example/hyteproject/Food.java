package com.example.hyteproject;

import android.util.Log;

public class Food {

    private String name;
    private int calories;
    private int totalCalories;

    public Food (String name, int calories){
        this.name = name;
        this.calories = calories;
    }

    public String getName(){
        return this.name;
    }

    public int getCalories(){
        return this.calories;
    }

    public String getFood(){
        return this.name + ", " + Integer.toString(this.calories) + " Calories.";
    }

    public int getTotalCalories(){
        totalCalories = this.calories + totalCalories;
        return totalCalories;
    }
}
