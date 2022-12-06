package com.example.i190417_i190468_i190260;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.i190417_i190468_i190260.Adapters.FragmentsAdapter;
import com.google.android.material.tabs.TabLayout;

public class ExerciseMainScreen extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_main_screen);

        tabLayout=findViewById(R.id.tabLayout);
        viewPager=findViewById(R.id.viewPager);

        viewPager.setAdapter(new FragmentsAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_outline_home_24);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_baseline_fitness_center_24);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_baseline_timer_grey_24);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_baseline_history_24);
    }


}