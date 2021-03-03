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
     * @return returns calories
     */
    public int getCalories(){
        return this.calories;
    }

    /**
     *
     * @return returns calories
     */
    public String getName(){
        return this.name;
    }

    /**
     *
     * @return returns complete information about the meal
     */
    @Override
    public String toString(){
        return this.name + ", " + Integer.toString(this.calories) + " Calories.";
    }
}
