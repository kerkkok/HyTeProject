package com.example.hyteproject;

import android.Manifest;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.Calendar;

/**
 * MainActivity contains the Step Counter sensor, Reset Button for the stepCount, BMI Calculator in its entirety, and buttons for the other Activities
 * @author Malin Mortti, Kyyrö Kerkko, Aaro Musse, Joona Sillanpää
 */
public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private TextView textViewStep;
    private SensorManager sensorManager;
    private Sensor stepCounter;
    private boolean isCounterSensorPresent;
    private int stepCount = 0;
    private int stepCountCompensator = 0;
    private int stepReset = 0;
    private final String firstLaunch = "firstLaunch";
    private EditText weightInput;
    private EditText heightInput;
    private TextView textViewCalculatedBMI;
    private float weight, height, bmi = 0;
    private int units = 0;
    private boolean metric = true;
    private boolean imperial = false;
    private String bmiWeightHintString;
    private String bmiHeightHintString;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED){
            //ask for permission to use
            requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 0);
        }

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        textViewStep = findViewById(R.id.stepText);

        stepCountCompensator = DataManager.readStepCountInPref(this);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        textViewStep.setText(String.valueOf(stepCount));
        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)!=null){
            stepCounter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            isCounterSensorPresent = true;

        }else {
            textViewStep.setText("Counter not present");
            isCounterSensorPresent = false;
        }

        weightInput = (EditText) findViewById(R.id.weightInput);
        heightInput = (EditText) findViewById(R.id.heightInput);
        textViewCalculatedBMI = (TextView)findViewById(R.id.textViewCalculatedBMI);
        units = DataManager.readBMISettingInPref(this);
        bmiWeightHintString = getString(R.string.weight_input);
        bmiHeightHintString = getString(R.string.height_input);

        firstTimeLaunch();
    }



    /**
     * Calculates the steps taken by the user since last reset.
     * Method checks if androids total steps are below the compensator value and resets if needed.
     */
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor == stepCounter) {
            stepReset = (int) sensorEvent.values[0];
            if(stepReset < stepCountCompensator){
                doStepReset();
            }
            stepCount = (int) sensorEvent.values[0] - stepCountCompensator;
            textViewStep.setText(String.valueOf(stepCount));
        }
    }
    public void resetStepsButton(View view){
        doStepReset();
    }
    public void doStepReset(){
        DataManager.writeStepCountInPref(this, stepReset);
        stepCountCompensator = stepReset;
        textViewStep.setText(String.valueOf(0));
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    protected void onResume() {
        super.onResume();
        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)!=null) {
            sensorManager.registerListener(this, stepCounter, SensorManager.SENSOR_DELAY_NORMAL);
        }
        units = DataManager.readBMISettingInPref(this);
        if (units == 0) {
             metric = true;
             imperial = false;
             weightInput.setHint("Type here (kg)");
            heightInput.setHint("Type here (cm)");
        } else if (units == 1) {
            metric = false;
            imperial = true;
            weightInput.setHint("Type here (lbs)");
            heightInput.setHint("Type here (feet)");
        } else {
            Log.e("BMIUnitsError", "Failed to retrieve BMI units from pref");
        }
    }


    /**
     * Calculates BMI using mathematical formulas. Method checks that user input is not empty.
     * Method checks settings regarding users preferred unit types and does the calculation accordingly.
     */
    public void calculateBMI (View view) {
        if (!weightInput.getText().toString().isEmpty() || !heightInput.getText().toString().isEmpty()) {
            if (metric) {
                weight = Float.valueOf(weightInput.getText().toString());
                height = Float.valueOf(heightInput.getText().toString());
                bmi = weight / ((height / 100) * (height / 100));
            } else if (imperial) {
                weight = Float.valueOf(weightInput.getText().toString());
                height = Float.valueOf(heightInput.getText().toString());
                weight *= 0.45359237;
                height *= 30.48;
                bmi = weight / ((height / 100) * (height / 100));
            } else {
                Log.e("BMIConversionError", "Failed to determine unit type");
            }

            String bmin = String.format("%.02f", bmi);
            textViewCalculatedBMI.setText("Your BMI is " + bmin);
        } else {
            Toast.makeText(this, "Please insert both weight and height", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * @param view switches activity to FoodDiary
     */
    public void goToFoodDiary (View view) {
        Intent nextActivity = new Intent(MainActivity.this, FoodDiary.class);
        startActivity(nextActivity);
    }

    /**
     * @param view switches activity to Settings
     */
    public void goToSettings (View view) {
        Intent nextActivity = new Intent(MainActivity.this, Settings.class);
        startActivity(nextActivity);
    }

    /**
     * Sets a specific clock time for a reset.
     */
    public void setTime(){
        Calendar c = Calendar.getInstance();
        c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH),
                23, 59, 59);
        setReset(c.getTimeInMillis());
        Log.d("DailyReset", "Timer applied");
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

    /**
     * Checks if the application has ever been launched with the help of SharedPreferences, if application started first time, runs the code inside.
     */
    private void firstTimeLaunch(){
        SharedPreferences prefEdit = getSharedPreferences(firstLaunch, 0);
        if (prefEdit.getBoolean("FirstLaunch", true)) {
            setTime();
            prefEdit.edit().putBoolean("FirstLaunch", false).commit();
        }
    }


}
