package com.example.i190417_i190468_i190260;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class All_Work_Outs extends AppCompatActivity {
    LinearLayout ex_1,ex_2,ex_3,ex_4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_work_outs);

        ex_1=findViewById(R.id.ex_1);
        ex_2=findViewById(R.id.ex_2);
        ex_3=findViewById(R.id.ex_3);
        ex_4=findViewById(R.id.ex_4);

        ex_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(All_Work_Outs.this,WorkoutPage.class));
            }
        });

        ex_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(All_Work_Outs.this,WorkoutPage.class));
            }
        });

        ex_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(All_Work_Outs.this,WorkoutPage.class));
            }
        });

        ex_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(All_Work_Outs.this,WorkoutPage.class));
            }
        });

    }
}