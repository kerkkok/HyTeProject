package com.example.hyteproject;

import android.util.Log;

public class Food {

    private String name;
    private int calories;

    public Food (String name, int calories){
        this.name = name;
        this.calories = calories;
        Log.d("Olio", "Olio luotu");
    }

    public String getName(){
        return this.name;
    }

    public int getCalories(){
        return this.calories;
    }

    public String getFood(){
        return this.name + ", " + Integer.toString(this.calories) + " kaloria.";
    }
}
