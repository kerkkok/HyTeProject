package com.example.hyteproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class BmiC extends AppCompatActivity {

    float weight, height;
    float bmi;

    EditText weightInput;
    EditText heightInput;
    TextView yourB;
    Button calcButton;

    String inputWeight;
    String inputHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_c);
        weightInput = (EditText) findViewById(R.id.weightt);
        heightInput = (EditText) findViewById(R.id.heightt);


        calcButton = (Button) findViewById(R.id.calcBut);
        yourB = (TextView)findViewById(R.id.yourb);




         calcButton.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                if (!weightInput.getText().toString().isEmpty() || !heightInput.getText().toString().isEmpty()) {


                    float weight = Float.valueOf(weightInput.getText().toString());
                    float height = Float.valueOf(heightInput.getText().toString());
                    bmi = weight / ((height / 100) * (height / 100));
                    String bmin = String.valueOf(Float.valueOf(bmi));
                    yourB.setText("Your BMI is " + bmin);
                }else yourB.setText("Input values");
            }

            });
        }
    }


