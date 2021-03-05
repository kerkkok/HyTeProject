package com.example.hyteproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

/**
 * Settings for the application. User can tweak the app according to their personal targets and preferences.
 * @author Kyyrö Kerkko
 */

public class Settings extends AppCompatActivity {

    private EditText editTextCalorieTarget;
    private RadioGroup radioGroupBMI;
    private int selectedButton, selectedButtonTag = 0;
    private String targetCalories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        radioGroupBMI = findViewById(R.id.radioGroupBMI);
        editTextCalorieTarget = findViewById(R.id.editTextCalorieTarget);
    }

    /**
     * @param view switches activity to MainActivity
     */
    public void goToMenu (View view) {
        Intent nextActivity = new Intent(Settings.this, MainActivity.class);
        startActivity(nextActivity);
    }

    protected void onResume() {
        super.onResume();
        targetCalories = DataManager.readTargetCaloriesInPref(this);
        editTextCalorieTarget.setText(targetCalories);

        selectedButtonTag = DataManager.readBMISettingInPref(this);
        if (selectedButtonTag == 0) {
            radioGroupBMI.check(R.id.radioButtonMetric);
        } else if (selectedButtonTag == 1) {
            radioGroupBMI.check(R.id.radioButtonImperial);
        }
    }

    protected void onPause() {
        super.onPause();
        saveSettings();
    }

    /**
     * Calls methods to save user settings.
     */
    public void saveSettings () {
        targetCalories = editTextCalorieTarget.getText().toString();
        DataManager.writeTargetCaloriesInPref(getApplicationContext(), targetCalories);

        selectedButton = radioGroupBMI.getCheckedRadioButtonId();
        switch (selectedButton) {
            case R.id.radioButtonMetric:
                DataManager.writeBMISettingInPref(getApplicationContext(), 0);
                break;
            case R.id.radioButtonImperial:
                DataManager.writeBMISettingInPref(getApplicationContext(), 1);
                break;
        }
    }
}