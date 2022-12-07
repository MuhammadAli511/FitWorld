package com.example.i190417_i190468_i190260;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

public class WorkoutDetails extends AppCompatActivity {

    TextView exerciseName, exerciseDescription, exerciseCalories, exerciseTime, exerciseLink;
    VideoView simpleVideoView;
    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_details);

        Intent intent = getIntent();
        String exerciseNameStr = intent.getStringExtra("exerciseName");
        String exerciseDescriptionStr = intent.getStringExtra("exerciseDescription");
        String exerciseCaloriesStr = intent.getStringExtra("exerciseCalories");
        String exerciseTimeStr = intent.getStringExtra("exerciseTime");
        String exerciseImageStr = intent.getStringExtra("exerciseImage");
        String exerciseVideoStr = intent.getStringExtra("exerciseVideo");

        exerciseName = findViewById(R.id.exerciseName);
        exerciseName.setText(exerciseNameStr);
        exerciseDescription = findViewById(R.id.exerciseDescription);
        exerciseDescription.setText(exerciseDescriptionStr);
        exerciseTime = findViewById(R.id.exerciseTime);
        exerciseTimeStr += " seconds";
        exerciseTime.setText(exerciseTimeStr);
        exerciseCalories = findViewById(R.id.exerciseCalories);
        exerciseCaloriesStr += " kCal";
        exerciseCalories.setText(exerciseCaloriesStr);

        // play video in video view
        simpleVideoView = findViewById(R.id.simpleVideoView);
        simpleVideoView.setVideoPath(exerciseVideoStr);
        simpleVideoView.start();

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}