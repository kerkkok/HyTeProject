package com.example.hyteproject;

import android.util.Log;

/**
 * Food class for the food diary
 * @author Malin Mortti, Kyyrö Kerkko
 * @version 1.0
 *
 */
public class Food {

    private String name;
    private int calories;

    /**
     * Takes the meals name and calorie amount
     * @param name name of the meal
     * @param calories calories amount of the meal
     */
    public Food (String name, int calories){
        this.name = name;
        this.calories = calories;
    }

    /**
     *
     * @return returns the name and calorie amount of the meal
     */
    public String getFood(){
        return this.name + ", " + Integer.toString(this.calories) + " Calories.";
    }
}
