package com.example.hyteproject;

import android.util.Log;

/**
 * Food class for the food diary
 * @author Malin Mortti, Kyyrö Kerkko
 */
public class Food {

    private String name;
    private int calories;

    /**
     * Takes the meals name and calorie amount.
     * @param name String name of the meal.
     * @param calories int calories amount of the meal.
     */
    public Food (String name, int calories){
        this.name = name;
        this.calories = calories;
    }

    /**
     * Returns calories amount.
     * @return int calories of the food.
     */
    public int getCalories(){
        return this.calories;
    }

    /**
     * Returns foods name.
     * @return String name of the .
     */
    public String getName(){
        return this.name;
    }

    /**
     * Puts name and calories together to show them in ListView.
     * @return returns complete information about the meal.
     */
    @Override
    public String toString(){
        return this.name + ", " + Integer.toString(this.calories) + " Calories.";
    }
}
