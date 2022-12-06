package com.example.i190417_i190468_i190260;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class WorkoutDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_details);

        Intent intent = getIntent();
        String exerciseName = intent.getStringExtra("exerciseName");
        String exerciseDescription = intent.getStringExtra("exerciseDescription");
        String exerciseCalories = intent.getStringExtra("exerciseCalories");
        String exerciseTime = intent.getStringExtra("exerciseTime");
        String exerciseImage = intent.getStringExtra("exerciseImage");
        String exerciseVideo = intent.getStringExtra("exerciseVideo");
    }
}