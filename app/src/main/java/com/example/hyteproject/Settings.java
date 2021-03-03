package com.example.hyteproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;

public class Settings extends AppCompatActivity {

    private RadioGroup radioGroupBMI;
    private int selectedButton = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        radioGroupBMI = findViewById(R.id.radioGroupBMI);
    }

    public void goToMenu (View view) {
        Intent nextActivity = new Intent(Settings.this, MainActivity.class);
        startActivity(nextActivity);
    }

    protected void onResume() {
        super.onResume();
        int i = DataManager.readBMISettingInPref(this);
        if (i == 0) {
            radioGroupBMI.check(R.id.radioButtonMetric);
        } else if (i == 1) {
            radioGroupBMI.check(R.id.radioButtonImperial);
        }
    }

    protected void onPause() {
        super.onPause();
        updateBMI();
    }

    public void updateBMI () {
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