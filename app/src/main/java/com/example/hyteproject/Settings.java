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
    private int selectedButton = 0;

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
        String tC = DataManager.readTargetCaloriesInPref(this);
        editTextCalorieTarget.setText(tC);

        int i = DataManager.readBMISettingInPref(this);
        if (i == 0) {
            radioGroupBMI.check(R.id.radioButtonMetric);
        } else if (i == 1) {
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
        String targetCalories = editTextCalorieTarget.getText().toString();
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